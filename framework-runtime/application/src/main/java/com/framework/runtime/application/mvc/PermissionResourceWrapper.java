
package com.framework.runtime.application.mvc;

import java.io.Serializable;
import java.util.List;

public class PermissionResourceWrapper implements Serializable
{
	private List<MenuWrapper> menus;
	private List<ResourceWrapper> resources;

	public List<MenuWrapper> getMenus()
	{
		return menus;
	}

	public void setMenus(List<MenuWrapper> menus)
	{
		this.menus = menus;
	}

	public List<ResourceWrapper> getResources()
	{
		return resources;
	}

	public void setResources(List<ResourceWrapper> resources)
	{
		this.resources = resources;
	}

}
