package com.upg.ucars.basesystem.audit.core.auditNode;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.upg.ucars.constant.ErrorCodeConst;
import com.upg.ucars.framework.base.BaseDAO;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.framework.exception.ExceptionManager;
import com.upg.ucars.mapping.basesystem.audit.AuditNode;

public class AuditNodeDAOImp extends BaseDAO<AuditNode, Long> implements IAuditNodeDAO {

	@Override
	public Class getEntityClass() {
		return AuditNode.class;
	}

	public List<AuditNode> findAuditNodeByAuditRouteId(Long auditRouteId) {
		String hql="from AuditNode an where an.auditRouteId=:auditRouteId order by an.sortNo";
		Map<String, Object> parameterMap = new HashMap<String, Object>(1);
		parameterMap.put("auditRouteId", auditRouteId);
		List<AuditNode> ret=queryByParam(hql, parameterMap, null);
		return ret;
	}

	public Long getMaxSortNo(Long auditRouteId) {
		String hql="select max(node.sortNo) from AuditNode node where node.auditRouteId=?";
		try{
			List<Long> list=find(hql,auditRouteId);
			if(list!=null&&list.size()>0){
				Long maxNo=list.get(0);
				if(maxNo!=null){
					return maxNo;
				}
			}
		}catch(Exception e){
			ExceptionManager.throwException(DAOException.class, ErrorCodeConst.DB_OPERATION_ERROR, new String[]{hql}, e);
		}
		return 0L;
	}

	public List<AuditNode> getButtomAuditNode(AuditNode target) {
		String hql="from AuditNode a where a.auditRouteId=:routeId and a.sortNo>:sortNo";
		try{
			if(target!=null){
				Map<String, Object> parameterMap = new HashMap<String, Object>(1);
				parameterMap.put("routeId", target.getAuditRouteId());
				parameterMap.put("sortNo", target.getSortNo());
				return queryByParam(hql,parameterMap,null);
			}
		}catch(Exception e){
			ExceptionManager.throwException(DAOException.class, ErrorCodeConst.DB_OPERATION_ERROR, hql, e);
		}
		return null;
	}


}
