package com.upg.ucars.basesystem.webUtil.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.upg.ucars.basesystem.audit.core.auditRoute.IAuditRouteService;
import com.upg.ucars.basesystem.dictionary.core.code.ICodeMetaService;
import com.upg.ucars.basesystem.dictionary.core.code.ICodeService;
import com.upg.ucars.basesystem.product.core.product.IProductService;
import com.upg.ucars.basesystem.security.core.branch.IBranchService;
import com.upg.ucars.basesystem.security.core.member.IMemberService;
import com.upg.ucars.basesystem.security.core.role.IRoleService;
import com.upg.ucars.factory.JQueryTreeNodeManager;
import com.upg.ucars.framework.base.BaseAction;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.SessionTool;
import com.upg.ucars.framework.base.queryComponent.QueryComponent;
import com.upg.ucars.framework.base.queryComponent.QueryObject;
import com.upg.ucars.mapping.basesystem.audit.AuditRoute;
import com.upg.ucars.mapping.basesystem.dictionary.Code;
import com.upg.ucars.mapping.basesystem.dictionary.CodeMeta;
import com.upg.ucars.mapping.basesystem.product.ProductInfo;
import com.upg.ucars.mapping.basesystem.security.Branch;
import com.upg.ucars.mapping.basesystem.security.MemberInfo;
import com.upg.ucars.mapping.basesystem.security.Role;
import com.upg.ucars.model.ConditionBean;
import com.upg.ucars.model.JQueryTreeNode;
import com.upg.ucars.model.OrderBean;
import com.upg.ucars.model.security.UserLogonInfo;
import com.upg.ucars.util.JsonUtils;
import com.upg.ucars.util.StringUtil;

