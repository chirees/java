package com.framework.runtime.application.mvc;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.framework.runtime.application.redis.RedisService;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateNumberModel;
import freemarker.template.TemplateScalarModel;

public class PermissionTemplateDirectiveModel implements TemplateDirectiveModel
{

	@Autowired
	private HttpServletRequest request;
	@Autowired
	private RedisService redisService;

	private String accountAuthSessionName;

	public String getAccountAuthSessionName()
	{
		return accountAuthSessionName;
	}

	public void setAccountAuthSessionName(String accountAuthSessionName)
	{
		this.accountAuthSessionName = accountAuthSessionName;
	}

	/**
	* (not Javadoc)
	* Title: execute
	* Description:当模板页面遇到用户自定义的标签指令时，该方法会被执行
	* 
	* @param env 系统环境变量，通常用它来输出相关内容，如Writer out = env.getOut();
	* 
	* @param params 自定义标签传过来的对象，其key=自定义标签的参数名，value值是TemplateModel类型，
	* 而TemplateModel是一个接口类型，通常我们都使用TemplateScalarModel接口来替代它获取一个String值，
	* 如TemplateScalarModel.getAsString();当然还有其它常用的替代接口，
	* 如TemplateNumberModel获取number，TemplateHashModel等
	* 
	* @param loopVars 循环替代变量
	* 
	* @param body 用于处理自定义标签中的内容
	* 
	* @throws TemplateException
	* @throws IOException
	* @see freemarker.template.TemplateDirectiveModel#execute(freemarker.core.Environment, java.util.Map, freemarker.template.TemplateModel[], freemarker.template.TemplateDirectiveBody)
	*/
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException
	{
		Map permissTag = getPermissionTag(PermissionEnum.PERMISSION_MODEL_NAME.getValue(), PermissionEnum.PERMISSION_VALUE_NAME.getValue(), params, env);
		String m = (String) permissTag.get(PermissionEnum.PERMISSION_MODEL_NAME.getValue());
		String v = (String) permissTag.get(PermissionEnum.PERMISSION_VALUE_NAME.getValue());
		boolean passFlag = false;
		// 无自定义权限标签
		if (StringUtils.isBlank(m) && StringUtils.isBlank(v))
		{
			passFlag = true;
		}
		else
		{
			// 有自定义权限标签
			// 先从session中获取用户资源
			HttpSession session = request.getSession(false);
			AccountAuthWrapper authWrapper = (AccountAuthWrapper) session.getAttribute(accountAuthSessionName);
			if (null == authWrapper || null == authWrapper.getResourceWrapper())
			{
				// redis中获取资源
				authWrapper = (AccountAuthWrapper) redisService.getJson(session.getId(), AccountAuthWrapper.class);
			}
			if ((null != authWrapper) && (null != authWrapper.getResourceWrapper()))
			{
				List<ResourceWrapper> resources = authWrapper.getResourceWrapper().getResources();
				for (ResourceWrapper resource : resources)
				{
					if (resource.getModel().equals(m) && resource.getValue().equals(v))
					{
						passFlag = true;
						break;
					}
				}
			}
		}
		if (passFlag)
		{
			body.render(env.getOut());
		}
	}

	/**
	* @Title: getPermissionTag
	* @Description: 获取自定义权限标签属性
	* @param modelStr
	* @param valueStr
	* @param params
	* @param env
	* @return
	* @throws TemplateException param
	* @return Map return type
	* @throws
	*/
	private Map getPermissionTag(String modelStr, String valueStr, Map<String, TemplateModel> params, Environment env) throws TemplateException
	{
		Map permissionTag = new HashMap();
		TemplateModel m = params.get(modelStr);
		TemplateModel v = params.get(valueStr);
		String model = "", value = "";
		if (m instanceof TemplateScalarModel)
		{
			model = ((TemplateScalarModel) m).getAsString();
			value = ((TemplateScalarModel) v).getAsString();
		}
		else if ((m instanceof TemplateNumberModel))
		{
			model = ((TemplateNumberModel) m).getAsNumber().toString();
			value = ((TemplateNumberModel) v).getAsNumber().toString();
		}
		permissionTag.put(PermissionEnum.PERMISSION_MODEL_NAME.getValue(), model);
		permissionTag.put(PermissionEnum.PERMISSION_VALUE_NAME.getValue(), value);
		return permissionTag;
	}

}
