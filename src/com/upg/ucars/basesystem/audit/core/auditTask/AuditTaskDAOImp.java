package com.upg.ucars.basesystem.audit.core.auditTask;




import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.upg.ucars.basesystem.audit.core.AuditHistSearchBean;
import com.upg.ucars.framework.base.BaseDAO;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.mapping.basesystem.audit.AuditTask;
public class AuditTaskDAOImp extends BaseDAO<AuditTask, Long> implements
		IAuditTaskDAO {

	@Override
	public Class getEntityClass() {
		return AuditTask.class;
	}

	public List<AuditTask> findAuditingTask(Long brchId, Long prodId) {
		String hql="from AuditTask a where a.brchId=:brchId and a.prodId=:prodId and a.auditStatus=:status";
		Map<String,Object> parameterMap=new HashMap<String,Object> ();
		parameterMap.put("brchId", brchId);
		parameterMap.put("prodId", prodId);
		parameterMap.put("status", AuditTask.STATUS_AUDITING);
		List<AuditTask> list=queryByParam(hql,parameterMap,null);
		return list;
	}

	public List<AuditTask> findAuditTask(Long brchId, Long prodId, Long pk) {
		String hql="from AuditTask a where a.brchId=:brchId and a.prodId=:prodId and a.auditEntityId=:entityId";
		Map<String,Object> parameterMap=new HashMap<String,Object>();
		parameterMap.put("brchId", brchId);
		parameterMap.put("prodId", prodId);
		parameterMap.put("entityId", pk);
		List<AuditTask> list=queryByParam(hql,parameterMap,null);
		return list;
	}

	public AuditTask findLastAuditTask(Long brchId, Long prodId, Long entityId) {
		AuditTask task=null;
		String hql="select max(a.id) from  AuditTask a where a.brchId=:brchId and a.prodId=:prodId and a.auditEntityId=:entityId ";
		Map<String,Object> parameterMap=new HashMap<String,Object>();
		parameterMap.put("brchId", brchId);
		parameterMap.put("prodId", prodId);
		parameterMap.put("entityId", entityId);
		List list=queryByParam(hql,parameterMap,null);
		Long id=null;
		if(list!=null&&list.size()>0){
			id=(Long)list.get(0);
			if(null!=id)
			task=get(id);
		}
		return task;
	}
	public AuditTask getLastAuditTask(Long prodId,Serializable entityId,Class<?> cls){
		AuditTask ret = null;
		String hql = "select max(id) from AuditTask where prodId=? and auditEntityId=? and auditEntityName=?";
		List<Long> list = getHibernateTemplate().find(hql, prodId,entityId,cls.getName());
		if( list != null && list.size() == 1 ){
			Long id = list.get(0);
			if( id != null ){
				ret = get(id);
			}
		}
		return ret;
	}

	public List<Map<String,Object>> findAuditHist(AuditHistSearchBean search,
			Page page) {
		String sql="select (select u.user_name from buser u where a.at_author=u.sys_user_id) as user_name, a.id,b.brch_name,d.prod_no,e.prod_alias,f.ar_name,a.at_status,a.at_create_time,a.at_done_time,a.audit_remark " +
				" from audit_task a, branch b,prod_info d,member_prod_info e,audit_route f,buser g " +
				" where a.brch_id=b.brch_id " +
				" and a.prod_id=d.id " +
				" and a.prod_id=e.prod_id " +
				" and b.mi_no=e.mi_no " +
				" and a.ar_id=f.id ";
		Long userId=search.getAuditActor();
		Long brchId=search.getBrchId();
		Long prodId=search.getProdId();
		Boolean isAudit=search.getContainAudit();//审批
		Boolean isBusi=search.getContainBusi(); //业务
		Boolean isContainChildBrch=search.getContainChild();//包含子机构
		Date endDate=search.getEndDate();//开始日期
		Date startDate=search.getStartDate();//结束日期
		String status=search.getStatus();
		
		HashMap<String,Object> map=new HashMap<String,Object>();
		if(status!=null&&status.length()>0){
			if(status.length()==1){
				sql+=" and a.at_status in ('"+status+"') ";
			}else{
				sql+=" and a.at_status in ("+status+") ";
			}
		}
		if(userId!=null){
			sql+=" and g.sys_user_id=:userId ";
			map.put("userId", userId);
		}
		if(prodId!=null){
			sql+=" and a.prod_id=:prodId ";
			map.put("prodId", prodId);
		}
		if(brchId!=null){
			map.put("brchId", brchId);
		}
		if(brchId!=null&&!isContainChildBrch)
		{
			sql+=" and b.brch_id=:brchId ";
		}
		if(brchId!=null&&isContainChildBrch)
		{
			sql+=" and b.tree_code like (select t.tree_code||'%' from branch t where t.brch_id=:brchId ) ";
		}
		if((isAudit&&isBusi)||(!isAudit&&!isBusi))
		{
			sql+=" and ((a.at_author=g.sys_user_id) or a.id in(select c.at_id from audit_process c where c.ap_exec_person=g.sys_user_id)) ";
			if(startDate!=null){
				sql+=" and (a.at_create_time>=:startDate1 or a.id in(select c.at_id from audit_process c where c.ap_done_time >=:startDate2)) ";
				map.put("startDate1", startDate);
				map.put("startDate2", startDate);
			}
			if(endDate!=null){
				sql+=" and (a.at_create_time<=:endDate1 or a.id in(select c.at_id from audit_process c where c.ap_done_time <=:endDate2)) ";
				map.put("endDate1", endDate);
				map.put("endDate2", endDate);
			}
			
		}
		if(!isAudit&&isBusi)
		{
			sql+=" and a.at_author=g.sys_user_id ";
			if(startDate!=null){
				sql+=" and a.at_create_time>=:startDate  ";
				map.put("startDate", startDate);
			}
			if(endDate!=null){
				sql+=" and a.at_create_time<=:endDate  ";
				map.put("endDate", endDate);
			}
		}
		if(isAudit&&!isBusi)
		{
			sql+=" and  a.id in(select c.at_id from audit_process c where c.ap_exec_person=g.sys_user_id) ";
			if(startDate!=null){
				sql+=" and  a.id in(select c.at_id from audit_process c where c.ap_done_time >=:startDate) ";
				map.put("startDate", startDate);
			}
			if(endDate!=null){
				sql+=" and  a.id in(select c.at_id from audit_process c where c.ap_done_time <=:endDate) ";
				map.put("endDate", endDate);
			}
		}
		sql+=" order by a.at_create_time desc";
		List<Map<String,Object>> list=getMapListByStanderdSQL(sql, map, page);
		List<Map<String,Object>> result=new ArrayList<Map<String,Object>>();
		if(list!=null&&list.size()>0){
			for(Map<String,Object> mp:list){
				Map<String,Object> hm=new HashMap<String,Object>();
				Set<String> set=mp.keySet();
				for(String key:set){
					Object value=mp.get(key);
					hm.put(key.toUpperCase(), value);
				}
				result.add(hm);
			}
		}
		return result;
	}


}
