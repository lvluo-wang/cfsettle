package com.upg.ucars.framework.bpm.assign.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.upg.ucars.constant.ErrorCodeConst;
import com.upg.ucars.framework.base.BaseDAO;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.framework.exception.ExceptionManager;
import com.upg.ucars.framework.exception.ServiceException;
import com.upg.ucars.mapping.framework.bpm.HumnTaskActr;

public class HumanTaskActrDAOImpl extends BaseDAO<HumnTaskActr, Long> implements
		IHumanTaskActrDAO {

	@Override
	public Class getEntityClass() {
		return HumnTaskActr.class;
	}

	public List<String> getTaskActors(String processName, String taskName,
			Long brchId) throws DAOException {
		final String pName = processName;
		final String tName = taskName;
		final Long bId = brchId;
		List<String> userNoList = null;
		
		String actrSql = " from HUMN_TASK_ACTR a, HUMN_TASK t, PROCESS_DEF p "
			+ "where a.TASK_ID=t.id and t.PROC_ID=p.id and t.TASK_NAME=:taskName and p.PROC_NAME=:procName and a.BRCH_ID=:brchId";
		
		String userActorSql = "select a.ACTR_USER_ID " + actrSql + " and a.ACTR_USER_ID is not null";
		String roleActorSql = "select a.ACTR_BRCH_ID, a.ACTR_ROLE_ID " + actrSql + " and a.ACTR_ROLE_ID is not null";

		final StringBuffer baseSb = new StringBuffer();

		baseSb.append("select u.USER_NO from BUSER u where u.USER_NO in (");
		baseSb.append(" select u1.USER_NO from BUSER u1 where u1.SYS_USER_ID in ("+userActorSql+") union ");

		baseSb.append(" select u3.USER_NO from BUSER u3, RE_USER_ROLE ur, ROLE r where u3.SYS_USER_ID=ur.SYS_USER_ID and ur.ROLE_ID=r.ROLE_ID ");
		baseSb.append(" and (u3.BRCH_ID, ur.ROLE_ID) in ("+roleActorSql+")");
		baseSb.append(")");

		try {
			userNoList = (List<String>) this.getHibernateTemplate().execute(
					new HibernateCallback() {
						public Object doInHibernate(Session session)
								throws HibernateException, SQLException {							

							List userNos = session
									.createSQLQuery(baseSb.toString())
									.setParameter("taskName", tName)
									.setParameter("procName", pName)
									.setParameter("brchId", bId).list();

							return userNos;
						}

					});
		} catch (Exception e) {
			throw ExceptionManager.getException(DAOException.class, ErrorCodeConst.DB_COMMON_ERROR, new String[]{e.getMessage()});
		}
		return userNoList;
	}

	public List<HumnTaskActr> getHumnTaskActrs(String processName,
			String taskName, Long brchId) throws ServiceException {
		String hql = "select a from HumnTaskActr a,HumnTask t,ProcessDef p " +
				" where a.taskId=t.id and t.procId=p.id and t.taskName=? and p.procName=? and a.brchId=?";
		List<HumnTaskActr> list = this.find(hql, new Object[] {taskName,	processName, brchId });
		return list;
	}

	public List<HumnTaskActr> getHumnTaskActrs(Long taskId, Long brchId)	throws DAOException {
		String hql = "select a from HumnTaskActr a " +
		" where a.taskId=? and a.brchId=?";
		List<HumnTaskActr> list = this.find(hql, new Object[] {taskId, brchId });
		return list;
	}
	
	public void deleteHumnTaskActr(Long branchId,Long processDefId){
		String hql = "delete from HumnTaskActr a where a.brchId=? and a.taskId in (select t.id from HumnTask t where t.procId=?)";
		getHibernateTemplate().bulkUpdate(hql, branchId,processDefId);
	}

	public List<HumnTaskActr> getHumnTaskActr(Long branchId,Long processDefId){
		String hql = "select a from HumnTaskActr a,HumnTask t where a.taskId=t.id and a.brchId=? and t.procId=?";
		return getHibernateTemplate().find(hql, branchId,processDefId);
	}
	/**
	 * 获取授权的角色与用户
	 * 
	 * @param processName
	 * @param taskName
	 * @param brchId
	 * @return Object[List< roleId>, List< userId>]
	 * @throws DAOException
	 */
	/*public Object[] getRefIdsByBrchId(String processName, String taskName,
			Long brchId) throws DAOException {
		List roleIds = new ArrayList();
		List userIds = new ArrayList();

		String hql = "select a.actrRoleId, a.actrUserId  from HumnTaskActr a,HumnTask t,ProcessDef p "
				+ "where a.taskId=t.id and t.procId=p.id and t.taskName=? and p.processName=? and a.brchId=?";
		List<Object[]> list = this.find(hql, new Object[] { taskName,
				processName, brchId });

		for (int i = 0; i < list.size(); i++) {
			Object[] obj = list.get(i);

			Object roleId = obj[0];
			Object userId = obj[1];

			if (roleId != null)
				roleIds.add(roleId);
			if (userId != null)
				userIds.add(userId);
		}

		if (roleIds.size() < 1)
			roleIds.add(new Long(-1));

		if (userIds.size() < 1)
			userIds.add(new Long(-1));

		return new Object[] { roleIds, userIds };
	}*/
	
	

}
