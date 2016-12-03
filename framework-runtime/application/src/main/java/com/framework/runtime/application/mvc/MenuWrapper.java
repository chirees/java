    
package com.framework.runtime.application.mvc;

import java.io.Serializable;
import java.util.List;

public class MenuWrapper extends ModuleWrapper implements Serializable
{
	private List<ModuleWrapper> childMenus;
	
	public List<ModuleWrapper> getChildMenus()
	{
		return childMenus;
	}
	public void setChildMenus(List<ModuleWrapper> childMenus)
	{
		this.childMenus = childMenus;
	}
	
}
