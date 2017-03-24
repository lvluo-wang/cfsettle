package com.upg.ucars.framework.bpm.base;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

import com.upg.ucars.bpm.core.InstanceBusinessSearchBean;
import com.upg.ucars.framework.annotation.Dao;
import com.upg.ucars.framework.base.BaseDAO;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.mapping.framework.bpm.InstanceBusiness;

/**
 * 
 * 
 * @author shentuwy
 * 
 * 
 */
@Dao
public class InstanceBusinessDaoImpl extends BaseDAO<InstanceBusiness, Long>
		implements IInstanceBusinessDao {

	public Class<InstanceBusiness> getEntityClass() {
		return InstanceBusiness.class;
	}

	public Object getEntityById(Long id) {
		Object ret = null;
		InstanceBusiness instanceBusiness = get(id);
		if (instanceBusiness != null) {
			Long entityId = instanceBusiness.getEntityId();
			ret = getHibernateTemplate().get(instanceBusiness.getEntityType(),
					entityId);
		}
		return ret;
	}

	public List<InstanceBusiness> getInstanceBusinessList(InstanceBusinessSearchBean searchBean,Page page){
		List<InstanceBusiness> ret = null;
		StringBuilder hql = new StringBuilder();
		hql.append("from InstanceBusiness ib");
		Map<String,Object> parameterMap = new HashMap<String,Object>();
		if (searchBean != null) {
			hql.append(" where 1=1 ");
			String col1 = searchBean.getCol1();
			if (StringUtils.isNotBlank(col1)) {
				hql.append(" and ib.col1 like :col1");
				parameterMap.put("col1", "%"+col1+"%");
			}
			Long creator = searchBean.getCreator();
			if (creator != null) {
				hql.append(" and ib.creator=:creator");
				parameterMap.put("creator", creator);
			}
			String entityType = searchBean.getEntityType();
			if (StringUtils.isNotBlank(entityType)) {
				hql.append(" and ib.entityType=:entityType");
				parameterMap.put("entityType", entityType);
			}
			Date startDate = searchBean.getCreateStartTime();
			if (startDate != null) {
				hql.append(" and ib.createTime >=:startDate");
				parameterMap.put("startDate", startDate);
			}
			Date endDate = searchBean.getCreateEndTime();
			if (endDate != null) {
				endDate = DateUtils.addDays(endDate, 1);
				hql.append(" and ib.createTime<=:endDate");
				parameterMap.put("endDate", endDate);
			}
			List<String> processNameList = searchBean.getProcessNameList();
			if (processNameList != null && processNameList.size() >0) {
				hql.append(" and ib.processName in (:processNameList)");
				parameterMap.put("processNameList", processNameList);
			}
			Long entityId = searchBean.getEntityId();
			if (entityId != null) {
				hql.append(" and ib.entityId =:entityId");
				parameterMap.put("entityId", entityId);
			}
			String instanceStatus = searchBean.getInstanceStatus();
			if (StringUtils.isNotBlank(instanceStatus)) {
				if (StringUtils.equals(IInstanceBusinessDao.INSTANCE_STATUS_AUDIT, instanceStatus)) {
					hql.append(" and exists (from org.jbpm.graph.exe.ProcessInstance pi where  pi.id =ib.instanceId and pi.end is null )");
				} else if (StringUtils.equals(IInstanceBusinessDao.INSTANCE_STATUS_END_NORMAL, instanceStatus)) {
					hql.append(" and exists (from org.jbpm.graph.exe.ProcessInstance pi where  pi.id =ib.instanceId and pi.end is not null and size(pi.rootToken.node.leavingTransitions) = 0 )");
				} else if (StringUtils.equals(IInstanceBusinessDao.INSTANCE_STATUS_END_REJECT, instanceStatus)) {
					hql.append(" and exists (from org.jbpm.graph.exe.ProcessInstance pi where  pi.id =ib.instanceId and pi.end is not null and size(pi.rootToken.node.leavingTransitions) > 0 )");
				}
			}
		}
		hql.append(" order by createTime desc");
		ret = queryByParam(hql.toString(), parameterMap, page);
		return ret == null ? EMPTY_LIST : ret;
	}

	@Override
	public InstanceBusiness getInstanceBusinessByInstanceId(Long instanceId) {
		InstanceBusiness result = null;
		if (instanceId != null) {
			List<InstanceBusiness> list = find("from InstanceBusiness where instanceId=?", instanceId);
			if (list != null ){
				if (list.size() != 1) {
					throw new RuntimeException("get too many InstanceBusiness by instanceId=" + instanceId);
				}
				result = list.get(0);
			}
		}
		return result;
	}
	
}
