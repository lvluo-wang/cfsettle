package com.upg.ucars.bpm.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.jbpm.taskmgmt.def.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.upg.ucars.basesystem.UcarsHelper;
import com.upg.ucars.basesystem.dictionary.util.DictionaryUtil;
import com.upg.ucars.basesystem.product.core.product.IProductService;
import com.upg.ucars.basesystem.security.core.role.IRoleService;
import com.upg.ucars.basesystem.security.core.user.IUserService;
import com.upg.ucars.bpm.core.IUcarsProcessService;
import com.upg.ucars.bpm.core.InstanceBusinessSearchBean;
import com.upg.ucars.bpm.core.TaskSearchBean;
import com.upg.ucars.constant.CommonConst;
import com.upg.ucars.factory.DynamicPropertyTransfer;
import com.upg.ucars.framework.base.BaseAction;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.SessionTool;
import com.upg.ucars.framework.bpm.assign.service.IHumanTaskAssignService;
import com.upg.ucars.framework.bpm.base.Formatable;
import com.upg.ucars.framework.bpm.base.TaskTraceInfoFormat;
import com.upg.ucars.framework.bpm.publish.service.IHumnTaskService;
import com.upg.ucars.framework.bpm.publish.service.IProcessDefService;
import com.upg.ucars.framework.bpm.tasktrace.service.ITaskTraceInfoService;
import com.upg.ucars.mapping.basesystem.dictionary.Code;
import com.upg.ucars.mapping.basesystem.product.ProductInfo;
import com.upg.ucars.mapping.basesystem.security.Buser;
import com.upg.ucars.mapping.framework.bpm.HumnTask;
import com.upg.ucars.mapping.framework.bpm.InstanceBusiness;
import com.upg.ucars.mapping.framework.bpm.ProcessDef;
import com.upg.ucars.mapping.framework.bpm.TaskTraceInfo;
import com.upg.ucars.model.TaskProcessResult;
import com.upg.ucars.model.security.UserLogonInfo;
import com.upg.ucars.util.JsonUtils;
import com.upg.ucars.util.StringUtil;

/**
 * 
 * 
 * @author shentuwy
 * @date 2012-7-9
 * 
 */
public class ProcessAction extends BaseAction {

	/**
	 * 
	 */
	private static final long			serialVersionUID	= -7737973618344017395L;

	private static final Logger			LOG					= LoggerFactory.getLogger(ProcessAction.class);

	private IUcarsProcessService		ucarsProcessService;

	private ITaskTraceInfoService		taskTraceInfoService;

	private IProcessDefService			processService;

	private IHumanTaskAssignService		humnTaskAssignService;
	
	private IHumnTaskService			humnTaskService;

	private IProductService				productService;
	
	private IRoleService				roleService;
	
	/** 项目类型 */
	private List<Map<String, Object>>	projectType;

	private ProductInfo					productInfo;

	private TaskSearchBean				searchBean;

	private InstanceBusinessSearchBean	ibSearchBean;

	/** 任务ID */
	private Long						taskId;
	/** 流程名称 */
	private String						processName;

	private Long						entityId;

	private InstanceBusiness			ib;

	private TaskProcessResult			tpr;

	private IUserService				userService;
	
	private List<Code> 					instanceStatusList;
	
	private Long 						roleId;
	
	private String 						countType;
	
	private Buser						user;

	// private boolean showSaveInfo;

	/** 是否处理任务 */
	private transient boolean			dealTask			= true;

	public void setUcarsProcessService(IUcarsProcessService ucarsProcessService) {
		this.ucarsProcessService = ucarsProcessService;
	}

	public void setTaskTraceInfoService(ITaskTraceInfoService taskTraceInfoService) {
		this.taskTraceInfoService = taskTraceInfoService;
	}

	public void setProcessService(IProcessDefService processService) {
		this.processService = processService;
	}

