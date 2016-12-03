package com.framework.runtime.application.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.configuration.AbstractConfiguration;
import org.apache.commons.configuration.CombinedConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationConverter;
import org.apache.commons.configuration.DefaultConfigurationBuilder;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import com.framework.runtime.application.Application;


/**
 * 
 * @author xiongliang
 *
 */
public class ConfigurationBean implements InitializingBean, FactoryBean<Object> {
	private static Logger logger = LoggerFactory.getLogger(ConfigurationBean.class);
	
	private Configuration  configuration;
	private Properties properties;
	private String configurationPath = null;
	
	private static final String ENV_TYPE = "env";
	private static final String APP_CODE = "app.code";
	private static final String APP_NAME = "app.name";
	
	@Override
	public Object getObject() throws Exception {
		return this.properties;
	}

	@Override
	public Class<?> getObjectType() {
		return java.util.Properties.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if(StringUtils.isEmpty(this.configurationPath)) {
			throw new java.lang.IllegalArgumentException("Main config can not be null!");
		}
		
		DefaultConfigurationBuilder builder = new DefaultConfigurationBuilder(configurationPath);
		CombinedConfiguration config = builder.getConfiguration(true);
				
		String envType = System.getenv(ENV_TYPE);
		if(StringUtils.isEmpty(envType)) {
			logger.warn("no env config properties file exist, use default dev env!!!!!");
			envType = "rd";
		}
		
		StringBuffer sb = new StringBuffer();
		sb.append("\n");
		sb.append("*********************************************************************************");
		sb.append("\n");
		sb.append("                            System env is [" + envType + "]                      ");
		sb.append("\n");
		sb.append("*********************************************************************************");
		sb.append("\n");
		logger.info(sb.toString());
		
		List<AbstractConfiguration> configs = config.getConfigurations();
		
		List<Configuration> commonConfigs = new ArrayList();
		
		boolean matchEnv = false;
		for(AbstractConfiguration c:configs) {
			String cType = c.getString(ENV_TYPE);
			
			if(cType != null) {
				if(!envType.equalsIgnoreCase(cType))
					config.removeConfiguration(c);
				else {
					matchEnv = true;
					config.addProperty("disconf.env", envType);
				}
			}
		}
		
		if(!matchEnv) {
			throw new java.lang.IllegalArgumentException("[" + ENV_TYPE + "] config properties file is not exist!");
		}
		
		configuration = config;		
		this.properties = ConfigurationConverter.getProperties(config); 
		logger.info(this.properties.toString());
		String appCode = this.properties.getProperty(APP_CODE);
		String appName = this.properties.getProperty(APP_NAME);
		
		this.properties.put("disconf.app", appName);
		
		Application.getInstance().setCode(appCode);
		Application.getInstance().setName(appName);
		LoggerFactory.getLogger(Application.getInstance().getRuntimeLogger()).info("=============================== config start ============================");;
	}

	public String getConfigurationPath() {
		return configurationPath;
	}

	public void setConfigurationPath(String configurationPath) {
		this.configurationPath = configurationPath;
	}
	
	

}
