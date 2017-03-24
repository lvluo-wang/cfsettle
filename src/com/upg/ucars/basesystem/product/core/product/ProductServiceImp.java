package com.upg.ucars.basesystem.product.core.product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.upg.ucars.constant.ErrorCodeConst;
import com.upg.ucars.framework.base.BaseService;
import com.upg.ucars.framework.base.ICommonDAO;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.QueryCondition;
import com.upg.ucars.framework.base.queryComponent.QueryComponent;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.framework.exception.ExceptionManager;
import com.upg.ucars.framework.exception.ServiceException;
import com.upg.ucars.mapping.basesystem.product.MemberProductAttribute;
import com.upg.ucars.mapping.basesystem.product.MemberProductInfo;
import com.upg.ucars.mapping.basesystem.product.ProductAttribute;
import com.upg.ucars.mapping.basesystem.product.ProductInfo;
import com.upg.ucars.mapping.basesystem.product.ReBrchProd;
import com.upg.ucars.mapping.basesystem.security.Branch;
import com.upg.ucars.model.ConditionBean;
import com.upg.ucars.model.OrderBean;

public class ProductServiceImp extends BaseService implements IProductService {
	IProductInfoDAO productInfoDAO=null;
	IMemberProductInfoDAO memberProductInfoDAO = null;
	IReBrchProdDAO reBrchProdDAO = null;
	
	/** 产品属性服务 */
	private IProductAttributeService productAttributeService;
	
	/** 机构产品属性DAO */
	private IMemberProductAttributeDAO memberProductAttributeDAO;
	
	private ICommonDAO commonDAO;//spring set
	public void createProduct(ProductInfo prod) {
		if(prod!=null){
			String prodName=prod.getProdName();
			String prodNameKey=prod.getProdNameKey();
			Long parentProdId=prod.getParentProdId();
			if(productInfoDAO.getIdByProdNameInParent(prodName,parentProdId)!=null){
				ExceptionManager.throwException(ServiceException.class, ErrorCodeConst.PRODUCT_SUBPORD_NAMEREPEAT);
			}
			if(productInfoDAO.getIdByNameKeyInParent(prodNameKey, parentProdId)!=null){
				ExceptionManager.throwException(ServiceException.class, ErrorCodeConst.PRODUCT_SUBPORD_KEYREPEAT);
			}
			Long no=productInfoDAO.getMaxSortNo(parentProdId)+1;
			prod.setSortNo(no);

			productInfoDAO.save(prod);
		}
		
		
	}

	public void modifyProduct(ProductInfo prod) {		
		
		String prodName=prod.getProdName();
		String prodNameKey=prod.getProdNameKey();
		Long parentProdId=prod.getParentProdId();
		
		Long namePid = productInfoDAO.getIdByProdNameInParent(prodName,parentProdId);
		if(namePid!=null && !namePid.equals(prod.getId())){
			ExceptionManager.throwException(ServiceException.class, ErrorCodeConst.PRODUCT_SUBPORD_NAMEREPEAT);
		}
		
		Long keyPid = productInfoDAO.getIdByNameKeyInParent(prodNameKey,parentProdId);
		if(keyPid!=null && !keyPid.equals(prod.getId())){
			ExceptionManager.throwException(ServiceException.class, ErrorCodeConst.PRODUCT_SUBPORD_KEYREPEAT);
		}
		
		productInfoDAO.merge(prod);
		
		
	}

	public List<ProductInfo> queryProduct(QueryComponent qc, Page page) {
		
		return productInfoDAO.queryEntity(qc, page);
	}
	
	public List<ProductInfo> queryProductByConditin(List<ConditionBean> conditionList, List<OrderBean> orderList, Page page)throws DAOException{
		
		return this.productInfoDAO.queryEntity(conditionList, orderList, page);
	}

	public List<ProductInfo> findSubProduct(Long prodId) {
		return productInfoDAO.findProdByParentId(prodId);
	}