	public void setHumnTaskAssignService(IHumanTaskAssignService humnTaskAssignService) {
		this.humnTaskAssignService = humnTaskAssignService;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public InstanceBusiness getIb() {
		return ib;
	}

	public void setIb(InstanceBusiness ib) {
		this.ib = ib;
	}

	public TaskProcessResult getTpr() {
		return tpr;
	}

	public void setTpr(TaskProcessResult tpr) {
		this.tpr = tpr;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public Long getEntityId() {
		return entityId;
	}

	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	public List<Map<String, Object>> getProjectType() {
		return projectType;
	}

	public void setProjectType(List<Map<String, Object>> projectType) {
		this.projectType = projectType;
	}

	public TaskSearchBean getSearchBean() {
		return searchBean;
	}

	public void setSearchBean(TaskSearchBean searchBean) {
		this.searchBean = searchBean;
	}

	public boolean isDealTask() {
		return dealTask;
	}

	public void setDealTask(boolean dealTask) {
		this.dealTask = dealTask;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	// =========================================

	public String main() {
		// 项目类型
		List<ProcessDef> allProcessDef = processService.getAllProcessDef();
		if (allProcessDef != null && allProcessDef.size() > 0) {
			projectType = new ArrayList<Map<String, Object>>(allProcessDef.size());
			for (ProcessDef pd : allProcessDef) {
				Map<String, Object> typeMap = new HashMap<String, Object>(3);
				typeMap.put(Code.CODE_NO, pd.getProcName());
				typeMap.put(Code.CODE_NAME, pd.getProcCnName());
				projectType.add(typeMap);
			}
		}

		return MAIN;
	}
	
	public void doTransfor(){
		if (user == null || user.getUserId() == null) {
			UcarsHelper.throwActionException("请选择转发用户");
		}
		List<Long> tids = getIdList();
		humnTaskAssignService.transfor(tids, user.getUserId());
		
	}

	public String list() {
		long start = 0;
		if (LOG.isDebugEnabled()) {
			start = System.currentTimeMillis();
		}
		Page page = getPg();
		UserLogonInfo uli = SessionTool.getUserLogonInfo();
		if (searchBean == null) {
			searchBean = new TaskSearchBean();
		}
		List<String> entityTypeList = new ArrayList<String>();
//		entityTypeList.add(Project.class.getName());
//		entityTypeList.add(PrjLoanRecord.class.getName());
		searchBean.setEntityTypeList(entityTypeList);
		List<InstanceBusiness> list = ucarsProcessService.getTaskList(uli.getSysUserId(), searchBean, page);

		list = addExtenalInfoForProjectAndRecord(list);

		if (LOG.isDebugEnabled()) {
			LOG.debug("query task speed time:" + (System.currentTimeMillis() - start) + "ms.");
		}
		return setDatagridInputStreamData(list, page);
	}

	public String toMyTask() {
		return SUCCESS;
	}

	public String listMyTask() {
		Page page = getPg();
		UserLogonInfo uli = SessionTool.getUserLogonInfo();
		if (searchBean == null) {
			searchBean = new TaskSearchBean();
		}
		if (productInfo != null && productInfo.getId() != null) {
			List<String> processTypes = getProcessNameList(productInfo.getId());
			searchBean.setProcessTypes(processTypes);
		}
		List<InstanceBusiness> list = ucarsProcessService.getTaskList(uli.getSysUserId(), searchBean, page);
		return setDatagridInputStreamData(list, page);
	}

	public String toMyDoneTask() {
		instanceStatusList = DictionaryUtil.getCodesByKey(CommonConst.INSTANCE_STATUS_CODE);
		return SUCCESS;
	}

	public String listMyDoneTask() {
		Page page = getPg();
		Long userId = SessionTool.getUserLogonInfo().getSysUserId();
		if (searchBean == null) {
			searchBean = new TaskSearchBean();
		}
		if (productInfo != null && productInfo.getId() != null) {
			List<String> processTypes = getProcessNameList(productInfo.getId());
			searchBean.setProcessTypes(processTypes);
		}
		List<InstanceBusiness> list = ucarsProcessService.getDoneTaskList(userId, searchBean, page);
		List<InstanceBusiness> myProcessList = new ArrayList<InstanceBusiness>();
		if (list != null) {
			for (InstanceBusiness ib : list) {
				String instanceStatus = ucarsProcessService.getInstanceStatus(ib.getInstanceId());
				myProcessList.add((InstanceBusiness)DynamicPropertyTransfer.dynamicAddProperty(ib, "instanceStatus", instanceStatus));
			}
		}
		return setDatagridInputStreamData(myProcessList, page);
	}

	private List<InstanceBusiness> addExtenalInfoForProjectAndRecord(List<InstanceBusiness> list) {
		List<InstanceBusiness> ret = null;
		/*
		if (list != null && list.size() > 0) {
			ret = new ArrayList<InstanceBusiness>(list.size());
			List<InstanceBusiness> projectInstanceBusinessList = new ArrayList<InstanceBusiness>();
			List<InstanceBusiness> recordInstanceBusinessList = new ArrayList<InstanceBusiness>();
			for (InstanceBusiness instanceBusiness : list) {
				if (StringUtils.equals(Project.class.getName(), instanceBusiness.getEntityType())) {
					projectInstanceBusinessList.add(instanceBusiness);
				} else {
					recordInstanceBusinessList.add(instanceBusiness);
				}
			}
			if (projectInstanceBusinessList.size() > 0) {
				List<PropertyTransVo> propertyTvList = new ArrayList<PropertyTransVo>();
				propertyTvList.add(new PropertyTransVo("entityId", Project.class, "id",
						"startingDate#expirDate#financingAmount", "startingDate#expirDate#financingAmount"));
				ret.addAll(DynamicPropertyTransfer.transform(projectInstanceBusinessList, propertyTvList));

			}
			if (recordInstanceBusinessList.size() > 0) {
				List<PropertyTransVo> propertyTvList = new ArrayList<PropertyTransVo>();
				propertyTvList.add(new PropertyTransVo("entityId", PrjLoanRecord.class, "id",
						"startingDate#expirDate#financingAmount#deadline",
						"startingDate#expirDate#financingAmount#deadline"));
				ret.addAll(DynamicPropertyTransfer.transform(recordInstanceBusinessList, propertyTvList));
			}
			if (ret != null && ret.size() > 0) {
				Collections.sort(ret, new Comparator<InstanceBusiness>() {

					public int compare(InstanceBusiness o1, InstanceBusiness o2) {
						int result = 0;
						if (o1 != null && o1.getTaskId() != null && o2 != null && o2.getTaskId() != null) {
							long l1 = o1.getTaskId();
							long l2 = o2.getTaskId();
							if (l1 > l2) {
								result = -1;
							} else if (l1 < l2) {
								result = 1;
							}
						}
						return result;
					}

				});
			}
		}
		*/
		return ret;
	}

	public String toProcess() {
		ucarsProcessService.checkDealTaskPerm(taskId, SessionTool.getUserLogonInfo().getSysUserId());
		HumnTask humnTask = ucarsProcessService.getHumnTask(ib.getProcessName(), ib.getTaskName());
		if (humnTask == null) { //数据迁移
			String processName = ucarsProcessService.getProcessName(SessionTool.getUserLogonInfo().getBranchId(), ib.getProdNo());
			humnTask = ucarsProcessService.getHumnTask(processName, ib.getTaskName());
		}
		String url = getBaseDir()
				+ (StringUtils.isBlank(humnTask.getUrl()) ? IUcarsProcessService.DEFAULT_TASK_PROCESS_URL : humnTask
						.getUrl().trim());
		if (LOG.isDebugEnabled()) {
			LOG.debug("{processName:" + ib.getProcessName() + ",taskName:" + ib.getTaskName() + "} url:" + url);
		}
		return setInputStreamData(url);
	}

	public String getTaskHistory() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("processName=" + String.valueOf(processName));
		}
		List<TaskTraceInfo> taskTraceList = null;
		Long processInstanceId = null;
		if (ib != null && ib.getId() != null) {
			ib = ucarsProcessService.getInstanceBusiness(ib.getId());
			taskTraceList = taskTraceInfoService.getTaskTraceInfoList(ib.getId());
		} else {
			if (entityId != null && StringUtils.isNotBlank(processName)) {
				taskTraceList = taskTraceInfoService.getTaskTraceInfoList(entityId, processName);
			}
		}
		processTaskTraceInfo(taskTraceList);
		// 增加未处理的任务
		if (ib != null && ib.getInstanceId() != null) {
			processInstanceId = ib.getInstanceId();
			if (ib.getId() == null) {
				ib = ucarsProcessService.getInstanceBusinessByInstanceId(processInstanceId);
			}
		}
		
		if (ib != null && ib.getId() != null) {
			if (taskTraceList != null) {//增加发起人的信息
				TaskTraceInfo processStarter = new TaskTraceInfo();
				processStarter.setTaskName("发起人");
				processStarter.setIsAgree("1");
				processStarter.setDealUserName(ib.getCreatorName());
				processStarter.setDealTime(ib.getCreateTime());
				taskTraceList.add(0, processStarter);
			}
		}
		if (processInstanceId != null) {
			List<TaskTraceInfo> todoTaskTraceInfo = getTodoTaskTraceInfo(processInstanceId);
			List<TaskTraceInfo> futureTaskTraceInfo =  getFutureTaskTraceInfo(ib.getId());
			taskTraceList.addAll(todoTaskTraceInfo);
			taskTraceList.addAll(futureTaskTraceInfo);
			
		}
		return setDatagridInputStreamData(taskTraceList, getPg());
	}
	
	public String getTodoTaskInfos(){
		StringBuilder sb = new StringBuilder();
		if (ib != null && ib.getId() != null && ib.getId() > 0) {
			ib = ucarsProcessService.getInstanceBusiness(ib.getId());
			if(ib != null){
				List<TaskTraceInfo> todoTaskTraceInfo = getTodoTaskTraceInfo(ib.getInstanceId());
				if (todoTaskTraceInfo != null && !todoTaskTraceInfo.isEmpty()) {
					for (TaskTraceInfo tti : todoTaskTraceInfo){
						if(sb.length() > 0){
							sb.append("<br/>");
						}
						sb.append("<span>").append(tti.getTaskName());
						Long dealUserId = tti.getDealUserId();
						if(dealUserId!=null){
							sb.append("：");
							String dealUserName = userService.getUserById(dealUserId).getUserName();
							sb.append(dealUserName == null?"":dealUserName);
						}
						sb.append("</span>");
					}
				} else {
					sb.append("流程已结束");
				}
			} else {
				sb.append("没有流程信息");
			}
		}
		return setInputStreamData(sb.toString());
	}
	
	private List<TaskTraceInfo> getFutureTaskTraceInfo(Long instanceBusinessId){
		List<TaskTraceInfo> result = new ArrayList<TaskTraceInfo>();
		if (instanceBusinessId != null) {
			List<Task> taskList = ucarsProcessService.getUndoTaskNode(instanceBusinessId);
			if (taskList != null && taskList.size() > 0) {
				for (Task task : taskList ) {
					TaskTraceInfo tti = new TaskTraceInfo();
					tti.setIsAgree("-2");
					tti.setTaskName(task.getDescription());
					result.add(tti);
				}
			}
		}
		return result;
	}

	private List<TaskTraceInfo> getTodoTaskTraceInfo(Long instanceId) {
		List<TaskTraceInfo> ret = new ArrayList<TaskTraceInfo>();
		List<Map<String, Object>> taskInstanceList = ucarsProcessService.getTodoTaskList(instanceId);
		if (taskInstanceList != null && taskInstanceList.size() > 0) {
			for (Map<String, Object> task : taskInstanceList) {

				TaskTraceInfo tti = new TaskTraceInfo();
				tti.setIsAgree("-1");
				tti.setTaskName((String) task.get("DESCRIPTION_"));
				String actorId = (String) task.get("ACTORID_");
				if (StringUtils.isNotBlank(actorId)) {
					Buser user = userService.getUserById(Long.parseLong(actorId));
					tti.setDealUserId(Long.parseLong(actorId));
					if (user != null) {
						tti.setDealUserName(user.getUserName());
					}
				} else {
					List<Buser> userList = humnTaskService.getTaskDealUsers(Long.valueOf(task.get("ID_").toString()));
					if (userList != null && !userList.isEmpty()) {
						Set<String> userNames = new HashSet<String>();
						for (Buser user : userList) {
							userNames.add(user.getUserName());
						}
						tti.setDealUserName(StringUtils.join(userNames, "<br/>"));
					}
				}
				if (StringUtils.equals("SettlementOfConfirmation", String.valueOf(task.get("NAME_")))) {
					TaskTraceInfo existTti = ucarsProcessService.getTaskTraceInfoByTaskId(Long.valueOf(task.get("ID_")
							.toString()));
					if (existTti != null) {
						tti.setRemark(existTti.getRemark());
						tti.setBusinessData(existTti.getBusinessData());
						tti.setBusinessType(existTti.getBusinessType());
						List<TaskTraceInfo> todoTaskTraceInfoList = new ArrayList<TaskTraceInfo>(1);
						todoTaskTraceInfoList.add(tti);
						processTaskTraceInfo(todoTaskTraceInfoList);
					}
				}
				ret.add(tti);
			}
		}
		return ret;
	}

	/**
	 * 领取任务
	 */
	public String doHoldTask() {
		humnTaskAssignService.holdTask(getIdList(), SessionTool.getUserLogonInfo().getSysUserId());
		return setInputStreamData(SUCCESS_1);
	}

	/**
	 * 已办任务页面
	 * 
	 * @return
	 */
	public String doneTask() {
		main();
		return SUCCESS;
	}

	/**
	 * 查询已办任务
	 * 
	 * @return
	 */
	public String listDoneTask() {
		Page page = getPg();
		Long userId = SessionTool.getUserLogonInfo().getSysUserId();
		if (searchBean == null) {
			searchBean = new TaskSearchBean();
		}
		List<String> entityTypeList = new ArrayList<String>();
//		entityTypeList.add(Project.class.getName());
//		entityTypeList.add(PrjLoanRecord.class.getName());
		searchBean.setEntityTypeList(entityTypeList);
		List<InstanceBusiness> list = ucarsProcessService.getDoneTaskList(userId, searchBean, page);

		list = addExtenalInfoForProjectAndRecord(list);

		return setDatagridInputStreamData(list, page);
	}

	/**
	 * 取消领取
	 */
	public String doCancelHold() {
		humnTaskAssignService.cancelHoldTask(getIdList(), SessionTool.getUserLogonInfo().getSysUserId());
		return setInputStreamData(SUCCESS_1);
	}

	private static void processTaskTraceInfo(List<TaskTraceInfo> list) {
		if (list != null && list.size() > 0) {
			for (Iterator<TaskTraceInfo> it = list.iterator(); it.hasNext();) {
				try {
					TaskTraceInfo tti = it.next();
					TaskTraceInfoFormat ttif = null;
					if (StringUtils.isNotBlank(tti.getBusinessData()) && StringUtils.isNotBlank(tti.getBusinessType())) {
						Class<?> clazz = Class.forName(tti.getBusinessType());
						Object obj = JsonUtils.string2Object(tti.getBusinessData(), clazz);
						String businessFormatMessage = StringUtils.EMPTY;
						if (obj instanceof Collection<?>) {
							Collection<?> collection = (Collection<?>) obj;
							for (Object o : collection) {
								if (o instanceof TaskTraceInfoFormat) {
									ttif = (TaskTraceInfoFormat) o;
								}
								businessFormatMessage += appendHtmlBr(getFormatString(o));
							}
						} else {
							businessFormatMessage = getFormatString(obj);
							if (obj instanceof TaskTraceInfoFormat) {
								ttif = (TaskTraceInfoFormat) obj;
							}
						}
						if (StringUtils.isNotBlank(businessFormatMessage)) {
							if (StringUtils.isNotBlank(tti.getRemark())) {
								tti.setBusinessFormatMessage(appendHtmlBr(businessFormatMessage));
							} else {
								tti.setBusinessFormatMessage(businessFormatMessage);
							}
						}
					}

					String remark = StringUtils.EMPTY;
					if (ttif != null) {
						remark += ttif.getRemarkPreFix();
					}
					if (StringUtils.isNotBlank(tti.getRemark())) {
						remark += StringUtil.convertHTML(tti.getRemark());
					}
					tti.setRemark(remark);
				} catch (Exception e) {
					LOG.error(e.getMessage(), e);
				}
			}
		}
	}

	private static final String appendHtmlBr(String str) {
		String result = str;
		if (result != null) {
			result += "<br/>";
		}
		return result;
	}

	public String toViewTask() {
		if (ib != null && ib.getId() != null) {
			ib = ucarsProcessService.getInstanceBusiness(ib.getId());
		}
		return SUCCESS;
	}

	private static String getFormatString(Object obj) {
		String ret = null;
		if (obj != null) {
			if (obj instanceof Formatable) {
				ret = ((Formatable) obj).format();
			}
		}
		if (ret == null) {
			ret = StringUtils.EMPTY;
		}
		return ret;
	}

	public String toMyProcess() {
		instanceStatusList = DictionaryUtil.getCodesByKey(CommonConst.INSTANCE_STATUS_CODE);
		return SUCCESS;
	}

	public String listMyProcess() {
		Long userId = SessionTool.getUserLogonInfo().getSysUserId();
		Page pg = getPg();
		if (ibSearchBean == null) {
			ibSearchBean = new InstanceBusinessSearchBean();
		}

		if (productInfo != null && productInfo.getId() != null) {
			List<String> processNameList = getProcessNameList(productInfo.getId());
			ibSearchBean.setProcessNameList(processNameList);
		}

		ibSearchBean.setCreator(userId);
		List<InstanceBusiness> list = ucarsProcessService.getInstanceBusinessList(ibSearchBean, pg);
		List<InstanceBusiness> myProcessList = new ArrayList<InstanceBusiness>();
		if (list != null) {
			for (InstanceBusiness ib : list) {
				String instanceStatus = ucarsProcessService.getInstanceStatus(ib.getInstanceId());
				myProcessList.add((InstanceBusiness)DynamicPropertyTransfer.dynamicAddProperty(ib, "instanceStatus", instanceStatus));
			}
		}
		return setDatagridInputStreamData(myProcessList, pg);
	}

	private List<String> getProcessNameList(Long prodId) {
		List<String> processNameList = new ArrayList<String>();
		if (prodId != null) {
			ProductInfo product = productService.getProduct(prodId);
			if (product != null) {
				List<String> allProductNo = getProductNoList(product);
				List<ProcessDef> allProcess = processService.getAllProcessDef();
				if (allProcess != null && allProcess.size() > 0) {
					for (ProcessDef pd : allProcess) {
						if (allProductNo.contains(pd.getDesiProdNo())) {
							processNameList.add(pd.getProcName());
						}
					}
				}
			}
		}
		if (processNameList.isEmpty()) {
			processNameList.add("-1");
		}
		return processNameList;
	}
	
	public String getEntityObject(){
		Object obj = null;
		if (ib != null && ib.getId() != null) {
			obj = ucarsProcessService.getEntityObject(ib.getId());
		}
		return setInputStreamData(obj == null ? Collections.EMPTY_MAP:obj);
	}

	private List<String> getProductNoList(ProductInfo product) {
		List<String> ret = new ArrayList<String>();
		if (product != null) {
			ret.add(product.getProdNo());
			List<ProductInfo> subProduct = productService.getSubProduct(product.getId());
			if (subProduct != null && subProduct.size() > 0) {
				for (ProductInfo sub : subProduct) {
					ret.addAll(getProductNoList(sub));
				}
			}
		}
		return ret;
	}

	/**
	 * 单个流程的任务历史
	 * 
	 * @return
	 */
	public String getInstanceTaskHistory() {
		InstanceBusiness instanceBusiness = ucarsProcessService.getInstanceBusiness(ib.getId());
		List<TaskTraceInfo> taskTraceList = taskTraceInfoService.getInstanceTaskTraceInfoList(ib.getId());
		processTaskTraceInfo(taskTraceList);
		taskTraceList.addAll(getTodoTaskTraceInfo(instanceBusiness.getInstanceId()));
		return setDatagridInputStreamData(taskTraceList, getPg());
	}
	
	public String taskCount(){
		return SUCCESS;
	}
	
	public String taskCountList(){
		List<Map<String, Object>> tempCountList = humnTaskService.getTaskCountList(ibSearchBean.getCol1(), getPg());
		List<Map<String, Object>> taskCountList = new ArrayList<Map<String, Object>>();
		for(Map<String, Object>tempCount:tempCountList){
			String roleName = roleService.getRoleById((Long.valueOf(tempCount.get("ROLE_ID").toString()))).getRoleName();
			tempCount.put("ROLE_NAME", roleName);
			taskCountList.add(tempCount);
		}
		return setDatagridInputStreamData(taskCountList, getPg());
	}
	
	public String toViewProcessDetail(){
		searchBean = new TaskSearchBean();
		return SUCCESS;
	}
	
	public String getProcessDetailList(){
		searchBean.setRoleId(roleId);
		List<Map<String, Object>> tempDetailList = humnTaskService.getProcessDetail(searchBean, Integer.parseInt(countType),getPg());
		List<Map<String, Object>> taskDetailList = new ArrayList<Map<String,Object>>();
		for(Map<String, Object> tempMap:tempDetailList){
			String actorName = "";
			if(tempMap.get("ACTORID_") != null){
				Buser tempUser = userService.getUserById(Long.parseLong(tempMap.get("ACTORID_").toString()));
				if(tempUser != null){
					actorName = tempUser.getUserName();
				}
			}
			tempMap.put("ACTOR_NAME", actorName);	
			
			taskDetailList.add(tempMap);
		}
		return setDatagridInputStreamData(taskDetailList, getPg());
	}

	public InstanceBusinessSearchBean getIbSearchBean() {
		return ibSearchBean;
	}

	public void setIbSearchBean(InstanceBusinessSearchBean ibSearchBean) {
		this.ibSearchBean = ibSearchBean;
	}

	public IProductService getProductService() {
		return productService;
	}

	public void setProductService(IProductService productService) {
		this.productService = productService;
	}

	public ProductInfo getProductInfo() {
		return productInfo;
	}

	public void setProductInfo(ProductInfo productInfo) {
		this.productInfo = productInfo;
	}

	public List<Code> getInstanceStatusList() {
		return instanceStatusList;
	}

	public void setInstanceStatusList(List<Code> instanceStatusList) {
		this.instanceStatusList = instanceStatusList;
	}

	public void setHumnTaskService(IHumnTaskService humnTaskService) {
		this.humnTaskService = humnTaskService;
	}

	public void setRoleService(IRoleService roleService) {
		this.roleService = roleService;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getCountType() {
		return countType;
	}

	public void setCountType(String countType) {
		this.countType = countType;
	}

	public Buser getUser() {
		return user;
	}

	public void setUser(Buser user) {
		this.user = user;
	}
}
