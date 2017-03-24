package com.upg.ucars.framework.bpm.assign.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.upg.ucars.basesystem.dictionary.util.DictionaryUtil;
import com.upg.ucars.basesystem.product.core.product.IProductService;
import com.upg.ucars.basesystem.security.core.branch.BranchHelper;
import com.upg.ucars.basesystem.security.core.branch.IBranchService;
import com.upg.ucars.basesystem.security.core.role.IRoleService;
import com.upg.ucars.basesystem.security.core.role.RoleConstant;
import com.upg.ucars.basesystem.security.core.user.IUserService;
import com.upg.ucars.constant.BeanNameConstants;
import com.upg.ucars.constant.CodeKey;
import com.upg.ucars.framework.base.BaseAction;
import com.upg.ucars.framework.base.QueryCondition;
import com.upg.ucars.framework.base.SessionTool;
import com.upg.ucars.framework.bpm.assign.common.TaskAssignConst;
import com.upg.ucars.framework.bpm.assign.service.IHumanTaskAssignService;
import com.upg.ucars.framework.bpm.procmap.IProdProcMapService;
import com.upg.ucars.framework.bpm.publish.service.IHumnTaskService;
import com.upg.ucars.mapping.basesystem.dictionary.Code;
import com.upg.ucars.mapping.basesystem.product.ProductInfo;
import com.upg.ucars.mapping.basesystem.security.Branch;
import com.upg.ucars.mapping.basesystem.security.Buser;
import com.upg.ucars.mapping.basesystem.security.Role;
import com.upg.ucars.mapping.framework.bpm.HumnTask;
import com.upg.ucars.mapping.framework.bpm.HumnTaskActr;
import com.upg.ucars.mapping.framework.bpm.ProcessDef;
import com.upg.ucars.model.ConditionBean;
import com.upg.ucars.model.JQueryTreeNode;
import com.upg.ucars.util.SourceTemplate;
import com.upg.ucars.util.StringUtil;



/**
 * 
 * TaskAssignAction.java
 * 
 * @author shentuwy
 *
 */
public class TaskAssignAction extends BaseAction {
	
	private static final long	serialVersionUID	= -995653323292337677L;
	
	/** 产品流程映射服务 */
	private IProdProcMapService prodProcMapService;
	private IProductService	productService;
	/** 任务所属机构 */
	private Branch takeBranch;
	/** 可授权的机构列表（任务所属机构\直系上级及以上机构） */
	private List<Branch> toBrchList;
	/** 授权机构 */
	private Long toBrchId;
	/** 授权类型列 */
	private List<Code> assignTypeList;
	/** 授权类型 */
	private String assignType;
	private String toBrchIdStr;
	
	private String taskId;
	private String brchId;
	
	private String beIds;
	private String unIds;
	/** 流程Ids */
	private String processIds;
	
	
	public String main(){
		Long brchId = SessionTool.getUserLogonInfo().getBranchId();
		takeBranch = branchService.getBranchByBrchId(brchId);		
		return MAIN;
	}
	
	
	public String queryTaskTree() throws Exception{
		String miNo = SessionTool.getUserLogonInfo().getMiNo();
		ArrayList<JQueryTreeNode> beNodeList = new ArrayList<JQueryTreeNode>();
		if (StringUtil.isEmpty(this.getId())){//流程
			List<ProcessDef> procList = prodProcMapService.findProcessByMember(miNo, null);
			
			ArrayList<ProcessDef> procNodeList = new ArrayList<ProcessDef>();
			List<ProcessDef> brchProcList = null;
			if (StringUtils.isNotBlank(brchId)) {
				brchProcList = prodProcMapService.findProcessByBrch(Long.valueOf(brchId), null);
			}else{
				brchProcList = Collections.emptyList();
			}
			
			if (!brchProcList.isEmpty()){//机构存在分配
				HashSet<String> prodNoSet = new HashSet<String>();
				for (ProcessDef processDef : brchProcList) {
					prodNoSet.add(processDef.getDesiProdNo());
				}			
				
				for (ProcessDef processDef : procList) {
					if (!prodNoSet.contains(processDef.getDesiProdNo())){
						procNodeList.add(processDef);
					}
				}
				
				procNodeList.addAll(brchProcList);
				
				
			}else{//机构不存在分配的，则使用接入点的
				procNodeList.addAll(procList);
			}
			
			List<JQueryTreeNode> productNodes = getProcessDefTree(procNodeList,false);
			beNodeList.addAll(productNodes);
			
		}else{//任务
			List<HumnTask> taskList = SourceTemplate.getBean(IHumnTaskService.class,BeanNameConstants.HUMN_TASK_SERVICE).getHumnTasksByProcId(Long.valueOf(this.getId()));
			Collections.sort(taskList, IHumnTaskService.HUMN_TASK_COMPARATOR);
			for (HumnTask humnTask : taskList) {
				JQueryTreeNode node = new JQueryTreeNode();
				node.setId(humnTask.getId().toString());
				node.setText(humnTask.getTaskCnName() + (StringUtils.isBlank(humnTask.getRemark()) ? "" :  ("_"  + humnTask.getRemark())));	
				beNodeList.add(node);
			}
			
		}
		
		return setInputStreamData(beNodeList);
	}
	
