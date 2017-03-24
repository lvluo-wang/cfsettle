package com.upg.ucars.mapping.basesystem.audit;

import java.io.Serializable;
/**
 * 审批岗位与角色关联
 * 
 * @author shentuwy
 * @date 2011-3-31 下午01:01:30
 *
 */
public class ReAuditStationRole implements Serializable {
	
	private static final long serialVersionUID = -355522985762156399L;
	/** 主键 */
	private Long id;
	/** 审批岗位id */
	private Long auditStationId;
	/** 角色id */
	private Long roleId;
	/** 版本号 */
	private Long version;
	
	public ReAuditStationRole(){}

	public Long getVersion() {
		return version;
	}
	public void setVersion(Long version) {
		this.version = version;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public Long getAuditStationId() {
		return auditStationId;
	}


	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public void setAuditStationId(Long auditStationId) {
		this.auditStationId = auditStationId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((auditStationId == null) ? 0 : auditStationId.hashCode());
		result = prime * result + ((roleId == null) ? 0 : roleId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReAuditStationRole other = (ReAuditStationRole) obj;
		if (auditStationId == null) {
			if (other.auditStationId != null)
				return false;
		} else if (!auditStationId.equals(other.auditStationId))
			return false;
		if (roleId == null) {
			if (other.roleId != null)
				return false;
		} else if (!roleId.equals(other.roleId))
			return false;
		return true;
	}


	
}
