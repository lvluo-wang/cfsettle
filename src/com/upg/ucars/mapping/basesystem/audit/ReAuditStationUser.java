package com.upg.ucars.mapping.basesystem.audit;

import java.io.Serializable;
/**
 * 审批岗位与用户关联
 * 
 * @author shentuwy
 * @date 2011-3-31 下午01:01:30
 *
 */
public class ReAuditStationUser implements Serializable {
	
	private static final long serialVersionUID = -355522985762156399L;
	/** 主键 */
	private Long id;
	/** 审批岗位id */
	private Long auditStationId;
	/** 用户id */
	private Long userId;
	/** 版本号 */
	private Long version;
	
	public ReAuditStationUser(){}

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((auditStationId == null) ? 0 : auditStationId.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
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
		ReAuditStationUser other = (ReAuditStationUser) obj;
		if (auditStationId == null) {
			if (other.auditStationId != null)
				return false;
		} else if (!auditStationId.equals(other.auditStationId))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}

	public void setAuditStationId(Long auditStationId) {
		this.auditStationId = auditStationId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
}
