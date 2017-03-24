package com.upg.ucars.basesystem.audit.core.auditStation;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.upg.ucars.constant.ErrorCodeConst;
import com.upg.ucars.framework.base.BaseDAO;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.framework.exception.ExceptionManager;
import com.upg.ucars.mapping.basesystem.audit.AuditStation;
import com.upg.ucars.model.ConditionBean;

public class AuditStationDAOImp extends BaseDAO<AuditStation, Long> implements
		IAuditStationDAO {

	@Override
	public Class<AuditStation> getEntityClass() {
		return AuditStation.class;
	}
	
	public List<AuditStation> getAuditStationListByNodeId(Long auditNodeId){
		List<AuditStation> ret = null;
		if( auditNodeId != null ){
			String hql = "from AuditStation where auditNodeId=?";
			ret = getHibernateTemplate().find(hql, auditNodeId);
		}
		return ret == null ? EMPTY_LIST : ret;
	}
	
	public List<AuditStation> findAuditStationByAuditNodeId(Long auditNodeId,
			Long createBrchId) {
		List<AuditStation> ret=new ArrayList<AuditStation>();
		String hql="from AuditStation station where station.auditNodeId=:auditNodeId and station.createBrchId=:brchId order by station.sortNo";
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("auditNodeId", auditNodeId);
		if(createBrchId==null){
			hql="from AuditStation station where station.auditNodeId=:auditNodeId and station.createBrchId is null order by station.sortNo";
		}else{
			parameterMap.put("brchId", createBrchId);
		}
		ret=queryByParam(hql, parameterMap, null);
		return ret;
	}

	public Long getMaxSortNo(Long auditNodeId,Long createBrchId) {
		String hql="select max(station.sortNo) from AuditStation station where station.auditNodeId=:nodeId and station.createBrchId=:brchId";
		List list=new ArrayList();
	try{
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("nodeId", auditNodeId);
		if(createBrchId==null){
			hql="select max(station.sortNo) from AuditStation station where station.auditNodeId=:nodeId and station.createBrchId is null";
		}else{
			parameterMap.put("brchId", createBrchId);
		}
		list=queryByParam(hql,parameterMap,null);
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

	public List<AuditStation> getButtomAuditStation(AuditStation target) {
		String hql="from AuditStation a where a.auditNodeId=:nodeId and a.sortNo>:sortNo and a.createBrchId=:brchId";
		try{
			if(target!=null){
				Map<String, Object> parameterMap = new HashMap<String, Object>(1);
				parameterMap.put("nodeId", target.getAuditNodeId());
				parameterMap.put("sortNo", target.getSortNo());
				if(target.getCreateBrchId()==null){
					hql="from AuditStation a where a.auditNodeId=:nodeId and a.sortNo>:sortNo and a.createBrchId is null";
				}else{
					parameterMap.put("brchId", target.getCreateBrchId());
				}
				
				return queryByParam(hql,parameterMap,null);
			}
		}catch(Exception e){
			ExceptionManager.throwException(DAOException.class, ErrorCodeConst.DB_OPERATION_ERROR, hql, e);
		}
		return null;
	}

	public List<AuditStation> findAuditStationByAuditRouteId(Long auditRouteId,Long brchId) {
		String hql="from AuditStation a where a.auditRouteId=:routeId and a.createBrchId=:brchId order by a.sortNo";
		try{
			if(auditRouteId!=null){
				Map<String, Object> parameterMap = new HashMap<String, Object>(1);
				parameterMap.put("routeId", auditRouteId);
				if(brchId==null){
					hql="from AuditStation a where a.auditRouteId=:routeId and a.createBrchId is null";
				}else{
					parameterMap.put("brchId", brchId);
				}
				return queryByParam(hql,parameterMap,null);
			}
		}catch(Exception e){
			ExceptionManager.throwException(DAOException.class, ErrorCodeConst.DB_OPERATION_ERROR, hql, e);
		}
		return null;
	}

	public List<AuditStation> findBindedAuditStation(AuditStation as, Page pg) {
		List<ConditionBean> conditionList = new ArrayList<ConditionBean>();
		conditionList.add(new ConditionBean("bindBrchId",as.getBindBrchId()));
		if( as.getCreateBrchId() != null ){
			conditionList.add(new ConditionBean("createBrchId", as.getCreateBrchId()));
		}else{
			conditionList.add(new ConditionBean("createBrchId", ConditionBean.IS_NOT_NULL,null));
		}
		if( as.getAuditRouteId() != null ){
			conditionList.add(new ConditionBean("auditRouteId", as.getAuditRouteId()));
		}
		if(as.getAuditStationPrivilege() != null){
			conditionList.add(new ConditionBean("auditStationPrivilege",ConditionBean.LESS_AND_EQUAL,as.getAuditStationPrivilege()));
		}
		return queryEntity(conditionList, pg);
	}

	public List<AuditStation> findAllStationOrderByNodeAndSort(
			Long auditRouteId, Long brchId) throws DAOException {
		String hql="Select a from AuditStation a,AuditNode b " +
				"where a.auditNodeId=b.id " +
				"and a.auditRouteId=:routeId " +
				"and a.createBrchId=:brchId " +
				"order by b.sortNo,a.sortNo ";
		HashMap map=new HashMap();
		map.put("routeId", auditRouteId);
		map.put("brchId", brchId);
		return queryByParam(hql,map,null);
	}

	public List<AuditStation> getAuditStationsByAridAndBrchNull(Long auditRouteId,boolean isSysDef) throws DAOException {
		List<AuditStation> ret = null;
		if( auditRouteId != null ){
			String hql = "select ast from AuditStation ast ,AuditRoute ar where ast.auditRouteId=ar.id and ar.id=? and ast.createBrchId is null";
			if( isSysDef ){
				hql += " and ar.miNo is null and ast.bindBrchId is null";
			}else{
				hql += " and ar.miNo is not null";
			}
			ret = getHibernateTemplate().find(hql, auditRouteId);
		}
		return ret == null ? EMPTY_LIST : ret;
	}



	

}
