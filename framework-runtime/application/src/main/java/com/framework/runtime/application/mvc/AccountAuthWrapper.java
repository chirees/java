/**
* @Project: framework-runtime-application
* @Package com.framework.runtime.application.mvc
* FileName：AccountPermissionWrapper.java
* Version：v1.0
* date：2016年3月21日
* Copyright © 2016 Shanghai Deyi Network Technology Co.,Ltd All Rights Reserved
*/

package com.framework.runtime.application.mvc;

/**
* FileName: AccountAuthWrapper.java
* @author: Hubert 
* @version: v1.0
* @create at: 2016年3月21日 下午7:42:36
* @reviewer:
* @review at:
*
* Revision history:
* date        author      version     content
* ------------------------------------------------------------
* 2016年3月21日    Hubert    v1.0        XXXX
*
* Copyright © 2016 Shanghai Deyi Network Technology Co.,Ltd All Rights Reserved
*/
public class AccountAuthWrapper
{
	private AccountWrapper accountWrapper;
	private PermissionResourceWrapper resourceWrapper;

	public AccountAuthWrapper(AccountWrapper accountWrapper, PermissionResourceWrapper resourceWrapper)
	{
		super();
		this.accountWrapper = accountWrapper;
		this.resourceWrapper = resourceWrapper;
	}

	public AccountWrapper getAccountWrapper()
	{
		return accountWrapper;
	}

	public void setAccountWrapper(AccountWrapper accountWrapper)
	{
		this.accountWrapper = accountWrapper;
	}

	public PermissionResourceWrapper getResourceWrapper()
	{
		return resourceWrapper;
	}

	public void setResourceWrapper(PermissionResourceWrapper resourceWrapper)
	{
		this.resourceWrapper = resourceWrapper;
	}

}
