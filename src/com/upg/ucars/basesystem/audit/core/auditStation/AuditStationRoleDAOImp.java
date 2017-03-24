/*
 * 源程序名称: AuidtStationRoleDAOImp.java 
 * 软件著作权: 投融长富金融服务集团有限公司 版权所有
 * 系统名称: XXXX业务系统平台(UBSP)
 * 模块名称：TODO(这里注明模块名称)
 * 
 */

package com.upg.ucars.basesystem.audit.core.auditStation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.upg.ucars.framework.base.BaseDAO;
import com.upg.ucars.mapping.basesystem.audit.AuditStation;
import com.upg.ucars.mapping.basesystem.audit.ReAuditStationRole;
import com.upg.ucars.mapping.basesystem.security.Buser;
import com.upg.ucars.mapping.basesystem.security.Role;

public class AuditStationRoleDAOImp extends BaseDAO<ReAuditStationRole, Long>
		implements IAuditStationRoleDAO {
	@Override
	public Class<ReAuditStationRole> getEntityClass() {
		return ReAuditStationRole.class;
	}

	public List<Role> findRoleByAuditStationId(Long auditStationId) {
		String hql="select b from ReAuditStationRole a,Role b where a.roleId=b.roleId and a.auditStationId=:auditStationId";
		HashMap map=new HashMap();
		map.put("auditStationId", auditStationId);
		return queryByParam(hql,map,null);
	}

	public List<ReAuditStationRole> findReAuditStationRoleBy(Long auditStationId) {
		String hql="from ReAuditStationRole a where a.auditStationId=:auditStationId";
		HashMap map=new HashMap();
		map.put("auditStationId", auditStationId);
		return queryByParam(hql,map,null);
	}
	public void delete(String[] roleids, AuditStation auditStation) {
		String hql="select re from ReAuditStationRole re where re.auditStationId=:stationId and re.roleId in(:roleId)";
		Map<String, Object> parameterMap = new HashMap<String, Object>(2);
		parameterMap.put("stationId", auditStation.getId());
		if(roleids!=null&&roleids.length>0){
			Long rids[]=new Long[roleids.length];
			for(int i=0;i<rids.length;i++){
				rids[i]=Long.valueOf(roleids[i]);
			}
			parameterMap.put("roleId", rids);
		}else{
			return ;
		}
		
		List<ReAuditStationRole> list=queryByParam(hql,parameterMap,null);
		delAll(list);
		
	}

	public List<Buser> findBuserByAuditStationId(Long auditStationid) {
		String hql="select u from ReAuditStationRole a,Role b,Buser u,ReUserRole r where a.roleId=b.roleId and b.roleId=r.roleId and r.userId=u.userId and u.brchId=b.brchId and a.auditStationId=:auditStationId";
		HashMap map=new HashMap();
		map.put("auditStationId", auditStationid);
		return queryByParam(hql,map,null);
	}

	public Long countUserNumberByAuditStationId(Long auditStationId) {
		String hql="select count(u) from ReAuditStationRole a,Role b,Buser u,ReUserRole r where a.roleId=b.roleId and b.roleId=r.roleId and r.userId=u.userId and u.brchId=b.brchId and a.auditStationId=:auditStationId";
		HashMap map=new HashMap();
		map.put("auditStationId", auditStationId);
		List list=queryByParam(hql,map,null);
		Long userNumber=0L;
		if(list!=null&&list.size()>0){
			userNumber=(Long)list.get(0);
		}
		return userNumber;
	}
	

}