	public List<Map<String, Object>> getAsyncSubProduct(Long parentProdId) {
		Long prodId=null;
		List<Map<String,Object>> items=new ArrayList<Map<String,Object>>();
		if(parentProdId!=null){
			prodId=parentProdId;
		}
		List<ProductInfo> nodes=productInfoDAO.findProdByParentId(prodId);
		for(ProductInfo node:nodes){
			Map<String,Object> item=new HashMap<String,Object>();
			item.put("id", node.getId());
			String funcName=node.getProdName();
			item.put("text", funcName);
			Map<String,Object> attrMap=new HashMap<String,Object>();
			attrMap.put("url", node.getProdUrl());
			item.put("attributes", attrMap);
			if(productInfoDAO.hasSubProdByParentId(node.getId())){
				item.put("state", "closed");
			}
			items.add(item);
		}
		return items;
	}

	public boolean hasSubProduct(Long parentProdId) {
		return productInfoDAO.hasSubProdByParentId(parentProdId);
	}

	public ProductInfo getProduct(Long prodId) throws ServiceException {
		return productInfoDAO.get(prodId);
	}

	public void removeProduct(Long prodId) throws ServiceException {
		if(productInfoDAO.hasSubProdByParentId(prodId)){
			ExceptionManager.throwException(ServiceException.class, ErrorCodeConst.PRODUCT_DEL_HASSUB);
		}
		List<ConditionBean> conditionList = new ArrayList<ConditionBean>();
		conditionList.add(new ConditionBean("prodId", prodId));
		List<MemberProductInfo> list = this.memberProductInfoDAO.queryEntity(conditionList, new Page());
		if (!list.isEmpty()){
			ExceptionManager.throwException(ServiceException.class, ErrorCodeConst.PRODUCT_DEL_ASSIGN_MEMBER);
		}
		productInfoDAO.delete(prodId);
		
	}



