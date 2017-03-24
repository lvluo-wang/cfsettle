package com.upg.ucars.basesystem.product.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.upg.ucars.basesystem.dictionary.core.code.ICodeService;
import com.upg.ucars.basesystem.dictionary.util.DictionaryUtil;
import com.upg.ucars.basesystem.product.core.product.IMemberProductAttributeService;
import com.upg.ucars.basesystem.product.core.product.IProductAttributeService;
import com.upg.ucars.basesystem.product.core.product.IProductService;
import com.upg.ucars.basesystem.product.core.product.IProductSysfuncService;
import com.upg.ucars.basesystem.product.core.product.ProdType;
import com.upg.ucars.basesystem.product.core.product.ProductNode;
import com.upg.ucars.basesystem.security.core.branch.BranchHelper;
import com.upg.ucars.basesystem.security.core.branch.IBranchService;
import com.upg.ucars.basesystem.security.core.member.IMemberService;
import com.upg.ucars.basesystem.security.core.sysfunc.ISysfuncService;
import com.upg.ucars.constant.CodeKey;
import com.upg.ucars.constant.CommonConst;
import com.upg.ucars.constant.ErrorCodeConst;
import com.upg.ucars.constant.QueryComponentConst;
import com.upg.ucars.constant.SessionKeyConst;
import com.upg.ucars.factory.JQueryTreeNodeManager;
import com.upg.ucars.framework.base.BaseAction;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.QueryCondition;
import com.upg.ucars.framework.base.SessionTool;
import com.upg.ucars.framework.base.queryComponent.QueryComponent;
import com.upg.ucars.framework.base.queryComponent.SortObject;
import com.upg.ucars.framework.exception.ExceptionManager;
import com.upg.ucars.framework.exception.ServiceException;
import com.upg.ucars.mapping.basesystem.dictionary.Code;
import com.upg.ucars.mapping.basesystem.product.MemberProductAttribute;
import com.upg.ucars.mapping.basesystem.product.MemberProductInfo;
import com.upg.ucars.mapping.basesystem.product.ProductAttribute;
import com.upg.ucars.mapping.basesystem.product.ProductInfo;
import com.upg.ucars.mapping.basesystem.product.ReBrchProd;
import com.upg.ucars.mapping.basesystem.security.Branch;
import com.upg.ucars.mapping.basesystem.security.Buser;
import com.upg.ucars.mapping.basesystem.security.MemberInfo;
import com.upg.ucars.mapping.basesystem.security.Sysfunc;
import com.upg.ucars.model.ConditionBean;
import com.upg.ucars.model.JQueryTreeNode;
import com.upg.ucars.model.security.UserLogonInfo;
import com.upg.ucars.util.StringUtil;


/**
 * 产品信息
 * 
 * @author shentuwy
 * @date 2012-7-3
 *
 */
