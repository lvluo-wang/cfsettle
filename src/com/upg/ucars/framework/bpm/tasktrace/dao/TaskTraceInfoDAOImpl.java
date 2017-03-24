package com.upg.ucars.framework.bpm.tasktrace.dao;

import java.util.List;

import com.upg.ucars.framework.base.BaseDAO;
import com.upg.ucars.mapping.framework.bpm.TaskTraceInfo;

public class TaskTraceInfoDAOImpl extends BaseDAO<TaskTraceInfo, Long> implements ITaskTraceInfoDAO {

	public Class<TaskTraceInfo> getEntityClass() {
		return TaskTraceInfo.class;
	}

	public TaskTraceInfo getByTaskId(Long taskId) {
		String hql = "from TaskTraceInfo where taskId=?";

		List<TaskTraceInfo> list = this.find(hql, taskId);

		if (!list.isEmpty())
			return list.get(0);
		return null;

	}

	public List<TaskTraceInfo> getTaskTraceInfoList(Long entityId, String processName) {
		String hql = "select t from TaskTraceInfo t,InstanceBusiness b where t.entityId=b.id and b.entityId=? and b.processName=? order by t.dealTime asc";
		return find(hql, new Object[] { entityId, processName });
	}

	@Override
	public List<TaskTraceInfo> getTaskTraceInfoList(Long entityId, String processName, String taskName) {
		String hql = "select t from TaskTraceInfo t,InstanceBusiness b where t.entityId=b.id and b.entityId=? and b.entityType in ( select sib.entityType from InstanceBusiness sib where sib.entityId=? and sib.processName=?  ) and t.taskName=? order by t.dealTime asc";
		return find(hql, new Object[] { entityId, entityId, processName, taskName });
	}

	public List<TaskTraceInfo> getTaskTraceInfoListByEntityIdAndType(Long entityId, String entityType) {
		final String hql = "select t from TaskTraceInfo t,InstanceBusiness b where t.entityId=b.id and b.entityId=? and b.entityType=? order by t.dealTime asc";
		return find(hql, new Object[] { entityId, entityType });
	}

	public List<TaskTraceInfo> getInstanceTaskTraceInfoList(Long instanceBusinessId) {
		final String hql = "select t from TaskTraceInfo t,InstanceBusiness b where t.entityId=b.id and b.id=? order by t.dealTime asc";
		return find(hql, new Object[] { instanceBusinessId });
	}

	public boolean hasTaskTraceInfo(Long instanceBusinessId) {
		final String hql = "select count(t) from TaskTraceInfo t,InstanceBusiness b where t.entityId=b.id and b.id=?";
		int count = ((Number) (find(hql, instanceBusinessId).get(0))).intValue();
		return count > 0;
	}

	@Override
	public List<TaskTraceInfo> getTaskTraceInfoByDelegateId(Long delegateId) {
		List<TaskTraceInfo> result = null;
		if (delegateId != null) {
			String hql = "select t from TaskTraceInfo t,InstanceBusiness b where t.entityId=b.id and t.delegateId=?";
			result = find(hql, delegateId);
		}
		return result == null ? EMPTY_LIST : result;
	}

}