	private List<JQueryTreeNode> getProcessDefTree(List<ProcessDef> processDefs,boolean leaf){
		Map<Long,ProductInfo> productMap = new HashMap<Long,ProductInfo>();
		Map<String,ProcessDef> processDefMap = new HashMap<String,ProcessDef>();
		if (processDefs != null && processDefs.size() > 0) {
			for (ProcessDef pd : processDefs) {
				String productNo = pd.getDesiProdNo();
				processProductMap(productMap,productNo);
				processDefMap.put(pd.getDesiProdNo(), pd);
			}
		}
		
		List<JQueryTreeNode> result = new ArrayList<JQueryTreeNode>();
		Map<Long,JQueryTreeNode> treeNodeMap = new HashMap<Long,JQueryTreeNode>();
		if (productMap != null && productMap.size() > 0) {
			for (Iterator<Map.Entry<Long, ProductInfo>> it = productMap.entrySet().iterator();it.hasNext(); ) {
				Map.Entry<Long, ProductInfo> entry = it.next();
				ProductInfo product = entry.getValue();
				
				JQueryTreeNode node = new JQueryTreeNode();
				node.setId(product.getId().toString());
				node.setText(product.getProdName());	
				node.setState(JQueryTreeNode.STATE_CLOSED);
				if (product.getParentProdId() != null) {
					node.setParentId(product.getParentProdId().toString());
				}else{
					result.add(node);
				}
				
				ProcessDef pd = processDefMap.get(product.getProdNo());
				if (pd != null) {
					node.setId(pd.getId().toString()); //转化成流程ID
					if (leaf) {
						node.setState(null);
					}
				}
				
				treeNodeMap.put(product.getId(), node);
				
			}
			
			for (Iterator<Map.Entry<Long, JQueryTreeNode>> it = treeNodeMap.entrySet().iterator(); it.hasNext();) {
				Map.Entry<Long, JQueryTreeNode> entry = it.next();
				JQueryTreeNode node = entry.getValue();
				if (StringUtils.isNotBlank(node.getParentId())) {
					JQueryTreeNode parent = treeNodeMap.get(Long.valueOf(node.getParentId()));
					if (parent != null) {
						parent.addChild(node);
					}
				}
			}
			
		}
		
		
		
		return result;
	}
	
