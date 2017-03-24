package com.upg.ucars.framework.bpm.assign.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.springframework.beans.factory.annotation.Autowired;

import com.upg.ucars.basesystem.security.core.branch.IBranchService;
import com.upg.ucars.basesystem.security.core.user.IUserService;
import com.upg.ucars.constant.ErrorCodeConst;
import com.upg.ucars.framework.base.BaseService;
import com.upg.ucars.framework.base.ICommonDAO;
import com.upg.ucars.framework.base.SessionTool;
import com.upg.ucars.framework.bpm.assign.dao.IHumanTaskActrDAO;
import com.upg.ucars.framework.bpm.assign.dao.ITaskInstanceDao;
import com.upg.ucars.framework.bpm.base.IInstanceBusinessDao;
import com.upg.ucars.framework.bpm.publish.service.IHumnTaskService;
import com.upg.ucars.framework.bpm.publish.service.IProcessDefService;
import com.upg.ucars.framework.bpm.tasktrace.service.ITaskTraceInfoService;
import com.upg.ucars.framework.exception.ExceptionManager;
import com.upg.ucars.framework.exception.ServiceException;
import com.upg.ucars.mapping.basesystem.security.Branch;
import com.upg.ucars.mapping.basesystem.security.Buser;
import com.upg.ucars.mapping.framework.bpm.HumnTaskActr;
import com.upg.ucars.mapping.framework.bpm.InstanceBusiness;
import com.upg.ucars.mapping.framework.bpm.TaskTraceInfo;
import com.upg.ucars.model.ConditionBean;
import com.upg.ucars.model.TaskProcessResult.ProcessBehavior;
import com.upg.ucars.model.security.UserLogonInfo;


public class HumanTaskAssignService extends BaseService implements IHumanTaskAssignService{
	private IHumanTaskActrDAO humanTaskActrDAO;//spring set
	private ITaskInstanceDao taskInstanceDao;//spring set
	private ICommonDAO commonDAO;//spring set
	@Autowired
	private IProcessDefService processDefService;
	@Autowired
	private IBranchService branchService;
	@Autowired
	private IHumnTaskService humnTaskService;
	@Autowired
	private ITaskTraceInfoService taskTraceInfoService;
	@Autowired
	private IInstanceBusinessDao	instanceBusinessDao;
	@Autowired
	private IUserService		userService;
	
	
	public List<String> getTaskActors(String processName, String taskName, Long brchId)
			throws ServiceException {
		
		return humanTaskActrDAO.getTaskActors(processName, taskName, brchId);
		
	}

	public List<Long> getRoleActorsByBrch(Long taskId, Long brchId, Long roleBrchId) throws ServiceException {
		List<HumnTaskActr> list = this.humanTaskActrDAO.getHumnTaskActrs(taskId, brchId);
		ArrayList<Long> roleList = new ArrayList<Long>();
		for (HumnTaskActr ta : list) {
			if (ta.getActrRoleId() != null && roleBrchId.equals(ta.getActrBrchId())){
				roleList.add(ta.getActrRoleId());
			}
		}
		return roleList;
	}

	public List<Long> getUserActors(Long taskId, Long brchId) throws ServiceException {
		List<HumnTaskActr> list = this.humanTaskActrDAO.getHumnTaskActrs(taskId, brchId);
		ArrayList<Long> userList = new ArrayList<Long>();
		for (HumnTaskActr humnTaskActr : list) {
			if (humnTaskActr.getActrUserId() != null){
				userList.add(humnTaskActr.getActrUserId());
			}
		}
		return userList;
	}
	public List<Long> getBrchActors(Long taskId, Long brchId) throws ServiceException {
		if (brchId != null){
			List<HumnTaskActr> list = this.humanTaskActrDAO.getHumnTaskActrs(taskId, brchId);
			
			ArrayList<Long> brchList = new ArrayList<Long>();
			for (HumnTaskActr humnTaskActr : list) {
				if (humnTaskActr.getActrBrchId() != null && humnTaskActr.getActrRoleId() == null){
					brchList.add(humnTaskActr.getActrBrchId());
				}
			}
			return brchList;
		}else{
			
			//已存在的
			List<Long> existIdList = this.commonDAO.find("SELECT brchId FROM HumnTaskActr WHERE taskId=? AND actrRoleId IS NULL AND actrUserId IS NULL", taskId);
			
			return existIdList;
		}
		
	}

	public void holdTask(Long tid, Long userId){
		holdTask(tid, userId, false);
	}
	
