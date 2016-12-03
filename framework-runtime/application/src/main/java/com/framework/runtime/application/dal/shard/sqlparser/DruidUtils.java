package com.framework.runtime.application.dal.shard.sqlparser;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOpExpr;
import com.alibaba.druid.sql.ast.expr.SQLIdentifierExpr;
import com.alibaba.druid.sql.ast.expr.SQLInListExpr;
import com.alibaba.druid.sql.ast.statement.SQLDeleteStatement;
import com.alibaba.druid.sql.ast.statement.SQLExprTableSource;
import com.alibaba.druid.sql.ast.statement.SQLInsertStatement;
import com.alibaba.druid.sql.ast.statement.SQLSelectQueryBlock;
import com.alibaba.druid.sql.ast.statement.SQLSelectStatement;
import com.alibaba.druid.sql.ast.statement.SQLUpdateSetItem;
import com.alibaba.druid.sql.ast.statement.SQLUpdateStatement;
import com.alibaba.druid.util.JdbcUtils;
import com.framework.runtime.application.util.Consumer;
import com.google.common.collect.Lists;

/**
 * Created by lipeng on 16/2/4.
 */
public class DruidUtils {

    public static List<SQLStatement> parse(String sql) {
        /**
         SQLStatementParser parser = SQLParserUtils.createSQLStatementParser(sql, JdbcUtils.MYSQL);
         return parser.parseStatementList();
         */
        return SQLUtils.toStatementList(sql, JdbcUtils.MYSQL);
    }

    public static String getTableName(SQLStatement stmt) {
        SQLExpr re = getTableNameExpr(stmt);
        return re == null ? null : re.toString();
    }

    public static boolean setTableName(SQLStatement stmt, String tableName) {
        SQLExpr re = getTableNameExpr(stmt);
        if (re != null) {
            setName(re, tableName);
            return true;
        }
        return false;
    }

    private static SQLExpr getTableNameExpr(SQLStatement stmt) {
        if (stmt instanceof SQLSelectStatement) {
            SQLSelectStatement s = (SQLSelectStatement) stmt;
            SQLSelectQueryBlock query = (SQLSelectQueryBlock) s.getSelect().getQuery();
            SQLExprTableSource re = (SQLExprTableSource) query.getFrom();
            return re == null ? null : re.getExpr();
        } else if (stmt instanceof SQLUpdateStatement) {
            return ((SQLUpdateStatement) stmt).getTableName();
        } else if (stmt instanceof SQLInsertStatement) {
            return ((SQLInsertStatement) stmt).getTableName();
        } else if (stmt instanceof SQLDeleteStatement) {
            return ((SQLDeleteStatement) stmt).getTableName();
        }
        return null;
    }

    private static void setName(SQLExpr SQLExpr, String name) {
        ((SQLIdentifierExpr) SQLExpr).setName(name);
    }

    /***
     * get where , except delete
     *
     * @param stmt
     * @return
     */
    public static SQLExpr getWhere(SQLStatement stmt) {
        if (stmt instanceof SQLSelectStatement) {
            SQLSelectStatement s = (SQLSelectStatement) stmt;
            SQLSelectQueryBlock query = (SQLSelectQueryBlock) s.getSelect().getQuery();
            return query.getWhere();
        } else if (stmt instanceof SQLUpdateStatement) {
            return ((SQLUpdateStatement) stmt).getWhere();
        } else if (stmt instanceof SQLDeleteStatement) {
            return ((SQLDeleteStatement) stmt).getWhere();
        }
        return null;
    }

    public static List<String> getColNamesFromWhere(SQLExpr where) {
        final List<String> re = Lists.newLinkedList();
        flatSqlExpr(where, new Consumer<SQLExpr>() {

			@Override
			public void accept(SQLExpr t) {
				if (t instanceof SQLIdentifierExpr)
	                re.add(t.toString());
			}
        	
        });
        return re;
    }


    public static List<ColumnValue> getColumns(SQLStatement stmt) {
        if (stmt instanceof SQLInsertStatement) {
            SQLInsertStatement insertStatement = (SQLInsertStatement) stmt;
            List<SQLExpr> cols = insertStatement.getColumns();
            List<ColumnValue> re = new ArrayList(cols.size());
            for (int i = 0; i < cols.size(); i++)
                re.add(new ColumnValue(cols.get(i).toString(), 1));
            return re;
        }
        if (stmt instanceof SQLUpdateStatement) {
        	List<ColumnValue> re = new ArrayList();
        	List<SQLUpdateSetItem> items = ((SQLUpdateStatement) stmt).getItems();
        	for(SQLUpdateSetItem item:items) {
        		re.add(new ColumnValue(item.getColumn().toString(), 1));
        	}
        	
//            List<ColumnValue> re = ((SQLUpdateStatement) stmt).getItems().stream()
//                    .map(sqlUpdateSetItem -> new ColumnValue(sqlUpdateSetItem.getColumn().toString(), 1))
//                    .collect(Collectors.toList());
            re.addAll(getColumnsFromWhere(getWhere(stmt)));
            return re;
        }
        return getColumnsFromWhere(getWhere(stmt));
    }


    public static List<ColumnValue> getColumnsFromWhere(SQLExpr where) {
        final List<ColumnValue> re = Lists.newLinkedList();
        flatSqlExpr(where, new Consumer<SQLExpr>() {

			@Override
			public void accept(SQLExpr t) {
				re.add(calValueCount(t));
			}
        	
        });
        return re;
    }

//    @Deprecated
//    /***
//     * SQLUtils.split has issues
//     */
//    static List<ColumnValue> getColumnsFromWhereOld(SQLExpr where) {
//        return Lists.reverse(SQLUtils.split((SQLBinaryOpExpr) where).stream()
//                .map(sqlExpr -> calValueCount(sqlExpr))
//                .collect(Collectors.toList()));
//    }

    private static ColumnValue calValueCount(SQLExpr sqlExpr) {
        if (sqlExpr instanceof SQLInListExpr) {
            SQLInListExpr in = (SQLInListExpr) sqlExpr;
            return new ColumnValue(in.getExpr().toString(), in.getTargetList().size());
        } else if (sqlExpr instanceof SQLBinaryOpExpr) {
            return new ColumnValue(((SQLBinaryOpExpr) sqlExpr).getLeft().toString(), 1);
        }
        return null;
    }


    private static void flatSqlExpr(SQLExpr sqlExpr, Consumer<SQLExpr> re) {
        if (sqlExpr instanceof SQLBinaryOpExpr) {
            SQLBinaryOpExpr binaryOpExpr = (SQLBinaryOpExpr) sqlExpr;
            if (isColumnOperator(binaryOpExpr)) {
                re.accept(sqlExpr);
                return;
            }
            flatSqlExpr(binaryOpExpr.getLeft(), re);
            flatSqlExpr(binaryOpExpr.getRight(), re);
        } else
            re.accept(sqlExpr);
    }

    private static boolean isColumnOperator(SQLBinaryOpExpr binaryOpExpr) {
        switch (binaryOpExpr.getOperator()) {
            case Equality:
            case GreaterThan:
            case GreaterThanOrEqual:
            case NotEqual:
            case LessThan:
            case LessThanOrEqual:
            case LessThanOrEqualOrGreaterThan:
            case Like:
            case NotLike:
                return true;
        }
        return false;
    }

}
