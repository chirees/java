/**
* @Project: framework-runtime-application
* @Package com.framework.runtime.application.mvc
* FileName：PermissionEnum.java
* Version：v1.0
* date：2016年4月12日
* Copyright © 2016 Shanghai Deyi Network Technology Co.,Ltd All Rights Reserved
*/

package com.framework.runtime.application.mvc;

/**
* FileName: PermissionEnum.java
* @author: Hubert 
* @version: v1.0
* @create at: 2016年4月12日 下午2:20:08
* @reviewer:
* @review at:
*
* Revision history:
* date        author      version     content
* ------------------------------------------------------------
* 2016年4月12日    Hubert    v1.0        XXXX
*
* Copyright © 2016 Shanghai Deyi Network Technology Co.,Ltd All Rights Reserved
*/
public enum PermissionEnum
{

	PERMISSION_MODEL_NAME("model"), PERMISSION_VALUE_NAME("value");
	private PermissionEnum(String value)
	{
		this.value = value;
	}

	private String value;

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

}
