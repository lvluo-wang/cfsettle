package com.upg.ucars.mapping.basesystem.security;

/**
 * ReRoleSysfunc entity.
 * 
 * @author shentuwy
 */

public class ReRoleSysfunc implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7182712893928235068L;
	/** ID */
	private Long id;
	/** 角色ID */
	private Long roleId;
	/** 权限ID */
	private Long funcId;
	/** 状态 */
	private String status;
	
	/** 有效状态 */
	public static final String STATUS_EFFECTIVE = "1";
	
	public ReRoleSysfunc(){}


	public ReRoleSysfunc(Long roleId, Long funcId, String status) {
		super();
		this.roleId = roleId;
		this.funcId = funcId;
		this.status = status;
	}


	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Long getRoleId() {
		return roleId;
	}


	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}


	public Long getFuncId() {
		return funcId;
	}


	public void setFuncId(Long funcId) {
		this.funcId = funcId;
	};
	
}