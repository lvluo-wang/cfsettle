package com.upg.ucars.basesystem.product.core.product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;

import com.upg.ucars.constant.ErrorCodeConst;
import com.upg.ucars.framework.base.BaseDAO;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.framework.exception.ExceptionManager;
import com.upg.ucars.mapping.basesystem.product.ProductInfo;

public class ProductInfoDAOImp extends BaseDAO<ProductInfo,Long> implements IProductInfoDAO{



	@Override
	public Class getEntityClass() {
		return ProductInfo.class;
	}

	public List findProdByParentId(Long parentProdId) {
		String hql="from ProductInfo prod where prod.parentProdId=? order by prod.sortNo";
		List list=null;
		try{
			if(parentProdId==null){
				hql="from ProductInfo prod where  prod.parentProdId is null order by prod.sortNo";
				list=find(hql);
			}else{
				list=find(hql, parentProdId);
			}
		}catch(Exception e){
			ExceptionManager.throwException(DAOException.class, ErrorCodeConst.DB_OPERATION_ERROR, new String[]{hql}, e);
		}
		return list;
	}

	public boolean hasSubProdByParentId(Long parentProdId) {
		String hql="select count(*) from ProductInfo prod where prod.parentProdId=?";
		List list=null;
		try{
			int count=0;
			list=find(hql, parentProdId);
			count=Integer.parseInt(""+list.get(0));
			return count>0;
		}catch(Exception e){
			ExceptionManager.throwException(DAOException.class, ErrorCodeConst.DB_OPERATION_ERROR, new String[]{hql}, e);
		}
		return false;
	}

	


	
	public Long getIdByProdNameInParent(String prodName,Long parentProdId) {
		String hql="select prod from ProductInfo prod where prod.prodName=:prodName and prod.parentProdId=:prodId";
		try{
			
			Map<String, Object> parameterMap = new HashMap<String, Object>(1);
			parameterMap.put("prodName", prodName);
			if(parentProdId!=null){
				parameterMap.put("prodId", parentProdId);
			}else{
				hql="select prod from ProductInfo prod where prod.prodName=:prodName and prod.parentProdId is null";
			}
			
			List<ProductInfo> list=super.queryByParam(hql, parameterMap, null);
			if (list.isEmpty())
				return null;
			else
				return list.get(0).getId();
		}catch(Exception e){
			throw ExceptionManager.getException(DAOException.class, ErrorCodeConst.DB_OPERATION_ERROR, new String[]{hql}, e);
		}
		
	}

	
	public Long getIdByNameKeyInParent(String prodNameKey,Long parentProdId) {
		String hql="select prod from ProductInfo prod where prod.prodNameKey=:prodNameKey and prod.parentProdId=:prodId";
		try{
		
			Map<String, Object> parameterMap = new HashMap<String, Object>(1);
			parameterMap.put("prodNameKey", prodNameKey);
			if(parentProdId!=null){
				parameterMap.put("prodId", parentProdId);
			}else{
				hql="select prod from ProductInfo prod where prod.prodNameKey=:prodNameKey and prod.parentProdId is null";
			}
			List<ProductInfo> list=super.queryByParam(hql, parameterMap, null);
			if (list.isEmpty())
				return null;
			else
				return list.get(0).getId();
		}catch(Exception e){
			throw ExceptionManager.getException(DAOException.class, ErrorCodeConst.DB_OPERATION_ERROR, new String[]{hql}, e);
		}
	}

	public List findAllURLProd() {
		String hql="from ProductInfo prod where prod.prodUrl is not null";
		List list=null;
		try{
			list=find(hql);
		}catch(Exception e){
			ExceptionManager.throwException(DAOException.class, ErrorCodeConst.DB_OPERATION_ERROR, new String[]{hql}, e);
		}
		return list;
	}


	public Long getMaxSortNo(Long targetProdId) {
		String hql="select max(prod.sortNo) from ProductInfo prod where prod.parentProdId=?";
		if(targetProdId==null){
			hql="select max(prod.sortNo) from ProductInfo prod where prod.parentProdId is null";
		}
			List list=null;
		try{
			if(targetProdId!=null){
				list=find(hql,targetProdId);
			}else{
				list=find(hql);
			}
			
			if(list!=null&&list.size()>0){
				Long maxNo=(Long)list.get(0);
				if(maxNo!=null){
					return maxNo;
				}
			}
		}catch(Exception e){
			ExceptionManager.throwException(DAOException.class, ErrorCodeConst.DB_OPERATION_ERROR, new String[]{hql}, e);
		}
		return 0L;
	}

	public List getButtomProduct(ProductInfo target) {
		String hql="from ProductInfo prod where prod.parentProdId=:prodId and prod.sortNo>:sortNo";
		try{
			if(target!=null){
				Map<String, Object> parameterMap = new HashMap<String, Object>(1);
				parameterMap.put("prodId", target.getParentProdId());
				parameterMap.put("sortNo", target.getSortNo());
				return super.queryByParam(hql, parameterMap, null);
			}
		}catch(Exception e){
			ExceptionManager.throwException(DAOException.class, ErrorCodeConst.DB_OPERATION_ERROR, new String[]{hql}, e);
		}
		return null;
	}

	public ProductInfo getProductInfoByProdNo(String prodNo) {
		String hql="from ProductInfo p where p.prodNo=:prodNo";
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("prodNo", prodNo);
		List list=queryByParam(hql,map,null);
		if(list!=null&&list.size()>0){
			return (ProductInfo) list.get(0);
		}
		return null;
	}

	public List<ProductInfo> getProductListByMino(String miNo) throws DAOException {
		List<ProductInfo> ret = null;
		if(StringUtils.isNotBlank(miNo)){
			String hql = "select p from ProductInfo p ,MemberProductInfo m where p.id=m.prodId and m.miNo=? ";
			ret = getHibernateTemplate().find(hql,miNo);
		}
		return ret == null ? new ArrayList<ProductInfo>(0) : ret ;
	}
	
	/** 
	 * 根据产品列表id获取数据库中的产品数量
	 * @param productIds
	 * @return
	 * @throws DAOException
	 */
	public int getProductAmount(List<Long> productIds) throws DAOException{
		String hql = "select count(*) from ProductInfo t where t.id in(:productIds)";
		try{
			Session session = this.getSession();
			Query query = session.createQuery(hql);
			query.setParameterList("productIds", productIds);
			Long count = (Long) query.list().get(0);
			return count.intValue();
		}catch(Exception e){
			ExceptionManager.throwException(DAOException.class, ErrorCodeConst.DB_OPERATION_ERROR, new String[]{hql}, e);
		}
		return 0;
	}

	@Override
	public ProductInfo getProductInfoByProdName(String prodName) {
		List<ProductInfo> list=find ("from ProductInfo where prodName=?",prodName);
		if(list.isEmpty()){
			return null;
		}else{
			return list.get(0);
		}
	}

}
