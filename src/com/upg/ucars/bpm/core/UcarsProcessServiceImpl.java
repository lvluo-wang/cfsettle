package com.upg.ucars.bpm.core;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;
import org.jbpm.taskmgmt.def.Task;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.upg.ucars.basesystem.product.core.product.IProductService;
import com.upg.ucars.basesystem.security.core.branch.IBranchService;
import com.upg.ucars.basesystem.security.core.user.IUserService;
import com.upg.ucars.constant.ErrorCodeConst;
import com.upg.ucars.framework.annotation.Service;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.SessionTool;
import com.upg.ucars.framework.base.TaskCallback;
import com.upg.ucars.framework.bpm.assign.dao.IHumanTaskActorDelegatorDao;
import com.upg.ucars.framework.bpm.assign.dao.ITaskInstanceDao;
import com.upg.ucars.framework.bpm.assign.service.IHumanTaskAssignService;
import com.upg.ucars.framework.bpm.base.BPQueryCondition;
import com.upg.ucars.framework.bpm.base.BpmConstants;
import com.upg.ucars.framework.bpm.base.BpmControlDAO;
import com.upg.ucars.framework.bpm.base.BpmQueryDAO;
import com.upg.ucars.framework.bpm.base.IInstanceBusinessDao;
import com.upg.ucars.framework.bpm.base.ProcessBaseService;
import com.upg.ucars.framework.bpm.publish.service.IHumnTaskService;
import com.upg.ucars.framework.bpm.publish.service.IProcessDefService;
import com.upg.ucars.framework.bpm.tasktrace.service.ITaskTraceInfoService;
import com.upg.ucars.framework.exception.ExceptionManager;
import com.upg.ucars.framework.exception.ServiceException;
import com.upg.ucars.mapping.basesystem.product.ProductInfo;
import com.upg.ucars.mapping.basesystem.security.Branch;
import com.upg.ucars.mapping.basesystem.security.Buser;
import com.upg.ucars.mapping.framework.bpm.HumnTask;
import com.upg.ucars.mapping.framework.bpm.HumnTaskActorDelegate;
import com.upg.ucars.mapping.framework.bpm.InstanceBusiness;
import com.upg.ucars.mapping.framework.bpm.ProcessDef;
import com.upg.ucars.mapping.framework.bpm.TaskTraceInfo;
import com.upg.ucars.model.ConditionBean;
import com.upg.ucars.model.DealOpinionInfo;
import com.upg.ucars.model.TaskProcessResult;
import com.upg.ucars.model.security.UserLogonInfo;


/**
 * 流程服务
 * 
 * @author shentuwy
 * @date 2012-7-9
 * 
 */
@Service
public class UcarsProcessServiceImpl extends ProcessBaseService implements IUcarsProcessService {

	protected static Logger				log	= LoggerFactory.getLogger(UcarsProcessServiceImpl.class);

	@Autowired
	private IInstanceBusinessDao		instanceBusinessDao;
	@Autowired
	private IProcessDefService			processDefService;
	@Autowired
	private IHumnTaskService			humnTaskService;
	@Autowired
	private ITaskInstanceDao			taskInstanceDao;
	@Autowired
	private IUserService				userService;
	@Autowired
	private IHumanTaskAssignService		humanTaskAssignService;
	@Autowired
	private ITaskTraceInfoService		taskTraceInfoService;
	@Autowired
	private IProductService				productService;
	@Autowired
	private IBranchService				branchService;
	@Autowired
	private IHumanTaskActorDelegatorDao	humanTaskActorDelegatorDao;

	@Resource
	public void setBpmControlDAO(BpmControlDAO bpmControlDAO) {
		super.setBpmControlDAO(bpmControlDAO);
	}

	@SuppressWarnings("rawtypes")
	@Resource
	public void setBpmQueryDAO(BpmQueryDAO bpmQueryDAO) {
		super.setBpmQueryDAO(bpmQueryDAO);
	}

