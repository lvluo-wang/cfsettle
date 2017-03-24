package com.upg.ucars.framework.bpm.assign.action;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.upg.ucars.basesystem.dictionary.util.DictionaryUtil;
import com.upg.ucars.basesystem.security.core.branch.BranchHelper;
import com.upg.ucars.basesystem.security.core.branch.IBranchService;
import com.upg.ucars.basesystem.security.core.role.IRoleService;
import com.upg.ucars.basesystem.security.core.role.RoleConstant;
import com.upg.ucars.constant.BeanNameConstants;
import com.upg.ucars.constant.CodeKey;
import com.upg.ucars.constant.CommonConst;
import com.upg.ucars.framework.base.BaseAction;
import com.upg.ucars.framework.base.ICommonDAO;
import com.upg.ucars.framework.base.QueryCondition;
import com.upg.ucars.framework.base.SessionTool;
import com.upg.ucars.framework.bpm.assign.common.TaskAssignConst;
import com.upg.ucars.framework.bpm.assign.service.IHumanTaskAssignService;
import com.upg.ucars.framework.bpm.procmap.IProdProcMapService;
import com.upg.ucars.framework.bpm.publish.service.IHumnTaskService;
import com.upg.ucars.mapping.basesystem.dictionary.Code;
import com.upg.ucars.mapping.basesystem.security.Branch;
import com.upg.ucars.mapping.basesystem.security.Role;
import com.upg.ucars.mapping.framework.bpm.HumnTask;
import com.upg.ucars.mapping.framework.bpm.ProcessDef;
import com.upg.ucars.model.ConditionBean;
import com.upg.ucars.model.JQueryTreeNode;
import com.upg.ucars.model.OrderBean;
import com.upg.ucars.model.security.UserLogonInfo;
import com.upg.ucars.util.SourceTemplate;
import com.upg.ucars.util.StringUtil;

/**
 * 任务授权的扩展，用于批量的分配方式
 * 
 * @author shentuwy
 * @date 2012-7-19
 *
 */
public class TaskAssignExAction extends BaseAction {
	private IProdProcMapService prodProcMapService;
	
	private InputStream treeStream;
	private InputStream jsonStream;	
	private Branch takeBranch;//任务所属机构
	
	private List<Branch> toBrchList;//可授权的机构列表（任务所属机构\直系上级及以上机构）
	private Long toBrchId;//授权机构
	private List<Code> assignTypeList;//授权类型列
	private String assignType;//授权类型
	private String toBrchIdStr;
	
	private String taskId;
	private String brchId;
	
	private String beIds;
	private String unIds;
	
