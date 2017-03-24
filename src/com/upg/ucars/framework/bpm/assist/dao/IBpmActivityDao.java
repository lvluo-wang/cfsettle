package com.upg.ucars.framework.bpm.assist.dao;

import java.util.List;

import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.bpm.assist.model.NodeLineDTO;
import com.upg.ucars.framework.bpm.assist.model.ProcessInstanceDTO;
import com.upg.ucars.framework.bpm.assist.model.TaskInstanceDTO;
import com.upg.ucars.mapping.basesystem.security.Buser;



/** 
 * 流程活动DAO
 * @author yangjun (mailto:yangjun@leadmind.com.cn)
 */

public interface IBpmActivityDao {
	/**
	 * 查询流程实例
	 * @param procName
	 * @param entityId
	 * @param page
	 * @return
	 */
	List<ProcessInstanceDTO> queryProcessInstances(String procName, Long entityId, Page page);
	
	/**
	 * 查询流程实例
	 * @param procName
	 * @param entityIds
	 * @param page
	 * @return
	 */
	List<ProcessInstanceDTO> queryProcessInstances(String procName, List entityIds, Page page);	
	
	/**
	 * 获取任务信息
	 * @param processInstanceId
	 * @return
	 */
	List<TaskInstanceDTO> getTaskInstances(Long processInstanceId);	
	
	/**
	 * 获取流转信息
	 * @param tokenId
	 * @return
	 */
	List<NodeLineDTO> getTransitionInfoByToken(Long tokenId);
	
	/**
	 * 获取任务执行者
	 * @param taskInstanceId
	 * @return
	 */
	List<Buser> getActorsByTask(Long taskInstanceId);
	
	
}
 
 