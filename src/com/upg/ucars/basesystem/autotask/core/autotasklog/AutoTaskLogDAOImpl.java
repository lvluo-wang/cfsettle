package com.upg.ucars.basesystem.autotask.core.autotasklog;

import java.util.ArrayList;
import java.util.List;

import com.upg.ucars.framework.base.BaseDAO;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.mapping.basesystem.autotask.AutoTaskLog;
/**
 * TODO 
 *
 * @author shentuwy
 */
public class AutoTaskLogDAOImpl extends BaseDAO<AutoTaskLog, Long> implements IAutoTaskLogDAO{
	
	public void addAutoTaskLog(AutoTaskLog autoTask) throws DAOException {
		this.getHibernateTemplate().save(autoTask);
	}

	public void delById(Long id) throws DAOException {
		AutoTaskLog obj=this.getAutoTaskLog(id);
		this.getHibernateTemplate().delete(obj);
	}

	public void delAutoTaskLog(AutoTaskLog autoTask) throws DAOException {
		this.getHibernateTemplate().delete(autoTask);
	}

	public List getBySearchbean(Page page,LogSearchBean bean) throws DAOException {
		List result=new ArrayList();
		/*try{
			QueryCondition qc=new QueryCondition();	
			//增加查询条件,用于列表页面的查询功能  add by changxl 20101015
			String name="";
			if(null != bean.getName() && !"".equals(bean.getName())){
				name = bean.getName();
				bean.setName("%"+name+"%");
				bean.addProperty2TableObj("name", "task");
				bean.addSpecialOpertion("name",BaseSearchBean.LIKE);
			}
			if(null != bean.getRunDate() && !"".equals(bean.getRunDate())){
				bean.addProperty2TableObj("runDate", "logSearch");
				bean.addSpecialOpertion("runDate", BaseSearchBean.DEFAULT_EQUAL);
			}
			bean.setDfaultSrchTbAliasName("logSearch");
			qc.initFromSearchBean(bean);
			page.setKeyQueryId("logSearch.id");//用于分页时,计算总数拼Hql用	
			String HQL="select logSearch, task from AutoTask task, AutoTaskLog logSearch where task.id=logSearch.taskId and logSearch.memberBrchId is null order by logSearch.id desc";
			result=super.getListByPage(page, qc, HQL);
			if(null != bean.getName() && !"".equals(bean.getName())){
				bean.setName(name);
			}
		}catch(Exception e){
			throw new DAOException(e);
		}*/
		return result;
	}

	public AutoTaskLog getAutoTaskLog(Long id) throws DAOException {
		return (AutoTaskLog) this.getHibernateTemplate().get(AutoTaskLog.class, id);
//		return (AutoTaskLog) this.getHibernateTemplate().load(AutoTaskLog.class, id);
	}

	public List getAutoTaskLogs() throws DAOException {
		String sql="from AutoTaskLog	";
		return this.getHibernateTemplate().find(sql);
	}

	public void updateAutoTaskLog(AutoTaskLog autoTask) throws DAOException {
		this.getHibernateTemplate().update(autoTask);
	}

	public List getSubLog(Page page,LogSearchBean searchBean) throws DAOException {
		List logList = new ArrayList();
		/*try{
			searchBean.setDfaultSrchTbAliasName("log");
			QueryCondition qc = new QueryCondition();
			qc.initFromSearchBean(searchBean);
			page.setKeyQueryId("log.id");
			String HQL = "select log,branch from AutoTaskLog log , Branch branch where log.memberBrchId is not null and log.memberBrchId=branch.brchId order by log.id desc";
			logList = super.getListByPage(page, qc, HQL);
		}catch(Exception e){
			throw new DAOException(e);
		}*/
		return logList;
	}

	@Override
	public Class<AutoTaskLog> getEntityClass() {
		return AutoTaskLog.class;
	}
}
