package com.upg.ucars.mapping.basesystem.audit;

import java.io.Serializable;
/**
 * 审批路线与机构关联
 * 
 * @author shentuwy
 * @date 2011-3-31 下午01:40:36
 *
 */
public class ReAuditRouteBranch implements Serializable {
	private static final long serialVersionUID = 2166516809159306648L;
	/** 主键 */
	private Long id;
	/** 审批路线id */
	private Long auditRouteid;
	/** 机构id */
	private Long brchId;
	/** 版本号 */
	private Long version;
	public ReAuditRouteBranch(){}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}


	public Long getAuditRouteid() {
		return auditRouteid;
	}
	public void setAuditRouteid(Long auditRouteid) {
		this.auditRouteid = auditRouteid;
	}
	public Long getBrchId() {
		return brchId;
	}
	public void setBrchId(Long brchId) {
		this.brchId = brchId;
	}
	public Long getVersion() {
		return version;
	}
	public void setVersion(Long version) {
		this.version = version;
	}
	
}
