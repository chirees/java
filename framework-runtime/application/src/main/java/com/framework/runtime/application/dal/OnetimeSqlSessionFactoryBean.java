package com.framework.runtime.application.dal;

import java.io.IOException;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OnetimeSqlSessionFactoryBean extends SqlSessionFactoryBean {
	private static Logger logger = LoggerFactory.getLogger(OnetimeSqlSessionFactoryBean.class);
	
	protected SqlSessionFactory buildSqlSessionFactory() throws IOException {
		return super.buildSqlSessionFactory();
	}

	public void afterPropertiesSet() throws Exception {
		try {
			super.afterPropertiesSet();
		} catch (Exception e) {
			logger.error("", e);
			throw e;
		}
	}

}