	private void processProductMap(Map<Long,ProductInfo> productMap,String productNo){
		if (productMap != null && StringUtils.isNotBlank(productNo)){
			if (productMap.get(productNo) == null) {
				ProductInfo product = productService.getProductByProdNo(productNo);
				if (product != null) {
					productMap.put(product.getId(), product);
					if (product.getParentProdId() != null) {
						ProductInfo parentProduct = productService.getProduct(product.getParentProdId());
						if (parentProduct != null) {
							processProductMap(productMap,parentProduct.getProdNo());
						}
					}
				}
			}
		}
	}
	
	
	public String actorList(){
		
		this.toBrchList = new ArrayList<Branch>(3);
//		takeBranch = this.branchService.getBranchByBrchId(Long.valueOf(this.getBrchId()));
//		this.toBrchList.add(takeBranch);
//		Branch parentBranch = this.branchService.getSuperBranch(takeBranch.getTreeCode());
//		if (parentBranch != null){
//			this.toBrchList.add(parentBranch);
//		}
		toBrchList = branchService.getParentBranchList(Long.valueOf(this.getBrchId()));
		
		
		assignTypeList = new ArrayList<Code>(2);
		List<Code> codeList =  DictionaryUtil.getCodesByKey(CodeKey.TASK_ASSIGN_TYPE);
		
		for (Code code : codeList) {
			if (!TaskAssignConst.Assign_Type_Branch.equals(code.getCodeNo()))//不按机构授权，只授权给角色与用户
				assignTypeList.add(code);
		}
		
		
		if (StringUtil.isEmpty(assignType))
			assignType = TaskAssignConst.Assign_Type_User;
			
		
		return "actorlist";
	}
	
	
	public String roleAssignList(){
		Long brchId = Long.valueOf(this.getBrchId());
		Long taskId = Long.valueOf(this.getTaskId());
		toBrchId = Long.valueOf(this.getToBrchIdStr());
		
		IRoleService roleService = SourceTemplate.getBean(IRoleService.class,BeanNameConstants.ROLE_SERVICE);
		String hql = "select role from Role role, Branch branch where role.brchId=branch.brchId  order by convert_gbk( 'role.roleName') ";
		QueryCondition qc = new QueryCondition(hql);
		
		IBranchService branchService = SourceTemplate.getBean(IBranchService.class,BeanNameConstants.BRANCH_SERVICE);
		Branch branch = branchService.getBranchByBrchId(toBrchId);	
		
		List<String> treeCodeList = BranchHelper.getAllPreTreeCode(branch.getTreeCode());
		
		ArrayList<ConditionBean> conditionList = new ArrayList<ConditionBean>(1);
		ConditionBean cond = new ConditionBean("branch.treeCode", ConditionBean.IN, treeCodeList);
		conditionList.add(cond);
		
		
		List<String> roleTypeList = new ArrayList<String>();
		roleTypeList.add(RoleConstant.ROLE_TYPE_COMMON);
		roleTypeList.add(RoleConstant.ROLE_TYPE_JUDGMENT);
		
		cond = new ConditionBean("role.roleType", ConditionBean.IN,roleTypeList);
		
		conditionList.add(cond);
		qc.addConditionList(conditionList);
		
		
		
		List<Role> roleList = roleService.queryByCondition(qc, this.getPg());
		
		
		//查询已授权的roleId
		List<Long> roleIdlist = SourceTemplate.getBean(IHumanTaskAssignService.class,BeanNameConstants.HUMN_TASK_ASSIGN_SERVICE).getRoleActorsByBrch(taskId, brchId, toBrchId);
		
		ArrayList<Integer> initSelectedsList = new ArrayList<Integer>();
		
		for (int i=0; i<roleList.size(); i++) {
			if (roleIdlist.contains(roleList.get(i).getRoleId())){
				initSelectedsList.add(i);
			}			
		}		
		
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		jsonMap.put(TOTAL,this.getPg().getTotalRows());
		jsonMap.put(ROWS,roleList);
		jsonMap.put("initSelecteds", initSelectedsList);
		
		return setInputStreamData(jsonMap);
	}
	
	
	public String userAssignList(){
		Long brchId = Long.valueOf(this.getBrchId());
		Long taskId = Long.valueOf(this.getTaskId());
		toBrchId = Long.valueOf(this.getToBrchIdStr());
		
		List<ConditionBean> conditionList = new ArrayList<ConditionBean>(1);
		conditionList.add(new ConditionBean("userType", ConditionBean.IN, new String[]{Buser.TYPE_BRCH_NOMAL_USER}));
		List<Buser> userList = SourceTemplate.getBean(IUserService.class,BeanNameConstants.USER_SERVICE).queryBranchUser(toBrchId, conditionList, false,this.getPg());
		
		//查询已授权的userId
		List<Long> userIdlist = SourceTemplate.getBean(IHumanTaskAssignService.class,BeanNameConstants.HUMN_TASK_ASSIGN_SERVICE).getUserActors(taskId, brchId);
		
		ArrayList<Integer> initSelectedsList = new ArrayList<Integer>();
		
		for (int i=0; i<userList.size(); i++) {
			if (userIdlist.contains(userList.get(i).getUserId())){
				initSelectedsList.add(i);
			}			
		}
		
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		jsonMap.put(TOTAL,this.getPg().getTotalRows());
		jsonMap.put(ROWS,userList);	
		jsonMap.put("initSelecteds", initSelectedsList);
		
		return setInputStreamData(jsonMap);
	}
	
