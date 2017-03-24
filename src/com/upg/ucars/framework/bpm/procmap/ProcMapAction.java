package com.upg.ucars.framework.bpm.procmap;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.upg.ucars.basesystem.product.core.product.IProductService;
import com.upg.ucars.basesystem.security.core.branch.BranchHelper;
import com.upg.ucars.basesystem.security.core.branch.IBranchService;
import com.upg.ucars.basesystem.security.core.member.IMemberService;
import com.upg.ucars.constant.QueryComponentConst;
import com.upg.ucars.framework.base.BaseAction;
import com.upg.ucars.framework.base.SessionTool;
import com.upg.ucars.framework.base.queryComponent.QueryComponent;
import com.upg.ucars.framework.base.queryComponent.QueryObject;
import com.upg.ucars.framework.base.queryComponent.SortObject;
import com.upg.ucars.framework.bpm.publish.service.IProcessDefService;
import com.upg.ucars.mapping.basesystem.product.ProductInfo;
import com.upg.ucars.mapping.basesystem.security.Branch;
import com.upg.ucars.mapping.basesystem.security.Buser;
import com.upg.ucars.mapping.basesystem.security.MemberInfo;
import com.upg.ucars.mapping.framework.bpm.ProcessDef;
import com.upg.ucars.model.JQueryTreeNode;
import com.upg.ucars.model.security.UserLogonInfo;
import com.upg.ucars.util.JsonUtils;
import com.upg.ucars.util.StringUtil;

public class ProcMapAction extends BaseAction{
	private static final String TO_MAIN="main";
	private static final String TO_ADD="add";
	private static final String TO_EDIT="edit";
	private static final String TO_VIEW="view";
	private static final String AFTER_QUERY="query";
	private static final String QUERY_COMPONENT="component";
	
		
	private List list;	
	private String miNo;//接入编号
	private String prodNo;//产品编号
	private String brchId;
	
	private InputStream jsonStream;
	private InputStream memberStream;
	private InputStream queryStream;
	private String treeJSONInfo;
	
	/**
	 * 基于编码的方式组件式查询
	 * @return
	 */
	public String query(){
		QueryComponent queryComponent=memberService.getQueryComponent();
		if(queryComponent!=null){
			List<QueryObject> list=queryComponent.getQueryList();
			for(QueryObject query:list){
				String label=query.getQueryName();
				query.setLabel(getText(label));
			}
			List<SortObject> sorts=queryComponent.getSortList();
			for(SortObject sort:sorts){
				String viewName=sort.getSortName();
				sort.setViewName(getText(viewName));
			}
		}
		JSONObject jsonObject=JSONObject.fromObject(queryComponent);
		System.out.println(jsonObject.toString());
		queryStream=outJsonUTFStream(jsonObject);
		return "memberComponent";
	}
	/**
	 * 接入管理的主页面
	 * @return
	 */
	public String memberMain(){
		setQueryComponentUrl(QueryComponentConst.MEMBER_QUERY_URL);
		return "memberMain";
	}
	
	/** 数据组件异步查询
	 * @return
	 */
	public String data(){
		setQueryComponentUrl(QueryComponentConst.MEMBER_QUERY_URL);
		QueryComponent query=buildQueryWithHttpRequest(memberService.getQueryComponent());
		List<MemberInfo> list=memberService.queryMemberInfo(getPg(), query);
		return this.setDatagridInputStreamData(list, getPg());
	}
	
	
	public String toMemberProc() throws Exception{		
		
		this.initMemberProc();
		
		return "memberProc";
	}
	
