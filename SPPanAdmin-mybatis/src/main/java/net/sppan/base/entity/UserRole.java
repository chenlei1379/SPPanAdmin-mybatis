package net.sppan.base.entity;

import javax.persistence.Table;

import net.sppan.base.entity.support.BaseEntity;

@Table(name = "tb_user_role")
public class UserRole extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Integer userId;
	Integer roleId;
	
	
	public UserRole() {
		super();
	}
	
	public UserRole(Integer userId, Integer roleId) {
		super();
		this.userId = userId;
		this.roleId = roleId;
	}

	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	

}