	public void holdTask(Long tid, Long userId,boolean force){
		Long holdUserId = this.getTaskInstanceDao().getTaskHoldActor(tid);
		if(force){
			this.getTaskInstanceDao().updateTaskHoldActor(tid, userId);
		}else{
			if (holdUserId==null){
				this.getTaskInstanceDao().updateTaskHoldActor(tid, userId);
			}else{
				//已被领用
				if (!holdUserId.equals(userId)) {
					ExceptionManager.throwException(ServiceException.class, ErrorCodeConst.WF_TASK_HOLD, new String[]{userId.toString()});
				}
			}
		}
	}
	
	public void holdTask(List<Long> idList,Long userId){
		if (idList != null && idList.size() > 0) {
			for (Long tid : idList) {
				holdTask(tid, userId);
			}
		}
	}

	public void cancelHoldTask(Long tid, Long userId){
		Long holdUserId = this.getTaskInstanceDao().getTaskHoldActor(tid);
		if (userId.equals(holdUserId)){
			TaskInstance ti = this.getTaskInstanceDao().getTaskInstance(tid);
			if (StringUtils.isNotBlank(ti.getPreActor()) || humnTaskService.taskHasActr(ti.getBrchId(), ti.getProcNameKey(), ti.getName())) {
				this.getTaskInstanceDao().updateTaskHoldActor(tid, null);
			}else{
				//TODO 增加标志位
				ExceptionManager.throwException(ServiceException.class, ErrorCodeConst.WF_TASK_CANCEL_HOLD_FIX);
			}
		}else{
			//无权撤销
			ExceptionManager.throwException(ServiceException.class, ErrorCodeConst.WF_TASK_CANCEL_HOLD, new String[]{userId.toString()});
		}
		
	}
	
	public void cancelHoldTask(List<Long> idList,Long userId){
		if (idList != null && idList.size() > 0) {
			for (Long tid : idList) {
				cancelHoldTask(tid,userId);
			}
		}
	}

	public Long getTaskHoldActor(Long tid) {
		return this.getTaskInstanceDao().getTaskHoldActor(tid);
	}
	
	public void copyTaskRoleActor(Long srcBranchId,List<Long> processIds,List<Long> destBranchIds){
		if (srcBranchId != null && processIds != null && processIds.size() > 0 && destBranchIds != null && destBranchIds.size() > 0) {
			Set<Long> destBranchs = new HashSet<Long>();
			for (Long destId : destBranchIds) {
				Branch branch = branchService.getBranchByBrchId(destId);
				List<Branch> branchList = branchService.getSubBranchList(branch.getTreeCode(), branch.getMiNo());
				for (Branch b : branchList) {
					destBranchs.add(b.getBrchId());
				}
			}
			
			destBranchs.remove(srcBranchId);
			
			for (Long processDefId : processIds) {
				for (Long brchId : destBranchs) {
					//删除
					humanTaskActrDAO.deleteHumnTaskActr(brchId, processDefId);
					
					List<HumnTaskActr> actrList = humanTaskActrDAO.getHumnTaskActr(srcBranchId, processDefId);
					
					if (actrList != null && actrList.size() > 0) {
						List<HumnTaskActr> addActrList = new ArrayList<HumnTaskActr>(actrList.size());
						for (HumnTaskActr actr : actrList) {
							HumnTaskActr newActr = new HumnTaskActr();
							newActr.setTaskId(actr.getTaskId());
							newActr.setBrchId(brchId);
							if(srcBranchId.equals(actr.getActrBrchId())){
								newActr.setActrBrchId(brchId);
							}else{
								newActr.setActrBrchId(actr.getActrBrchId());
							}
							newActr.setActrRoleId(actr.getActrRoleId());
							newActr.setActrUserId(actr.getActrUserId());
							newActr.setStatus(actr.getStatus());
							addActrList.add(newActr);
						}
						humanTaskActrDAO.saveOrUpdateAll(addActrList);
					}
					
				}
			}
		}
	}
	

	public IHumanTaskActrDAO getHumanTaskActrDAO() {
		return humanTaskActrDAO;
	}

	public void setHumanTaskActrDAO(IHumanTaskActrDAO humanTaskActrDAO) {
		this.humanTaskActrDAO = humanTaskActrDAO;
	}

	public ITaskInstanceDao getTaskInstanceDao() {
		return taskInstanceDao;
	}
	public void setTaskInstanceDao(ITaskInstanceDao taskInstanceDao) {
		this.taskInstanceDao = taskInstanceDao;
	}

