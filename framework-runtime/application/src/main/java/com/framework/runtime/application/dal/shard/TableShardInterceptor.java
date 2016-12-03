package com.framework.runtime.application.dal.shard;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.MappedStatement.Builder;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import com.framework.runtime.application.LogU;
import com.framework.runtime.application.util.ReflectUtil;

@Intercepts({
    @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
    @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
    @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class})
})
public class TableShardInterceptor implements Interceptor {

	static int MAPPED_STATEMENT_INDEX = 0;
	static int PARAMETER_INDEX = 1;
	static int ROWBOUNDS_INDEX = 2;
	static int RESULT_HANDLER_INDEX = 3;
	
	private ShardRouter shardRouter = new ShardRouter();
	
	private MappedStatement copyFromNewSql(MappedStatement ms,
			BoundSql boundSql, String sql) {
		BoundSql newBoundSql = copyFromBoundSql(ms, boundSql, sql);
		return copyFromMappedStatement(ms, new BoundSqlSqlSource(newBoundSql));
	}

	private BoundSql copyFromBoundSql(MappedStatement ms, BoundSql boundSql,
			String sql) {
		BoundSql newBoundSql = new BoundSql(ms.getConfiguration(),sql, boundSql.getParameterMappings(), boundSql.getParameterObject());
		for (ParameterMapping mapping : boundSql.getParameterMappings()) {
		    String prop = mapping.getProperty();
		    if (boundSql.hasAdditionalParameter(prop)) {
		        newBoundSql.setAdditionalParameter(prop, boundSql.getAdditionalParameter(prop));
		    }
		}
		return newBoundSql;
	}

	// see: MapperBuilderAssistant
	private MappedStatement copyFromMappedStatement(MappedStatement ms, SqlSource newSqlSource) {
		Builder builder = new Builder(ms.getConfiguration(), ms.getId(), newSqlSource, ms.getSqlCommandType());

		builder.resource(ms.getResource());
		builder.fetchSize(ms.getFetchSize());
		builder.statementType(ms.getStatementType());
		builder.keyGenerator(ms.getKeyGenerator());
		if (ms.getKeyProperties() != null && ms.getKeyProperties().length != 0) {
			StringBuffer keyProperties = new StringBuffer();
			for (String keyProperty : ms.getKeyProperties()) {
				keyProperties.append(keyProperty).append(",");
			}
			keyProperties.delete(keyProperties.length() - 1, keyProperties.length());
			builder.keyProperty(keyProperties.toString());
		}

		// setStatementTimeout()
		builder.timeout(ms.getTimeout());

		// setStatementResultMap()
		builder.parameterMap(ms.getParameterMap());

		// setStatementResultMap()
		builder.resultMaps(ms.getResultMaps());
		builder.resultSetType(ms.getResultSetType());

		// setStatementCache()
		builder.cache(ms.getCache());
		builder.flushCacheRequired(ms.isFlushCacheRequired());
		builder.useCache(ms.isUseCache());

		return builder.build();
	}
	
	
	private String getSql(String sqlId, String sql) {
		
		final StringBuffer bufferSql = new StringBuffer();
		char[] chars = sql.toCharArray();
		int lastSpace = 0;
		for(int i = 0; i < chars.length; i++) {
			if(chars[i] == '\n' || chars[i] == '\t' || chars[i] == ' ') {
				if(lastSpace == 0) {
					bufferSql.append(' ');
				}
				lastSpace += 1;
			}
			else {
				bufferSql.append(chars[i]);
				lastSpace = 0;
			}
		}
		if (bufferSql.lastIndexOf(";") == bufferSql.length() - 1) {
			bufferSql.deleteCharAt(bufferSql.length() - 1);
		}
		
		String formatSql = bufferSql.toString();
		
		return formatSql;
	}

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		final Object[] queryArgs = invocation.getArgs();
		final MappedStatement ms = (MappedStatement) queryArgs[MAPPED_STATEMENT_INDEX];
		final Object parameter = queryArgs[PARAMETER_INDEX];

		final BoundSql boundSql = ms.getBoundSql(parameter);

		String sql = getSql(ms.getId(), boundSql.getSql());
	
		LogU.rn(this, "SQL:", sql);
		
		
		List jdbcArgs = JdbcParameterHandler.getParameters(ms, parameter, boundSql);
		
		LogU.r(this, "PARAMS:", jdbcArgs != null ? jdbcArgs.toString() : "[]");
		
		String[] sqls = shardRouter.route(ms.getId(), sql, jdbcArgs);
		
		if(sqls != null) {
			queryArgs[MAPPED_STATEMENT_INDEX] = copyFromNewSql(ms, boundSql, sqls[0]);
		}
		
		return invocation.proceed();
	}
	
	 private void setParameters(PreparedStatement ps, MappedStatement mappedStatement, BoundSql boundSql,
	            Object parameterObject) throws SQLException {
	        ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, parameterObject, boundSql);
	        parameterHandler.setParameters(ps);
	    }
	
	private List<Object> executeSql(String sql, MappedStatement mappedStatement,
            BoundSql boundSql) throws SQLException {
		
		Connection connection = null;
        PreparedStatement exStmt = null;
        ResultSet rs = null;
        try {

        	connection = mappedStatement.getConfiguration().getEnvironment().getDataSource().getConnection();
       
        	exStmt = connection.prepareStatement(sql);
            BoundSql exBoundSql = new BoundSql(mappedStatement.getConfiguration(), sql,
                    boundSql.getParameterMappings(), boundSql.getParameterObject());
            
            Field metaParamsField = ReflectUtil.getFieldByFieldName(boundSql, "metaParameters");
            if (metaParamsField != null) {
                MetaObject mo = (MetaObject) ReflectUtil.getValueByFieldName(boundSql, "metaParameters");
                ReflectUtil.setValueByFieldName(exBoundSql, "metaParameters", mo);
            }
            setParameters(exStmt, mappedStatement, exBoundSql, boundSql.getParameterObject());
            
            rs = exStmt.executeQuery();
            
        } catch (SQLException e) {
            throw e;
        }catch (Exception e) {
           e.printStackTrace();
        } finally {
        	if (rs != null) {
	        	try {
	                rs.close();
	            } catch (SQLException e) {
	            }
        	}
        	
        	if (exStmt != null) {
	            try {
	            	exStmt.close();
	            } catch (SQLException e) {
	            }
        	}
        	
        	if (connection != null) {
	            try {
	            	connection.close();
	            } catch (SQLException e) {
	            }
        	}
        }
        return null;
	}

	@Override
	public Object plugin(Object target) {
		if (Executor.class.isAssignableFrom(target.getClass())) {
			return Plugin.wrap(target, this);
		}
		return target;
	}

	@Override
	public void setProperties(Properties properties) {

	}
	
	public static class BoundSqlSqlSource implements SqlSource {
		BoundSql boundSql;
		public BoundSqlSqlSource(BoundSql boundSql) {
			this.boundSql = boundSql;
		}
		public BoundSql getBoundSql(Object parameterObject) {
			return boundSql;
		}
	}
}
