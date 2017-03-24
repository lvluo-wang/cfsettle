package com.upg.ucars.framework.bpm.tasktrace.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.springframework.beans.factory.annotation.Autowired;

import com.upg.ucars.basesystem.security.core.user.IUserService;
import com.upg.ucars.constant.CommonConst;
import com.upg.ucars.framework.base.BaseService;
import com.upg.ucars.framework.bpm.assign.dao.IHumanTaskActorDelegatorDao;
import com.upg.ucars.framework.bpm.assign.dao.ITaskInstanceDao;
import com.upg.ucars.framework.bpm.base.IInstanceBusinessDao;
import com.upg.ucars.framework.bpm.tasktrace.dao.ITaskTraceInfoDAO;
import com.upg.ucars.mapping.basesystem.security.Buser;
import com.upg.ucars.mapping.framework.bpm.HumnTaskActorDelegate;
import com.upg.ucars.mapping.framework.bpm.InstanceBusiness;
import com.upg.ucars.mapping.framework.bpm.TaskTraceInfo;
import com.upg.ucars.model.DealOpinionInfo;
import com.upg.ucars.util.DateTimeUtil;

public class TaskTraceInfoServiceImpl extends BaseService implements
		ITaskTraceInfoService {

	private ITaskTraceInfoDAO	taskTraceInfoDAO;	// spring set

	@Autowired
	private IUserService		userService;
	@Autowired
	private IInstanceBusinessDao	instanceBusinessDao;
	
	@Autowired
	private ITaskInstanceDao	taskInstanceDao;
	
	@Autowired
	private IHumanTaskActorDelegatorDao	humanTaskActorDelegatorDao;

	public void saveTaskTraceInfo(TaskTraceInfo tti) {
		this.taskTraceInfoDAO.save(tti);

	}

	public void updateTaskTraceInfo(TaskTraceInfo tti) {
		this.taskTraceInfoDAO.update(tti);

	}

	public void deleteByTaskId(Long taskId) {
		this.taskTraceInfoDAO.delete(this.taskTraceInfoDAO.getByTaskId(taskId));

	}

	public ITaskTraceInfoDAO getTaskTraceInfoDAO() {
		return taskTraceInfoDAO;
	}

	public void setTaskTraceInfoDAO(ITaskTraceInfoDAO taskTraceInfoDAO) {
		this.taskTraceInfoDAO = taskTraceInfoDAO;
	}

	public void traceTaskDeal(Long taskId, Long entityId,
			DealOpinionInfo opinionInfo) {
		TaskTraceInfo tti = this.taskTraceInfoDAO.getByTaskId(taskId);
		if (tti == null) {
			tti = new TaskTraceInfo();
		}
		tti.setTaskId(taskId);
		tti.setEntityId(entityId);
		tti.setDealUserId(opinionInfo.getUserId());
		tti.setDealUserName(userService.getUserById(opinionInfo.getUserId())
				.getUserName());
		tti.setProcessName(opinionInfo.getProcessName());
		tti.setTaskName(opinionInfo.getTaskName());
		tti.setDealTime(DateTimeUtil.getNowDateTime());
		tti.setIsAgree(opinionInfo.getAgree());
		tti.setRemark(opinionInfo.getRemark());
		tti.setBusinessData(opinionInfo.getBusinessData());
		tti.setBusinessType(opinionInfo.getBusinessType());
		tti.setApprovalOpinion(opinionInfo.getApprovalOpinion());
		this.taskTraceInfoDAO.saveOrUpdate(tti);
		//判断是否委托
		Long userId = opinionInfo.getUserId();
		if (userId != null) {
			TaskInstance taskInstance = taskInstanceDao.getTaskInstance(taskId);
			if (taskInstance != null) {
				List<HumnTaskActorDelegate> delegateList = humanTaskActorDelegatorDao.findHumnTaskActorDelegateByDelegator(userId);
				if (delegateList != null && !delegateList.isEmpty()) {
					Long delegatorId = null;
					List<Long> actorIds = new ArrayList<Long>();
					actorIds.add(tti.getDealUserId());
					if (StringUtils.isNotBlank(taskInstance.getPreActor()) ) {
						if (!taskInstance.getPreActor().contains(taskInstance.getActorId()+CommonConst.COMMA)) {
							String[] candidateArray = taskInstance.getPreActor().trim().split(CommonConst.COMMA);
							for (String candidateId : candidateArray) {
								if (StringUtils.isNotBlank(candidateId)) {
									actorIds.add(Long.valueOf(candidateId.trim()));
								}
							}
						}
					} else {
						for (HumnTaskActorDelegate delegate : delegateList) {
							actorIds.add(delegate.getActor());
						}
					}
					
					if (!actorIds.isEmpty()) {
						List<Long> candidates = humanTaskActorDelegatorDao.findActorByTaskIdAndDelegator(taskId, actorIds);
						if (candidates != null && !candidates.isEmpty() && !candidates.contains(tti.getDealUserId())) {
							delegatorId = candidates.get(0);
						}
					}
					
					if (delegatorId != null) {
						Buser user = userService.getUserById(delegatorId);
						if (user != null) {
							tti.setDelegateUser(user.getUserName());
							for (HumnTaskActorDelegate delegate : delegateList) {
								if (delegatorId.equals(delegate.getActor())) {
									tti.setDelegateId(delegate.getId());
								}
							}
						}
					}
				}
			}
		}
	}

	public List<TaskTraceInfo> getTaskTraceInfoList(Long entityId,String processName) {
		return taskTraceInfoDAO.getTaskTraceInfoList(entityId,processName);
	}

	@SuppressWarnings("deprecation")
	@Override
	public List<TaskTraceInfo> getTaskTraceInfoList(Long entityId, String processName, String taskName) {
		return taskTraceInfoDAO.getTaskTraceInfoList(entityId,processName,taskName);
	}

	public TaskTraceInfo getTaskTraceInfoByTaskId(Long taskId) {
		return taskTraceInfoDAO.getByTaskId(taskId);
	}

	public void traceTaskDealWithoutTask(Long taskId, Long entityId,
			DealOpinionInfo opinionInfo) {
		// TODO Auto-generated method stub
		
	}

	public List<TaskTraceInfo> getTaskTraceInfoList(Long instanceBusinessId) {
		List<TaskTraceInfo> list = null;
		if (instanceBusinessId != null) {
			InstanceBusiness instanceBusiness = instanceBusinessDao.get(instanceBusinessId);
			if (instanceBusiness != null) {
				String entityType = instanceBusiness.getEntityType();
				Long entityId = instanceBusiness.getEntityId();
				list = taskTraceInfoDAO.getTaskTraceInfoListByEntityIdAndType(entityId, entityType);
			}
		}
		if (list == null) {
			list = Collections.emptyList();
		}
		return list;
	}
	
	public List<TaskTraceInfo> getInstanceTaskTraceInfoList(Long instanceBusinessId){
		return taskTraceInfoDAO.getInstanceTaskTraceInfoList(instanceBusinessId);
	}
	
	public boolean hasTaskTraceInfo(Long instanceBusinessId){
		return taskTraceInfoDAO.hasTaskTraceInfo(instanceBusinessId);
	}

	@Override
	public List<TaskTraceInfo> getTaskTraceInfoByDelegateId(Long delegateId) {
		return taskTraceInfoDAO.getTaskTraceInfoByDelegateId(delegateId);
		
	}

}
