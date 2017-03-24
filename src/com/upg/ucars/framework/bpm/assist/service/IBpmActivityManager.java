package com.upg.ucars.framework.bpm.assist.service;

import java.util.List;

import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.bpm.assist.model.BpmSearchBean;
import com.upg.ucars.framework.bpm.assist.model.CurrentNodeInfo;
import com.upg.ucars.framework.bpm.assist.model.NodeLineDTO;
import com.upg.ucars.framework.bpm.assist.model.ProcessInstanceDTO;
import com.upg.ucars.framework.bpm.assist.model.TaskInstanceDTO;
import com.upg.ucars.framework.bpm.assist.model.VariableInstanceDTO;
import com.upg.ucars.framework.exception.ServiceException;
import com.upg.ucars.mapping.basesystem.security.Buser;



/** 
 * 流程活动管理者功能定义
 *
 * @author yangjun (mailto:yangjun@leadmind.com.cn)
 */
public interface IBpmActivityManager {
	/**
	 * 查询流程实例信息
	 * @param sb
	 * @param page
	 * @return
	 */
	List<ProcessInstanceDTO> queryProcessInstances(BpmSearchBean sb, Page page);
	
	/**
	 * 获取任务信息
	 * @param processInstanceId
	 * @return
	 */
	List<TaskInstanceDTO> getTaskInstances(Long processInstanceId);
	/**
	 * 获取流转信息
	 * @param processInstanceId
	 * @return
	 */
	List<NodeLineDTO> getTransitionInfo(Long processInstanceId);
	
	/**
	 * 获取当前节点
	 * @param processInstanceId
	 * @return
	 */
	CurrentNodeInfo getCurrentNodeInfo(Long processInstanceId);
	/**
	 * 获取上下文信息
	 * @param processInstanceId
	 * @return
	 */
	List<VariableInstanceDTO> getVariableInstances(Long processInstanceId);
	/**
	 * 获取任务执行者
	 * @param taskInstanceId
	 * @return
	 */
	List<Buser> getActorsByTask(Long taskInstanceId);
	
	/**
	 * 增加任务执行者
	 * @param taskInstanceId
	 * @param userNo
	 */
	void addTaskActor(Long taskInstanceId, String userNo);
	/**
	 * 删除任务执行者
	 * @param taskInstanceId
	 * @param userNo
	 */
	void delTaskActor(Long taskInstanceId, String userNo);
	
	/**
	 * 终止流程
	 * @param processInstanceId
	 */
	boolean suspendProcessInstance(Long processInstanceId) throws ServiceException;
	/**
	 * 恢复流程
	 * @param processInstanceId
	 */
	boolean resumeProcessInstance(Long processInstanceId) throws ServiceException;
	/**
	 * 终止任务
	 * @param taskInstanceId
	 */
	boolean suspendTaskInstance(Long taskInstanceId) throws ServiceException;
	/**
	 * 恢复任务
	 * @param taskInstanceId
	 */
	boolean resumeTaskInstance(Long taskInstanceId) throws ServiceException;	
	/**
	 * 设置上下文
	 * @param processInstanceId
	 * @param name
	 * @param value
	 */
	boolean addOrUpdateVariable(Long processInstanceId, String name, Object value) throws ServiceException;
	/**
	 * 删除上下文
	 * @param processInstanceId
	 * @param name
	 */
	boolean delVariable(Long processInstanceId, String name) throws ServiceException;
	/**
	 * 流转
	 * @param processInstanceId
	 * @param lineName 连线名
	 */
	boolean signal(Long processInstanceId, String lineName) throws ServiceException;
	/**
	 * 撤销领用人 
	 *
	 * @param taskId
	 * @throws ServiceException
	 */
	void cancelHolder(Long taskId) throws ServiceException;
	/**
	 * 设置领用人
	 *
	 * @param taskId
	 * @param userId
	 * @throws ServiceException
	 */
	void holdTask(Long taskId, Long userId) throws ServiceException;
	
}

 