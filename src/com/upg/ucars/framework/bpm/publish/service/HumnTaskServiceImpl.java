package com.upg.ucars.framework.bpm.publish.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.jbpm.taskmgmt.exe.TaskInstance;

import com.upg.ucars.basesystem.security.core.user.IUserService;
import com.upg.ucars.bpm.core.TaskSearchBean;
import com.upg.ucars.framework.base.BaseService;
import com.upg.ucars.framework.base.IExtCommonDao;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.bpm.base.BpmQueryDAO;
import com.upg.ucars.framework.bpm.publish.dao.IHunmTaskDao;
import com.upg.ucars.framework.exception.ServiceException;
import com.upg.ucars.mapping.basesystem.security.Buser;
import com.upg.ucars.mapping.framework.bpm.HumnTask;

public class HumnTaskServiceImpl extends BaseService implements IHumnTaskService{
	private IHunmTaskDao humnTaskDao;
	
	@Resource
	private IExtCommonDao	extCommonDao;
	@Resource
	private BpmQueryDAO bpmQueryDAO;
	@Resource
	private IUserService	userService;
	
	public void saveHunmTask(HumnTask humnTask) throws ServiceException {
		humnTaskDao.save(humnTask);		
	}
	
	public void saveHunmTasks(List humnTasks) throws ServiceException {
		humnTaskDao.saveHumnTasks(humnTasks);
		
	}

	public void deleteHunmTask(Long id) throws ServiceException {
		humnTaskDao.delete(id);
	}

	public HumnTask getHunmTaskById(Long id) throws ServiceException {
		HumnTask h = (HumnTask)humnTaskDao.get(id);
		return h;
	}

	public List<HumnTask> getHumnTasks() throws ServiceException {
		List<HumnTask> humnTasks=humnTaskDao.getHumnTasks();
		return humnTasks;
	}

	public void updateHumnTask(HumnTask humnTask) throws ServiceException {
		humnTaskDao.update(humnTask);
	}

	public IHunmTaskDao getHumnTaskDao() {
		return humnTaskDao;
	}

	public void setHumnTaskDao(IHunmTaskDao humnTaskDao) {
		this.humnTaskDao = humnTaskDao;
	}

	public List<HumnTask> getHumnTasksByProcId(Long procId){
		return humnTaskDao.getHumnTasksByProcId(procId);
	}
	public List getTaskNamesByProcId(Long procId) throws ServiceException {
		List list=humnTaskDao.getTaskNameByProcId(procId);
		return list;
	}
	
	/**
	 * 根据流程ID和任务名称取得任务信息
	 * @param prodId 流程ID
	 * @param taskName 任务名称
	 * @return
	 * @throws ServiceException
	 */
	public HumnTask getHumnTaskByProcIdAndTaskName(Long prodId, String taskName) throws ServiceException{
		HumnTask humnTask = humnTaskDao.getHumnTaskByProcIdAndTaskName(prodId, taskName);
		return humnTask;
	}

	public String getMenuPath(String funcId) {		
		return this.getHumnTaskDao().getFuncPath(funcId);
	}
	

	public void deleteHumnTask(Long id) throws ServiceException {
		this.humnTaskDao.delete(id);		
	}

	public HumnTask getHumnTaskById(Long id) throws ServiceException {
		return (HumnTask)this.humnTaskDao.get( id);
	}

	public void delHumnTasksByProcId(Long procId) throws ServiceException {
		List<HumnTask> taskList = this.getHumnTasksByProcId(procId);
		for (HumnTask humnTask : taskList) {
			this.deleteHumnTask(humnTask.getId());
		}
		
	}
	
	public HumnTask getHumnTaskByProcessAndTaskName(String processName,String taskName){
		return humnTaskDao.getHumnTaskByProcessAndTaskName(processName, taskName);
	}

	public boolean taskHasActr(Long brchId, String processName, String taskName) {
		return humnTaskDao.taskHasActr(brchId, processName, taskName);
	}

	@Override
	public List<Buser> getTaskDealUsers(Long taskId) {
		List<Buser> result = new ArrayList<Buser>();;
		if (taskId != null) {
			TaskInstance taskInstance = bpmQueryDAO.getTaskInstanceById(taskId);
			if (taskInstance != null) {
				String preactor = taskInstance.getPreActor();
				if (StringUtils.isNotBlank(preactor)) {
					String[] preactorArray = preactor.split(",");
					for (String uid : preactorArray) {
						if (StringUtils.isNotBlank(uid)) {
							Buser user = userService.getUserById(Long.valueOf(uid));
							if (user != null) {
								result.add(user);
							}
							
						}
					}
				}
				
				HumnTask ht = getHumnTaskByProcessAndTaskName(taskInstance.getProcNameKey(), taskInstance.getName());
				if (ht != null) {
					String hql = "select u from HumnTaskActr hta,Buser u,Branch b,Role r,ReUserRole rur where hta.actrBrchId=b.brchId and u.userId=rur.userId and r.roleId=rur.roleId and u.brchId=b.brchId and r.roleId=hta.actrRoleId " +
							" and hta.taskId=:taskId and hta.brchId=:brchId and u.status <> :notStatus";
					Map<String,Object> params = new HashMap<String,Object>();
					params.put("taskId", ht.getId());
					params.put("brchId", taskInstance.getBrchId());
					params.put("notStatus", Buser.STATUS_CLOSE);
					List<Buser> userList = extCommonDao.findEntities(hql, params);
					if (userList != null && !userList.isEmpty()) {
						result.addAll(userList);
					}
				}
				
				
			}
		}
		return result;
	}

	@Override
	public List<Map<String, Object>> getTaskCountList(String roleName, Page pg) {
		return humnTaskDao.countTask(roleName, pg);
	}

	@Override
	public List<Map<String, Object>> getProcessDetail(
			TaskSearchBean taskSearchBean, int countType, Page page) {
		return humnTaskDao.getProcessDetail(taskSearchBean, countType, page);
	}
}
