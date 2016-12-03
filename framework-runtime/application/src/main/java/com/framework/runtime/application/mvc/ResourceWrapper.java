package com.framework.runtime.application.mvc;

import com.framework.runtime.application.dal.BaseEntity;

public class ResourceWrapper extends BaseEntity implements java.io.Serializable
{

	// columns START
	/* id */
	private Long id;
	/* 资源名称 */
	private String name;
	/* model */
	private String model;
	/* value */
	private String value;
	/* 状态，0：禁用 1：可用 */
	private String status;

	// columns END

	public ResourceWrapper()
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

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getModel()
	{
		return model;
	}

	public void setModel(String model)
	{
		this.model = model;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}
}
