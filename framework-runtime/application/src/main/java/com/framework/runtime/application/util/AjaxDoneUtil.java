/**
* @Project: fmp-agent
* @Package com.freemypay.util
* FileName：AjaxDoneUtil.java
* Version：v1.0
* date：2014-7-7
* Copyright © 2014 Shanghai Deyi Network Technology Co.,Ltd All Rights Reserved
*/

package com.framework.runtime.application.util;

import java.io.Serializable;

import net.sf.json.JSONObject;

/**
* FileName: AjaxDoneUtil.java
* @Description:j-ui框架ajax提交返回结果处理工具类
* @author: Hubert 
* @version: v1.0
* @create at: 2014-7-7 下午4:32:47
* @reviewer:
* @review at:
*
* Revision history:
* date        author      version     content
* ------------------------------------------------------------
* 2014-7-7    Hubert    v1.0        XXXX
*
* Copyright © 2014 Shanghai Deyi Network Technology Co.,Ltd All Rights Reserved
*/
public class AjaxDoneUtil implements Serializable
{
	private static final long serialVersionUID = 7751995051866855734L;

	public static final String STATUS_SUCCESS_CODE = "200";// 操作成功
	public static final String STATUS_FAILIURE_CODE = "300";// 操作失败
	public static final String STATUS_TIMEOUT_CODE = "301";// 会话超时
	public static final String CALLBACKTYPE_CLOSECURRENT = "closeCurrent";
	public static final String CALLBACKTYPE_FORWARD = "forward";
	public static final String CLOSETHENFORWORD = "closeThenForword";

	public static final String STATUS_SUCCESS_MESSAGE = "操作成功！";
	public static final String STATUS_FAILIURE_MESSAGE = "操作失败！";
	public static final String STATUS_TIMEOUT_MESSAGE = "会话超时！";

	private String statusCode;
	private String message;
	private String navTabId;
	private String rel;
	private String callbackType;
	private String forwardUrl;
	private String dialogId;

	public AjaxDoneUtil()
	{
	}

	/**
		Ajax 表单提交后服务器端需要返回以下 json 代码：
		{
		"statusCode":"200",
		"message":"操作成功",
		"navTabId":"",
		"rel":"",
		"callbackType":"closeCurrent",
		"forwardUrl":""
		}
	 */

	/**
	 * 返回对象
	 * @param statusCode 返回码 200、300
	 * 
	 * @param message 返回到页面的信息
	 * 
	 * @param navTabId 需要跳转到tab的rel值
	 * 
	 * @param rel 标签rel
	 * 
	 * @param callbackType 返回后执行的方法类型 如：forward、close
	 * callbackType如果是closeCurrent就会关闭当前tab
	 * 只有callbackType="forward"时需要forwardUrl值
	 * 
	 * @param forwardUrl 返回后继续跳转的目标url
	 */
	public AjaxDoneUtil(String statusCode, String message, String navTabId, String rel, String callbackType, String forwardUrl)
	{
		this.statusCode = statusCode;
		this.message = message;
		this.navTabId = navTabId;
		this.rel = rel;
		this.callbackType = callbackType;
		this.forwardUrl = forwardUrl;
	}

	public AjaxDoneUtil(String statusCode, String message, String callbackType, String forwardUrl, String dialogId)
	{
		this.statusCode = statusCode;
		this.message = message;
		this.callbackType = callbackType;
		this.forwardUrl = forwardUrl;
		this.dialogId = dialogId;
	}

	/**
	 * 只返回操作成功或失败，无回调函数，刷新tab
	 * @param statusCode 返回code
	 * @param message 返回消息
	 * @param navTabId 刷新的标签页ID
	 */
	public AjaxDoneUtil(String statusCode, String message, String navTabId)
	{
		this.statusCode = statusCode;
		this.message = message;
		this.navTabId = navTabId;
	}

	/**
	 * 默认无指定 回调函数、返回数据、不刷新tab
	 * @param statusCode
	 * @param message
	 */
	public AjaxDoneUtil(String statusCode, String message)
	{
		this.statusCode = statusCode;
		this.message = message;
	}

	/**
	* @Title: getJsonAjaxDone
	* @Description:转换为json对象
	* @param ajaxDoneUtil
	* @return param
	* @return String return type
	* @throws
	*/
	public String getJsonAjaxDone(AjaxDoneUtil ajaxDoneUtil)
	{
		return JSONObject.fromObject(ajaxDoneUtil).toString();
	}

	public String getStatusCode()
	{
		return statusCode;
	}

	public AjaxDoneUtil setStatusCode(String statusCode)
	{
		this.statusCode = statusCode;
		return this;
	}

	public String getMessage()
	{
		return message;
	}

	public AjaxDoneUtil setMessage(String message)
	{
		this.message = message;
		return this;
	}

	public String getNavTabId()
	{
		return navTabId;
	}

	public AjaxDoneUtil setNavTabId(String navTabId)
	{
		this.navTabId = navTabId;
		return this;
	}

	public String getRel()
	{
		return rel;
	}

	public AjaxDoneUtil setRel(String rel)
	{
		this.rel = rel;
		return this;
	}

	public String getCallbackType()
	{
		return callbackType;
	}

	public AjaxDoneUtil setCallbackType(String callbackType)
	{
		this.callbackType = callbackType;
		return this;
	}

	public String getForwardUrl()
	{
		return forwardUrl;
	}

	public AjaxDoneUtil setForwardUrl(String forwardUrl)
	{
		this.forwardUrl = forwardUrl;
		return this;
	}

	public String getDialogId()
	{
		return dialogId;
	}

	public AjaxDoneUtil setDialogId(String dialogId)
	{
		this.dialogId = dialogId;
		return this;
	}

}
