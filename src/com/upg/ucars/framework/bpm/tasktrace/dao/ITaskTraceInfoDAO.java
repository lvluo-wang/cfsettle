package com.upg.ucars.framework.bpm.tasktrace.dao;

import java.util.List;

import com.upg.ucars.framework.base.IBaseDAO;
import com.upg.ucars.mapping.framework.bpm.TaskTraceInfo;

/**
 * 任务追踪信息DAO
 * 
 * @author shentuwy
 * 
 */
public interface ITaskTraceInfoDAO extends IBaseDAO<TaskTraceInfo, Long> {
	/**
	 * 根据任务ID获取信息
	 * 
	 * @param taskId
	 * @return
	 */
	public TaskTraceInfo getByTaskId(Long taskId);

	/**
	 * 获取任务审批信息列表
	 * 
	 * @param processName
	 * @return
	 * @see #getTaskTraceInfoListByEntityIdAndType(Long, String)
	 */
	@Deprecated
	public List<TaskTraceInfo> getTaskTraceInfoList(Long entityId,String processName);

	/**
	 * 获取任务审批信息列表
	 * 
	 * @param processName
	 * @return
	 * @see #getTaskTraceInfoListByEntityIdAndType(Long, String, String)
	 */
	@Deprecated
	public List<TaskTraceInfo> getTaskTraceInfoList(Long entityId,String processName,String taskName);
	
	/**
	 * 获取任务审批信息列表
	 * 
	 * @param entityId
	 * @param entityType
	 * @return
	 */
	public List<TaskTraceInfo> getTaskTraceInfoListByEntityIdAndType(Long entityId,String entityType);
	
	/**
	 * 获取单个流程任务审批信息
	 * 
	 * @return
	 */
	public List<TaskTraceInfo> getInstanceTaskTraceInfoList(Long instanceBusinessId);
	
	/**
	 * 流程是否有任务处理信息
	 * 
	 * @param instanceBusinessId
	 * @return
	 */
	public boolean hasTaskTraceInfo(Long instanceBusinessId);
	
	/**
	 * 获取委托处理结果
	 * 
	 * @param delegateId
	 * @return
	 */
	public List<TaskTraceInfo> getTaskTraceInfoByDelegateId(Long delegateId);

}
