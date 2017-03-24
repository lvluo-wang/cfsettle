package com.upg.ucars.basesystem.audit.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.upg.ucars.mapping.basesystem.audit.AuditProcess;
import com.upg.ucars.mapping.basesystem.audit.AuditTask;
/**
 * 审批任务撤销结果
 * 
 * @author shentuwy
 * @date 2011-4-1 上午11:50:43
 *
 */
public class AuditTaskRevokeResult implements Serializable {

	private static final long serialVersionUID = -1658351818830091125L;
	/** 撤销成功 */
	public static boolean PASS=true;
	/** 撤销失败 */
	public static boolean FAIL=false;
	/** 审批任务 */
	private AuditTask auditTask=null;
	/** 审批过程集合 */
	private List<AuditProcess> auditProcessList=new ArrayList<AuditProcess>();
	/** 撤销结果 */
	private boolean revokePass=false;
	
	public AuditTaskRevokeResult(){}
	
	public AuditTask getAuditTask() {
		return auditTask;
	}
	public void setAuditTask(AuditTask auditTask) {
		this.auditTask = auditTask;
	}
	public List<AuditProcess> getAuditProcessList() {
		return auditProcessList;
	}
	public void setAuditProcessList(List<AuditProcess> auditProcessList) {
		this.auditProcessList = auditProcessList;
	}
	public boolean isRevokePass() {
		return revokePass;
	}
	public void setRevokePass(boolean revokePass) {
		this.revokePass = revokePass;
	}

	
}