	public String save(){
		IHumanTaskAssignService assignService = SourceTemplate.getBean(IHumanTaskAssignService.class,BeanNameConstants.HUMN_TASK_ASSIGN_SERVICE);
		Long brchId = Long.valueOf(this.getBrchId());
		Long taskId = Long.valueOf(this.getTaskId());
		toBrchId = Long.valueOf(this.getToBrchIdStr());
		
		if (TaskAssignConst.Assign_Type_User.equals(this.getAssignType())){//按用户
			List<Long> unList = this.getUnIdList();
			for (Long unId : unList) {//删除未选中的
				assignService.deleteByUserId(brchId, taskId, unId);
			}
			
			List<Long> beList = this.getBeIdList();
			for (Long beId : beList) {//增加选中的
				HumnTaskActr taskActr = new HumnTaskActr();
				taskActr.setBrchId(brchId);
				taskActr.setTaskId(taskId);
				taskActr.setActrBrchId(toBrchId);
				taskActr.setActrUserId(beId);
				
				assignService.save(taskActr);
			}
		}
		
		if (TaskAssignConst.Assign_Type_Role.equals(this.getAssignType())){//按角色
			List<Long> unList = this.getUnIdList();
			for (Long unId : unList) {//删除未选中的
				assignService.deleteByBrchRoleId(brchId, taskId, toBrchId, unId);
			}
			
			List<Long> beList = this.getBeIdList();
			for (Long beId : beList) {//增加选中的
				HumnTaskActr taskActr = new HumnTaskActr();
				taskActr.setBrchId(brchId);
				taskActr.setTaskId(taskId);
				taskActr.setActrBrchId(toBrchId);
				taskActr.setActrRoleId(beId);
				
				assignService.save(taskActr);
			}
		}
		
		return null;
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
	
	private List<Long> getUnIdList(){
		return getLongListSplitColon(unIds);
	}
	
	private List<Long> getBeIdList(){
		return getLongListSplitColon(beIds);
	}
	
	private List<Long> getLongListSplitColon(String str){
		return getLongList(str, COLON);
	}
	
	private List<Long> getLongList(String str,String splitStr){
		List<Long> ret = new ArrayList<Long>();
		if (StringUtils.isNotBlank(str)) {
			String[] arr = str.split(splitStr);
			for (String s : arr) {
				ret.add(Long.valueOf(s));
			}
		}
		return ret;
	}
	
	/**
	 * 获取流程树
	 * 
	 * @return
	 */
	public String getProcessTree(){
		String miNo = SessionTool.getUserLogonInfo().getMiNo();
		List<ProcessDef> procList = prodProcMapService.findProcessByMember(miNo, null);
		List<JQueryTreeNode> nodeList = getProcessDefTree(procList,true);
		return setInputStreamData(nodeList);
	}
	
	
	/**
	 * 授权复制页面
	 * 
	 * @return
	 */
	public String toCopy(){
		return SUCCESS;
	}

	/**
	 * 授权复制
	 * 
	 */
	public void doCopy(){
		List<Long> processIdList = getLongList(processIds, COMMA);
		List<Long> destBranchIds = getIdList();
		IHumanTaskAssignService assignService = SourceTemplate.getBean(IHumanTaskAssignService.class,BeanNameConstants.HUMN_TASK_ASSIGN_SERVICE);
		assignService.copyTaskRoleActor(Long.valueOf(brchId), processIdList, destBranchIds);
	}


	public void setProdProcMapService(IProdProcMapService prodProcMapService) {
		this.prodProcMapService = prodProcMapService;
	}


	public String getProcessIds() {
		return processIds;
	}
	public void setProcessIds(String processIds) {
		this.processIds = processIds;
	}


	public void setProductService(IProductService productService) {
		this.productService = productService;
	}
	

}