	public InstanceBusiness startProcessInstance(String prodNo, InstanceBusiness instanceBusiness,
			Map<String, Object> variableMap) {
		
		if (log.isInfoEnabled()) {
			log.info("prodNo=" + String.valueOf(prodNo) + ",variableMap=" + String.valueOf(variableMap) + ",entity="
					+ String.valueOf(instanceBusiness));
		}
		
		if (instanceBusiness == null) {
			ExceptionManager.throwException(ServiceException.class, ErrorCodeConst.COMMON_ERROR_CODE, "流程实例业务为空");
		}
			
		if (variableMap == null) {
			variableMap = new HashMap<String, Object>();
		}
		UserLogonInfo uli = SessionTool.getUserLogonInfo(true);
		Long brchId = uli.getBranchId();
		if (instanceBusiness.getBrchId() != null) {
			brchId = instanceBusiness.getBrchId();
		}
		// 设置当前发起人
		if (instanceBusiness.getCreator() == null) {
			instanceBusiness.setCreator(uli.getSysUserId());
		}
		if (StringUtils.isBlank(instanceBusiness.getCreatorName())) {
			instanceBusiness.setCreatorName(uli.getUserName());
		}

		//		String processName = this.getProcessDefName(brchId, prodNo);
		//		ProcessDef processDef = processDefService.getProcessDefByName(processName);

		// 设置流程名和名称
		//		instanceBusiness.setProcessName(processName);
		//		instanceBusiness.setProcessCnName(processDef.getProcCnName());

		instanceBusiness.setProdNo(prodNo);
		ProductInfo productInfo = productService.getProductByProdNo(prodNo);
		instanceBusiness.setProdName(productInfo.getProdName());

		instanceBusiness.setMiNo(uli.getMiNo());

		instanceBusinessDao.save(instanceBusiness);

		// 启动流程
		Long instanceId = this.startProcessInstance(brchId, prodNo, instanceBusiness, variableMap);

		ProcessDefinition definition = this.getBpmQueryDAO().getProcessInstance(instanceId).getProcessDefinition();
		instanceBusiness.setProcessName(definition.getName());
		instanceBusiness.setProcessCnName(definition.getDescription());

		instanceBusiness.setInstanceId(instanceId);
		instanceBusinessDao.update(instanceBusiness);
		return instanceBusiness;
	}

	@SuppressWarnings("unchecked")
	public List<InstanceBusiness> getTaskList(Long userId, TaskSearchBean searchBean, Page page) {

		BPQueryCondition condition = getBpQueryConditon(searchBean);

		String taskName = null;
		if (searchBean != null) {
			taskName = searchBean.getStatusType();
		}

		List<Long> userIds = new ArrayList<Long>();

		if (StringUtils.isBlank(searchBean.getDelegate())) {
			userIds.add(userId);
			List<HumnTaskActorDelegate> delegateList = humanTaskActorDelegatorDao
					.findHumnTaskActorDelegateByDelegator(userId);
			if (delegateList != null && delegateList.size() > 0) {
				for (HumnTaskActorDelegate htad : delegateList) {
					if (htad.getActor() != null) {
						userIds.add(htad.getActor());
					}
				}
			}
		} else {
			if (StringUtils.equals("1", searchBean.getDelegate())) {
				List<HumnTaskActorDelegate> delegateList = humanTaskActorDelegatorDao
						.findHumnTaskActorDelegateByDelegator(userId);
				if (delegateList != null && delegateList.size() > 0) {
					for (HumnTaskActorDelegate htad : delegateList) {
						if (htad.getActor() != null) {
							userIds.add(htad.getActor());
						}
					}
				}
			} else {
				userIds.add(userId);
			}
		}

		if (userIds.isEmpty()) {
			return new ArrayList<InstanceBusiness>(0);
		}

		List<InstanceBusiness> list = this.getBpmQueryDAO().queryTaskAndEntityByConditon(condition, userIds, taskName,
				page);

		return list;
	}

	@SuppressWarnings("unchecked")
	public List<InstanceBusiness> getSimpleTaskList(Long userId, TaskSearchBean searchBean, Page page) {
		List<InstanceBusiness> result = new ArrayList<InstanceBusiness>();
		if (searchBean.isMiNoCondition()) {
			UserLogonInfo uli = SessionTool.getUserLogonInfo();
			if (uli != null) {
				searchBean.setMiNo(uli.getMiNo());
			} else {
				if (userId != null) {
					Buser user = userService.getUserById(userId);
					searchBean.setMiNo(user.getMiNo());
				}
			}
		}
		BPQueryCondition condition = getBpQueryConditon(searchBean);
		String taskName = null;
		if (searchBean != null) {
			taskName = searchBean.getStatusType();
		}
		result = this.getBpmQueryDAO().querySimpleTaskAndEntity(condition, userId, taskName, page);
		return result;
	}

