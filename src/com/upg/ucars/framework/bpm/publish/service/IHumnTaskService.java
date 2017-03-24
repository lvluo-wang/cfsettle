package com.upg.ucars.framework.bpm.publish.service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.upg.ucars.bpm.core.TaskSearchBean;
import com.upg.ucars.framework.base.IBaseService;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.exception.ServiceException;
import com.upg.ucars.mapping.basesystem.security.Buser;
import com.upg.ucars.mapping.framework.bpm.HumnTask;

/**
 * 人工任务服务定义
 * 
 * @author shentuwy
 * @date 2012-7-10
 *
 */
public interface IHumnTaskService extends IBaseService {
	
	public static final Comparator<HumnTask> HUMN_TASK_COMPARATOR = new Comparator<HumnTask>() {
		@Override
		public int compare(HumnTask o1, HumnTask o2) {
			BigDecimal sort1 = BigDecimal.ZERO;
			if (o1 != null && o1.getSortNo() != null) {
				sort1 = o1.getSortNo();
			}
			BigDecimal sort2 = BigDecimal.ZERO;
			if (o2 != null && o2.getSortNo() != null) {
				sort2 = o2.getSortNo();
			}
			return sort1.compareTo(sort2);
		}
	}; 
	
	/**
	 * 批量保存
	 * @param humnTasks
	 * @throws ServiceException
	 */
	public void saveHunmTasks(List humnTasks) throws ServiceException;
	/**
	 * 保存
	 * @param humnTask
	 * @throws ServiceException
	 */
	public void saveHunmTask(HumnTask humnTask) throws ServiceException;
	/**
	 * 更新
	 * @param humnTask
	 * @throws ServiceException
	 */
	public void updateHumnTask(HumnTask humnTask) throws ServiceException;
	/**
	 * 删除
	 * @param id
	 * @throws ServiceException
	 */
	public void deleteHumnTask(Long id)throws ServiceException;
	/**
	 * 根据ID得到HumnTask
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	public HumnTask getHumnTaskById(Long id) throws ServiceException;
	/**
	 * 取出所有HumnTask
	 * @return
	 * @throws ServiceException
	 */
	public List<HumnTask> getHumnTasks() throws ServiceException;
	/**
	 * 根据processId取出所有HumnTask
	 * @param procId
	 * @return
	 */
	public List<HumnTask> getHumnTasksByProcId(Long procId);
	
	/**
	 * 根据流程名取出所有HumnTask
	 * @param procId
	 * @return
	 * @throws ServiceException
	 */
	public List getTaskNamesByProcId(Long procId) throws ServiceException;
	
	public void delHumnTasksByProcId(Long procId) throws ServiceException;
	
	/**
	 * 根据流程ID和任务名称取得任务信息
	 * @param prodId 流程ID
	 * @param taskName 任务名称
	 * @return
	 * @throws ServiceException
	 */
	public HumnTask getHumnTaskByProcIdAndTaskName(Long prodId, String taskName) throws ServiceException;
	
	/**
	 * 获取菜单路径
	 * @param funcId
	 * @return
	 */
	public String getMenuPath(String funcId);
	
	/**
	 * 根据流程名和任务名获取人工任务
	 * 
	 * @param processName
	 * @param taskName
	 * @return
	 */
	public HumnTask getHumnTaskByProcessAndTaskName(String processName,String taskName);
	
	/**
	 * 任务节点是否被指定处理人
	 * 
	 * @param brchId
	 * @param processName
	 * @param taskName
	 * @return
	 */
	public boolean taskHasActr(Long brchId,String processName,String taskName);
	
	/**
	 * 获取任务的所有处理人
	 * 
	 * @param taskId
	 * @return
	 */
	public List<Buser> getTaskDealUsers(Long taskId);
	
	/**
	 * 获取任务统计列表
	 * @param roleName
	 * @param pg
	 * @return
	 * 
	 * */
	public List<Map<String, Object>> getTaskCountList(String roleName, Page pg);
	
	/**
	 * 获取任务统计详情
	 * @param taskSearchBean
	 * @param countType
	 * @param page
	 * @return
	 * 
	 * */
	public List<Map<String,Object>> getProcessDetail(TaskSearchBean taskSearchBean, int countType, Page page);
}

