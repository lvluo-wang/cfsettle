package com.upg.ucars.mapping.basesystem.security;



/**
 * Role entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Role implements java.io.Serializable {

	private static final long serialVersionUID = -5544396801741870302L;
	
	/** ID */
	private Long roleId;
	/** 名称 */
	private String roleName;
	/** 状态 */
	private String roleStatus;
	/** 类型 1：实施 2：saas管理 3：saas运维 4：总部管理 5：机构管理 6：普通,7:评委 */
	private String roleType;
	/** 机构ID */
	private Long brchId;
	/** 备注 */
	private String remark;
	/** 版本 */
	private Integer version;

	// Constructors

	/** default constructor */
	public Role() {
	}


	public Role(Long roleId, String roleName, String roleStatus,
			String roleType, Long brchId, String remark) {
		super();
		this.roleId = roleId;
		this.roleName = roleName;
		this.roleStatus = roleStatus;
		this.roleType = roleType;
		this.brchId = brchId;
		this.remark = remark;
	}


	// Property accessors

	public Long getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}


	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleStatus() {
		return this.roleStatus;
	}

	public void setRoleStatus(String roleStatus) {
		this.roleStatus = roleStatus;
	}

	public String getRoleType() {
		return this.roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public Long getBrchId() {
		return this.brchId;
	}

	public void setBrchId(Long brchId) {
		this.brchId = brchId;
	}


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
		    return true;
		}
		
		if (obj instanceof Role){
			Role r = (Role)obj;
			return this.getRoleId().equals(r.getRoleId());
		}
		
		return false;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}


	public Integer getVersion() {
		return version;
	}


	public void setVersion(Integer version) {
		this.version = version;
	}

}