	private BPQueryCondition getBpQueryConditon(TaskSearchBean searchBean) {
		String hql = "from InstanceBusiness instanceBusiness";
		BPQueryCondition condition = new BPQueryCondition(hql);
		condition.setEntityAlias("instanceBusiness");
		if (searchBean != null) {
			String col1 = searchBean.getCol1();
			if (StringUtils.isNotBlank(col1)) {
				condition.addCondition(new ConditionBean("instanceBusiness.col1", ConditionBean.LIKE, col1));
			}
			List<String> processTypes = searchBean.getProcessTypes();
			if (processTypes != null && processTypes.size() > 0) {
				condition
						.addCondition(new ConditionBean("instanceBusiness.processName", ConditionBean.IN, processTypes));
			}
			List<String> entityTypeList = searchBean.getEntityTypeList();
			if (entityTypeList != null && entityTypeList.size() > 0) {
				condition.addCondition(new ConditionBean("instanceBusiness.entityType", ConditionBean.IN,
						entityTypeList));
			}
			Long entityId = searchBean.getEntityId();
			if (entityId != null) {
				condition.addCondition(new ConditionBean("instanceBusiness.entityId", entityId));
			}
			String miNo = searchBean.getMiNo();
			if (StringUtils.isNotBlank(miNo)) {
				condition.addCondition(new ConditionBean("instanceBusiness.miNo", miNo));
			}
			Date fromDate = searchBean.getFromDate();
			if (fromDate != null) {
				condition.addCondition(new ConditionBean("instanceBusiness.createTime", ConditionBean.MORE_AND_EQUAL,
						fromDate));
			}
			Date toDate = searchBean.getToDate();
			if (toDate != null) {
				condition.addCondition(new ConditionBean("instanceBusiness.createTime", ConditionBean.LESS_AND_EQUAL,
						DateUtils.addDays(toDate, 1)));
			}

			Date taskEndFrom = searchBean.getTaskEndFrom();
			if (taskEndFrom != null) {
				condition.addCondition(new ConditionBean("ti.end", ConditionBean.MORE_AND_EQUAL, taskEndFrom));
			}
			Date taskEndTo = searchBean.getTaskEndTo();
			if (taskEndTo != null) {
				condition.addCondition(new ConditionBean("ti.end", ConditionBean.LESS_AND_EQUAL, DateUtils.addDays(
						taskEndTo, 1)));
			}
			String col10 = searchBean.getCol10();
			if (StringUtils.isNotBlank(col10)) {
				condition.addCondition(new ConditionBean("instanceBusiness.col10", ConditionBean.IN, col10.split(",")));
			}

			String description = searchBean.getTaskDescription();
			if (StringUtils.isNotBlank(description)) {
				condition.addCondition(new ConditionBean("ti.description", ConditionBean.LIKE, description));
			}
			String holdCond = searchBean.getHoldCond();
			if (StringUtils.isNotBlank(holdCond)) {
				if (StringUtils.equals(TaskSearchBean.TASK_HOLD_DONE, holdCond)) {
					condition.setHasActor(Boolean.TRUE);
				} else if (StringUtils.equals(TaskSearchBean.TASK_HOLD_TODO, holdCond)) {
					condition.setHasActor(Boolean.FALSE);
				}
			}
			String instanceStatus = searchBean.getInstanceStatus();
			if (StringUtils.isNotBlank(instanceStatus)) {
				if (IInstanceBusinessDao.INSTANCE_STATUS_AUDIT.equals(instanceStatus)) {
					condition.addCondition(new ConditionBean("from ti.processInstance pi where pi.end is null",
							ConditionBean.EXISTS, null));
				} else if (IInstanceBusinessDao.INSTANCE_STATUS_END_NORMAL.equals(instanceStatus)) {
					condition
							.addCondition(new ConditionBean(
									"from ti.processInstance pi where pi.end is not null and size(pi.rootToken.node.leavingTransitions) = 0 ",
									ConditionBean.EXISTS, null));
				} else if (IInstanceBusinessDao.INSTANCE_STATUS_END_REJECT.equals(instanceStatus)) {
					condition
							.addCondition(new ConditionBean(
									"from ti.processInstance pi where pi.end is not null and size(pi.rootToken.node.leavingTransitions) > 0 ",
									ConditionBean.EXISTS, null));
				}
			}
			String brchIds = searchBean.getBrchIds();
			if (StringUtils.isNotBlank(brchIds)) {
				String[] brchIdArray = brchIds.split(",");
				Set<Long> brchIdSet = new HashSet<Long>();
				for (String brchId : brchIdArray) {
					if (StringUtils.isNotBlank(brchId)) {
						Long id = Long.valueOf(brchId);
						brchIdSet.add(id);
						Branch branch = branchService.getBranchByBrchId(id);
						if (branch != null) {
							List<Branch> subBranchList = branchService.getSubBranchList(branch.getTreeCode(), miNo);
							if (subBranchList != null && subBranchList.size() > 0) {
								for (Branch brch : subBranchList) {
									brchIdSet.add(brch.getBrchId());
								}
							}
						}
					}
				}
				if (!brchIdSet.isEmpty()) {
					condition.addCondition(new ConditionBean("ti.brchId", ConditionBean.IN, brchIdSet));
				}
			}
		}
		return condition;
	}

