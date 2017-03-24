package com.upg.ucars.framework.bpm.publish.dao;

import java.util.List;
import java.util.Map;

import com.upg.ucars.bpm.core.TaskSearchBean;
import com.upg.ucars.framework.base.IBaseDAO;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.mapping.framework.bpm.HumnTask;


public interface IHunmTaskDao extends IBaseDAO<HumnTask, Long>{
	
	/**
	 * 批量保存
	 * @param humnTasks
	 * @throws DAOException
	 */
	public void saveHumnTasks(List<HumnTask> humnTasks) throws DAOException;
	
	/**
	 *取出所有HumnTask
	 */
	public List<HumnTask> getHumnTasks() throws DAOException;
	
	/**
	 * 根据processId取出所有HumnTask 
	 *
	 * @param procId
	 * @return
	 * @throws DAOException
	 */
	public List getHumnTasksByProcId(Long procId) throws DAOException;
	
	/**
	 * 根据流程名取出所有HumnTask 
	 *
	 * @param procId
	 * @return
	 * @throws DAOException
	 */
	public List getTaskNameByProcId(Long procId) throws DAOException;
	

	/**
	 * 根据流程ID和任务名称取得任务信息
	 * @param prodId 流程ID
	 * @param taskName 任务名称
	 * @return
	 * @throws DAOException
	 */
	public HumnTask getHumnTaskByProcIdAndTaskName(Long prodId, String taskName) throws DAOException;
	
	/**
	 * 获取菜单路径
	 * @param funcId
	 * @return
	 */
	public String getFuncPath(String funcId);
	
	/**
	 * 根据流程名称和任务名称获取人工任务
	 * 
	 * @param processName
	 * @param taskName
	 * @return
	 */
	public HumnTask getHumnTaskByProcessAndTaskName(String processName,String taskName);
	
	/**
	 * 任务节点是否指定处理人
	 * 
	 * @param brchId
	 * @param processName
	 * @param taskName
	 * @return
	 */
	public boolean taskHasActr(Long brchId, String processName, String taskName);
	
	/**
	 * 任务统计
	 * 
	 * @param roleName
	 * @param pg
	 * @return
	 * */
	public List<Map<String, Object>> countTask(String roleName, Page pg);
	
	/**
	 * 按角色查看任务统计详情
	 * 
	 * @param roleId
	 * @param countType
	 * @return
	 * */
	public List<Map<String,Object>> getProcessDetail(TaskSearchBean taskSearchBean, int countType, Page page);
}