	public void moveProduct(Long sourceProdId, Long targetProdId, String point) {
		ProductInfo source=productInfoDAO.get(sourceProdId);
		ProductInfo target=productInfoDAO.get(targetProdId);
		Long parentProdId=targetProdId;
		if("append".equals(point)){
			if(isChild(sourceProdId,targetProdId)){
				//如果目标节点时原节点的子节点，则不能移动
				ExceptionManager.throwException(ServiceException.class, ErrorCodeConst.SECURITY_SYSFUNC_PARENT_NOTO_CHILD);
			}
		}else{
			parentProdId=target.getParentProdId();
		}

				//校验同名问题
				String funcName=source.getProdName();
				String funcNameKey=source.getProdNameKey();
				Long existId = productInfoDAO.getIdByProdNameInParent(funcName,parentProdId);
				if (existId != null && existId.longValue() != source.getId()) {
					ExceptionManager.throwException(ServiceException.class, ErrorCodeConst.PRODUCT_SUBPORD_NAMEREPEAT);
				}
				existId = productInfoDAO.getIdByNameKeyInParent(funcNameKey, parentProdId);
				if (existId != null && existId.longValue() != source.getId()) {
					ExceptionManager.throwException(ServiceException.class, ErrorCodeConst.PRODUCT_SUBPORD_KEYREPEAT);
				}

		/**
		 * 移动为目标的子节点
		 */
		if("append".equals(point)){
			if(source.getParentProdId()!=null){
				if(source.getParentProdId().equals(target.getId())){
					//如果原节点的父级节点就是目标节点，则不用移动
					return;
				}
			}
			
			Long maxNo=productInfoDAO.getMaxSortNo(targetProdId)+1;
			source.setSortNo(maxNo);
			source.setParentProdId(target.getId());
			productInfoDAO.update(source);
		}
		/**
		 * 移动为目标的同级节点
		 */
		if("top".equals(point)){
			List list=productInfoDAO.getButtomProduct(target);
			if(list!=null&&list.size()>0){
				List list2=new ArrayList();
				Iterator it=list.iterator();
				while(it.hasNext()){
					ProductInfo prod=(ProductInfo)it.next();
					if(prod.getId().equals(source.getId())){
						continue;
					}
					Long sortNo=prod.getSortNo()+1;
					prod.setSortNo(sortNo);
					list2.add(prod);
				}
				Long no=target.getSortNo();
				source.setSortNo(no);
				source.setParentProdId(target.getParentProdId());
				target.setSortNo(no+1);
				list2.add(source);
				list2.add(target);
				productInfoDAO.saveOrUpdateAll(list2);
				return;
			}else{
				Long no=target.getSortNo();
				source.setSortNo(no);
				source.setParentProdId(target.getParentProdId());
				target.setSortNo(no+1);
				list=new ArrayList<ProductInfo>();
				list.add(target);
				list.add(source);
				productInfoDAO.saveOrUpdateAll(list);
				return;
			}
		}
		if("bottom".equals(point)){
			List list=productInfoDAO.getButtomProduct(target);
			if(list!=null&&list.size()>0){
				List list2=new ArrayList();
				Iterator it=list.iterator();
				while(it.hasNext()){
					ProductInfo prod=(ProductInfo)it.next();
					if(prod.getId().equals(source.getId())){
						continue;
					}
					Long sortNo=prod.getSortNo()+1;
					prod.setSortNo(sortNo);
					list2.add(prod);
				}
				Long no=target.getSortNo();
				source.setSortNo(no+1);
				source.setParentProdId(target.getParentProdId());
				list2.add(source);
				productInfoDAO.saveOrUpdateAll(list2);
				return;
			}else{
				Long no=target.getSortNo();
				source.setSortNo(no+1);
				source.setParentProdId(target.getParentProdId());
				list=new ArrayList<ProductInfo>();
				list.add(source);
				productInfoDAO.saveOrUpdateAll(list);
				return;
			}
		}

	}
	/**
	 * 判断target是否为source的子孙节点
	 * @param sourceId
	 * @param targetId
	 * @return
	 */
	public boolean isChild(Long sourceId,Long targetId){
		ProductInfo target=productInfoDAO.get(targetId);
		Long parentId=target.getParentProdId();
		if(sourceId.equals(parentId)){
			return true;
		}
		if(parentId==null){
			return false;
		}
		return isChild(sourceId,parentId);
	}


	public List<ProductInfo> getSubProduct(Long funcId) {
		List<ProductInfo> nodes=productInfoDAO.findProdByParentId(funcId);
		return nodes;
	}

	public IProductInfoDAO getProductInfoDAO() {
		return productInfoDAO;
	}

	public void setProductInfoDAO(IProductInfoDAO productInfoDAO) {
		this.productInfoDAO = productInfoDAO;
	}

	public IMemberProductInfoDAO getMemberProductInfoDAO() {
		return memberProductInfoDAO;
	}

	public void setMemberProductInfoDAO(IMemberProductInfoDAO memberProductInfoDAO) {
		this.memberProductInfoDAO = memberProductInfoDAO;
	}
	public IReBrchProdDAO getReBrchProdDAO() {
		return reBrchProdDAO;
	}

	public void setReBrchProdDAO(IReBrchProdDAO reBrchProdDAO) {
		this.reBrchProdDAO = reBrchProdDAO;
	}
	public ICommonDAO getCommonDAO() {
		return commonDAO;
	}

	public void setCommonDAO(ICommonDAO commonDAO) {
		this.commonDAO = commonDAO;
	}
	public void setMemberProductAttributeDAO(
			IMemberProductAttributeDAO memberProductAttributeDAO) {
		this.memberProductAttributeDAO = memberProductAttributeDAO;
	}
	public void setProductAttributeService(IProductAttributeService productAttributeService) {
		this.productAttributeService = productAttributeService;
	}

