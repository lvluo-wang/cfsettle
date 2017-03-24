package com.upg.ucars.framework.bpm.assist.action;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.upg.ucars.constant.BeanNameConstants;
import com.upg.ucars.framework.base.BaseAction;
import com.upg.ucars.framework.base.ICommonDAO;
import com.upg.ucars.framework.base.SessionTool;
import com.upg.ucars.framework.bpm.assign.service.IHumanTaskAssignService;
import com.upg.ucars.framework.bpm.assist.model.BpmSearchBean;
import com.upg.ucars.framework.bpm.assist.model.CurrentNodeInfo;
import com.upg.ucars.framework.bpm.assist.model.NodeLineDTO;
import com.upg.ucars.framework.bpm.assist.model.ProcDefDTO;
import com.upg.ucars.framework.bpm.assist.model.ProcessInstanceDTO;
import com.upg.ucars.framework.bpm.assist.model.TaskInstanceDTO;
import com.upg.ucars.framework.bpm.assist.model.VariableInstanceDTO;
import com.upg.ucars.framework.bpm.assist.service.IBpmActivityManager;
import com.upg.ucars.framework.bpm.assist.service.ProcDefManager;
import com.upg.ucars.mapping.basesystem.security.Buser;
import com.upg.ucars.util.SourceTemplate;


public class BpmActivityAction extends BaseAction{
	private IHumanTaskAssignService humanTaskAssignService;
	private IBpmActivityManager bpmActivityManager;
	
	
	private BpmSearchBean bpmSearchBean;
	
	private List<ProcDefDTO> procDefList = ProcDefManager.getProcDefList();
	
	List<ProcessInstanceDTO> procInstList;
	private Long processInstanceId;
	
	CurrentNodeInfo curNodeInfo;//当前节点信息
	List<NodeLineDTO> nodeLineList;//节点与连线
	List<TaskInstanceDTO> taskInstList;//流程任务
	List<VariableInstanceDTO> varInstList;//流程变量
	
	private Long taskInstanceId;
	List<Buser> actorList;//任务执行者
	String actorUserNo;//执行者柜员号
	private String actorId;
	
	String varName;//流程变量名
	VariableInstanceDTO vi;
	
	private String lineName;//连线名
	
	private InputStream jsonStream;
	
	
	
	
	public String main() throws Exception {
		
		return "main";
	}

	public String queryData() throws Exception {
		IBpmActivityManager manager = SourceTemplate.getBean(IBpmActivityManager.class,BeanNameConstants.BPM_ACTIVITY_MANAGER);			
		procInstList = new ArrayList<ProcessInstanceDTO>(0);
		if (bpmSearchBean!=null){
			bpmSearchBean.setBrchId(SessionTool.getUserLogonInfo().getBranchName());
			procInstList = manager.queryProcessInstances(bpmSearchBean, this.getPg());
		}
		
		this.setDatagridInputStreamData(procInstList, this.getPg());
		jsonStream = this.getDataStream();
		return "table";
	}
	
	
	public String queryFlow(){
		IBpmActivityManager manager = SourceTemplate.getBean(IBpmActivityManager.class,BeanNameConstants.BPM_ACTIVITY_MANAGER);		
		nodeLineList = manager.getTransitionInfo(this.getPKId());
		curNodeInfo = manager.getCurrentNodeInfo(this.getPKId());
		this.processInstanceId = this.getPKId();
		return "flow";
	}
	
	public String signal(){
		IBpmActivityManager manager = SourceTemplate.getBean(IBpmActivityManager.class,BeanNameConstants.BPM_ACTIVITY_MANAGER);		
		manager.signal(this.getPKId(), lineName);	
		return null;
	}
	
	public String toTaskInst(){
		
		return "taskinst";
	}
	
	public String queryTaskInstData() throws Exception {
		IBpmActivityManager manager = SourceTemplate.getBean(IBpmActivityManager.class,BeanNameConstants.BPM_ACTIVITY_MANAGER);		
		taskInstList = manager.getTaskInstances(this.getPKId());
				
		ArrayList<Long> temIdList = new ArrayList<Long>();
		for (TaskInstanceDTO ti : taskInstList) {//收集领用人
			if (ti.getHoldUserId()!=null){
				temIdList.add(ti.getHoldUserId());		
			}
		}
		if (!temIdList.isEmpty()){//设置领用人名称
			 ICommonDAO dao = SourceTemplate.getBean(ICommonDAO.class, "commonDAO");
			 List<Buser> miList = dao.findByOneProperty(Buser.class, "userId", temIdList);
			 HashMap<Long, String> idNameMap = new HashMap<Long, String>();
			 for (Buser buser : miList) {
				 idNameMap.put(buser.getUserId(), buser.getUserName());
			}
			
			for (TaskInstanceDTO ti : taskInstList) {
				ti.setHoldUserName(idNameMap.get(ti.getHoldUserId()));
			}
			 
		}
		
		
		this.setDatagridInputStreamData(taskInstList, this.getPg());
		
		jsonStream = this.getDataStream();
		
		return "table";
	}
	
