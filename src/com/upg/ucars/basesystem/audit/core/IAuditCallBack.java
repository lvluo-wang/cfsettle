package com.upg.ucars.basesystem.audit.core;

import java.util.List;

import com.upg.ucars.mapping.basesystem.audit.AuditProcess;
import com.upg.ucars.mapping.basesystem.audit.AuditTask;
/**
 * 审批回调接口（审批任务完成后自动触发此接口中实现的callBack方法）
 * 
 * @date 2011-4-1 上午09:12:51
 *
 */
public interface IAuditCallBack {
	/**
	 * 在审批任务完成后需要执行的操作，在此方法中实现
	 * @param task 审批任务
	 * @param processList 审批过程集合
	 * @throws Exception
	 */
	public void auditCallBack(AuditTask task,List<AuditProcess> processList) throws Exception;
}
