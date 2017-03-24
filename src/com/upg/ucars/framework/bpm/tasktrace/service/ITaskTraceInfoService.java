package com.upg.ucars.framework.bpm.tasktrace.service;

import java.util.List;

import com.upg.ucars.framework.base.IBaseService;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.mapping.framework.bpm.TaskTraceInfo;
import com.upg.ucars.model.DealOpinionInfo;

/**
 * 任务信息追踪服务
 * 
 * @author shentuwy
 *
 */
public interface ITaskTraceInfoService extends IBaseService {
	
	
	/**
	 * 记录任务处理信息 
	 *
	 * @param taskId
	 * @param entityId
	 * @param opinionInfo
	 * @throws ServiceException
	 */
	public void traceTaskDeal(Long taskId, Long entityId, DealOpinionInfo opinionInfo);
	
	/**
	 * 记录任务处理信息  不关联任务节点
	 * 
	 * @param taskId
	 * @param entityId
	 * @param opinionInfo
	 */
	public void traceTaskDealWithoutTask(Long taskId, Long entityId, DealOpinionInfo opinionInfo);
	
	
	/**
	 * 保存信息
	 * @param tti
	 * @throws DAOException
	 * @throws ServiceException
	 */
	public void saveTaskTraceInfo(TaskTraceInfo tti);
	/**
	 * 修改信息
	 * @param tti
	 * @throws DAOException
	 * @throws ServiceException
	 */
	public void updateTaskTraceInfo(TaskTraceInfo tti);
	/**
	 * 根据任务ID删除追踪信息
	 * @param taskId
	 * @throws DAOException
	 * @throws ServiceException
	 */
	public void deleteByTaskId(Long taskId);
	
	/**
	 * 根据流程名称获取任务审批信息
	 * 
	 * @param processName
	 * @return
	 */
	public List<TaskTraceInfo> getTaskTraceInfoList(Long entityId,String processName);

	/**
	 * 根据流程名称获取任务审批信息
	 * @param entityId
	 * @param processName
	 * @param taskName
	 * @return
	 */
	public List<TaskTraceInfo> getTaskTraceInfoList(Long entityId,String processName, String taskName);
	
	/**
	 * 获取任务审批信息
	 * 
	 * @param instanceBusinessId
	 * @return
	 */
	public List<TaskTraceInfo> getTaskTraceInfoList(Long instanceBusinessId);
	
	/**
	 * 单个流程任务审批信息
	 * 
	 * @param instanceBusinessId
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
	 * 获取任务的处理结果
	 * 
	 * @param taskId
	 * @return
	 */
	public TaskTraceInfo getTaskTraceInfoByTaskId(Long taskId);
	
	/**
	 * 获取委托处理结果
	 * 
	 * @param delegateId
	 * @return
	 */
	public List<TaskTraceInfo> getTaskTraceInfoByDelegateId(Long delegateId);

}