	public List<Object[]> findMemberProduct(String miNo, Page page) throws DAOException {
		String hql = "select p,mp from MemberProductInfo mp, ProductInfo p where mp.prodId=p.id and mp.miNo=:miNo order by p.prodNo";
		Map<String, Object> paraMap = new HashMap<String, Object>(1);
		paraMap.put("miNo", miNo);
		
		List<Object[]> list = this.commonDAO.queryByParam(hql, paraMap, page);
		return list;
	}

	public ProductInfo getProductByProdNo(String prodNo)
			throws ServiceException, DAOException {
		return productInfoDAO.getProductInfoByProdNo(prodNo);
	}

	public ProductInfo getParentProductByProdNo(String prodNo)
			throws ServiceException, DAOException {
		ProductInfo prodInfo = this.getProductByProdNo(prodNo);
		
		ProductInfo parentProd = this.getProduct(prodInfo.getParentProdId());
		
		return parentProd;
	}

	public MemberProductInfo getMemberProductByProdNo(String miNo, String prodNo)
			throws DAOException {
		MemberProductInfo mp = this.memberProductInfoDAO.getMemberProductByProdNo(miNo, prodNo);
		return mp;
	}

	public List<Object[]> getMemberSubProduct(String miNo, Long parentProdId)
			throws ServiceException, DAOException {
		List<Object[]> list = this.memberProductInfoDAO.findMemberProdByParentId(miNo, parentProdId);
		
		return list;
	}

	public void modifyMemberProduct(MemberProductInfo memberProd)
			throws ServiceException, DAOException {
		
		
		
		this.memberProductInfoDAO.update(memberProd);
		
	}



	public List<ProductNode> findSubMemberProduct(Long prodId, String miNo) {
		return memberProductInfoDAO.findSebMemberProduct(prodId,miNo);
	}
	


	public void buildMemberProduct(String miNo, List<Long> productIdList) {
		List<MemberProductInfo> infoList = this.memberProductInfoDAO.findProductsByMiNo(miNo);
		ArrayList<Long> beList = new ArrayList<Long>(productIdList);
		ArrayList<Long> unList = new ArrayList<Long>(); 
		
		for (MemberProductInfo productInfo : infoList) {
			if (productIdList.contains(productInfo.getProdId()))
				beList.remove(productInfo.getProdId());
			else
				unList.add(productInfo.getProdId());
		}
		if (!unList.isEmpty()){
			this.memberProductInfoDAO.deleteMemberProducts(miNo, unList);
			
			//
			//
			//this.getCommonDAO().bulkUpdate("delete from ReBrchProd where prodId in (?) and brchId in (?)", unList, brchIdList);
			List<Long> brchIdList = this.getCommonDAO().find("select brchId from Branch where miNo=?", miNo);
			ArrayList<ConditionBean> condList = new ArrayList<ConditionBean>();
			condList.add(new ConditionBean("prodId", ConditionBean.IN, unList));
			condList.add(new ConditionBean("brchId", ConditionBean.IN, brchIdList));
			List<ReBrchProd> rebrchList = this.reBrchProdDAO.queryEntity(condList, null);
			this.reBrchProdDAO.delAll(rebrchList);
			
			List<ConditionBean> conditionList = new ArrayList<ConditionBean>();
			conditionList.add(new ConditionBean("miNo",miNo));
			conditionList.add(new ConditionBean("productId",ConditionBean.IN,unList.toArray(new Long[unList.size()])));
			List<MemberProductAttribute> delBPAList = memberProductAttributeDAO.queryEntity(conditionList, null);
			if( delBPAList != null && delBPAList.size() > 0 ){
				memberProductAttributeDAO.delAll(delBPAList);
			}
		}
			
		if (!beList.isEmpty()){
			this.memberProductInfoDAO.saveMemberProducts(miNo, beList);			
		}
		
	}

	public List<ReBrchProd> findReBrchProdByBrch(Long brchId) {
		ArrayList<ConditionBean> conditionList = new ArrayList<ConditionBean>();
		conditionList.add(new ConditionBean("brchId", brchId));
		
		return this.getReBrchProdDAO().queryEntity(conditionList, null);
	}

