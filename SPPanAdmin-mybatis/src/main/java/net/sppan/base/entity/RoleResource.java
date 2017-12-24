package net.sppan.base.entity;

import javax.persistence.Table;

import net.sppan.base.entity.support.BaseEntity;

@Table(name = "tb_role_resource")
public class RoleResource extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer roleId;
	private Integer resourceId;
	
	
	public RoleResource() {
		super();
	}
	public RoleResource(Integer roleId, Integer resourceId) {
		super();
		this.roleId = roleId;
		this.resourceId = resourceId;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public Integer getResourceId() {
		return resourceId;
	}
	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}
	
	
}
