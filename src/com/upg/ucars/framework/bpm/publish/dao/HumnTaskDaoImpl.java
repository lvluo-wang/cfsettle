package com.upg.ucars.framework.bpm.publish.dao;

import java.util.List;
import java.util.Map;

import com.upg.ucars.bpm.core.TaskSearchBean;
import com.upg.ucars.framework.base.BaseDAO;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.SessionTool;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.mapping.framework.bpm.HumnTask;
import com.upg.ucars.model.security.UserLogonInfo;
import com.upg.ucars.util.SQLCreater;
import com.upg.ucars.util.StringUtil;

public class HumnTaskDaoImpl extends BaseDAO<HumnTask, Long> implements
		IHunmTaskDao {

	@Override
	public Class getEntityClass() {

		return HumnTask.class;
	}

	public void saveHumnTasks(List<HumnTask> humnTasks) throws DAOException {
		this.getHibernateTemplate().saveOrUpdateAll(humnTasks);
	}

	public List<HumnTask> getHumnTasks() throws DAOException {
		String sql = "from HumnTask";
		return this.getHibernateTemplate().find(sql);
	}

	public List getHumnTasksByProcId(Long procId) throws DAOException {
		String sql = "from HumnTask h where h.procId=?";
		return getHibernateTemplate().find(sql, procId);
	}

	/**
	 * 根据流程名取出所有HumnTask
	 */
	public List getTaskNameByProcId(Long procId) throws DAOException {
		String sql = "select h.taskName from HumnTask h where h.procId=?";
		return getHibernateTemplate().find(sql, procId);
	}

	/**
	 * 根据流程ID和任务名称取得任务信息
	 * 
	 * @param prodId
	 *            流程ID
	 * @param taskName
	 *            任务名称
	 * @return
	 * @throws DAOException
	 */
	public HumnTask getHumnTaskByProcIdAndTaskName(Long prodId, String taskName)
			throws DAOException {
		String sql = "from HumnTask h where h.procId=? and h.taskName=?";
		List humnTaskList = getHibernateTemplate().find(sql,
				new Object[] { prodId, taskName });

		HumnTask humnTask = null;
		if (null != humnTaskList && humnTaskList.size() > 0) {
			humnTask = (HumnTask) humnTaskList.get(0);
		}
		return humnTask;
	}

	public String getFuncPath(String funcId) {
		String hql = "select url from Sysfunc o where o.funcId=?";
		List list = this.getHibernateTemplate().find(hql, funcId);
		if (list.isEmpty())
			return null;
		else
			return (String) list.get(0);
	}

	public HumnTask getHumnTaskByProcessAndTaskName(String processName,
			String taskName) {
		HumnTask humnTask = null;
		String hql = "select h from HumnTask h,ProcessDef p where h.procId=p.id and p.procName=? and h.taskName=?";
		List list = this.getHibernateTemplate().find(hql,
				new Object[] { processName, taskName });
		if (list != null && list.size() == 1) {
			humnTask = (HumnTask) list.get(0);
		}
		return humnTask;
	}

	public boolean taskHasActr(Long brchId, String processName, String taskName) {
		boolean ret = false;
		String hql = "select count(*) from HumnTask h,ProcessDef p,HumnTaskActr a where h.procId=p.id and h.id=a.taskId"
				+ " and a.brchId=? and p.procName=? and h.taskName=? ";
		List<?> list = this.getHibernateTemplate().find(hql,
				new Object[] { brchId, processName, taskName });
		ret = ((Number) list.get(0)).longValue() > 0;
		return ret;
	}

	@Override
	public List<Map<String, Object>> countTask(String roleName, Page pg) {
		UserLogonInfo uol =  SessionTool.getUserLogonInfo();
		SQLCreater sql = new SQLCreater(
				"select ROLE_ID, COUNT(*) PROCESSING, SUM(NVL2(ACTORID_ ,1,0 )) GET, SUM( case when CREATE_ + 0 < sysdate and CREATE_ + 1 >= sysdate then 1 else 0 end ) ONE_DAY, SUM( case when CREATE_ + 1 < sysdate and CREATE_ + 2 >= sysdate then 1 else 0 end ) TWO_DAY, SUM( case when CREATE_ + 2 < sysdate and CREATE_ + 3 >= sysdate then 1 else 0 end ) THREE_DAY, SUM( case when CREATE_ + 3 < sysdate and CREATE_ + 7 >= sysdate then 1 else 0 end ) SEVEN_DAY, SUM( case when CREATE_ + 7 < sysdate then 1 else 0 end ) AFTER_SEVEN_DAY FROM ( SELECT DISTINCT TI.ID_, ti.create_, TI.ACTORID_, HTA.ACTR_ROLE_ID role_id FROM PROCESS_DEF DEF, HUMN_TASK HT, HUMN_TASK_ACTR HTA, JBPM_TASKINSTANCE TI, role rl, branch bc, sys_instance_business ib WHERE ",
				true);
		sql.and("def.id = ht.proc_id", true);
		sql.and("HT.id = HTA.TASK_ID", true);
		sql.and("HT.TASK_NAME =TI.NAME_", true);
		sql.and("rl.role_id =hta.actr_role_id", true);
		sql.and("DEF.PROC_NAME_KEY = TI.PROCNAMEKEY_", true);
		sql.and("ti.end_ is null", true);
		sql.and("bc.brch_id = rl.brch_id", true);
		sql.and("bc.mi_no=ib.mi_no", true);
		sql.and("ib.mi_no=:miNo", "miNo",uol.getMiNo(),true);
		sql.and("ib.instance_id=ti.procinst_", true);
		sql.and("rl.role_name like :roleName  ", "roleName","%"+roleName+"%",!StringUtil.isEmpty(roleName));
		sql.addExpression("", ")", true);
		sql.addExpression("GROUP BY", "role_id", true);
		return this.getMapListByStanderdSQL(sql.getSql(),sql.getParameterMap(), pg);
	}

	@Override
	public List<Map<String, Object>> getProcessDetail(TaskSearchBean taskSearchBean, int countType, Page page) {
		UserLogonInfo uol =  SessionTool.getUserLogonInfo();
		SQLCreater sql = new SQLCreater("select distinct IB.COL9, IB.COL1,IB.PROCESS_CN_NAME,TI.DESCRIPTION_,IB.CREATE_TIME,TI.CREATE_, IB.ENTITY_ID, IB.PROD_NO, TI.ACTORID_ FROM PROCESS_DEF DEF,HUMN_TASK HT,HUMN_TASK_ACTR HTA,JBPM_TASKINSTANCE TI,sys_instance_Business ib,role rl WHERE ",true);
		sql.and("def.id =ht.proc_id ", true);
		sql.and("HT.id = HTA.TASK_ID", true);
		sql.and("HT.TASK_NAME =TI.NAME_", true);
		sql.and("rl.role_id =hta.actr_role_id", true);
		sql.and("DEF.PROC_NAME_KEY = TI.PROCNAMEKEY_", true);
		sql.and("IB.INSTANCE_ID = TI.PROCINST_", true);
		sql.and("ti.end_ is null", true);
		sql.and("rl.role_id=:roleId", "roleId",taskSearchBean.getRoleId(),true);
		sql.and("IB.COL1 like :custName ", "custName","%"+taskSearchBean.getCol1()+"%",!StringUtil.isEmpty(taskSearchBean.getCol1()));
		sql.and("IB.COL9 like :projectNo ", "projectNo","%"+taskSearchBean.getCol9()+"%",!StringUtil.isEmpty(taskSearchBean.getCol9()));
		sql.and("IB.MI_NO=:miNo","miNo",uol.getMiNo(),true);
		switch(countType){
			case 2:	sql.and("TI.ACTORID_ is not null", true);break;
			case 3: sql.and("CREATE_ + 0 < sysdate and CREATE_ + 1 >= sysdate", true);break;
			case 4: sql.and("CREATE_ + 1 < sysdate and CREATE_ + 2 >= sysdate", true);break;
			case 5: sql.and("CREATE_ + 2 < sysdate and CREATE_ + 3 >= sysdate", true);break;
			case 6: sql.and("CREATE_ + 3 < sysdate and CREATE_ + 7 >= sysdate", true);break;
			case 7: sql.and("CREATE_ + 7 < sysdate", true);break;
		}
		return this.getMapListByStanderdSQL(sql.getSql(),sql.getParameterMap(), page);
	}
	
}