public class ChooseAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3754554276583457115L;
	//机构
	private Branch branch = new Branch();
	private String brchIdDiv;
	private String brchNoDiv;
	private String brchNameDiv;
	private String treeCodeDiv;
	/** 联行号 */
	private String unionBankNoId;
	
	private IBranchService branchService;
	//
	private String serialNo;//编号
	private String name;//名称
	private String identity;//id
	private String winId;
	private String treeJSONInfo;
	//接入点
	
	private String memberNoDiv;
	private String memberNameDiv;
	private IMemberService memberService;
	//产品
	private String prodIdDiv;
	private String prodNoDiv;
	private String prodNameDiv;
	private String prodType;
	private String attrKey;
	private String attrValue;
	private String prodNos;
	private String notProdNos;
	private IProductService productService;
	
	
	
	/** 字典元信息服务 */
	private ICodeMetaService codeMetaService;
	/** 字典服务 */
	private ICodeService		codeService;
	/** 审批路线服务 */
	private IAuditRouteService auditRouteService;
	/** 审批路线实体 */
	private AuditRoute auditRoute;
	
	/** 字典元信息实体 */
	private CodeMeta cm;
	private Code code;
	private Role commonRole;
	private IRoleService roleService;
	/** 字典元信息KEY */
	private String codeMetaKey;
	/** 字典元信息名称 */
	private String codeMetaName;
	/** 接入点 */
	private MemberInfo member;
	/** 显示ID */
	private String displayId;
	/** valueID */
	private String valueId;
	
	/**
	 * 
	 * 字典元信息选择页面
	 *
	 * @return
	 */
	public String codeMeta(){
		return "chooseCodeMeta";
	}
	public String code(){
		return SUCCESS;
	}
	public String commonRole(){
		return SUCCESS;
	}
	public String queryCommonRole(){
		List<ConditionBean> conditionList = new ArrayList<ConditionBean>();
		if (commonRole != null) {
			String roleName=commonRole.getRoleName();
			if (StringUtils.isNotBlank(roleName)) {
				conditionList.add(new ConditionBean("roleName",ConditionBean.LIKE, roleName));
			}
		}
		List<Role> list = roleService.queryRoles(conditionList, getPg());
		return setDatagridInputStreamData(list, getPg());
	}
	public String queryCode(){
		List<ConditionBean> conditionList = new ArrayList<ConditionBean>();
		if (code != null) {
			String codeKey = code.getCodeKey();
			if (StringUtils.isNotBlank(codeKey)) {
				conditionList.add(new ConditionBean(Code.CODE_KEY, codeKey));
			}
			String codeNo = code.getCodeNo();
			if (StringUtils.isNotBlank(codeNo)) {
				conditionList.add(new ConditionBean(Code.CODE_NO,
						ConditionBean.LIKE, codeNo));
			}
			String codeName = code.getCodeName();
			if (StringUtils.isNotBlank(codeName)) {
				conditionList.add(new ConditionBean(Code.CODE_NAME,
						ConditionBean.LIKE, codeName));
			}
			String langType = code.getLangType();
			if (StringUtils.isNotBlank(langType)) {
				conditionList.add(new ConditionBean(Code.LANG_TYPE, langType));
			}
		}
		List<Code> list = codeService.getEntityList(conditionList, getPg());
		return setDatagridInputStreamData(list, getPg());
	}
	/**
	 * 
	 * 字典元信息列表
	 *
	 * @return
	 */
	public String queryCodeMeta(){
		Page pg = getPg();
		List<CodeMeta> list = codeMetaService.getCodeMetaList(cm, pg);
		return setDatagridInputStreamData(list,pg);
	}
	
	/**
	 * 选择机构主页面
	 * @return
	 */
	public String branch(){
		return "chooseBrchPage";
	}
	
	/**
	 * 选择树状机构主页面
	 * @return
	 */
	public String treeBranch(){
		return "chooseTreeBrchPage";
	}
	/**
	 * 机构分页查询
	 * @return
	 */
	public String queryBranch(){
		UserLogonInfo logonInfo = SessionTool.getUserLogonInfo();
		getPg().setCurrentPage(getPage());
		getPg().setPageSize(getRows());
		List<Branch> brchList = null;
		String miNo=logonInfo.getMiNo();
		Page pg = getPg();
		if(miNo==null||miNo.length()<1){
			brchList = branchService.getHQBranches(branch,pg);
		}else{
			if(logonInfo.getBranchId() != null ) {
				Branch cur_branch = branchService.getBranchByBrchId(logonInfo.getBranchId());
				Branch queryBrch = new Branch();
				queryBrch.setMiNo(miNo);
				queryBrch.setTreeCode(cur_branch.getTreeCode());
				if( branch != null ){
					if( branch.getBrchName() != null && branch.getBrchName().trim().length() > 0){
						queryBrch.setBrchName(branch.getBrchName().trim());
					}
					if( branch.getBrchNo() != null && branch.getBrchNo().trim().length() > 0 ){
						queryBrch.setBrchNo(branch.getBrchNo().trim());
					}
				}
				brchList = branchService.findSubBrchs(queryBrch, pg,true);
			}
		}
		return setDatagridInputStreamData(brchList,pg);
	}
	
	/**
	 * 
	 * 接入点选择页面
	 *
	 * @return
	 */
	public String miNo(){
		return SUCCESS;
	}
	/**
	 * 
	 * 接入点查询
	 *
	 * @return
	 */
	public String queryMiNo(){
		Page pg = getPg();
		List<MemberInfo> list = memberService.getMemberList(member, pg);
		return setDatagridInputStreamData(list, pg);
	}
	
	
	/**
	 * 选择机构用户主页面
	 * @return
	 */
	public String brchUser(){
		return "chooseBrchUser";
	}
	
	
	/**
	 * 
	 * 审批路线选择
	 *
	 * @return
	 */
	public String auditRoute(){
		return SUCCESS;
	}
	
	/**
	 * 
	 * 审批路线查询
	 * 
	 * @return
	 */
	public String queryAuditRoute() {
		Page pg = getPg();
		if (auditRoute == null) {
			auditRoute = new AuditRoute();
		}
		auditRoute.setMiNo(SessionTool.getUserLogonInfo().getMiNo());
		List<AuditRoute> list = auditRouteService
				.findAuditRoute(auditRoute, pg);
		return setDatagridInputStreamData(list, pg);
	}

	/**
	 * 选择接入点主页面
	 * @return
	 */
	public String member(){
		return "chooseMemberPage";
	}
	/**
	 * 接入点分页查询
	 * @return
	 */
	public String queryMember(){
		QueryComponent qc = new QueryComponent();
		{
			QueryObject nameQo = new QueryObject();
			nameQo.setQueryName("miName");nameQo.setQueryOperate(ConditionBean.LIKE);nameQo.setQueryValue(this.getName());
			qc.addQuery(nameQo);			
		}
		
		{
			QueryObject noQo = new QueryObject();
			noQo.setQueryName("miNo");noQo.setQueryOperate(ConditionBean.EQUAL);noQo.setQueryValue(this.getSerialNo());
			qc.addQuery(noQo);			
		}
		
		List  memberList = this.memberService.queryMemberInfo(this.getPg(), qc);

		return setDatagridInputStreamData(memberList,getPg());
	}
	
	/**
	 * 选择产品主页面
	 * @return
	 */
	public String product(){
		
		return "ChooseProduct";
	}
	public String queryProduct(){
		ArrayList<ConditionBean> conditionList = new ArrayList<ConditionBean>(2);
		
		
		if (!StringUtil.isEmpty(this.prodType)){
			String[] types = this.prodType.split(",");
			conditionList.add(new ConditionBean("prodType", ConditionBean.IN,  types));
					
		}
		
		String parentId = getId();
		if (StringUtils.isBlank(parentId)) {
			if (StringUtils.isBlank(prodNos) && StringUtils.isBlank(notProdNos)) {
				conditionList.add(new ConditionBean("parentProdId", ConditionBean.IS_NULL,null));
				
			}else{
				if (StringUtils.isNotBlank(prodNos)) {
					conditionList.add(new ConditionBean("prodNo",ConditionBean.IN,prodNos.split(COMMA)));
				}
				if (StringUtils.isNotBlank(notProdNos)) {
					conditionList.add(new ConditionBean("prodNo",ConditionBean.NOT_IN,notProdNos.split(COMMA)));
					conditionList.add(new ConditionBean("parentProdId", ConditionBean.IS_NULL,null));
				}
			}
		}else{
			conditionList.add(new ConditionBean("parentProdId", getPKId()));
		}
		
		List<OrderBean> orderList = new ArrayList<OrderBean>();
		orderList.add(new OrderBean("sortNo", true));
		
		List<ProductInfo> allList = this.productService.queryProductByConditin(conditionList, orderList, null);
		
//		allList = ProductHelper.sortProductList(allList);
		
		ArrayList<JQueryTreeNode> rootNodeList = new ArrayList<JQueryTreeNode>();
		
		
//		HashMap<Long, JQueryTreeNode> beNodeMap = new HashMap<Long, JQueryTreeNode>();
		
		for (ProductInfo product : allList) {			
			JQueryTreeNode node = new JQueryTreeNode();
			node.setId(product.getId()+"");
			node.setText(product.getProdName());
			node.setAttribute("product", JsonUtils.objectToJsonString(product));
			
//			beNodeMap.put(product.getId(), node);
			
//			if(product.getParentProdId()==null){//根菜单跳过，由子菜单通过父级来添加。
//				rootNodeList.add(node);				
//			}else{
//				JQueryTreeNode parentNode = beNodeMap.get(product.getParentProdId());
//				parentNode.setState(JQueryTreeNode.STATE_CLOSED);
//				parentNode.addChild(node);
//			}		
			
			rootNodeList.add(node);
			
			if (productService.hasSubProduct(product.getId())) {
				node.setState(JQueryTreeNode.STATE_CLOSED);
			}
			
		}
		return setInputStreamData(rootNodeList);
	}
	
	public String attrProduct(){
		
		
		return "ChooseProduct";
	}
	public String queryAttrProduct(){
		List<ProductInfo> list = null;
		String miNo = SessionTool.getUserLogonInfo().getMiNo();
		boolean firstLevel = false;
		if (StringUtils.isBlank(getId())) { //
			firstLevel = true;
			list = new ArrayList<ProductInfo>();
			List<ProductInfo> attrList = productService.getProductListByAttr(miNo, attrKey, attrValue);
			if (attrList != null && attrList.size() > 0) {
				List<Long> productIdList = new ArrayList<Long>();
				for (ProductInfo pi : attrList) {
					ProductInfo rootProduct = getFirstLevelProductInfo(pi);
					if (rootProduct != null) {
						if (!productIdList.contains(rootProduct.getId())) {
							list.add(rootProduct);
							productIdList.add(rootProduct.getId());
						}
					}
				}
			}
		}else{
			list = productService.getSubProductListByAttrAndParent(miNo, attrKey, attrValue,getPKId());
		}
		
		list = processIncludeAndExcludeProduct(list, prodNos, notProdNos);
		
		List<JQueryTreeNode> result = convertProductToJtn(list, prodType);
		if (firstLevel && result != null && result.size() > 0) {
			for (JQueryTreeNode jtn : result) {
				if (StringUtils.equals(JQueryTreeNode.STATE_CLOSED, jtn.getState())) {
					jtn.setState(JQueryTreeNode.STATE_OPEN);
					List<ProductInfo> childs = productService.getSubProductListByAttrAndParent(miNo, attrKey, attrValue,Long.valueOf(jtn.getId()));
					childs = processIncludeAndExcludeProduct(childs, prodNos, notProdNos);
					jtn.setChildren(convertProductToJtn(childs, prodType));
				}
			}
		}
		return setInputStreamData(result);
	}
	
	private List<ProductInfo> processIncludeAndExcludeProduct(List<ProductInfo> list, String includes,String excludes){
		List<ProductInfo> result = list;
		if (result != null && result.size() > 0) {
			if (StringUtils.isNotBlank(excludes)) {
				List<ProductInfo> newList = new ArrayList<ProductInfo>(result.size());
				for (ProductInfo pi : result) {
					if (!excludes.contains(pi.getProdNo())) {
						newList.add(pi);
					}
				}
				result = newList;
			}
			
			if (StringUtils.isNotBlank(includes)) {
				List<ProductInfo> newList = new ArrayList<ProductInfo>(result.size());
				for (ProductInfo pi : result) {
					if (includes.contains(pi.getProdNo())) {
						newList.add(pi);
					} else {
						if (isParentProductInfo(pi, includes)) {
							newList.add(pi);
						}
					}
				}
				result = newList;
			}
			
		}
		return result;
	}
	
	private boolean isParentProductInfo(ProductInfo parentProductInfo,String childrenProdNos){
		boolean result = false;
		if (parentProductInfo != null && StringUtils.isNotBlank(childrenProdNos)) {
			String[] prodNoArray = childrenProdNos.trim().split(COMMA);
			for (String pn : prodNoArray) {
				if (result) {
					break;
				}
				ProductInfo pi = productService.getProductByProdNo(pn);
				if (pi != null && pi.getParentProdId() != null) {
					if (pi.getParentProdId().longValue() == parentProductInfo.getId().longValue()) {
						result = true;
						break;
					} else {
						ProductInfo parent = productService.getProduct(pi.getParentProdId());
						if (parent != null) {
							result = isParentProductInfo(parentProductInfo, parent.getProdNo());
						}
					}
				}
			}
		}
		return result;
	}
	
	private List<JQueryTreeNode> convertProductToJtn(List<ProductInfo> list,String prodType){
		List<JQueryTreeNode> result = new ArrayList<JQueryTreeNode>() ;
		if (list != null && list.size() > 0) {
			List<JQueryTreeNode> jtnList = JQueryTreeNodeManager.convertListToJTN(list);
			for (JQueryTreeNode jtn : jtnList) {
				if (productService.hasSubProduct(Long.valueOf(jtn.getId()))) {
					jtn.setState(JQueryTreeNode.STATE_CLOSED);
				}else{
					if (StringUtils.isNotBlank(prodType)) {
						if (!StringUtils.equals(prodType, jtn.getAttribute("prodType"))) {
							continue;
						}
					}
				}
				result.add(jtn);
			}
		}
		return result;
	}
	
	private ProductInfo getFirstLevelProductInfo(ProductInfo pi){
		ProductInfo productInfo = null;
		if (pi != null) {
			if (pi.getParentProdId() == null) {
				productInfo = pi;
			}else{
				ProductInfo parent = productService.getProduct(pi.getParentProdId());
				if (parent != null) {
					productInfo = getFirstLevelProductInfo(parent);
				}else{
					productInfo = pi;
				}
			}
		}
		return productInfo;
	}
	
	
	public Branch getBranch() {
		return branch;
	}
	public void setBranch(Branch branch) {
		this.branch = branch;
	}
	public void setBranchService(IBranchService branchService) {
		this.branchService = branchService;
	}
	public String getBrchIdDiv() {
		return brchIdDiv;
	}
	public void setBrchIdDiv(String brchIdDiv) {
		this.brchIdDiv = brchIdDiv;
	}
	
	public String getBrchNameDiv() {
		return brchNameDiv;
	}
	public void setBrchNameDiv(String brchNameDiv) {
		this.brchNameDiv = brchNameDiv;
	}
	public String getWinId() {
		return winId;
	}
	public void setWinId(String winId) {
		this.winId = winId;
	}
	public String getMemberNameDiv() {
		return memberNameDiv;
	}
	public void setMemberNameDiv(String memberNameDiv) {
		this.memberNameDiv = memberNameDiv;
	}
	public void setMemberService(IMemberService memberService) {
		this.memberService = memberService;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIdentity() {
		return identity;
	}
	public void setIdentity(String identity) {
		this.identity = identity;
	}
	public String getProdIdDiv() {
		return prodIdDiv;
	}
	public void setProdIdDiv(String prodIdDiv) {
		this.prodIdDiv = prodIdDiv;
	}
	public String getProdNoDiv() {
		return prodNoDiv;
	}
	public void setProdNoDiv(String prodNoDiv) {
		this.prodNoDiv = prodNoDiv;
	}
	public String getProdNameDiv() {
		return prodNameDiv;
	}
	public void setProdNameDiv(String prodNameDiv) {
		this.prodNameDiv = prodNameDiv;
	}
	public void setProductService(IProductService productService) {
		this.productService = productService;
	}
	public String getTreeJSONInfo() {
		return treeJSONInfo;
	}
	public void setTreeJSONInfo(String treeJSONInfo) {
		this.treeJSONInfo = treeJSONInfo;
	}
	public String getProdType() {
		return prodType;
	}
	public void setProdType(String prodType) {
		this.prodType = prodType;
	}
	public String getBrchNoDiv() {
		return brchNoDiv;
	}
	public void setBrchNoDiv(String brchNoDiv) {
		this.brchNoDiv = brchNoDiv;
	}
	public String getMemberNoDiv() {
		return memberNoDiv;
	}
	public void setMemberNoDiv(String memberNoDiv) {
		this.memberNoDiv = memberNoDiv;
	}
	public String getTreeCodeDiv() {
		return treeCodeDiv;
	}
	public void setTreeCodeDiv(String treeCodeDiv) {
		this.treeCodeDiv = treeCodeDiv;
	}

	public void setCodeMetaService(ICodeMetaService codeMetaService) {
		this.codeMetaService = codeMetaService;
	}

	public CodeMeta getCm() {
		return cm;
	}

	public void setCm(CodeMeta cm) {
		this.cm = cm;
	}

	public String getCodeMetaKey() {
		return codeMetaKey;
	}

	public void setCodeMetaKey(String codeMetaKey) {
		this.codeMetaKey = codeMetaKey;
	}

	public String getCodeMetaName() {
		return codeMetaName;
	}

	public void setCodeMetaName(String codeMetaName) {
		this.codeMetaName = codeMetaName;
	}

	public String getAttrKey() {
		return attrKey;
	}

	public void setAttrKey(String attrKey) {
		this.attrKey = attrKey;
	}

	public String getAttrValue() {
		return attrValue;
	}

	public void setAttrValue(String attrValue) {
		this.attrValue = attrValue;
	}
	public MemberInfo getMember() {
		return member;
	}
	public void setMember(MemberInfo member) {
		this.member = member;
	}

	public String getDisplayId() {
		return displayId;
	}

	public void setDisplayId(String displayId) {
		this.displayId = displayId;
	}

	public String getValueId() {
		return valueId;
	}

	public void setValueId(String valueId) {
		this.valueId = valueId;
	}

	public String getUnionBankNoId() {
		return unionBankNoId;
	}

	public void setUnionBankNoId(String unionBankNoId) {
		this.unionBankNoId = unionBankNoId;
	}
	public AuditRoute getAuditRoute() {
		return auditRoute;
	}	
	public void setAuditRoute(AuditRoute auditRoute) {
		this.auditRoute = auditRoute;
	}
	public void setAuditRouteService(IAuditRouteService auditRouteService) {
		this.auditRouteService = auditRouteService;
	}
	public void setCodeService(ICodeService codeService) {
		this.codeService = codeService;
	}
	public Code getCode() {
		return code;
	}
	public void setCode(Code code) {
		this.code = code;
	}
	public Role getCommonRole() {
		return commonRole;
	}
	public void setCommonRole(Role commonRole) {
		this.commonRole = commonRole;
	}
	public void setRoleService(IRoleService roleService) {
		this.roleService = roleService;
	}
	public String getProdNos() {
		return prodNos;
	}
	public void setProdNos(String prodNos) {
		this.prodNos = prodNos;
	}
	public String getNotProdNos() {
		return notProdNos;
	}
	public void setNotProdNos(String notProdNos) {
		this.notProdNos = notProdNos;
	}
	
}