	public void buildBranchProduct(Long brchId, List<Long> productIdList) {
		/* 
		 * 对于表中数据，如果存在productIdList中，审批中->审批中；取消中->已通过，已通过->已通过
		 * 对于表中数据，如果没在productIdList中，审批中->删除；取消中->取消中，已通过->取消中	
		 * 非表中数据，增加为审批中	
		 */
		List<ReBrchProd>  list = this.findReBrchProdByBrch(brchId);

		ArrayList<ReBrchProd> saveUpdateReList = new ArrayList<ReBrchProd>();
		ArrayList<ReBrchProd> delReList = new ArrayList<ReBrchProd>();
		
		for (ReBrchProd re : list){
			if (productIdList.contains(re.getProdId())){//存在
				
				if (ReBrchProd.STATUS_CANCLEING.equals(re.getStatus())){
					re.setStatus(ReBrchProd.STATUS_ENABLE);
					saveUpdateReList.add(re);
				}
				
				productIdList.remove(re.getProdId());//剔除表中已有的
					
			}else{//不存在
				if (ReBrchProd.STATUS_AUDITING.equals(re.getStatus())){
					delReList.add(re);
				}
				if (ReBrchProd.STATUS_ENABLE.equals(re.getStatus())){
					re.setStatus(ReBrchProd.STATUS_CANCLEING);
					saveUpdateReList.add(re);
				}
				
			}
			
			
		}
		
		for(Long prodId : productIdList){//非表中数据
			ReBrchProd re = new ReBrchProd();
			re.setBrchId(brchId);
			re.setProdId(prodId);
			re.setStatus(ReBrchProd.STATUS_AUDITING);
			saveUpdateReList.add(re);
		}
		
		this.getReBrchProdDAO().saveOrUpdateAll(saveUpdateReList);
		this.getReBrchProdDAO().delAll(delReList);
		
		
	}

	public void checkBranchProduct(Long brchId, boolean isAgree) {
		List<ReBrchProd> list = this.findReBrchProdByBrch(brchId);
		ArrayList<ReBrchProd> updateList = new  ArrayList<ReBrchProd>();
		ArrayList<ReBrchProd> deleteList = new  ArrayList<ReBrchProd>();
		for (ReBrchProd re : list) {
			if (isAgree){
				if (ReBrchProd.STATUS_AUDITING.equals(re.getStatus())){
					re.setStatus(ReBrchProd.STATUS_ENABLE);
					updateList.add(re);
				}else if (ReBrchProd.STATUS_CANCLEING.equals(re.getStatus())){					
					deleteList.add(re);
				}					
					
			}else{
				
				if (ReBrchProd.STATUS_AUDITING.equals(re.getStatus())){					
					deleteList.add(re);
				}else if (ReBrchProd.STATUS_CANCLEING.equals(re.getStatus())){					
					re.setStatus(ReBrchProd.STATUS_ENABLE);
					updateList.add(re);
				}	
				
			}
				
		}
		
		this.reBrchProdDAO.saveOrUpdateAll(updateList);
		this.reBrchProdDAO.delAll(deleteList);
		
	}

	public List<Branch> findBranchOfCheckProd(QueryCondition qc, String miNo, Page page){
		if (qc == null)
			qc = new QueryCondition();
		String hql = "select distinct branch from Branch branch, ReBrchProd re, MemberInfo m where m.miNo=branch.miNo and branch.brchId=re.brchId ";
		qc.setHql(hql);		
		qc.addCondition(new ConditionBean("re.status", ConditionBean.IN, new Object[]{ReBrchProd.STATUS_AUDITING, ReBrchProd.STATUS_CANCLEING}));
		
		List list = this.getCommonDAO().queryByCondition(qc, page);
		return list;
	}