	public void save(HumnTaskActr taskActr) throws ServiceException {
		List<ConditionBean> conditionList = new ArrayList<ConditionBean>();
		conditionList.add(new ConditionBean("taskId", taskActr.getTaskId()));
		conditionList.add(new ConditionBean("brchId", taskActr.getBrchId()));
		conditionList.add(new ConditionBean("actrBrchId", taskActr.getActrBrchId()));
		conditionList.add(new ConditionBean("actrRoleId", taskActr.getActrRoleId()));
		conditionList.add(new ConditionBean("actrUserId", taskActr.getActrUserId()));
		
		List list = this.humanTaskActrDAO.queryEntity(conditionList, null);
		if (list.isEmpty())
			this.humanTaskActrDAO.save(taskActr);		
	}

	public void deleteByUserId(Long brchId, Long taskId, Long userId)
			throws ServiceException {
		String hql = "from HumnTaskActr where taskId=? and brchId=? and actrUserId=?";
		List<HumnTaskActr> list = this.commonDAO.find(hql, new Object[]{taskId, brchId, userId});
		
		if (!list.isEmpty())
			this.humanTaskActrDAO.delAll(list);
		
	}

	public void deleteByBrchRoleId(Long brchId, Long taskId, Long roleBrchId,
			Long roleId) throws ServiceException {
		String hql = "from HumnTaskActr where taskId=? and brchId=? and actrBrchId=? and actrRoleId=?";
		List<HumnTaskActr> list = this.commonDAO.find(hql, new Object[]{taskId, brchId, roleBrchId, roleId});
		if (!list.isEmpty())
			this.humanTaskActrDAO.delAll(list);
		
	}

	public ICommonDAO getCommonDAO() {
		return commonDAO;
	}

	public void setCommonDAO(ICommonDAO commonDAO) {
		this.commonDAO = commonDAO;
	}
	
	private String joinStr(List<Long> list){
		StringBuffer sb = new StringBuffer();
		for (Long l : list) {
			sb.append(l.toString() + ",");
		}
		if (!list.isEmpty())
			sb.deleteCharAt(sb.length() -1);
		
		return sb.toString();
	}

	public void batchBuildBrchActor(Long taskId, List<Long> beBrchIdList, List<Long> unBrchIdList, boolean isSameSubBrch){
		//---------------删除
		ArrayList<Long> delIdList = new ArrayList<Long>(unBrchIdList);//待删除集合
		if (isSameSubBrch && !unBrchIdList.isEmpty()){
			Map<String, Object> parameterMap = new HashMap<String, Object>(1);
			parameterMap.put("brchId", unBrchIdList);
			List<Object[]> list = this.commonDAO.queryByParam("select brchId, treeCode from Branch where brchId in (:brchId)", parameterMap, null);
			for (Object[] objs : list) {
				//Long brchId = (Long)objs[0];
				String treeCode = (String)objs[1];
				List<Long> subBrchIdList = this.getSubBrchIdByBrchTreeCode(treeCode);
				delIdList.addAll(subBrchIdList);
				
			}
		}
		if (!delIdList.isEmpty()){
			String hql = "delete from HumnTaskActr where  taskId=? AND brchId in ("+this.joinStr(delIdList)+") AND actrRoleId IS NULL AND actrUserId IS NULL ";
			this.commonDAO.bulkUpdate(hql, taskId);			
		}
			
		
		//--------------增加
		
		//已存在的
		List<Long> existIdList = this.commonDAO.find("SELECT brchId FROM HumnTaskActr WHERE taskId=? AND actrRoleId IS NULL AND actrUserId IS NULL", taskId);
		
		HashSet<Long> addIdSet = new HashSet<Long>(beBrchIdList);//待增加集合
		if (isSameSubBrch && !beBrchIdList.isEmpty()){
			Map<String, Object> parameterMap = new HashMap<String, Object>(1);
			parameterMap.put("brchId", beBrchIdList);
			List<Object[]> list = this.commonDAO.queryByParam("select brchId, treeCode from Branch where brchId in (:brchId)",parameterMap, null);
			
			for (Object[] objs : list) {
				//Long brchId = (Long)objs[0];
				String treeCode = (String)objs[1];
				List<Long> subBrchIdList = this.getSubBrchIdByBrchTreeCode(treeCode);
				addIdSet.addAll(subBrchIdList);
				
			}
			
		}
		
		for (Long brchId : addIdSet) {
			if (!existIdList.contains(brchId)){
				HumnTaskActr taskActr = new HumnTaskActr();
				taskActr.setBrchId(brchId);
				taskActr.setTaskId(taskId);
				taskActr.setActrBrchId(brchId);
				
				this.humanTaskActrDAO.save(taskActr);
			}
			
		}
		
	}
	/**
	 * 获取所有下级机构 实体的ID
	 */
	private List<Long> getSubBrchIdByBrchTreeCode(String brchTreeCode){
		String hql = "SELECT brchId FROM Branch  WHERE treeCode like ? ";
		//查下一级子机构
		List<Long> idList = commonDAO.find(hql, brchTreeCode+"%");
		return idList;
	}