	public String toVarInst(){
		
		return "varinst";
	}
	
	public String queryVarInstData() throws Exception {
		IBpmActivityManager manager = SourceTemplate.getBean(IBpmActivityManager.class,BeanNameConstants.BPM_ACTIVITY_MANAGER);	
		varInstList = manager.getVariableInstances(this.getPKId());		
		
		this.setDatagridInputStreamData(varInstList, this.getPg());		
		jsonStream = this.getDataStream();
		
		return "table";
	}
	
	public String toAddVar(){
		
		return "addvar";
	}
	
	public String createVarInst(){
		processInstanceId = this.getPKId();
		
		if (vi.getName()==null || "".equals(vi.getName()))return null;//变量名不能为null
		
		IBpmActivityManager manager = SourceTemplate.getBean(IBpmActivityManager.class,BeanNameConstants.BPM_ACTIVITY_MANAGER);		
		manager.addOrUpdateVariable(processInstanceId, vi.getName(), vi.getValue());
		
		
		return null;
	}
	
	
	public String delVarInst(){
		processInstanceId = this.getPKId();
		IBpmActivityManager manager = SourceTemplate.getBean(IBpmActivityManager.class,BeanNameConstants.BPM_ACTIVITY_MANAGER);		
		manager.delVariable(processInstanceId, varName);
		
		return null;
	}
	
	public String cancelHolder(){
		
		bpmActivityManager.cancelHolder(this.getPKId());
		
		return null;
	}
	
	public String setHolder(){
		
		bpmActivityManager.holdTask(this.getPKId(), Long.valueOf(this.getActorId()));
		
		return null;
	}
	
	public String toChooseActor(){
		
		
		
		return null;
		
	}	                              
	
	
	public String queryActors(){
		IBpmActivityManager manager = SourceTemplate.getBean(IBpmActivityManager.class,BeanNameConstants.BPM_ACTIVITY_MANAGER);		
		actorList = manager.getActorsByTask(taskInstanceId);
		return "actor";
	}
	
	public String queryUsers(){
		//IBpmActivityManager manager = BpmAssistFactory.getBpmActivityManager();
		/*BpmControlDAO jbpmDao = (BpmControlDAO)SourceTemplate.getBean("ppmControlDAO");
		Long brchId = (Long)jbpmDao.getTaskVariable(taskInstanceId, BpmConstants.VAR_BRCH_ID);
		
		IUserService service=UserServiceFactory.getUserService();
		
		if (userSb==null) userSb = new UserSearchBean();
		userSb.setBrchId(brchId);
		userSb.addSpecialOpertion("brchId", UserSearchBean.DEFAULT_EQUAL);
		//增加执行者时只能选择普通柜员 5：代表普通柜员 add by liujiao at 20101011
		userSb.setUserType("5");
		userSb.addHibPropretyMapping("userType", "userType");
		userSb.addSpecialOpertion("userType", userSb.DEFAULT_EQUAL);*/
		
				
		//this.getPg().setPageCommand(this.getCommand());
		//TODO
		//actorList = service.getUserBySearchBeans(this.getPage(), userSb);
		
		return "user";
	}
	
	public String addActor(){	
		IBpmActivityManager manager = SourceTemplate.getBean(IBpmActivityManager.class,BeanNameConstants.BPM_ACTIVITY_MANAGER);		
		actorList = manager.getActorsByTask(taskInstanceId);
		HashSet set = new HashSet();
		for (Buser user : actorList){
			set.add(user.getUserNo());
		}
		
		if (set.contains(this.getActorUserNo())){
			this.addActionMessage("不能重复添加执行者.");
		}else{
			manager.addTaskActor(taskInstanceId, this.getActorUserNo());
		}
		
		return queryActors();
	}
	
	public String delActor(){		
		IBpmActivityManager manager = SourceTemplate.getBean(IBpmActivityManager.class,BeanNameConstants.BPM_ACTIVITY_MANAGER);		
		manager.delTaskActor(taskInstanceId, this.getActorUserNo());
		
		return queryActors();
	}
	
	/***********************************
	***********************************
	***********************************/
	