	public HumnTask getHumnTask(String processName, String taskName) {
		HumnTask humnTask = humnTaskService.getHumnTaskByProcessAndTaskName(processName, taskName);
		return humnTask;
	}

	private DealOpinionInfo getDealOpinionInfo(TaskProcessResult result) {
		DealOpinionInfo ret = new DealOpinionInfo();
		UserLogonInfo uli = SessionTool.getUserLogonInfo(true);
		ret.setAgree(result.getPass());
		ret.setRemark(result.getRemark());
		ret.setUserId(uli.getSysUserId());
		ret.setUserName(uli.getUserName());
		ret.setBusinessData(result.getBusinessData());
		ret.setBusinessType(result.getBusinessType());
		ret.setApprovalOpinion(result.getApprovalOpinion());
		return ret;
	}

	public void dealTask(Long taskId, TaskProcessResult result) {
		dealTask(taskId, result, null, null);
	}

	public void dealTask(Long taskId, TaskProcessResult result, Map<String, Object> map, Map<String, Object> callbackMap) {
		Map<String, Object> variableMap = new HashMap<String, Object>();
		variableMap.put(TASK_PROCESS_RESULT_KEY, result.getPass());
		// variableMap.put("remark", result.getRemark());
		if (map != null) {
			variableMap.putAll(map);
		}
		variableMap.put(BpmConstants.VAR_TASK_OPINION, getDealOpinionInfo(result));
		if (!StringUtils.equals(TASK_PROCESS_RESULT_REJECT, result.getPass())) {
			Long nextUserId = result.getNextUser();
			if (nextUserId != null) {
				variableMap.put(BpmConstants.VAR_NEXT_USER_ID, nextUserId);
			}
			String transitionName = null;
			if (!TASK_PROCESS_RESULT_PASS.equals(result.getPass())) {
				transitionName = result.getPass();
			}
			this.dealTask(taskId, transitionName, variableMap);
		} else { // 结束流程
			this.onlyEndTask(taskId, variableMap);
			this.endProcessInstanceByTaskId(taskId);
		}
		processCallback(callbackMap);
	}

	public void checkDealTaskPerm(Long taskId, Long userId) {
		if (taskId == null) {
			throw new IllegalArgumentException("taskId is null");
		}
		if (userId == null) {
			throw new IllegalArgumentException("userId is null");
		}
		Long actorId = taskInstanceDao.getTaskHoldActor(taskId);
		if (actorId == null) {
			// ExceptionManager.throwException(ServiceException.class,
			// ERROR_PROCESS_COMMON, new String[] { "任务还没有被领取" });
			humanTaskAssignService.holdTask(taskId, userId);
		} else if (!userId.equals(actorId)) {
			List<HumnTaskActorDelegate> delegateList = humanTaskActorDelegatorDao
					.findHumnTaskActorDelegateByDelegator(userId);
			List<Long> actorList = new ArrayList<Long>();
			if (delegateList != null && delegateList.size() > 0) {
				for (HumnTaskActorDelegate ad : delegateList) {
					if (ad.getActor() != null) {
						actorList.add(ad.getActor());
					}
				}
			}
			if (actorList.contains(actorId)) {
				humanTaskAssignService.holdTask(taskId, userId, true);
			} else {
				Buser user = userService.getUserById(actorId);
				String msg = "任务已经被" + (user != null ? user.getUserName() : "") + "领取";
				ExceptionManager.throwException(ServiceException.class, ERROR_PROCESS_COMMON, new String[] { msg });
			}
		}
	}

	public Object getEntityById(ExecutionContext context) {
		Object ret = null;
		if (context != null) {
			ret = instanceBusinessDao.getEntityById(context.getProcessInstance().getEntityId());
		}
		return ret;
	}

	public InstanceBusiness getInstanceBusiness(ExecutionContext context) {
		return instanceBusinessDao.get(context.getProcessInstance().getEntityId());
	}

	public void saveProcessResult(Long taskId, TaskProcessResult result, Map<String, Object> callbackMap) {
		DealOpinionInfo opinionInfo = getDealOpinionInfo(result);
		taskTraceInfoService.traceTaskDeal(taskId, null, opinionInfo);
		processCallback(callbackMap);
	}

	@SuppressWarnings("unchecked")
	private void processCallback(Map<String, Object> callbackMap) {
		if (callbackMap != null) {
			TaskCallback bean = (TaskCallback) callbackMap.get(CALLBACK_BEAN_NAME);
			if (bean != null) {
				Map<String, Object> param = (Map<String, Object>) callbackMap.get(CALLBACK_METHOD_PARAM);
				bean.callback(param);
			}
		}
	}

