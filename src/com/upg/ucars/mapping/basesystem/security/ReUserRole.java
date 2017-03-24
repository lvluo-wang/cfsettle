package com.upg.ucars.mapping.basesystem.security;

/**
 * ReUserRole entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class ReUserRole implements java.io.Serializable {
	private Long id;
	private Long userId;
	private Long roleId;
	private String status;
	public ReUserRole(){};


	public ReUserRole(Long userId, Long roleId, String status) {
		super();
		this.userId = userId;
		this.roleId = roleId;
		this.status = status;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}


	public Long getRoleId() {
		return roleId;
	}


	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}


	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}