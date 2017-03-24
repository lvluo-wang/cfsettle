package com.upg.ucars.basesystem.audit.core.auditProcess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.upg.ucars.framework.base.BaseDAO;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.mapping.basesystem.audit.AuditProcess;
import com.upg.ucars.mapping.basesystem.audit.AuditTask;

/**
 * 审批流程 
 * 
 * @author shentuwy
 *
 */

public class AuditProcessDAOImp extends BaseDAO<AuditProcess, Long> implements IAuditProcessDAO {

	@Override
	public Class<AuditProcess> getEntityClass() {
		return AuditProcess.class;
	}

	public List<Map<String,Object>> getCanOperateAuditProcess(Long userId,String prodNo,Long brchId,Page page)
			throws DAOException {
		List<Map<String,Object>> result=null;
		String[] prodArr = null;
		if( StringUtils.isNotBlank(prodNo) ){
			prodArr = prodNo.trim().split(",");
		}
		Object[] hqlAndParam = getCanOperateAPHqlAndParam(userId,prodArr,brchId);
		
		List<Object[]> list=queryByParam((String)hqlAndParam[0],(Map<String,Object>)hqlAndParam[1],page);
		
		if(list!=null){
			result=new ArrayList<Map<String,Object>>(list.size());
			for(Object[] objs:list){
				HashMap<String,Object> m=new HashMap<String,Object>();
				m.put("auditProcessId", objs[0]);
				m.put("auditTaskId", objs[1]);
				m.put("auditProcessStatus", objs[2]);
				m.put("auditProcessDoneTime", objs[3]);
				m.put("auditRemark", objs[4]);
				m.put("prodNo", objs[5]);
				m.put("prodAlias", objs[6]);
				m.put("auditRouteName", objs[7]);
				m.put("userName", objs[8]);
				m.put("brchName", objs[9]);
				m.put("miNo", objs[10]);
				m.put("version", objs[11]);				
				m.put("prodId", objs[12]);
				result.add(m);
			}
		}
		return result;
	}
	private Object[] getCanOperateAPHqlAndParam(Long userId, String[] prodNo, Long brchId) {
		Object[] ret = new Object[2];
		HashMap<String, Object> param = new HashMap<String, Object>();
		String hql = "select ap.id,ap.auditTaskId,ap.auditProcessStatus,ap.auditProcessDoneTime,atask.auditRemark,prod.prodNo,prod.prodName,ar.auditRouteName,u.userName,bh.brchName,bh.miNo,ap.version,prod.id "
				+ "from AuditProcess ap,AuditTask atask,Branch bh,AuditRoute ar,Buser u,ProductInfo prod,AuditStation s "
				+ "where atask.auditStatus='"+AuditTask.STATUS_AUDITING+"' and s.id=ap.auditProcessExecStation and ap.auditTaskId=atask.id and atask.prodId=prod.id and ap.auditProcessCommitPerson=u.userId and atask.brchId=bh.brchId and atask.auditRouteId=ar.id " 
				+ "and ("
				+ 	"(ap.auditProcessExecPerson=:userId and ap.auditProcessStatus='"+AuditProcess.STATUS_ACCEPING+"') " 
				+ 	"or (" 
				+ 		"ap.auditProcessStatus='"+AuditProcess.STATUS_UN_ACCEPT+"' " 
				+ 		"and (" 
				+ 			"exists (select 1 from ReAuditStationUser ru,Buser ur where ur.brchId=s.bindBrchId and ur.userId=ru.userId and ru.userId=:stationUser and ru.auditStationId = ap.auditProcessExecStation) " 
				+ 			"or " 
				+ 			"exists (select 1 from ReAuditStationRole rr,ReUserRole ru,Buser bu where bu.brchId=s.bindBrchId and bu.userId=ru.userId and bu.userId=:roleUser and rr.roleId=ru.roleId and  rr.auditStationId=ap.auditProcessExecStation)" 
				+ 		")" 
				+ 	")" 
				+ ")";
		if( prodNo != null && prodNo.length > 0 ){
			hql += " and prod.prodNo in(:prodNo)";
			param.put("prodNo", prodNo);
		}
		param.put("userId", userId);
		param.put("stationUser", userId);
		param.put("roleUser", userId);
		ret[0] = hql;
		ret[1] = param;
		return ret;
	}
	
//	private Object[] getCanOperateAPHqlAndParam(Long userId, String[] prodNo, Long brchId) {
//		Object[] ret = new Object[2];
//		HashMap<String, Object> param = new HashMap<String, Object>();
//		String hql = "select ap.id,ap.auditTaskId,bh.brchName,prod.prodName,ar.auditRouteName,ap.auditProcessStatus,u.userName,ap.version,prod.id,atask.auditRemark,prod.prodNo,ap.auditProcessDoneTime,bh.miNo "
//				+ "from AuditProcess ap,AuditTask atask,Branch bh,AuditRoute ar,Buser u,ProductInfo prod,AuditStation s "
//				+ "where (exists (select 1 from ReAuditStationUser ru where ru.userId=:userId and ru.auditStationId = ap.auditProcessExecStation) ";
//		if (brchId != null) {
//			hql += "or ( not exists(select 1 from ReAuditStationUser ru where ru.auditStationId = ap.auditProcessExecStation) )";
//		}
//		hql += ")"
//				+ "and (ap.auditProcessStatus=:status1 or (ap.auditProcessExecPerson=:userId and ap.auditProcessStatus=:status2)) "
//				+ "and s.id=ap.auditProcessExecStation and s.bindBrchId=:brchId "
//				+ "and ap.auditTaskId=atask.id and atask.brchId=bh.brchId and atask.auditRouteId=ar.id "
//				+ "and atask.prodId=prod.id and ap.auditProcessCommitPerson=u.userId "
//				+ "and atask.auditStatus=:auditStatus ";
//		
//		if( prodNo != null && prodNo.length > 0 ){
//			hql += " and prod.prodNo in(:prodNo)";
//			param.put("prodNo", prodNo);
//		}
//		param.put("brchId", brchId);
//		param.put("userId", userId);
//		param.put("status1", AuditProcess.STATUS_UN_ACCEPT);
//		param.put("status2", AuditProcess.STATUS_ACCEPING);
//		param.put("auditStatus", AuditTask.STATUS_AUDITING);
//		ret[0] = hql;
//		ret[1] = param;
//		return ret;
//	}
	