	/**
	 * 初始化已分配及未分配的成员流程。
	 * @throws Exception
	 */
	private void initMemberProc() throws Exception{		
		//UserLogonInfo logoner = SessionTool.getUserLogonInfo();		
		
		List<Object[]> allList =  this.getProdProcMapService().findProcAndProduct(null, null);
		
		List<ProcessDef> beList = this.getProdProcMapService().findProcessByMember(miNo, null);
		
		ArrayList<JQueryTreeNode> nodeList = new ArrayList<JQueryTreeNode>();		
		
		for (Object[] objs : allList) {	
			ProcessDef proc = (ProcessDef)objs[0];
			ProductInfo prod = (ProductInfo)objs[1];
			
			JQueryTreeNode node = new JQueryTreeNode();
			node.setId(proc.getId().toString());
			node.setText(proc.getProcCnName());
			node.setAttribute("isDefault", "1");
			node.setAttribute("prodNo", proc.getDesiProdNo());
			node.setAttribute("prodName", prod.getProdName());
			node.setIconCls("icon-ok");
			
			if (beList.contains(proc)){//已分配流程
				node.setChecked(true);
				
			}else{//未分配				
				node.setChecked(false);
					
			}	
			nodeList.add(node);
		}
		
		String json = JsonUtils.objectToJsonString(nodeList);

		this.setTreeJSONInfo(json);
		
	}
	
	private String pids;	
	
	public String getPids() {
		return pids;
	}
	public void setPids(String pids) {
		this.pids = pids;
	}
	
	/**
	 * 设置接入点流程权限及默认流程
	 * @return
	 * @throws Exception
	 */
	public String saveMemberProc() throws Exception{		
		
		List<Long> procIdList = new ArrayList<Long>();
		
		String[] ids = this.getIds().split(",");
		for (String id : ids) {
			if (!StringUtil.isEmpty(id))
				procIdList.add(Long.valueOf(id));
		}
		
		this.getProdProcMapService().buildMemberProcess(miNo, procIdList);
		
		if (!StringUtil.isEmpty(this.getPids())){
			String[] pids = this.getPids().split(",");
			ArrayList<Long> pidList = new ArrayList<Long>(pids.length); 
			for (String pid : pids) {
				pidList.add(Long.valueOf(pid));
			}
			this.getProdProcMapService().buildDefaultProcess(miNo, pidList);
			
		}
		
		return null;		
	}
	
	public String toMemberSelProc(){
		
		
		return "memberSelProc";
	}
	public String queryMemberSelProc(){
		
		List<ProcessDef> beList = this.getProdProcMapService().findProcessByMember(miNo, this.getPg());
		
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		
		jsonMap.put("total",this.getPg().getTotalRows());
		jsonMap.put("rows",beList);
		JSONObject   jsonObject = JSONObject.fromObject(jsonMap);
		memberStream=outJsonUTFStream(jsonObject);
		
		return "memberProcQuery";
	}
	
	//机构---------------------
	public String brchMain(){
		
		return "brchMain";
	}
	
	public String brchQuery() throws IOException {
		List<Branch> records = new ArrayList<Branch>();
		Branch brch = new Branch();
		getPg().setCurrentPage(getPage());
		getPg().setPageSize(getRows());
		UserLogonInfo uli = SessionTool.getUserLogonInfo();
		if(Buser.TYPE_BRCH_GLOBAL_MANAGER.equals(uli.getUserType()) || Buser.TYPE_BRCH_LOCAL_MANAGER.equals(uli.getUserType())) {
			if(uli.getBranchId() != null) {
				Branch usrBrch = branchService.getBranchByBrchId(uli.getBranchId());
				brch.setTreeCode(usrBrch.getTreeCode());
				records = branchService.findSubBrchs(brch, getPg());
			} else {
				records = branchService.getHQBranches(getPg());
			}
		}
		for(Branch branch : records) {
			String parentTreeCode = BranchHelper.getParentTreeCode(branch.getTreeCode());
			if(!"".equals(parentTreeCode)) {
				Branch parentBranch = branchService.getBrchByTreeCode(parentTreeCode);
				if(parentBranch != null) {
					//上级机构名称
					branch.setParentBrchName(parentBranch.getBrchName());
				}
			}
		}
		
		return super.setDatagridInputStreamData(records, this.getPg());
	}
	
