package com.framework.runtime.application.mvc;

import com.framework.runtime.application.dal.BaseEntity;

public class AccountWrapper extends BaseEntity implements java.io.Serializable
{

	// columns START
	/* id */
	private Long id;
	/* 服务商机构号 */
	private String orgNo;
	/* 登录帐号 */
	private String account;
	/* 角色编码 */
	private String roleCode;
	/* 姓名 */
	private String name;
	/* 状态 */
	private String status;
	private String orgName;// 服务商简称
	private String isAdmin;// 是否为管理员
	private String orgLevel;

	public AccountWrapper()
	{
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getOrgNo()
	{
		return orgNo;
	}

	public void setOrgNo(String orgNo)
	{
		this.orgNo = orgNo;
	}

	public String getAccount()
	{
		return account;
	}

	public void setAccount(String account)
	{
		this.account = account;
	}

	public String getRoleCode()
	{
		return roleCode;
	}

	public void setRoleCode(String roleCode)
	{
		this.roleCode = roleCode;
	}

	public String getOrgLevel() {
		return orgLevel;
	}

	public void setOrgLevel(String orgLevel) {
		this.orgLevel = orgLevel;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getOrgName()
	{
		return orgName;
	}

	public void setOrgName(String orgName)
	{
		this.orgName = orgName;
	}

	public String getIsAdmin()
	{
		return isAdmin;
	}

	public void setIsAdmin(String isAdmin)
	{
		this.isAdmin = isAdmin;
	}

}