	public TaskProcessResult getTaskProcessResult(Long taskId) {
		TaskProcessResult processResult = null;
		TaskTraceInfo tti = taskTraceInfoService.getTaskTraceInfoByTaskId(taskId);
		if (tti != null) {
			processResult = new TaskProcessResult();
			processResult.setDealUserId(tti.getDealUserId());
			processResult.setDealTime(tti.getDealTime());
			processResult.setPass(tti.getIsAgree());
			processResult.setRemark(tti.getRemark());
			processResult.setBusinessData(tti.getBusinessData());
			processResult.setApprovalOpinion(tti.getApprovalOpinion());
		}
		return processResult;
	}

	public void dealTask(Long instanceBusinessId, String taskName, TaskProcessResult result,
			Map<String, Object> variableMap) {
		if (instanceBusinessId == null || StringUtils.isBlank(taskName) || result == null) {
			throw new IllegalArgumentException("instanceBusinessId or taskName or result is empty");
		}
		InstanceBusiness instanceBusiness = instanceBusinessDao.get(instanceBusinessId);
		TaskInstance taskInstance = this.getTaskInstance(instanceBusiness.getInstanceId(), taskName);
		if (taskInstance != null) {
			dealTask(taskInstance.getId(), result, variableMap, null);
		} else {
			// throw new RuntimeException("taskName=" + String.valueOf(taskName)
			// + "已经被处理或不存在");
			ExceptionManager.throwException(ServiceException.class, ErrorCodeConst.COMMON_ERROR_CODE, "taskName="
					+ String.valueOf(taskName) + "不存在或已经被处理");
		}

	}

	public Object getVariable(Long processId, String name) {
		return this.getProcessVariable(processId, name);
	}

	public Object getVariableByInstanceBusiness(Long instanceBusinessId, String name) {
		InstanceBusiness instanceBusiness = getInstanceBusiness(instanceBusinessId);
		return getVariable(instanceBusiness.getInstanceId(), name);
	}

	@SuppressWarnings("unchecked")
	public List<InstanceBusiness> getDoneTaskList(Long userId, TaskSearchBean searchBean, Page page) {

		BPQueryCondition condition = getBpQueryConditon(searchBean);

		if (StringUtils.isNotBlank(searchBean.getDelegate())) {
			condition.addCondition(new ConditionBean("trace.delegateUser", ConditionBean.LIKE, "%"
					+ searchBean.getDelegate().trim() + "%"));
		}

		condition.setHql(condition.getHql() + " order by ti.end desc ");

		return this.getBpmQueryDAO().queryDoneTaskAndEntity(condition, userId, page);
	}