	public String suspendFlow(){
		IBpmActivityManager manager =SourceTemplate.getBean(IBpmActivityManager.class,BeanNameConstants.BPM_ACTIVITY_MANAGER);	
		manager.suspendProcessInstance(this.getPKId());
		
		return null;
	}
	
	public String resumeFlow(){
		IBpmActivityManager manager = SourceTemplate.getBean(IBpmActivityManager.class,BeanNameConstants.BPM_ACTIVITY_MANAGER);	
		manager.resumeProcessInstance(this.getPKId());
		
		return null;
	}
	
	public String suspendTask(){
		IBpmActivityManager manager = SourceTemplate.getBean(IBpmActivityManager.class,BeanNameConstants.BPM_ACTIVITY_MANAGER);	
		manager.suspendTaskInstance(this.getPKId());
		
		return null;//queryTaskInst();
	}
	
	public String resumeTask(){
		IBpmActivityManager manager = SourceTemplate.getBean(IBpmActivityManager.class,BeanNameConstants.BPM_ACTIVITY_MANAGER);	
		manager.resumeTaskInstance(this.getPKId());
		
		return null;//queryTaskInst();
	}
	
	

	public BpmSearchBean getBpmSearchBean() {
		return bpmSearchBean;
	}


	public void setBpmSearchBean(BpmSearchBean bpmSearchBean) {
		this.bpmSearchBean = bpmSearchBean;
	}


	public List<ProcessInstanceDTO> getProcInstList() {
		return procInstList;
	}


	public void setProcInstList(List<ProcessInstanceDTO> procInstList) {
		this.procInstList = procInstList;
	}


	public List<ProcDefDTO> getProcDefList() {
		return procDefList;
	}


	public void setProcDefList(List<ProcDefDTO> procDefList) {
		this.procDefList = procDefList;
	}


	public Long getProcessInstanceId() {
		return processInstanceId;
	}


	public void setProcessInstanceId(Long processInstanceId) {
		this.processInstanceId = processInstanceId;
	}


	public List<NodeLineDTO> getNodeLineList() {
		return nodeLineList;
	}


	public void setNodeLineList(List<NodeLineDTO> nodeLineList) {
		this.nodeLineList = nodeLineList;
	}


	public List<TaskInstanceDTO> getTaskInstList() {
		return taskInstList;
	}


	public void setTaskInstList(List<TaskInstanceDTO> taskInstanceList) {
		this.taskInstList = taskInstanceList;
	}


	public List<VariableInstanceDTO> getVarInstList() {
		return varInstList;
	}


	public void setVarInstList(List<VariableInstanceDTO> varInstList) {
		this.varInstList = varInstList;
	}


	public Long getTaskInstanceId() {
		return taskInstanceId;
	}


	public void setTaskInstanceId(Long taskInstanceId) {
		this.taskInstanceId = taskInstanceId;
	}


	public List<Buser> getActorList() {
		return actorList;
	}


	public void setActorList(List<Buser> actorList) {
		this.actorList = actorList;
	}

	public CurrentNodeInfo getCurNodeInfo() {
		return curNodeInfo;
	}


	public void setCurNodeInfo(CurrentNodeInfo curNodeInfo) {
		this.curNodeInfo = curNodeInfo;
	}


	public String getActorUserNo() {
		return actorUserNo;
	}


	public void setActorUserNo(String actorUserNo) {
		this.actorUserNo = actorUserNo;
	}


	public String getVarName() {
		return varName;
	}

	public void setVarName(String varName) {
		this.varName = varName;
	}


	public VariableInstanceDTO getVi() {
		return vi;
	}

	public void setVi(VariableInstanceDTO vi) {
		this.vi = vi;
	}

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public int getIsCenter() {
		if (SessionTool.getUserLogonInfo().getBranchId() == null || "".equalsIgnoreCase(SessionTool.getUserLogonInfo().getBranchName())) {
			return 1;
		} else {
			return 0;
		}
	}

	public InputStream getJsonStream() {
		return jsonStream;
	}

	public void setJsonStream(InputStream jsonStream) {
		this.jsonStream = jsonStream;
	}

	public IHumanTaskAssignService getHumanTaskAssignService() {
		return humanTaskAssignService;
	}

	public void setHumanTaskAssignService(
			IHumanTaskAssignService humanTaskAssignService) {
		this.humanTaskAssignService = humanTaskAssignService;
	}

	public IBpmActivityManager getBpmActivityManager() {
		return bpmActivityManager;
	}

	public void setBpmActivityManager(IBpmActivityManager bpmActivityManager) {
		this.bpmActivityManager = bpmActivityManager;
	}

	public String getActorId() {
		return actorId;
	}

	public void setActorId(String actorId) {
		this.actorId = actorId;
	}
}
