package com.upg.ucars.basesystem.audit.core;

import java.io.Serializable;
import java.util.Date;
/**
 * 审批历史查询实体bean
 * 功能说明：TODO(这里用一句话描述这个类的作用)
 * @author shentuwy  
 * @date 2011-6-8 下午01:54:10 
 *
 */
public class AuditHistSearchBean implements Serializable {
	private Long prodId;//产品id
	private Long brchId;//机构id
	private Boolean containChild=false;//包含下级
	private Long auditActor;//审批参与者
	private Boolean containBusi=true;//包含业务（自己发起的业务）
	private Boolean ContainAudit=true;//包含审批（自己审批别人的）
	private Date startDate;//发生起始日
	private Date endDate;//发生结束日
	private String status;//审批状态
	public AuditHistSearchBean(){}
	public Long getProdId() {
		return prodId;
	}
	public void setProdId(Long prodId) {
		this.prodId = prodId;
	}
	public Long getBrchId() {
		return brchId;
	}
	public void setBrchId(Long brchId) {
		this.brchId = brchId;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Boolean getContainChild() {
		return containChild;
	}
	public void setContainChild(Boolean containChild) {
		this.containChild = containChild;
	}
	public Long getAuditActor() {
		return auditActor;
	}
	public void setAuditActor(Long auditActor) {
		this.auditActor = auditActor;
	}
	public Boolean getContainBusi() {
		return containBusi;
	}
	public void setContainBusi(Boolean containBusi) {
		this.containBusi = containBusi;
	}
	public Boolean getContainAudit() {
		return ContainAudit;
	}
	public void setContainAudit(Boolean containAudit) {
		ContainAudit = containAudit;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}
