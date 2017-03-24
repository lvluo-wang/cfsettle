package com.upg.ucars.framework.bpm.assign.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.upg.ucars.framework.annotation.Dao;
import com.upg.ucars.framework.base.BaseDAO;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.mapping.framework.bpm.HumnTaskActorDelegate;

@Dao
public class HumanTaskActorDelegatorDaoImpl extends BaseDAO<HumnTaskActorDelegate, Long> implements
		IHumanTaskActorDelegatorDao {

	@Override
	public Class<HumnTaskActorDelegate> getEntityClass() {
		return HumnTaskActorDelegate.class;
	}

	@Override
	public List<HumnTaskActorDelegate> findHumnTaskActorDelegateByDelegator(Long delegator) {
		List<HumnTaskActorDelegate> result = null;
		if (delegator != null) {
			String hql = "from HumnTaskActorDelegate where delegator=? and startTime<=? and endTime >= ? and inEffect='1'";
			Date now = new Date();
			result = find(hql, new Object[] { delegator, now, now });
		}
		return result == null ? EMPTY_LIST : result;
	}

	@Override
	public List<HumnTaskActorDelegate> findAllTaskDelegatesByCreator(Long creator,Page page) {
		List<HumnTaskActorDelegate> result = null;
		if (creator != null) {
			String hql = "from HumnTaskActorDelegate where creator=:creator";
			Map<String,Object> parameterMap = new HashMap<String,Object>();
			parameterMap.put("creator", creator);
			result = queryByParam(hql, parameterMap, page);
		}
		return result == null ? EMPTY_LIST : result;
	}

	@Override
	public List<Long> findActorByTaskIdAndDelegator(Long taskId, List<Long> userIds) {
		List<Long> result = new ArrayList<Long>();
		if (taskId != null && userIds != null && userIds.size() > 0) {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT distinct u.SYS_USER_ID FROM JBPM_TASKINSTANCE TI,PROCESS_DEF  PD,HUMN_TASK HT,HUMN_TASK_ACTR HTA,BUSER U,RE_USER_ROLE UR,ROLE R  ");
			sql.append(" WHERE TI.PROCNAMEKEY_=PD.PROC_NAME ");
			sql.append(" AND PD.ID=HT.PROC_ID AND HT.TASK_NAME=TI.NAME_ AND HTA.TASK_ID=HT.ID AND HTA.BRCH_ID=TI.BRCHID_  ");
			sql.append(" AND HTA.ACTR_BRCH_ID=U.BRCH_ID AND R.ROLE_ID=UR.ROLE_ID AND U.SYS_USER_ID=UR.SYS_USER_ID AND UR.STATUS='1' ");
			sql.append(" AND HTA.ACTR_ROLE_ID=R.ROLE_ID  ");
			sql.append(" and ti.id_=:taskId and u.SYS_USER_ID in (:userIds) ");
			Map<String,Object> valueMap = new HashMap<String,Object>();
			valueMap.put("taskId", taskId);
			valueMap.put("userIds", userIds);
			List<Map<String,Object>> list = getMapListByStanderdSQL(sql.toString(), valueMap, null);
			if (list != null && !list.isEmpty()) {
				for (Map<String,Object> map : list) {
					Object actor = map.get("SYS_USER_ID");
					if (actor != null) {
						result.add(Long.valueOf(actor.toString()));
					}
				}
			}
		}
		return result;
	}

}
