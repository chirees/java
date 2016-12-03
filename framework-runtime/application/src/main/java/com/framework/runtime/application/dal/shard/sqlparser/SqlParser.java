package com.framework.runtime.application.dal.shard.sqlparser;

import java.util.List;

/**
 * Created by lipeng on 16/2/4.
 */
public interface SqlParser {

    void init(String sql) ;

    String getTableName();

    SqlType getType();

    List<String> getWhereColumns();

    boolean setTableName(String tableName);

    List<ColumnValue> getColumns();

    String toSql();

    String getSqlOriginal();
}
