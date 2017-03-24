package com.upg.ucars.basesystem.audit.core.auditStation;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.upg.ucars.framework.base.BaseDAO;
import com.upg.ucars.mapping.basesystem.audit.AuditStation;
import com.upg.ucars.mapping.basesystem.audit.ReAuditStationUser;
import com.upg.ucars.mapping.basesystem.security.Buser;

public class AuditStationUserDAOImp extends BaseDAO<ReAuditStationUser, Long>
		implements IAuditStationUserDAO {

	

	@Override
	public Class<ReAuditStationUser> getEntityClass() {
		return ReAuditStationUser.class;
	}

	public Long countUserNumberByAuditStationId(Long auditStationId) {
		String hql="select count(*) from ReAuditStationUser re where re.auditStationId=:auditStationId";
		HashMap map=new HashMap();
		map.put("auditStationId", auditStationId);
		List list=queryByParam(hql,map,null);
		Long userNumber=0L;
		if(list!=null&&list.size()>0){
			userNumber=(Long)list.get(0);
		}
		return userNumber;
	}

	public List<Buser> findBuserByAuditStationId(Long auditStationId) {
		String hql="select b from ReAuditStationUser a,Buser b where a.userId=b.userId and a.auditStationId=:auditStationId";
		HashMap map=new HashMap();
		map.put("auditStationId", auditStationId);
		return queryByParam(hql,map,null);
	}

	public List<ReAuditStationUser> findReAuditStationUserBy(Long auditStationId) {
		String hql="from ReAuditStationUser a where a.auditStationId=:auditStationId";
		HashMap map=new HashMap();
		map.put("auditStationId", auditStationId);
		return queryByParam(hql,map,null);
	}

	public boolean existStationUserMap(Long userId, Long stationId) {
		String hql="select count(*) from ReAuditStationUser a where a.auditStationId=:stationId and a.userId=:userId";
		HashMap map=new HashMap();
		map.put("stationId", stationId);
		map.put("userId", userId);
		List list=queryByParam(hql,map,null);
		int count=0;
		if(list!=null){
			count=Integer.parseInt(list.get(0)+"");
		}
		return count>0;
	}

	public void delete(String[] userIds, AuditStation auditStation) {
		String hql="select re from ReAuditStationUser re where re.auditStationId=:stationId and re.userId in(:userid)";
		Map<String, Object> parameterMap = new HashMap<String, Object>(2);
		parameterMap.put("stationId", auditStation.getId());
		if(userIds!=null&&userIds.length>0){
			Long uids[]=new Long[userIds.length];
			for(int i=0;i<uids.length;i++){
				uids[i]=Long.valueOf(userIds[i]);
			}
			parameterMap.put("userid", uids);
		}else{
			return ;
		}
		
		List<ReAuditStationUser> list=queryByParam(hql,parameterMap,null);
		delAll(list);
	}

}