	public String toBrchProc() throws Exception{	
		this.initBrchProc();
		return "toBrchProc";
	}
	/**
	 * 初始化已分配及未分配的成员流程。
	 * @throws Exception
	 */
	private void initBrchProc() throws Exception{		
		UserLogonInfo logoner = SessionTool.getUserLogonInfo();		
		
		List<ProcessDef> allList = this.getProdProcMapService().findProcessByMember(logoner.getMiNo(), null);
		
		List<ProcessDef> beList = this.getProdProcMapService().findProcessByBrch(Long.valueOf(this.getBrchId()), null);
		
		ArrayList<JQueryTreeNode> nodeList = new ArrayList<JQueryTreeNode>();		
		
		for (ProcessDef proc : allList) {		
			JQueryTreeNode node = new JQueryTreeNode();
			node.setId(proc.getId().toString());
			node.setText(proc.getProcCnName());
			node.setIconCls("icon-ok");
			
			if (beList.contains(proc)){//已分配流程
				node.setChecked(true);
				
			}else{//未分配				
				node.setChecked(false);
					
			}	
			nodeList.add(node);
		}
		
		String json = JsonUtils.objectToJsonString(nodeList);
		this.setTreeJSONInfo(json);
	}
	
	
	public String saveBrchProc() throws Exception{		
		
		List<Long> procIdList = new ArrayList<Long>();
		
		String[] ids = this.getIds().split(",");
		for (String id : ids) {
			if (!StringUtil.isEmpty(id))
				procIdList.add(Long.valueOf(id));
		}
		
		this.getProdProcMapService().buildBranchProcess(Long.valueOf(this.getBrchId()), procIdList);
		
		return null;		
	}
	
	
	
	public List getList() {
		return list;
	}
	public void setList(List list) {
		this.list = list;
	}
	public String getMiNo() {
		return miNo;
	}
	public void setMiNo(String miNo) {
		this.miNo = miNo;
	}
	public String getProdNo() {
		return prodNo;
	}
	public void setProdNo(String prodNo) {
		this.prodNo = prodNo;
	}
	public InputStream getMemberStream() {
		return memberStream;
	}
	public void setMemberStream(InputStream memberStream) {
		this.memberStream = memberStream;
	}
	public InputStream getQueryStream() {
		return queryStream;
	}
	public void setQueryStream(InputStream queryStream) {
		this.queryStream = queryStream;
	}
	public InputStream getJsonStream() {
		return jsonStream;
	}
	public void setJsonStream(InputStream jsonStream) {
		this.jsonStream = jsonStream;
	}
	
	public String getTreeJSONInfo() {
		return treeJSONInfo;
	}
	public void setTreeJSONInfo(String treeJSONInfo) {
		this.treeJSONInfo = treeJSONInfo;
	}

	public String getBrchId() {
		return brchId;
	}
	public void setBrchId(String brchId) {
		this.brchId = brchId;
	}

	//Spring注入开始
	private IMemberService memberService;
	
	public IMemberService getMemberService() {
		return memberService;
	}
	public void setMemberService(IMemberService memberService) {
		this.memberService = memberService;
	}
	
	private IProcessDefService processService;
	public IProcessDefService getProcessService() {
		return processService;
	}
	public void setProcessService(IProcessDefService processService) {
		this.processService = processService;
	}
	
	private IProdProcMapService prodProcMapService;
	public IProdProcMapService getProdProcMapService() {
		return prodProcMapService;
	}
	public void setProdProcMapService(IProdProcMapService prodProcMapService) {
		this.prodProcMapService = prodProcMapService;
	}
	private IProductService productService;

	public IProductService getProductService() {
		return productService;
	}
	public void setProductService(IProductService productService) {
		this.productService = productService;
	}
	private IBranchService branchService;
	public IBranchService getBranchService() {
		return branchService;
	}
	public void setBranchService(IBranchService branchService) {
		this.branchService = branchService;
	}
	
	//Spring注入结束
	

}
