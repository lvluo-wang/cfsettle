package com.upg.ucars.mapping.framework.bpm;

/**
 * HumnTaskActr generated by MyEclipse - Hibernate Tools
 */

public class HumnTaskActr  implements java.io.Serializable {
     private Long id;
     private Long taskId;
     private Long brchId;
     
     private Long actrBrchId;
     private Long actrRoleId;
     private Long actrUserId;
     private String status;
     
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getTaskId() {
		return taskId;
	}
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	public Long getBrchId() {
		return brchId;
	}
	public void setBrchId(Long brchId) {
		this.brchId = brchId;
	}
	public Long getActrBrchId() {
		return actrBrchId;
	}
	public void setActrBrchId(Long actrBrchId) {
		this.actrBrchId = actrBrchId;
	}
	public Long getActrRoleId() {
		return actrRoleId;
	}
	public void setActrRoleId(Long actrRoleId) {
		this.actrRoleId = actrRoleId;
	}
	public Long getActrUserId() {
		return actrUserId;
	}
	public void setActrUserId(Long actrUserId) {
		this.actrUserId = actrUserId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
     
}