	private String isChecked;
	
	
	public String main(){
		//Long brchId = SessionTool.getUserLogonInfo().getBranchId();
		//takeBranch = SourceTemplate.getBean(IBranchService.class,BeanNameConstants.BRANCH_SERVICE).getBranchByBrchId(brchId);		
		
		return "main";
	}
	
	
	public String queryTaskTree() throws Exception{
		String miNo = SessionTool.getUserLogonInfo().getMiNo();
		ArrayList<JQueryTreeNode> beNodeList = new ArrayList<JQueryTreeNode>();
		if (StringUtil.isEmpty(this.getId())){//流程
			
			List<ProcessDef> procList = prodProcMapService.findProcessByMember(miNo, null);
			
			for (ProcessDef proc : procList) {
				JQueryTreeNode node = new JQueryTreeNode();
				node.setId(proc.getId().toString());
				node.setText(proc.getProcCnName());	
				node.setState(JQueryTreeNode.STATE_CLOSED);
				beNodeList.add(node);
			}
			
		}else{//任务
			List<HumnTask> taskList = SourceTemplate.getBean(IHumnTaskService.class,BeanNameConstants.HUMN_TASK_SERVICE).getHumnTasksByProcId(Long.valueOf(this.getId()));
			for (HumnTask humnTask : taskList) {
				JQueryTreeNode node = new JQueryTreeNode();
				node.setId(humnTask.getId().toString());
				node.setText(humnTask.getTaskCnName());	
				//node.setState(JQueryTreeNode.STATE_CLOSED);
				beNodeList.add(node);
			}
			
		}
		
		return setInputStreamData(beNodeList);
	}
	
	
	public String actorList(){
		UserLogonInfo userInfo = SessionTool.getUserLogonInfo();
		this.takeBranch = new Branch();
		this.takeBranch.setBrchId(userInfo.getBranchId());
		this.takeBranch.setTreeCode(userInfo.getBranchTreeCode());
		this.takeBranch.setBrchName(userInfo.getBranchName());
		this.takeBranch.setBrchNo(userInfo.getBranchNo());

//		this.toBrchList = branchService.getParentBranchList(takeBranch.getBrchId());
				
		assignTypeList = new ArrayList<Code>(2);
		List<Code> codeList =  DictionaryUtil.getCodesByKey(CodeKey.TASK_ASSIGN_TYPE);
		
		for (Code code : codeList) {
			if (!TaskAssignConst.Assign_Type_User.equals(code.getCodeNo()))//不按用户授权，只授权给角色与机构
				assignTypeList.add(code);
		}
		
		if (StringUtil.isEmpty(assignType))
			assignType = TaskAssignConst.Assign_Type_Role;
			
		
		return "actorlist";
	}
	
	
	public String roleAssignList(){
		
		Long taskId = Long.valueOf(this.getTaskId());
		toBrchId = Long.valueOf(this.getToBrchIdStr());
		
		IRoleService roleService = SourceTemplate.getBean(IRoleService.class,BeanNameConstants.ROLE_SERVICE);
		IBranchService branchService = SourceTemplate.getBean(IBranchService.class,BeanNameConstants.BRANCH_SERVICE);
		
		Branch branch = branchService.getBranchByBrchId(toBrchId);	
		
		ArrayList<String> treeCodeList = new ArrayList<String>(3);
		treeCodeList.add(branch.getTreeCode());
		
		String treeCode = branch.getTreeCode();
		while (!StringUtil.isEmpty(BranchHelper.getParentTreeCode(treeCode))){
			treeCode=BranchHelper.getParentTreeCode(treeCode);
			treeCodeList.add(treeCode);
		}
		
		String hql = "select role from Role role, Branch branch where role.brchId=branch.brchId ";
		QueryCondition qc = new QueryCondition(hql);
		
		ArrayList<ConditionBean> conditionList = new ArrayList<ConditionBean>(1);
		ConditionBean cond = new ConditionBean("branch.treeCode", ConditionBean.IN, treeCodeList);//父机构的角色通用于子机构
		conditionList.add(cond);
		
		List<String> roleTypeList = new ArrayList<String>();
		roleTypeList.add(RoleConstant.ROLE_TYPE_COMMON);
		roleTypeList.add(RoleConstant.ROLE_TYPE_JUDGMENT);
		
		cond = new ConditionBean("role.roleType", ConditionBean.IN,roleTypeList);
		conditionList.add(cond);
		qc.addConditionList(conditionList);
		
		qc.addOrder(new OrderBean("role.roleName", true));
		
		List<Role> roleList = roleService.queryByCondition(qc, this.getPg());
		
		
		//查询已授权的roleId
		List<Long> roleIdlist = SourceTemplate.getBean(IHumanTaskAssignService.class,BeanNameConstants.HUMN_TASK_ASSIGN_SERVICE).getRoleActorsByBrch(taskId, takeBranch.getBrchId(), toBrchId);
		
		ArrayList<Integer> initSelectedsList = new ArrayList<Integer>();
		
		for (int i=0; i<roleList.size(); i++) {
			if (roleIdlist.contains(roleList.get(i).getRoleId())){
				initSelectedsList.add(i);
			}			
		}		
		
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		jsonMap.put("total",this.getPg().getTotalRows());
		jsonMap.put("rows",roleList);
		jsonMap.put("initSelecteds", initSelectedsList);
		
		return setInputStreamData(jsonMap);
	}
	
	
	public String brchAssignList(){
		Long taskId = Long.valueOf(this.getTaskId());
		toBrchId = Long.valueOf(this.getToBrchIdStr());
		
		UserLogonInfo userInfo = SessionTool.getUserLogonInfo();
		this.takeBranch = new Branch();
		this.takeBranch.setBrchId(userInfo.getBranchId());
		this.takeBranch.setBrchNo(userInfo.getBranchNo());
		this.takeBranch.setTreeCode(userInfo.getBranchTreeCode());
		this.takeBranch.setBrchName(userInfo.getBranchName());
		
		ICommonDAO commonDAO = SourceTemplate.getBean(ICommonDAO.class, "commonDAO");
		//查下一级子机构
		List<Branch> branchList = commonDAO.find("FROM Branch WHERE treeCode like ?", this.takeBranch.getTreeCode()+"____");
		
		this.toBrchList = new ArrayList<Branch>();
		this.toBrchList.add(this.takeBranch);
		this.toBrchList.addAll(branchList);
		
		this.toBrchList = this.getSubListByPage(toBrchList, this.getPg());
		
		//查询已授权的brchId
		List<Long> brchIdlist = SourceTemplate.getBean(IHumanTaskAssignService.class,BeanNameConstants.HUMN_TASK_ASSIGN_SERVICE).getBrchActors(taskId, null);
		
		ArrayList<Integer> initSelectedsList = new ArrayList<Integer>();		
		for (int i=0; i<toBrchList.size(); i++) {//选出勾选项
			if (brchIdlist.contains(toBrchList.get(i).getBrchId())){
				initSelectedsList.add(i);
			}
		}
		
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		jsonMap.put("total", this.getPg().getTotalRows());
		jsonMap.put("rows", toBrchList);	
		jsonMap.put("initSelecteds", initSelectedsList);
		JSONObject   jsonObject = JSONObject.fromObject(jsonMap);		
		jsonStream = outJsonUTFStream(jsonObject);
		
		return "table";
	}
	
