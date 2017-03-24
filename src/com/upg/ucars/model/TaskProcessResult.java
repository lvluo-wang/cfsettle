package com.upg.ucars.model;

import java.util.Date;

/**
 * 任务处理结果
 * 
 * @author shentuwy
 * @date 2012-7-10
 * 
 */
public class TaskProcessResult {
	
	public static enum ProcessBehavior {
		
		UNDO("undo","撤回"),BACK("back","退回"),JUMP("jump","跳过"),TRANSFOR("transfor","转发");;
		
		private String code;
		private String name;
		
		private ProcessBehavior(String code,String name){
			this.code = code;
			this.name = name;
		}
		
		public static final ProcessBehavior getProcessAction(String code){
			ProcessBehavior result = null;
			ProcessBehavior[] actions = ProcessBehavior.values();
			for (ProcessBehavior pa : actions) {
				if ( pa.code.equals(code) ){
					result = pa;
				}
			}
			return result;
		}

		public String getCode() {
			return code;
		}

		public String getName() {
			return name;
		}
		
	}

	private boolean	isDeal	= true;
	
	private Long 	dealUserId;
	
	private Date	dealTime;

	private Long	nextUser;

	private String	pass;

	private String	remark;

	private String	businessData;

	private String	businessType;
	
	private String approvalOpinion;
	
	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getNextUser() {
		return nextUser;
	}

	public void setNextUser(Long nextUser) {
		this.nextUser = nextUser;
	}

	public String getBusinessData() {
		return businessData;
	}

	public void setBusinessData(String businessData) {
		this.businessData = businessData;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public boolean isDeal() {
		return isDeal;
	}

	public void setDeal(boolean isDeal) {
		this.isDeal = isDeal;
	}

	public String getApprovalOpinion() {
		return approvalOpinion;
	}

	public void setApprovalOpinion(String approvalOpinion) {
		this.approvalOpinion = approvalOpinion;
	}
	
	public Long getDealUserId() {
		return dealUserId;
	}

	public void setDealUserId(Long dealUserId) {
		this.dealUserId = dealUserId;
	}

	public Date getDealTime() {
		return dealTime;
	}

	public void setDealTime(Date dealTime) {
		this.dealTime = dealTime;
	}

	public String toString() {
		return "{nextUser:" + String.valueOf(nextUser) + ",pass:"
				+ String.valueOf(pass) + ",remark:" + String.valueOf(remark)
				+ "}";
	}

}