public class ProductAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 143634791571875043L;
	
	private static final String QUERY_LIST="list";
	private static final String TO_MEMBER_PRODUCT="to_set_member_product";
	private static final String TO_BRANCH_PRODUCT="to_set_branch_product";
	
	private ProductInfo prod=null;
	private ProductInfo parentProd=null;
	private MemberProductInfo memberProd;
	private List<Code> prodType;
	private Long prodId;
	private IProductService productService;
	private Long sourceId;
	private Long targetId;
	private String point;
	private String miNo;
	private String prodNos;
	private String prodNo;
	
	/** 接入点产品属性 */
	private MemberProductAttribute mpa;
	/** 产品属性 */
	private ProductAttribute pa;
	/** 字典列表 */
	private List<Code> codeList;
	//
	private Branch brch;
	
	private IMemberService memberService;	
	private MemberInfo member;
	private IBranchService branchService;
	/** 权限服务 */
	private ISysfuncService sysfuncService;
	/** 产品权限服务 */
	private IProductSysfuncService productSysfuncService;
	/** 属性服务 */
	private IProductAttributeService productAttributeService;
	/** 产品属性关系服务 */
	private IMemberProductAttributeService memberProductAttributeService;
	
	/** 字典服务 */
	private ICodeService codeService;
	
	private List<Code> yesNoList;//
	private String isAgree;
	
	/** 接入点列表 */
	private List<MemberInfo> miList;
	
	/**
	 * 定义产品主页面
	 * @return
	 * 
	 */
	public String mainPage(){
		return MAIN;
	}
	
	/**帮助页面*/
	public String help(){
		return "help";
	}
	/**
	 * 添加产品信息
	 * @return
	 * 
	 */
	public String add(){
		prodType=DictionaryUtil.getCodesByKey(CodeKey.PRODUCT_PROD_TYPE);
		if(prodId!=null){
			parentProd=productService.getProduct(prodId);
		}
		return ADD;
	}
	/**
	 * 编辑产品信息
	 * @return
	 * 
	 */
	public String edit(){
		prodType=DictionaryUtil.getCodesByKey(CodeKey.PRODUCT_PROD_TYPE);
		prod=productService.getProduct(prodId);
		return EDIT;
	}
	/**
	 * 查看产品信息
	 * @return
	 */
	public String view(){
		prodType=DictionaryUtil.getCodesByKey(CodeKey.PRODUCT_PROD_TYPE);
		prod=productService.getProduct(prodId);
		return VIEW;
	}
	
	
	/**
	 * 删除产品信息
	 * 
	 */
	public void delete(){
		productSysfuncService.assignSysfuncForProduct(prodId, null);//设空权限
		productService.removeProduct(prodId);
	}
	/**
	 * 保存
	 * 
	 */
	public void save(){
		
		ProductInfo pi = productService.getProductByProdNo(prod.getProdNo());
		if (pi!=null){
			throw ExceptionManager.getException(ServiceException.class, ErrorCodeConst.PRODUCT_SAVE_EXIST_NO);
		}
		
		if(parentProd!=null&&parentProd.getId()!=null){
			prod.setParentProdId(parentProd.getId());
		}
		productService.createProduct(prod);
	}
	/**
	 * 更新
	 * 
	 */
	public void update(){
		ProductInfo pi = productService.getProductByProdNo(prod.getProdNo());
		if (pi!=null && !pi.getId().equals(prod.getId())){
			throw ExceptionManager.getException(ServiceException.class, ErrorCodeConst.PRODUCT_SAVE_EXIST_NO);
		}
		if (ProdType.MAIN_BUSI.equals(pi.getProdType()) && !pi.getProdType().equals(prod.getProdType())){
			List<Sysfunc> sysfuncList = this.productSysfuncService.getSysfuncByProduct(pi.getId());
			if (!sysfuncList.isEmpty()){
				throw ExceptionManager.getException(ServiceException.class, ErrorCodeConst.PRODUCT_UPDATETYPE_BE_FUNC);
			}
		}
		 
		productService.modifyProduct(prod);
	}
	public String queryList(){
		
		return QUERY_LIST;
	}
	/**
	 * 移动产品信息关系
	 * 
	 */
	public void move(){
		
		productService.moveProduct(sourceId, targetId, point);
	}

	/**
	 * 异步查询产品
	 * @return
	 * 
	 */
	public String querySubProd(){
		Long parentId = null;
		if(StringUtils.isNotBlank(getId())){
			parentId = getPKId();
		}
		List<ProductInfo> nodes = productService.getSubProduct(parentId);
		
		List<JQueryTreeNode> jtnList = JQueryTreeNodeManager.convertListToJTN(nodes);
		if( jtnList != null && jtnList.size() > 0 ){
			for( JQueryTreeNode jtn : jtnList ){
				if(productService.hasSubProduct(Long.valueOf(jtn.getId()))){
					jtn.setState(JQueryTreeNode.STATE_CLOSED);
				}
				if( StringUtils.isNotBlank(jtn.getTextKey()) ){
					jtn.setText(getText(jtn.getTextKey(),jtn.getText()));
				}
			}
		}
		return setInputStreamData(jtnList);
	}
	
	
	
	
	public String getEbankFunc(){
		List<Sysfunc> list = sysfuncService.getAllEBankMenuSysfunc();
		JQueryTreeNodeManager.convertListToJTN(list);
		return setInputStreamData(list);
	}
	
	
	public String findProdNames(){
		List list=new ArrayList();
		
		if (StringUtil.isEmpty(this.miNo)){
			List<ProductInfo> prodList = this.productService.queryProduct(null, null);
			for (ProductInfo productInfo : prodList) {
				ProductNode node = new ProductNode();
				node.setProdNo(productInfo.getProdNo());
				if (productInfo.getProdNameKey()==null)
					node.setProdName(productInfo.getProdName());
				else
					node.setProdName(this.getText(productInfo.getProdNameKey()));
				
				list.add(node);
			}
			
		}else{
			 List<Object[]> prodList = this.productService.findMemberProduct(miNo, null);
			 for (Object[] objects : prodList) {
				 ProductInfo productInfo= (ProductInfo)objects[0];
				 MemberProductInfo memberProductInfo= (MemberProductInfo)objects[1];
				 ProductNode node = new ProductNode();
				 node.setProdNo(productInfo.getProdNo());
				 if (memberProductInfo.getProdAlias()!=null)
					 node.setProdName(memberProductInfo.getProdAlias());
				 else{
					 if (productInfo.getProdNameKey()==null)
							node.setProdName(productInfo.getProdName());
						else
							node.setProdName(this.getText(productInfo.getProdNameKey()));
				 }				
				list.add(node);
			}
		}
		
		
		return setInputStreamData(list);
	}
	
	public String findProdName(){
		String prodName = null;
		if (StringUtil.isEmpty(this.miNo)){
			//this.productService.findMemberProduct(prodName, null);
			ProductInfo prodInfo = this.productService.getProductByProdNo(this.prodNo);
			if (prodInfo.getProdNameKey() == null)
				prodName = prodInfo.getProdName();
			else
				prodName = this.getText(prodInfo.getProdNameKey());
		}else{//有接入行
			MemberProductInfo mp = this.productService.getMemberProductByProdNo(this.miNo, this.prodNo);			
			if ( mp==null || mp.getProdAlias()==null){
				ProductInfo prodInfo = this.productService.getProductByProdNo(this.prodNo);
				if (prodInfo.getProdNameKey() == null)
					prodName = prodInfo.getProdName();
				else
					prodName = this.getText(prodInfo.getProdNameKey());
			}else{
				prodName = mp.getProdAlias();
			}
		}
		
		HashMap<String,Object> map=new HashMap<String,Object>();
		map.put("prodName", prodName);
		return setInputStreamData(map);
		
	}
	
	public String memberMain(){
		
		
		return "memberMain";
	}
	
	/**
	 * 异步查询接入点拥有产品
	 * @return
	 * 
	 */
	public String queryMemberSubProd(){
		String miNo = SessionTool.getUserLogonInfo().getMiNo();
		List<Map<String,Object>> funcs=new ArrayList<Map<String,Object>>();
		List<Object[]> nodes=null;
		if(this.getId()==null||this.getId().length()<1){
			nodes=productService.getMemberSubProduct(miNo, null);
		}else{
			nodes=productService.getMemberSubProduct(miNo, this.getPKId());
		}
		
		for(Object[] obj:nodes){
			ProductInfo prod = (ProductInfo)obj[0];
			MemberProductInfo memberProd = (MemberProductInfo)obj[1];
			Map<String,Object> item=new HashMap<String,Object>();
			item.put("id", prod.getId());
			String funcName=memberProd.getProdAlias();
			if (memberProd.getProdAlias()==null){
				String nameKey=prod.getProdNameKey();
				if(nameKey!=null&&nameKey.length()>0){
					funcName=getText(nameKey);
				}else{
					funcName=prod.getProdName();
				}
			}
			
			item.put("text", funcName);
			String funcType=prod.getProdType();
			if(ProdType.OTHER.equals(funcType)){
				item.put("iconCls","icon-reload");
			}
			Map<String,Object> attrMap=new HashMap<String,Object>();
			attrMap.put("url", prod.getProdUrl());
			attrMap.put("prodId", prod.getId());
			item.put("attributes", attrMap);
			if(productService.hasSubProduct(prod.getId())){
				item.put("state", "closed");
			}
			funcs.add(item);
		}
		return setInputStreamData(funcs);
	}
	
	/**
	 * 查看成员产品信息
	 * @return
	 */
	public String memberView(){
		String miNo = SessionTool.getUserLogonInfo().getMiNo();
		//this.prodType=DictionaryUtil.getCodesByKey(CodeKey.PRODUCT_PROD_TYPE);
		this.yesNoList  = DictionaryUtil.getCodesByKey(CodeKey.YES_NO);
		
		this.prod=productService.getProduct(prodId);
		
		this.memberProd = productService.getMemberProductByProdNo(miNo, prod.getProdNo());
		return "memberView";
	}
	
	/**
	 * 编辑产品信息
	 * @return
	 * 
	 */
	public String memberEdit(){
		String miNo = SessionTool.getUserLogonInfo().getMiNo();
		this.yesNoList  = DictionaryUtil.getCodesByKey(CodeKey.YES_NO);
		
		this.prod=productService.getProduct(prodId);
		
		this.memberProd = productService.getMemberProductByProdNo(miNo, prod.getProdNo());
		
		return "memberEdit";
	}
	
	/**
	 * 保存接入产品的属性设置
	 */
	public void saveMemberProduct(){
		
		productService.modifyMemberProduct(memberProd);
		
		
		
	}
	
	/**
	 * 接入管理的主页面
	 * @return
	 */
	public String memberProdMain(){
		setQueryComponentUrl(QueryComponentConst.MEMBER_QUERY_URL);
		return "memberList";
	}
	
	
	/**
	 * 数据组件异步查询
	 * @return
	 */
	public String data(){
		setQueryComponentUrl(QueryComponentConst.MEMBER_QUERY_URL);
		QueryComponent query=buildQueryWithHttpRequest(memberService.getQueryComponent());
		//设置排序
		SortObject sort = new SortObject("memberInfo.miNo");
		query.setSort(sort);
		List<MemberInfo> list=memberService.queryMemberInfo(getPg(), query);
		return setDatagridInputStreamData(list, getPg());
		
	}
	
	
	

	public String toMemberProduct(){
		return TO_MEMBER_PRODUCT;
	}
	public String setMemberProduct(){
		if(member!=null){
			miNo=member.getMiNo();
		}
		List<ProductNode> products=productService.findSubMemberProduct(null,miNo);
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		for(ProductNode product:products){
			Map<String,Object> map=getProduct(product);
			list.add(map);
		}
		Map<String,Object> productTreeMap=new HashMap<String,Object>();
		productTreeMap.put("products", list);
		return setInputStreamData(productTreeMap);
	}

	private Map<String,Object> getProduct(ProductNode node){
		Map<String,Object> map=new HashMap<String,Object>();
		if(node!=null){
			String id=node.getProdId()+"";
			String name=node.getProdName();
			String nameKey=node.getProdNameKey();
			String type=node.getProdType();
			String mn=node.getMiNo();
			String text = null;
			if(nameKey!=null&&nameKey.length()>0){
				text=getText(nameKey);
				if(!nameKey.equals(text))
					name = text;
			}
			
			map.put("id", id);
			map.put("text", name);
			if(ProdType.OTHER.equals(type)){
				map.put("iconCls", "icon-reload");
			}
			Map<String,String> attributes=new HashMap<String,String>();
			attributes.put("prodNo", node.getProdNo());
			attributes.put("prodName",name);
			attributes.put("parentProdId", node.getParentProdId()+"");
			map.put("attributes", attributes);
			if(productService.hasSubProduct(node.getProdId())){
				List<ProductNode> list=productService.findSubMemberProduct(node.getProdId(),miNo);
				List<Map<String,Object>> ms=new ArrayList<Map<String,Object>>();
				for(ProductNode prod:list){
					Map<String,Object> itemMap=getProduct(prod);
					ms.add(itemMap);
				}
				map.put("children", ms);
			}else{
				if(mn!=null&&mn.length()>0){
					map.put("checked", true);
				}
			}
		}
		return map;
	}

	public String saveProductToMember(){
		String miNo = this.getMember().getMiNo();
		List<Long> productIdList = new ArrayList<Long>(0);
		String ids = this.getIds();
		if (!StringUtil.isEmpty(ids)){
			String[] idPidarr = ids.split(",");
			for (String idPid : idPidarr) {
				String[] temp = idPid.split(":");
				productIdList.add(Long.valueOf(temp[0]));
			}
		}
		boolean b = productService.checkProductAmount(productIdList);
		if(!b)
			ExceptionManager.throwException(ServiceException.class, ErrorCodeConst.PRODUCT_DELETED);
		List<Long> superProdIds = new ArrayList<Long>();
		for(Long prodId : productIdList){
			getSuperNode(superProdIds, prodId);
		}
		for (Long prodId : superProdIds) {
			if (!productIdList.contains(prodId)) {
				productIdList.add(prodId);
			}
		}
		productService.buildMemberProduct(miNo, productIdList);
		
		return null;
		
	}
	
	/**
	 * 找到页面上传递过来的最顶层节点，递归调用
	 * */
	/*
	private Long getTopLevelNode(Map<Long,Long> idPidMap,Long prodId){
		Long parentProdId = idPidMap.get(prodId);
		if( parentProdId == null)  
			return prodId;
		else if(parentProdId == 0)
			return parentProdId;
		else
			return this.getTopLevelNode(idPidMap,parentProdId);
	}
	*/
	
	/**
	 * 找到上层节点，递归调用，结果集放在superProdIds中
	 * */
	private void getSuperNode(List<Long> superProdIds,Long prodId){
		if(prodId == null || prodId.longValue() == 0) return;
		ProductInfo product = productService.getProduct(prodId);
		if (product != null) {
			if (!superProdIds.contains(prodId)) {
				superProdIds.add(prodId);
			}
			if (product.getParentProdId() != null && product.getParentProdId().longValue() > 0) {
				getSuperNode(superProdIds, product.getParentProdId());
			}
		}
		
	}
	
	
	/**
	 * 机构管理的主页面
	 * @return
	 */
	public String brchProdMain(){
		setQueryComponentUrl(QueryComponentConst.MEMBER_QUERY_URL);
		return "brchList";
	}
	
	public String queryBrch() {
		UserLogonInfo uli = SessionTool.getUserLogonInfo();
		
		List<Branch> records = new ArrayList<Branch>(0);
		if(Buser.TYPE_BRCH_GLOBAL_MANAGER.equals(uli.getUserType()) || Buser.TYPE_BRCH_LOCAL_MANAGER.equals(uli.getUserType())) {
			if(uli.getBranchId() != null ) {
				Branch usrBrch = branchService.getBranchByBrchId(uli.getBranchId());
				Branch queryBrch=new Branch();				
				if (brch!=null){
					queryBrch.setBrchName(brch.getBrchName());
					queryBrch.setBrchNo(brch.getBrchNo());
					queryBrch.setTreeCode(brch.getTreeCode());
				}
				if (StringUtil.isEmpty(queryBrch.getTreeCode())){
					queryBrch.setTreeCode(usrBrch.getTreeCode());
				}
				records =branchService.findSubBrchs(queryBrch, getPg(), true);
			}
		}
		
		 List<Branch> checkList = this.productService.findBranchOfCheckProd(null, miNo, null);
		 ArrayList<Long> checkIdList = new ArrayList<Long>(checkList.size());
		 for (Branch b : checkList) {
			 checkIdList.add(b.getBrchId());
		}
		
		for(Branch brch : records) {
			String parentTreeCode = BranchHelper.getParentTreeCode(brch.getTreeCode());
			if(!"".equals(parentTreeCode)) {
				Branch parentBranch = branchService.getBrchByTreeCode(parentTreeCode);
				if(parentBranch != null) {
					//上级机构名称
					brch.setParentBrchName(parentBranch.getBrchName());
				}
			}
			
			if (checkIdList.contains(brch.getBrchId())){
				brch.setFuncStatus(CommonConst.LOGIC_YES);
			}else{
				brch.setFuncStatus(CommonConst.LOGIC_NO);
			}
		}
		return setDatagridInputStreamData(records, getPg());
	}
	
	//
	public String toSetBranchProduct(){
		return TO_BRANCH_PRODUCT;
	}
	public String setBranchProduct(){
		String miNo = SessionTool.getUserLogonInfo().getMiNo();
		
		ArrayList<JQueryTreeNode> nodeList = new ArrayList<JQueryTreeNode>();//最终的节点集合
		
		HashMap<String, JQueryTreeNode> nodeMap = new HashMap<String, JQueryTreeNode>();//
		
		List<Object[]> memberProductList = this.productService.findMemberProduct(miNo, null);
		for (Object[] objects : memberProductList) {
			ProductInfo product = (ProductInfo)objects[0];
			MemberProductInfo memberProduct = (MemberProductInfo)objects[1];
			String prodName = memberProduct.getProdAlias()==null? product.getProdName() :  memberProduct.getProdAlias();
			
			JQueryTreeNode node = new JQueryTreeNode();
			node.setId(product.getId().toString());
			node.setText(prodName);
			node.setAttribute("parentId", product.getParentProdId()==null? null: product.getParentProdId().toString());
			nodeMap.put(node.getId(), node);
			if (product.getParentProdId() == null){
				nodeList.add(node);
			}			
			
		}
		
		for (JQueryTreeNode node : nodeMap.values()) {//为结点建立父子关系
			if (node.getAttribute("parentId") !=null){
				nodeMap.get(node.getAttribute("parentId")).addChild(node);
			}
		}
		
		List<ReBrchProd> reBrchProdList = this.productService.findReBrchProdByBrch(this.getPKId());
		for (ReBrchProd reBrchProd : reBrchProdList) {//已授权的产品勾中
			JQueryTreeNode node = nodeMap.get(reBrchProd.getProdId().toString());
			if (node != null && (ReBrchProd.STATUS_ENABLE.equals(reBrchProd.getStatus()) || ReBrchProd.STATUS_AUDITING.equals(reBrchProd.getStatus())))
				node.setChecked(true);
			
		}
		
		Map<String,Object> productTreeMap=new HashMap<String,Object>();
		productTreeMap.put("products", nodeList);
		return setInputStreamData(productTreeMap);
	}
	
	public String saveProductToBranch(){
		Long brchId = this.getPKId();	
		List<Long> productIdList = this.getIdList();
		
		productService.buildBranchProduct(brchId, productIdList);
		
		return null;
		
	}
	
	//
	public String toViewBranchProduct(){
		
		return "to_view_branch_product";
	}
	
	public String viewBranchProduct(){
		String miNo = SessionTool.getUserLogonInfo().getMiNo();
		
		List<Object[]> memberProductList = this.productService.findMemberProduct(miNo, null);
		ArrayList<JQueryTreeNode> nodeList = new ArrayList<JQueryTreeNode>();//最终的节点集合
		
		HashMap<String, JQueryTreeNode> nodeMap = new HashMap<String, JQueryTreeNode>();//
		for (Object[] objects : memberProductList) {
			ProductInfo product = (ProductInfo)objects[0];
			MemberProductInfo memberProduct = (MemberProductInfo)objects[1];
			String prodName = memberProduct.getProdAlias()==null? product.getProdName() :  memberProduct.getProdAlias();
						
			JQueryTreeNode node = new JQueryTreeNode();
			node.setId(product.getId().toString());
			node.setText(prodName);
			node.setAttribute("parentId", product.getParentProdId()==null? null: product.getParentProdId().toString());
			nodeMap.put(node.getId(), node);
			if (product.getParentProdId() == null){
				nodeList.add(node);
			}		
			
			
			node.setText("<font color='gray'>"+node.getText()+"</font>");
			
			
		}
		
		for (JQueryTreeNode node : nodeMap.values()) {//为结点建立父子关系
			if (node.getAttribute("parentId") !=null){
				nodeMap.get(node.getAttribute("parentId")).addChild(node);
			}
		}
		
		List<ReBrchProd> reBrchProdList = this.productService.findReBrchProdByBrch(this.getPKId());
		for (ReBrchProd reBrchProd : reBrchProdList) {//已授权的产品勾中
			JQueryTreeNode node = nodeMap.get(reBrchProd.getProdId().toString());
			if (node != null){
				node.setAttribute("status", reBrchProd.getStatus());
				if (ReBrchProd.STATUS_AUDITING.equals(reBrchProd.getStatus())){
					node.setText("<B>"+node.getText().replaceFirst("color='gray'", "color='#FFCC00'")+"</B>");
				}
				else if (ReBrchProd.STATUS_CANCLEING.equals(reBrchProd.getStatus())){
					node.setText("<B><strike>"+node.getText().replaceFirst("color='gray'", "color='green'")+"</strike></B>");
				}
				else if (ReBrchProd.STATUS_ENABLE.equals(reBrchProd.getStatus())){
					node.setText("<B>"+node.getText().replaceFirst("color='gray'", "color='green'")+"</B>");
				}
			}
			
		}
		
		Map<String,Object> productTreeMap=new HashMap<String,Object>(2);		
		productTreeMap.put("products", nodeList);
		return setInputStreamData(productTreeMap);
	}
	
	/**
	 * 机构管理的主页面
	 * @return
	 */
	public String brchProdCheckMain(){
		setQueryComponentUrl(QueryComponentConst.MEMBER_QUERY_URL);
		return "brchCheckList";
	}
	
	public String queryCheckBrch() {
		UserLogonInfo uli = SessionTool.getUserLogonInfo();
		
		List<Branch> records = new ArrayList<Branch>(0);
		if(Buser.TYPE_BRCH_GLOBAL_MANAGER.equals(uli.getUserType()) || Buser.TYPE_BRCH_LOCAL_MANAGER.equals(uli.getUserType())) {
			if(uli.getBranchId() != null ) {
				Branch usrBrch = branchService.getBranchByBrchId(uli.getBranchId());
				
				QueryCondition qc = new QueryCondition();
				qc.addCondition(new ConditionBean("branch.treeCode", ConditionBean.NOT_EQUAL, usrBrch.getTreeCode()));
				qc.addCondition(new ConditionBean("branch.treeCode", ConditionBean.LIKE, usrBrch.getTreeCode()+"%"));
				records = this.productService.findBranchOfCheckProd(qc, miNo, this.getPg());
			}
		}
		for(Branch brch : records) {
			String parentTreeCode = BranchHelper.getParentTreeCode(brch.getTreeCode());
			if(!"".equals(parentTreeCode)) {
				Branch parentBranch = branchService.getBrchByTreeCode(parentTreeCode);
				if(parentBranch != null) {
					//上级机构名称
					brch.setParentBrchName(parentBranch.getBrchName());
				}
			}			
		}
		
		return setDatagridInputStreamData(records, getPg());
	}
	
	//
	public String toCheckBranchProduct(){
		return "to_check_brch_prod";
	}
	
	public String viewCheckBranchProduct(){
		String miNo = SessionTool.getUserLogonInfo().getMiNo();
		
		List<Object[]> memberProductList = this.productService.findMemberProduct(miNo, null);
		ArrayList<JQueryTreeNode> nodeList = new ArrayList<JQueryTreeNode>();//最终的节点集合
		
		HashMap<String, JQueryTreeNode> nodeMap = new HashMap<String, JQueryTreeNode>();//
		for (Object[] objects : memberProductList) {
			ProductInfo product = (ProductInfo)objects[0];
			MemberProductInfo memberProduct = (MemberProductInfo)objects[1];
			String prodName = memberProduct.getProdAlias()==null? product.getProdName() :  memberProduct.getProdAlias();
						
			JQueryTreeNode node = new JQueryTreeNode();
			node.setId(product.getId().toString());
			node.setText(prodName);
			node.setAttribute("parentId", product.getParentProdId()==null? null: product.getParentProdId().toString());
			nodeMap.put(node.getId(), node);
			if (product.getParentProdId() == null){
				nodeList.add(node);
			}		
			
			
			node.setText("<font color='gray'>"+node.getText()+"</font>");
			
			
		}
		
		for (JQueryTreeNode node : nodeMap.values()) {//为结点建立父子关系
			if (node.getAttribute("parentId") !=null){
				nodeMap.get(node.getAttribute("parentId")).addChild(node);
			}
		}
		
		List<ReBrchProd> reBrchProdList = this.productService.findReBrchProdByBrch(this.getPKId());
		for (ReBrchProd reBrchProd : reBrchProdList) {//
			JQueryTreeNode node = nodeMap.get(reBrchProd.getProdId().toString());
			if (node != null){
				node.setAttribute("status", reBrchProd.getStatus());
				if (ReBrchProd.STATUS_AUDITING.equals(reBrchProd.getStatus())){
					node.setText("<B>"+node.getText().replaceFirst("color='gray'", "color='#FFCC00'")+"</B>");
				}
				else if (ReBrchProd.STATUS_CANCLEING.equals(reBrchProd.getStatus())){
					node.setText("<B><strike>"+node.getText().replaceFirst("color='gray'", "color='green'")+"</strike></B>");
				}
				else if (ReBrchProd.STATUS_ENABLE.equals(reBrchProd.getStatus())){
					node.setText("<B>"+node.getText().replaceFirst("color='gray'", "color='green'")+"</B>");
				}
			}
			
		}
		
		Map<String,Object> productTreeMap=new HashMap<String,Object>(2);		
		productTreeMap.put("products", nodeList);
		return setInputStreamData(productTreeMap);
	}
	
	public String checkBranchProduct(){
		Long brchId = this.getPKId();
		
		if (CommonConst.LOGIC_YES.equals(this.getIsAgree())){
			this.productService.checkBranchProduct(brchId, true);
		}else{
			this.productService.checkBranchProduct(brchId, false);
		}
		return null;
	}
	
	/**
	 * 
	 * 产品权限页面
	 *
	 * @return
	 */
	public String productFunc(){
		return SUCCESS;
	}
	
	/**
	 * 
	 * 产品分配权限
	 *
	 */
	public void assignFuncsForProduct(){
		if( prodId != null ){
			//权限的ID列表
			List<Long> idList = getIdList();
			productSysfuncService.assignSysfuncForProduct(prodId, idList);
		}
	}
	
	/**
	 * 
	 * 获取权限列表
	 *
	 * @return
	 */
	public String getFuncs(){
		List<JQueryTreeNode> ret = null;
		if( prodId != null ){
			List<Sysfunc> allList = sysfuncService.getAllEBankMenuSysfunc();
			List<Sysfunc> checkedList = productSysfuncService.getSysfuncByProduct(prodId);
			ret = JQueryTreeNodeManager.convertToJTN(allList, checkedList);
		}
		return setInputStreamData(ret == null ? new ArrayList<JQueryTreeNode>(0) : ret);
	}
	
	/**
	 * 
	 * 产品属性页面
	 *
	 * @return
	 */
	public String productAttributes(){
		return SUCCESS;
	}
	
	/**
	 * 
	 * 获取属性列表
	 *
	 * @return
	 */
	public String getAllAttributes(){
		List<JQueryTreeNode> ret = null;
		if( prodId != null ){
			List<ProductAttribute> allList = productAttributeService.getEntityList(null, null);
			List<ProductAttribute> checkedList = productService.getAttributesByProdId(prodId,miNo);
			ret = JQueryTreeNodeManager.convertToJTN(allList, checkedList);
		}
		return setInputStreamData(ret == null ? new ArrayList<JQueryTreeNode>(0) : ret);
	}
	
	/**
	 * 
	 * 分配属性
	 *
	 */
	public void assignAttrsForProduct(){
		if( prodId != null && StringUtils.isNotBlank(miNo)){
			memberProductAttributeService.assignProductAttributes(prodId, getIdList(), miNo);
		}
	}
	
	/**
	 * 
	 * 接入点产品属性页面
	 *
	 * @return
	 */
	public String memberProdAttrs(){
		return SUCCESS;
	}
	/**
	 * 
	 * 获取接入点产品
	 *
	 * @return
	 */
	public String getMemberProducts(){
		List<JQueryTreeNode> ret = null;
		if( StringUtils.isNotBlank(miNo) ){
			Long parentId = null;
			if (StringUtils.isNotBlank(getId())) {
				parentId = getPKId();
			}
			List<Object[]> pmList = productService.getMemberSubProduct(miNo, parentId);
			List<ProductInfo> productList = new ArrayList<ProductInfo>();
			if( pmList != null ){
				for( Object[] arr : pmList ){
					productList.add((ProductInfo)arr[0]);
				}
			}
			ret = JQueryTreeNodeManager.convertListToJTN(productList);
			if (ret != null && ret.size() > 0) {
				for (JQueryTreeNode jtn : ret) {
					if (productService.hasSubProduct(Long.valueOf(jtn.getId()))) {
						jtn.setState(JQueryTreeNode.STATE_CLOSED);
					}
				}
			}
		}
		return setInputStreamData(ret == null ? new ArrayList<JQueryTreeNode>(0) : ret);
	}
	
	/**
	 * 
	 * 获取产品属性
	 *
	 * @return
	 */
	public String getAttributesByProdId(){
		List<Map<String,Object>> list = null;
		Page pg = getPg();
		if( prodId != null && StringUtils.isNotBlank(miNo) ){
			List<ConditionBean> conditionList = new ArrayList<ConditionBean>();
			conditionList.add(new ConditionBean("miNo", miNo));
			conditionList.add(new ConditionBean("productId", prodId));
			List<MemberProductAttribute> mpaList = memberProductAttributeService.getEntityList(conditionList, null);
			if( mpaList != null && mpaList.size() > 0 ){
				list = new ArrayList<Map<String,Object>>(mpaList.size());
				for( MemberProductAttribute mpa : mpaList ){
					Map<String,Object> mpaMap = new HashMap<String,Object>();
					mpaMap.put("id", mpa.getId());
					mpaMap.put("miNo", mpa.getMiNo());
					mpaMap.put("productId", mpa.getProductId());
					mpaMap.put("attributeId", mpa.getAttributeId());
					mpaMap.put("attributeKey", mpa.getAttributeKey());
					mpaMap.put("attributeValue", mpa.getAttributeValue());
					String attributeValueDisp = mpa.getAttributeValue();
					String attributeName = mpa.getAttributeKey();
					String codeMetaKey = null;
					ProductAttribute a = productAttributeService.getEntityById(mpa.getAttributeId());
					if( a != null ){
						attributeName = a.getName();
						codeMetaKey = a.getCodeMetaKey();
						if( StringUtils.isNotBlank(codeMetaKey) ){
							attributeValueDisp = DictionaryUtil.getCodeNameByKey(codeMetaKey, mpa.getAttributeValue());
						}
					}
					mpaMap.put("attributeName", attributeName);
					mpaMap.put("attributeValueDisp", attributeValueDisp);
					list.add(mpaMap);
				}
			}
		}
		return setDatagridInputStreamData(list == null ? Collections.EMPTY_LIST : list, pg);
	}
	
	/**
	 * 
	 * 编辑产品属性
	 *
	 * @return
	 */
	public String editMPAPage(){
		String id = getId();
		if( StringUtils.isNotBlank(id)){
			mpa = memberProductAttributeService.getEntityById(id);
			pa = productAttributeService.getEntityById(mpa.getAttributeId());
			if( pa != null && StringUtils.isNotBlank(pa.getCodeMetaKey()) ){
				List<ConditionBean> conditionList = new ArrayList<ConditionBean>(2);
				conditionList.add(new ConditionBean(Code.CODE_KEY,pa.getCodeMetaKey()));
				Locale locale =  SessionTool.getAttribute(SessionKeyConst.SESSION_LOCAL) == null ? Locale.CHINA : (Locale) SessionTool.getAttribute(SessionKeyConst.SESSION_LOCAL);
				conditionList.add(new ConditionBean(Code.LANG_TYPE, locale.toString()));
				codeList = codeService.getEntityList(conditionList, null);
			}
		}
		return SUCCESS;
	}
	
	
	public void editMPA(){
		if( mpa != null && mpa.getId() != null ){
			MemberProductAttribute updateMPA = memberProductAttributeService.getEntityById(mpa.getId());
			if( updateMPA != null ){
				updateMPA.setAttributeValue(mpa.getAttributeValue());
				memberProductAttributeService.update(updateMPA);
			}
		}
	}
	
	public String getAllProduct(){
		List<ProductInfo> allProduct = productService.queryProductByConditin(null, null, null);
		return setInputStreamData(JQueryTreeNodeManager.convertToJTN(allProduct, null,true));
	}
	
	public Long getSourceId() {
		return sourceId;
	}
	public void setSourceId(Long sourceId) {
		this.sourceId = sourceId;
	}
	public Long getTargetId() {
		return targetId;
	}
	public void setTargetId(Long targetId) {
		this.targetId = targetId;
	}
	public String getPoint() {
		return point;
	}
	public void setPoint(String point) {
		this.point = point;
	}
	public ProductInfo getProd() {
		return prod;
	}
	public void setProd(ProductInfo prod) {
		this.prod = prod;
	}
	public ProductInfo getParentProd() {
		return parentProd;
	}
	public void setParentProd(ProductInfo parentProd) {
		this.parentProd = parentProd;
	}
	public List<Code> getProdType() {
		return prodType;
	}
	public void setProdType(List<Code> prodType) {
		this.prodType = prodType;
	}
	public Long getProdId() {
		return prodId;
	}
	public void setProdId(Long prodId) {
		this.prodId = prodId;
	}
	public void setProductService(IProductService productService) {
		this.productService = productService;
	}
	public String getMiNo() {
		return miNo;
	}
	public void setMiNo(String miNo) {
		this.miNo = miNo;
	}
	public String getProdNos() {
		return prodNos;
	}
	public void setProdNos(String prodNos) {
		this.prodNos = prodNos;
	}
	public String getProdNo() {
		return prodNo;
	}
	public void setProdNo(String prodNo) {
		this.prodNo = prodNo;
	}
	public MemberProductInfo getMemberProd() {
		return memberProd;
	}
	public void setMemberProd(MemberProductInfo memberProd) {
		this.memberProd = memberProd;
	}
	public List<Code> getYesNoList() {
		return yesNoList;
	}
	public void setYesNoList(List<Code> yesNoList) {
		this.yesNoList = yesNoList;
	}
	public IMemberService getMemberService() {
		return memberService;
	}
	public void setMemberService(IMemberService memberService) {
		this.memberService = memberService;
	}
	public MemberInfo getMember() {
		return member;
	}
	public void setMember(MemberInfo member) {
		this.member = member;
	}
	public IBranchService getBranchService() {
		return branchService;
	}
	public void setBranchService(IBranchService branchService) {
		this.branchService = branchService;
	}
	public String getIsAgree() {
		return isAgree;
	}
	public void setIsAgree(String isAgree) {
		this.isAgree = isAgree;
	}
	public List<MemberInfo> getMiList() {
		return miList;
	}
	public void setMiList(List<MemberInfo> miList) {
		this.miList = miList;
	}
	public void setSysfuncService(ISysfuncService sysfuncService) {
		this.sysfuncService = sysfuncService;
	}
	public void setProductSysfuncService(
			IProductSysfuncService productSysfuncService) {
		this.productSysfuncService = productSysfuncService;
	}
	public void setProductAttributeService(IProductAttributeService productAttributeService) {
		this.productAttributeService = productAttributeService;
	}
	public void setMemberProductAttributeService(IMemberProductAttributeService memberProductAttributeService) {
		this.memberProductAttributeService = memberProductAttributeService;
	}
	public MemberProductAttribute getMpa() {
		return mpa;
	}
	public void setMpa(MemberProductAttribute mpa) {
		this.mpa = mpa;
	}
	public ProductAttribute getPa() {
		return pa;
	}	
	public void setPa(ProductAttribute pa) {
		this.pa = pa;
	}
	public List<Code> getCodeList() {
		return codeList;
	}
	public void setCodeList(List<Code> codeList) {
		this.codeList = codeList;
	}
	public void setCodeService(ICodeService codeService) {
		this.codeService = codeService;
	}

	public Branch getBrch() {
		return brch;
	}

	public void setBrch(Branch brch) {
		this.brch = brch;
	}
	
	
}