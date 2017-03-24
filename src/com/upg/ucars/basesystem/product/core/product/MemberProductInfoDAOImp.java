package com.upg.ucars.basesystem.product.core.product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;

import com.upg.ucars.constant.ErrorCodeConst;
import com.upg.ucars.framework.base.BaseDAO;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.framework.exception.ExceptionManager;
import com.upg.ucars.mapping.basesystem.product.MemberProductInfo;
import com.upg.ucars.mapping.basesystem.product.ProductInfo;

public class MemberProductInfoDAOImp extends BaseDAO<MemberProductInfo, Long> implements
		IMemberProductInfoDAO {

	@Override
	public Class getEntityClass() {
		return MemberProductInfo.class;
	}

	public List<MemberProductInfo> findProductsByMiNo(String miNo) {
		String hql = "from MemberProductInfo where miNo=?";
		List<MemberProductInfo> list = this.getHibernateTemplate().find(hql, miNo);
		return list;
	}

	public List<ProductNode> findSebMemberProduct(Long prodId, String miNo) {
		List<ProductNode> result=new ArrayList<ProductNode>();
		StringBuffer sql=new StringBuffer("select pi.id, pi.prod_name, pi.prod_name_key,pi.parent_prod_id, pi.prod_type, re.mi_no,pi.prod_no");
		sql.append(" from prod_info pi left outer join member_prod_info re");
		sql.append(" on pi.id=re.prod_id");
		sql.append(" and re.mi_no=:mi_no");
		if(prodId==null){
			sql.append(" where pi.parent_prod_id is null");
		}else{
			sql.append(" where pi.parent_prod_id=:prod_id");
		}
		sql.append(" order by pi.sort_no");
		SQLQuery query=getSession().createSQLQuery(sql.toString());
		query.setString("mi_no", miNo);
		if(prodId!=null){
			query.setLong("prod_id", prodId);
		}
		List list=query.list();
		if(list!=null){
			for(int i=0;i<list.size();i++){
				Object[] objs=(Object[])list.get(i);
				if(objs!=null&&objs.length>6){
					
					Long id=objs[0]==null?null:Long.valueOf(objs[0].toString());
					String prodName=objs[1]==null?null:objs[1].toString();
					String prodNameKey=objs[2]==null?null:objs[2].toString();
					Long parentProdId=objs[3]==null?null:Long.valueOf(objs[3].toString());
					String prodType=objs[4]==null?null:objs[4].toString();
					String mn=objs[5]==null?null:objs[5].toString();
					String prodNo=objs[6]==null?null:objs[6].toString();
					ProductNode node=new ProductNode(id,prodName,prodNameKey,parentProdId,prodType,mn,prodNo);
					result.add(node);
				}
			}
		}
		return result;
	}

	public void saveMemberProducts(String miNo, List<Long> prodIdList) {
		List<Object[]> plist = this.getHibernateTemplate().findByNamedParam("select id, prodName from ProductInfo where id in (:par)","par", prodIdList);
		HashMap<Long, String> prodMap = new HashMap<Long, String>();
		for (Object[] objects : plist) {
			prodMap.put((Long)objects[0], (String)objects[1]);
		}
		
		ArrayList<MemberProductInfo> list = new ArrayList<MemberProductInfo>();
		for (Long prodId : prodIdList) {
			MemberProductInfo mpi = new MemberProductInfo();
			mpi.setMiNo(miNo);
			mpi.setProdId(prodId);
			mpi.setProdAlias(prodMap.get(prodId));
			list.add(mpi);
		}
		
		this.getHibernateTemplate().saveOrUpdateAll(list);
		
	}

	public void deleteMemberProducts(String miNo, List<Long> prodIdList) {
		StringBuffer idsb = new StringBuffer("-1");
		for (Long pid : prodIdList) {
			idsb.append(","+pid);
		}
		
		String hql = "delete MemberProductInfo where miNo=? and prodId in ("+idsb+")";
		
		this.getHibernateTemplate().bulkUpdate(hql, miNo);
		
	}

	public List<MemberProductInfo> findProductsByMiNo(String miNo,ProductInfo product, Page pg) {
		String hql = "select mpi from MemberProductInfo mpi,ProductInfo p where mpi.prodId=p.id and mpi.miNo=:miNo and p.prodType in (:prodTypes) ";
		Map<String,Object> parameterMap=new HashMap<String,Object>();
		parameterMap.put("miNo",miNo);
		String[] prodTypes = new String[]{ProductInfo.BUSI,ProductInfo.OTHER}; 
		parameterMap.put("prodTypes", prodTypes);
		if( product != null ){
			String prodNo = product.getProdNo();
			if( StringUtils.isNotBlank(prodNo)){
				hql += " and p.prodNo like :prodNo";
				parameterMap.put("prodNo", "%"+prodNo.trim()+"%");
			}
			String prodName = product.getProdName();
			if(StringUtils.isNotBlank(prodName)){
				hql += " and (mpi.prodAlias like :prodName or p.prodName like :prodName2)";
				parameterMap.put("prodName", "%"+prodName.trim() + "%");
				parameterMap.put("prodName2", "%"+prodName.trim() + "%");
			}
		}
		hql+=" order by p.prodNo";
		List<MemberProductInfo> list = queryByParam(hql,parameterMap,pg);
		return list;
	}
	
	public MemberProductInfo getMemberProductByProdNo(String miNo, String prodNo) {
		String hql = "select mp from MemberProductInfo mp, ProductInfo p where mp.prodId=p.id and mp.miNo=:miNo and p.prodNo=:prodNo";
		HashMap parameterMap=new HashMap();
		parameterMap.put("miNo",miNo);
		parameterMap.put("prodNo",prodNo);
		List<MemberProductInfo> list = queryByParam(hql,parameterMap,null);
		if (list.isEmpty())
			return null;
		return list.get(0);
	}
	
	public List<Object[]> findMemberProdByParentId(String miNo, Long parentProdId) throws  DAOException{
		String hql="select p, mp from MemberProductInfo mp, ProductInfo p where mp.prodId=p.id and mp.miNo=? and p.parentProdId=? order by p.sortNo";
		List<Object[]> list=null;
		try{
			if(parentProdId==null){
				hql="select p, mp from MemberProductInfo mp, ProductInfo p where mp.prodId=p.id and mp.miNo=? and p.parentProdId is null order by p.sortNo";
				list=find(hql, miNo);
			}else{
				list=find(hql, new Object[]{miNo, parentProdId});
			}
		}catch(Exception e){
			ExceptionManager.throwException(DAOException.class, ErrorCodeConst.DB_OPERATION_ERROR, new String[]{hql}, e);
		}
		return list;
	}
	
}