	public Map<String,Integer> getCanOperateAPCount(Long userId,List<String> prodNos,Long brchId){
		Map<String,Integer> ret = new HashMap<String,Integer>();
		String[] prodNoArr = null;
		if( prodNos != null && prodNos.size() > 0 ){
			prodNoArr = prodNos.toArray(new String[prodNos.size()]);
			for( String pn : prodNos ){
				ret.put(pn,Integer.valueOf(0));
			}
		}
		Object[] hqlAndParam = getCanOperateAPHqlAndParam(userId, prodNoArr, brchId);
		String hql = (String) hqlAndParam[0];
		Map<String,Object> param = (Map<String,Object>) hqlAndParam[1];
		hql = hql.substring(hql.indexOf("from"));
		hql = "select new map(prod.prodNo as prodNo,count(ap.id) as count )" + hql + " group by prod.prodNo";
		
		List<Map<String,Object>> list = queryByParam(hql, param, null);
		if( list != null && list.size() > 0 ){
			for( Map<String,Object> item : list){
				ret.put((String)item.get("prodNo"), Integer.valueOf(String.valueOf(item.get("count"))));
			}
		}
		return ret;
	}
	

	public List<AuditProcess> findAllProcess(Long auditTaskId) {
		String hql="from AuditProcess a where a.auditTaskId=:taskId order by a.sortNo";
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("taskId", auditTaskId);
		return queryByParam(hql,map,null);
	}

	public List<Map<String,Object>> getAllProcess(Long id) {
		String sql="select  E.BRCH_NAME,D.AS_NAME,E.USER_NAME,A.AP_STATUS,A.AP_EXEC_RESULT,A.AP_EXEC_REMARK,A.AP_DONE_TIME " +
				" from AUDIT_PROCESS A " +
				" left join (SELECT B.SYS_USER_ID,B.USER_NAME,C.BRCH_NAME FROM BUSER B,BRANCH C WHERE B.BRCH_ID=C.BRCH_ID) E " +
				" ON A.AP_EXEC_PERSON = E.SYS_USER_ID  " +
				" LEFT join AUDIT_STATION D " +
				" ON A.AP_EXEC_STATION=D.ID " +
				" where a.AT_ID=:taskId " +
				" order by a.sort_no";
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("taskId", id);
		List<Map<String,Object>> list=getMapListByStanderdSQL(sql, map, null);
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