	public String save(){
		IHumanTaskAssignService assignService = SourceTemplate.getBean(IHumanTaskAssignService.class,BeanNameConstants.HUMN_TASK_ASSIGN_SERVICE);
		Long taskId = Long.valueOf(this.getTaskId());
		toBrchId = Long.valueOf(this.getToBrchIdStr());
		
		if (TaskAssignConst.Assign_Type_Branch.equals(this.getAssignType())){//按机构
			ArrayList<Long> unList = this.getUnIdList();
			ArrayList<Long> beList = this.getBeIdList();
			
			assignService.batchBuildBrchActor(taskId, beList, unList, CommonConst.LOGIC_YES.equals(this.getIsChecked()));
		}
		
		if (TaskAssignConst.Assign_Type_Role.equals(this.getAssignType())){//按角色
			ArrayList<Long> unList = this.getUnIdList();
			
			ArrayList<Long> beList = this.getBeIdList();
			
			assignService.batchBuildRoleActor(taskId, takeBranch.getBrchId(),toBrchId, beList, unList, CommonConst.LOGIC_YES.equals(this.getIsChecked()));
		}
		
		return null;
	}


	public InputStream getTreeStream() {
		return treeStream;
	}


	public void setTreeStream(InputStream treeStream) {
		this.treeStream = treeStream;
	}

	public InputStream getJsonStream() {
		return jsonStream;
	}


	public void setJsonStream(InputStream jsonStream) {
		this.jsonStream = jsonStream;
	}


	public Branch getTakeBranch() {
		return takeBranch;
	}


	public void setTakeBranch(Branch takeBranch) {
		this.takeBranch = takeBranch;
	}


	public List<Branch> getToBrchList() {
		return toBrchList;
	}


	public void setToBrchList(List<Branch> toBrchList) {
		this.toBrchList = toBrchList;
	}


	public Long getToBrchId() {
		return toBrchId;
	}


	public void setToBrchId(Long toBrchId) {
		this.toBrchId = toBrchId;
	}


	public List<Code> getAssignTypeList() {
		return assignTypeList;
	}


	public void setAssignTypeList(List<Code> assignTypeList) {
		this.assignTypeList = assignTypeList;
	}


	public String getAssignType() {
		return assignType;
	}


	public void setAssignType(String assignType) {
		this.assignType = assignType;
	}


	public String getTaskId() {
		return taskId;
	}


	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}


	public String getBrchId() {
		return brchId;
	}


	public void setBrchId(String brchId) {
		this.brchId = brchId;
	}

	public String getToBrchIdStr() {
		return toBrchIdStr;
	}


	public void setToBrchIdStr(String toBrchIdStr) {
		this.toBrchIdStr = toBrchIdStr;
	}




	private IBranchService branchService;//spring注入
	public IBranchService getBranchService() {
		return branchService;
	}
	public void setBranchService(IBranchService branchService) {
		this.branchService = branchService;
	}


	public String getBeIds() {
		return beIds;
	}


	public void setBeIds(String beIds) {
		this.beIds = beIds;
	}


	public String getUnIds() {
		return unIds;
	}


	public void setUnIds(String unIds) {
		this.unIds = unIds;
	}
	
	public ArrayList<Long> getUnIdList(){
		if (StringUtil.isEmpty(unIds))
			return new ArrayList<Long>(0);
		
		String[] idarr = unIds.split(":");		
		ArrayList<Long> list = new ArrayList<Long>(idarr.length);
		for (String str : idarr) {
			list.add(Long.valueOf(str));
		}
		return list;
		
	}
	public ArrayList<Long> getBeIdList(){
		if (StringUtil.isEmpty(beIds))
			return new ArrayList<Long>(0);
		
		String[] idarr = beIds.split(":");		
		ArrayList<Long> list = new ArrayList<Long>(idarr.length);
		for (String str : idarr) {
			list.add(Long.valueOf(str));
		}
		return list;
		
	}



	public String getIsChecked() {
		return isChecked;
	}


	public void setIsChecked(String isChecked) {
		this.isChecked = isChecked;
	}


	public IProdProcMapService getProdProcMapService() {
		return prodProcMapService;
	}


	public void setProdProcMapService(IProdProcMapService prodProcMapService) {
		this.prodProcMapService = prodProcMapService;
	}
	

}