	public List<ProductInfo> findAuthProdOfBrch(QueryCondition qc,
			Long brchId, Page page) {
		if (qc == null)
			qc = new QueryCondition();
		String hql = "select distinct product from Branch branch, ProductInfo product, ReBrchProd re " +
				" where branch.brchId=re.brchId and re.prodId=product.id ";
		qc.setHql(hql);
		
		qc.addCondition(new ConditionBean("re.status", ConditionBean.IN, new Object[]{ReBrchProd.STATUS_ENABLE, ReBrchProd.STATUS_CANCLEING}));
		qc.addCondition(new ConditionBean("branch.brchId", brchId));
		
		List list = this.getCommonDAO().queryByCondition(qc, page);
		return list;
	}

	public List<ProductAttribute> getAttributesByProdId(Long prodId, String miNo) {
		List<ProductAttribute> ret = null;
		if (prodId != null) {
			List<MemberProductAttribute> mpaList = memberProductAttributeDAO
					.getMemberProductAttributeList(prodId, miNo);
			if (mpaList != null && mpaList.size() > 0) {
				ret = new ArrayList<ProductAttribute>(mpaList.size());
				for (MemberProductAttribute mpa : mpaList) {
					ProductAttribute pa = productAttributeService.getEntityById(mpa.getAttributeId());
					if (pa != null) {
//						pa.setValue(mpa.getAttributeValue());
						ret.add(pa);
					}
				}
			}
		}
		return ret == null ? Collections.EMPTY_LIST : ret;
	}
	
	public List<ProductInfo> getProductListByAttr(String miNo,String attrKey,String attrValue) {
		List<ProductInfo> ret = memberProductAttributeDAO.getProductListByAttr(miNo, attrKey, attrValue);
		return ret == null ? SERVICE_EMPTY_LIST : ret;
	}
	
	public List<ProductInfo> getSubProductListByAttrAndParent(String miNo,String attrKey,String attrValue,Long parentId){
		return memberProductAttributeDAO.getSubProductListByAttrAndParent(miNo, attrKey, attrValue, parentId);
	}

	/**
	 * 校验产品数量
	 * @param productIds 产品id列表
	 * @return true： 表示数据库中的数量与productIds.size相等 
	 *         false：表示数据库中的数量与productIds.size不相等，表明数据库中有些产品可能被删除了
	 */
	public boolean checkProductAmount(List<Long> productIds){
		if(productIds == null || productIds.isEmpty()) return true;
		int size = productIds.size();
		if(size == this.productInfoDAO.getProductAmount(productIds))
			return true;
		else
			return false;
	}
	
	public String getAttributeValue(String miNo,String prodNo,String attr){
		String attributeValue = null;
		if (StringUtils.isNotBlank(miNo) && StringUtils.isNotBlank(prodNo) && StringUtils.isNotBlank(attr)) {
			ProductInfo product = productInfoDAO.getProductInfoByProdNo(prodNo);
			attributeValue = memberProductAttributeDAO.getProductAttributeValue(miNo, product.getId(), attr);
			if (StringUtils.isBlank(attributeValue)) {
				if (product.getParentProdId() != null) {
					ProductInfo parent = productInfoDAO.get(product.getParentProdId());
					attributeValue = getAttributeValue(miNo, parent.getProdNo(), attr);
				}
			}
		}
		return attributeValue;
	}
	
	public List<String> getAllSubProdNos(String prodNo){
		List<String> result = new ArrayList<String>();
		if (StringUtils.isNotBlank(prodNo)) {
			result.add(prodNo);
			ProductInfo product = getProductByProdNo(prodNo);
			if (product != null) {
				List<ProductInfo> subProducts = getSubProduct(product.getId());
				if (subProducts != null && subProducts.size() > 0) {
					for (ProductInfo pi : subProducts) {
						result.addAll(getAllSubProdNos(pi.getProdNo()));
					}
				}
			}
		}
		return result;
	}

	@Override
	public ProductInfo getProductByProdName(String prodName)
			throws ServiceException, DAOException {
		return productInfoDAO.getProductInfoByProdName(prodName);
	}
}