	public InstanceBusiness getInstanceBusiness(Long businessInstanceId) {
		return instanceBusinessDao.get(businessInstanceId);
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getTodoTaskList(Long processInstanceId) {
		return this.getBpmQueryDAO().getTodoTaskList(processInstanceId);
	}

	public boolean isAssessMeetingProduct(Long brchId, String prodNo) {
		boolean ret = false;
		if (brchId != null && StringUtils.isNotBlank(prodNo)) {
			String processName = this.getProcessDefNameIgnoreNoProcess(brchId, prodNo);
			ret = isAssessMeetingProcess(processName);
		}
		return ret;
	}

	public boolean isAssessMeetingProductUsedByProcess(Long brchId, String prodNo) {
		boolean ret = false;
		if (brchId != null && StringUtils.isNotBlank(prodNo)) {
			String processName = this.getProcessDefName(brchId, prodNo);
			ret = isAssessMeetingProcess(processName);
		}
		return ret;
	}

	public boolean isAssessMeetingProcess(String processName) {
		boolean ret = false;
		if (StringUtils.isNotBlank(processName)
				&& (processName.startsWith("assessMeeting") || processName.startsWith("guaranteeAssessMeeting")
						|| processName.startsWith("nonStand") || processName.startsWith("debtNonStandard")
						|| processName.startsWith("privatelyRaisedBonds") || processName.startsWith("goodGatherICBC")
						|| processName.startsWith("internationalTradeNonStandard")
						|| processName.startsWith("performanceNonStand") || processName.startsWith("listingServices") || processName
							.startsWith("prjFinancingAssessMeeting"))) {
			ret = true;
		}
		return ret;
	}

	public TaskTraceInfo getTaskTraceInfoByTaskId(Long taskId) {
		TaskTraceInfo taskTraceInfo = null;
		if (taskId != null) {
			taskTraceInfo = taskTraceInfoService.getTaskTraceInfoByTaskId(taskId);
		}
		return taskTraceInfo;
	}

	public void endProcess(Long instanceBusinessId) {
		if (instanceBusinessId != null) {
			InstanceBusiness instanceBusiness = instanceBusinessDao.get(instanceBusinessId);
			if (instanceBusiness != null && instanceBusiness.getInstanceId() != null) {
				this.getBpmControlDAO().endProcessInstance(instanceBusiness.getInstanceId());
			}
		}
	}

	public boolean canCancelProcess(Long instanceBusinessId) {
		return !taskTraceInfoService.hasTaskTraceInfo(instanceBusinessId);
	}

	public List<InstanceBusiness> getInstanceBusinessList(InstanceBusinessSearchBean searchBean, Page page) {
		return instanceBusinessDao.getInstanceBusinessList(searchBean, page);
	}

	public String getInstanceStatus(Long instanceId) {
		String result = null;
		if (instanceId != null) {
			ProcessInstance pi = getBpmQueryDAO().getProcessInstance(instanceId);
			if (pi != null) {
				if (pi.getEnd() == null) {
					result = IInstanceBusinessDao.INSTANCE_STATUS_AUDIT;
				} else {
					List transitions = pi.getRootToken().getNode().getLeavingTransitions();
					if (transitions != null && transitions.size() > 0) {
						result = IInstanceBusinessDao.INSTANCE_STATUS_END_REJECT;
					} else {
						result = IInstanceBusinessDao.INSTANCE_STATUS_END_NORMAL;
					}
				}
			}
		}
		return result;
	}

	public Map<String, String> getLeaveTransitionMapByTask(Long taskId) {
		return getBpmControlDAO().getLeaveTransitionMapByTask(taskId);
	}

	@Override
	public boolean hasActiveProcess(String prodNo, Long entityId, Class<?> clz) {
		boolean result = false;
		if (StringUtils.isNotBlank(prodNo) && entityId != null && clz != null) {
			UserLogonInfo uli = SessionTool.getUserLogonInfo();
			String processName = getProcessDefNameIgnoreNoProcess(uli.getBranchId(), prodNo);
			if (StringUtils.isBlank(processName)) {
				ExceptionManager.throwException(ServiceException.class, ErrorCodeConst.COMMON_ERROR_CODE, "找不到相应产品的流程");
			}
			InstanceBusinessSearchBean searchBean = new InstanceBusinessSearchBean();
			searchBean.setEntityType(clz.getName());
			List<String> processNameList = new ArrayList<String>();
			processNameList.add(processName);
			searchBean.setProcessNameList(processNameList);
			searchBean.setEntityId(entityId);
			List<InstanceBusiness> instanceBusinessList = instanceBusinessDao.getInstanceBusinessList(searchBean, null);
			if (instanceBusinessList != null && instanceBusinessList.size() > 0) {
				for (InstanceBusiness ib : instanceBusinessList) {
					if (!getBpmQueryDAO().isProcessEnd(ib.getInstanceId())) {
						result = true;
						break;
					}
				}
			}
		}
		return result;
	}

	public boolean isEntityInProcessNode(String prodNo, Long entityId, Class<?> clz, String taskName) {
		boolean result = false;
		if (StringUtils.isNotBlank(prodNo) && entityId != null && clz != null && StringUtils.isNotBlank(taskName)) {
			UserLogonInfo uli = SessionTool.getUserLogonInfo();
			String processName = getProcessDefNameIgnoreNoProcess(uli.getBranchId(), prodNo);
			if (StringUtils.isBlank(processName)) {
				ExceptionManager.throwException(ServiceException.class, ErrorCodeConst.COMMON_ERROR_CODE, "找不到相应产品的流程");
			}
			TaskSearchBean searchBean = new TaskSearchBean();
			List<String> processTypes = new ArrayList<String>();
			processTypes.add(processName);
			searchBean.setProcessTypes(processTypes);
			List<String> entityTypeList = new ArrayList<String>();
			entityTypeList.add(clz.getName());
			searchBean.setEntityTypeList(entityTypeList);
			searchBean.setEntityId(entityId);
			searchBean.setStatusType(taskName);
			List<InstanceBusiness> taskList = getSimpleTaskList(null, searchBean, null);
			if (taskList != null && taskList.size() > 0) {
				result = true;
			}
		} else {
			// throw new IllegalArgumentException("parameters has null,{prodNo="
			// + prodNo + ",entityId=" + entityId + ",clz=" + clz + ",taskName="
			// + taskName + "}");
			return false;
		}
		return result;
	}

	public TaskProcessResult getTaskProcessResult(Long instanceBusinessId, String taskName) {
		TaskProcessResult result = null;
		if (instanceBusinessId != null && StringUtils.isNotBlank(taskName)) {
			InstanceBusiness instanceBusiness = instanceBusinessDao.get(instanceBusinessId);
			if (instanceBusiness != null && instanceBusiness.getInstanceId() != null) {
				TaskInstance taskInstance = getBpmQueryDAO()
						.getTaskInstance(instanceBusiness.getInstanceId(), taskName);
				if (taskInstance != null) {
					result = getTaskProcessResult(taskInstance.getId());
				}
			}
		}
		return result;
	}

	public TaskProcessResult getLastTaskProcessResult(Long instanceBusinessId, String taskName) {
		TaskProcessResult result = null;
		if (instanceBusinessId != null && StringUtils.isNotBlank(taskName)) {
			InstanceBusiness instanceBusiness = instanceBusinessDao.get(instanceBusinessId);
			if (instanceBusiness != null && instanceBusiness.getInstanceId() != null) {
				List<TaskInstance> tiList = getBpmQueryDAO().getTaskInstanceList(instanceBusiness.getInstanceId(),
						taskName);
				if (tiList != null && tiList.size() > 0) {
					for (TaskInstance ti : tiList) {
						TaskProcessResult otherResult = getTaskProcessResult(ti.getId());
						if (otherResult != null) {
							if (result != null) {
								if (otherResult.getDealTime().compareTo(result.getDealTime()) > 0) {
									result = otherResult;
								}
							} else {
								result = otherResult;
							}
						}
					}
				}
			}
		}
		return result;
	}

	public String getProcessedConditionHql(String entityIdIdentifier, Class<?> entityClass) {
		StringBuilder sb = new StringBuilder();
		sb.append("select 1 from InstanceBusiness ib,org.jbpm.taskmgmt.exe.TaskInstance ti where ib.id=ti.entityId and ti.end is not null and ti.actorId=:actorId");
		if (StringUtils.isNotBlank(entityIdIdentifier)) {
			sb.append(" and ib.entityId=").append(entityIdIdentifier);
		}
		if (entityClass != null) {
			sb.append(" and ib.entityType='").append(entityClass.getName()).append("'");
		}

		return sb.toString();
	}

	@Override
	public void changeProcessStarter(Long instanceBusinessId, Long userId) {
		if (instanceBusinessId != null && userId != null) {
			Buser user = userService.getUserById(userId);
			InstanceBusiness instanceBusiness = instanceBusinessDao.get(instanceBusinessId);
			instanceBusiness.setCreatorName(user.getUserName());
			Long brchId = user.getBrchId();
			changeProcessBranch(instanceBusinessId, brchId);
		}
	}

	@Override
	public void changeProcessBranch(Long instanceBusinessId, Long brchId) {
		if (instanceBusinessId != null && brchId != null) {
			validateProcessInfo(instanceBusinessId);
			InstanceBusiness instanceBusiness = instanceBusinessDao.get(instanceBusinessId);
			Long processId = instanceBusiness.getInstanceId();
			ProcessInstance pi = getBpmQueryDAO().getProcessInstance(processId);
			if (!pi.hasEnded()) {
				pi.setBrchId(brchId);
				List<Map<String, Object>> getTodoTaskList = getTodoTaskList(processId);
				for (Map<String, Object> taskMap : getTodoTaskList) {
					Long taskId = ((Number) taskMap.get("ID_")).longValue();
					TaskInstance ti = getBpmQueryDAO().getTaskInstanceById(taskId);
					ti.setBrchId(brchId);
				}
			}

		}
	}

	private void validateProcessInfo(Long instanceBusinessId) {
		if (instanceBusinessId != null) {
			InstanceBusiness instanceBusiness = instanceBusinessDao.get(instanceBusinessId);
			if (instanceBusiness == null || instanceBusiness.getInstanceId() == null) {
				ExceptionManager.throwException(ServiceException.class, ErrorCodeConst.COMMON_ERROR_CODE, "没有找到对应的流程");
			}
		}
	}

	@Override
	public Branch getProcessBranch(Long instanceBusinessId) {
		Branch result = null;
		if (instanceBusinessId != null) {
			validateProcessInfo(instanceBusinessId);
			InstanceBusiness instanceBusiness = instanceBusinessDao.get(instanceBusinessId);
			Long processId = instanceBusiness.getInstanceId();
			ProcessInstance pi = getBpmQueryDAO().getProcessInstance(processId);
			result = branchService.getBranchByBrchId(pi.getBrchId());
		}
		return result;
	}

	@Override
	public List<Task> getUndoTaskNode(Long instanceBusinessId) {
		List<Task> result = new ArrayList<Task>();
		if (instanceBusinessId != null) {
			InstanceBusiness instanceBusiness = getInstanceBusiness(instanceBusinessId);
			if (instanceBusiness != null && instanceBusiness.getInstanceId() != null) {
				ProcessInstance pi = getBpmQueryDAO().getProcessInstance(instanceBusiness.getInstanceId());
				if (pi != null && pi.getEnd() == null) {
					ProcessDefinition pd = pi.getProcessDefinition();
					Map<String, Task> taskMap = pd.getTaskMgmtDefinition().getTasks();
					Collection<TaskInstance> tiList = pi.getTaskMgmtInstance().getTaskInstances();
					List<String> hasGenerateTasks = new ArrayList<String>();
					BigDecimal sortNo = BigDecimal.ZERO;
					ProcessDef processDef = processDefService.getProcessDefByName(pd.getName());
					if (processDef != null) {
						if (tiList != null && tiList.size() > 0) {
							for (TaskInstance ti : tiList) {
								if (StringUtils.isNotBlank(ti.getTask().getName())) {
									hasGenerateTasks.add(ti.getTask().getName());
									HumnTask ht = humnTaskService.getHumnTaskByProcIdAndTaskName(processDef.getId(), ti
											.getTask().getName());
									if (ht != null && ht.getSortNo() != null && ht.getSortNo().compareTo(sortNo) > 0) {
										sortNo = ht.getSortNo();
									}
								}
							}
						}

						Map<String, Task> undoTaskMap = new HashMap<String, Task>();

						if (taskMap != null && taskMap.size() > 0) {
							for (Iterator<String> it = taskMap.keySet().iterator(); it.hasNext();) {
								String key = it.next();
								if (!hasGenerateTasks.contains(key)) {
									undoTaskMap.put(key, taskMap.get(key));
								}
							}
						}

						if (undoTaskMap != null && undoTaskMap.size() > 0) {
							List<HumnTask> humnTaskList = humnTaskService.getHumnTasksByProcId(processDef.getId());
							if (humnTaskList != null && humnTaskList.size() > 0) {
								Collections.sort(humnTaskList, IHumnTaskService.HUMN_TASK_COMPARATOR);
								for (int i = 0; i < humnTaskList.size(); i++) {
									HumnTask ht = humnTaskList.get(i);
									if (undoTaskMap.containsKey(ht.getTaskName())
											&& (ht.getSortNo() != null && ht.getSortNo().compareTo(sortNo) > 0)) {
										result.add(undoTaskMap.get(ht.getTaskName()));
									}
								}

							}
						}
					}
				}
			}
		}
		return result;
	}

	@Override
	public InstanceBusiness getInstanceBusinessByTaskId(Long taskId) {
		InstanceBusiness result = null;
		if (taskId != null && taskId.longValue() > 0) {
			TaskInstance ti = getBpmQueryDAO().getTaskInstanceById(taskId);
			if (ti != null) {
				ProcessInstance pi = ti.getProcessInstance();
				result = instanceBusinessDao.getInstanceBusinessByInstanceId(pi.getId());
			}
		}
		return result;
	}

	@Override
	public InstanceBusiness getInstanceBusinessByInstanceId(Long instanceId) {
		InstanceBusiness result = null;
		if (instanceId != null) {
			result = instanceBusinessDao.getInstanceBusinessByInstanceId(instanceId);
		}
		return result;
	}

	@Override
	public Object getEntityObject(Long instanceBusinessId) {
		Object result = null;
		if (instanceBusinessId != null) {
			result = instanceBusinessDao.getEntityById(instanceBusinessId);
		}
		return result;
	}

	@Override
	public String getProcessName(Long brchId, String prodNo) {
		String result = this.getProcessDefName(brchId, prodNo);
		return result;
	}

	@Override
	public boolean isProcessInNode(Long instanceBusinessId, String nodeName) {
		boolean result = false;
		if (instanceBusinessId != null && nodeName != null) {
			InstanceBusiness instanceBusiness = getInstanceBusiness(instanceBusinessId);
			if (instanceBusiness != null) {
				ProcessInstance pi = getBpmQueryDAO().getProcessInstance(instanceBusiness.getInstanceId());
				List<Token> tokenList = pi.findAllTokens();
				if (tokenList != null) {
					for (Token token : tokenList) {
						if (token.getEnd() == null && nodeName.equals(token.getNode().getName())) {
							result = true;
							break;
						}
					}
				}
			}
		}
		return result;
	}

	@Override
	public boolean hasTodoTaskInProcess(Long instanceBusinessId, String taskName) {
		boolean result = false;
		if ( instanceBusinessId != null && taskName != null) {
			InstanceBusiness instanceBusiness = getInstanceBusiness(instanceBusinessId);
			if (instanceBusiness != null && instanceBusiness.getInstanceId() != null) {
				List<Map<String, Object>> taskList = getTodoTaskList(instanceBusiness.getInstanceId());
				if (taskList != null && !taskList.isEmpty()) {
					for (Map<String,Object> task : taskList) {
						if (StringUtils.equals(taskName, String.valueOf(task.get("NAME_")))){
							result = true;
							break;
						}
					}
				}
			}
		}
		return result;
	}
}
