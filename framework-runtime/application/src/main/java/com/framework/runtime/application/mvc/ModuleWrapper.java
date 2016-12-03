package com.framework.runtime.application.mvc;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import com.framework.runtime.application.dal.BaseEntity;
import com.framework.runtime.application.util.DateConvertUtils;



public class ModuleWrapper extends BaseEntity implements java.io.Serializable{
	
	//columns START
	/*id*/
	private Long id;
	/*parentId*/
	private Long parentId;
	/*模块或菜单名称*/
	private String name;
	/*url*/
	private String url;
	/*是否为菜单*/
	private String isMenu;
	/*顺序*/
	private String priority;
	/*iconClass*/
	private String iconClass;
	/*状态，0：禁用 1：可用*/
	private String status;
	/*remark*/
	private String remark;
	/*cuser*/
	private String cuser;
	/*ctime*/
	private java.util.Date ctime;
	private java.util.Date ctimeBegin;
	private java.util.Date ctimeEnd;
	/*uuser*/
	private String uuser;
	/*utime*/
	private java.util.Date utime;
	private java.util.Date utimeBegin;
	private java.util.Date utimeEnd;
	//columns END

	public ModuleWrapper(){
	}

	public ModuleWrapper(
		java.lang.Long id
	){
		this.id = id;
	}

	public void setId(Long value) {
		this.id = value;
	}
	
	public Long getId() {
		return this.id;
	}
	public void setParentId(Long value) {
		this.parentId = value;
	}
	
	public Long getParentId() {
		return this.parentId;
	}
	public void setName(String value) {
		this.name = value;
	}
	
	public String getName() {
		return this.name;
	}
	public void setUrl(String value) {
		this.url = value;
	}
	
	public String getUrl() {
		return this.url;
	}
	public void setIsMenu(String value) {
		this.isMenu = value;
	}
	
	public String getIsMenu() {
		return this.isMenu;
	}
	public void setPriority(String value) {
		this.priority = value;
	}
	
	public String getPriority() {
		return this.priority;
	}
	public void setIconClass(String value) {
		this.iconClass = value;
	}
	
	public String getIconClass() {
		return this.iconClass;
	}
	public void setStatus(String value) {
		this.status = value;
	}
	
	public String getStatus() {
		return this.status;
	}
	public void setRemark(String value) {
		this.remark = value;
	}
	
	public String getRemark() {
		return this.remark;
	}
	public void setCuser(String value) {
		this.cuser = value;
	}
	
	public String getCuser() {
		return this.cuser;
	}
	
	public void setCtimeBegin(java.util.Date value) {
		this.ctimeBegin = value;
	}
	
	public java.util.Date getCtimeBegin() {
		return this.ctimeBegin;
	}

	public void setCtimeEnd(java.util.Date value) {
		this.ctimeEnd = value;
	}
	
	public java.util.Date getCtimeEnd() {
		return this.ctimeEnd;
	}

	public void setCtime(java.util.Date value) {
		this.ctime = value;
	}
	
	public java.util.Date getCtime() {
		return this.ctime;
	}
	public void setUuser(String value) {
		this.uuser = value;
	}
	
	public String getUuser() {
		return this.uuser;
	}
	
	public void setUtimeBegin(java.util.Date value) {
		this.utimeBegin = value;
	}
	
	public java.util.Date getUtimeBegin() {
		return this.utimeBegin;
	}

	public void setUtimeEnd(java.util.Date value) {
		this.utimeEnd = value;
	}
	
	public java.util.Date getUtimeEnd() {
		return this.utimeEnd;
	}

	public void setUtime(java.util.Date value) {
		this.utime = value;
	}
	
	public java.util.Date getUtime() {
		return this.utime;
	}


	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.DEFAULT_STYLE)
			.append("Id",getId())
			.append("ParentId",getParentId())
			.append("Name",getName())
			.append("Url",getUrl())
			.append("IsMenu",getIsMenu())
			.append("Priority",getPriority())
			.append("IconClass",getIconClass())
			.append("Status",getStatus())
			.append("Remark",getRemark())
			.append("Cuser",getCuser())
			.append("Ctime",getCtime())
			.append("Uuser",getUuser())
			.append("Utime",getUtime())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof ModuleWrapper == false) return false;
		if(this == obj) return true;
		ModuleWrapper other = (ModuleWrapper)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