	public void batchBuildRoleActor(Long taskId,Long takeBrchId, Long toBrchId, List<Long> beRoleIdList, List<Long> unRoleIdList, boolean isSameSubBrch) {
		//----------全部删除
		List<Long> delBrchIdList = new ArrayList<Long>();
		delBrchIdList.add(takeBrchId);		
		if (isSameSubBrch){
			Branch branch = (Branch)this.commonDAO.find("from  Branch where brchId = ? ", takeBrchId).get(0);
			List<Long> subBrchIdList = this.getSubBrchIdByBrchTreeCode(branch.getTreeCode());
			delBrchIdList.addAll(subBrchIdList);
		}
		List<Long> delRoleIdList = new ArrayList<Long>();
		delRoleIdList.addAll(beRoleIdList);
		delRoleIdList.addAll(unRoleIdList);
		
		String hql = "delete from HumnTaskActr where  taskId=? AND brchId in ("+this.joinStr(delBrchIdList)+")  " +
				"AND actrBrchId in ("+toBrchId+") AND actrRoleId in ("+this.joinStr(delRoleIdList)+")";
		this.commonDAO.bulkUpdate(hql, taskId);
		
		//---------增加
		HashSet<Long> addBrchIdList = new HashSet<Long>();
		addBrchIdList.add(takeBrchId);		
		if (isSameSubBrch){
			Branch branch = (Branch)this.commonDAO.find("FROM  Branch WHERE brchId = ? ", takeBrchId).get(0);
			List<Long> subBrchIdList = this.getSubBrchIdByBrchTreeCode(branch.getTreeCode());
			addBrchIdList.addAll(subBrchIdList);
			
		}	
		
		for (Long addBrchId : addBrchIdList) {
			for (Long addRoleId : beRoleIdList) {
				HumnTaskActr taskActr = new HumnTaskActr();
				taskActr.setBrchId(addBrchId);
				taskActr.setTaskId(taskId);
				taskActr.setActrBrchId(toBrchId);
				taskActr.setActrRoleId(addRoleId);
				this.humanTaskActrDAO.save(taskActr);
			}
		}
		
	}

	@Override
	public void transfor(Long tid, Long userId) {
		if (tid == null ) {
			ExceptionManager.throwException(ServiceException.class, ErrorCodeConst.COMMON_ERROR_CODE, "没有要转发的任务");
		}
		if (userId == null) {
			ExceptionManager.throwException(ServiceException.class, ErrorCodeConst.COMMON_ERROR_CODE, "没有转发人");
		}
		
		TaskInstance taskInstance = taskInstanceDao.getTaskInstance(tid);
		if (taskInstance.getEnd() != null) {
			ExceptionManager.throwException(ServiceException.class, ErrorCodeConst.COMMON_ERROR_CODE, "已处理的任务不能转发");
		}
		
		holdTask(tid, userId, true);
		
		
		
		UserLogonInfo uli = SessionTool.getUserLogonInfo();
		InstanceBusiness ib = instanceBusinessDao.getInstanceBusinessByInstanceId(taskInstance.getProcessInstance().getId());
		Buser user = userService.getUserById(userId);
		
		TaskTraceInfo tti = new TaskTraceInfo();
		tti.setDealUserId(uli.getSysUserId());
		tti.setIsAgree(ProcessBehavior.TRANSFOR.getName());
		tti.setEntityId(ib.getId());
		tti.setDealTime(new Date());
		tti.setTaskName(taskInstance.getDescription());
		tti.setDealUserName(uli.getUserName());
		tti.setProcessName(taskInstance.getProcNameKey());
		tti.setRemark("任务转发给：" + user.getUserName());
		
		taskTraceInfoService.saveTaskTraceInfo(tti);
		
	}

	@Override
	public void transfor(List<Long> tids, Long userId) {
		if (tids != null && !tids.isEmpty()) {
			for (Long tid : tids) {
				transfor(tid, userId);
			}
		}
	}
	
	
}
