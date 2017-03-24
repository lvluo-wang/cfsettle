package com.upg.ucars.framework.bpm.assist.service;

import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springmodules.workflow.jbpm31.JbpmTemplate;

import com.upg.ucars.constant.BeanNameConstants;
import com.upg.ucars.framework.bpm.assist.dao.IBpmDataAssistDao;
import com.upg.ucars.framework.exception.ExceptionManager;
import com.upg.ucars.framework.exception.ServiceException;
import com.upg.ucars.util.LogUtil;
import com.upg.ucars.util.SourceTemplate;

/** 
 * 流程数据清理服务
 *
 * @author yangjun (mailto:yangjun@leadmind.com.cn)
 */

public class BpmDataClearServiceImpl implements IBpmDataClearService {
	
	private JbpmTemplate jbpmTemplate;
	private IBpmDataAssistDao bpmDataDao;

	public JbpmTemplate getJbpmTemplate() {
		return jbpmTemplate;
	}

	public void setJbpmTemplate(JbpmTemplate jbpmTemplate) {
		this.jbpmTemplate = jbpmTemplate;
	}

	public IBpmDataAssistDao getBpmDataDao() {
		return bpmDataDao;
	}

	public void setBpmDataDao(IBpmDataAssistDao bpmDataDao) {
		this.bpmDataDao = bpmDataDao;
	}

	
	public Long[] clearEndProcessInstanceData(Date endDate) {
		
		Long allNum = this.queryEndProcessInstanceCountByDate(endDate);
		Long num = 100L;//第次删除100笔
		long clearNum = 0;
		long errNum = 0;
		
		IBpmDataClearService service = SourceTemplate.getBean(IBpmDataClearService.class,BeanNameConstants.BPM_DATA_CLEAR_SERVICE);
		
		for(int i=0; i*num.longValue() < allNum.longValue(); i++){
			try{
				
				List<Long> list = this.getBpmDataDao().queryEndProcessInstanceIdsByDate(endDate,errNum, num);
				int cn = service.clearEndProcessInstance(list);
				clearNum += cn;
			}catch (Exception e) {
				errNum += num.longValue();
				LogUtil.getExceptionLog().error(e);				
			}
			
		}
		
		return new Long[]{allNum, clearNum};
		
		
	}
	
	
	public int clearEndProcessInstance(List<Long> piList) {		
		
		try{
			this.backupPiData(piList);			
			
		}catch (Exception e) {
			Logger.getLogger(this.getClass()).error("备份待清理的流程数据时产生异常", e);
			throw ExceptionManager.getException(ServiceException.class, "DAO_0002");
		}		
		
		/*try{
			for (Iterator<Long> iterator = piList.iterator(); iterator.hasNext();) {
				final Long pid = iterator.next();
				
				//删除jbpm流程实例
				this.getJbpmTemplate().execute(
						
						new JbpmCallback() {

							public Object doInJbpm(JbpmContext context)
									throws JbpmException {
								context.getGraphSession().deleteProcessInstance(pid);
								return null;
							}

				});
			}
			
			
		}catch (Exception e) {
			Logger.getLogger(this.getClass()).error("清理的流程数据时产生异常", e);
			throw ExceptionManager.getException(ServiceException.class, "DAO_0002");
		}	*/	
		
		return piList.size();
	}
	
	/**
	 * 备份流程数据
	 * @param processInstances
	 */
	private void backupPiData(final List<Long> processInstances){
		/**
		 * 
--Delete from JBPM_LOG where TOKEN_ in ();
--Delete from JBPM_VARIABLEINSTANCE where PROCESSINSTANCE_ in ();
--Delete from JBPM_MODULEINSTANCE where PROCESSINSTANCE_ in ();
--Delete from JBPM_TASKACTORPOOL where TASKINSTANCE_ in ();
--Delete from JBPM_TOKENVARIABLEMAP where TOKEN_ in ();
--Delete from JBPM_POOLEDACTOR where ID_ in ();
--Delete from JBPM_TASKINSTANCE where PROCINST_ in ();
--Delete from JBPM_TOKEN where PROCESSINSTANCE_ in ();
--Delete from JBPM_PROCESSINSTANCE where id_ in ();

		 */
		if (processInstances.isEmpty())
			return;
		
		StringBuffer sb = new StringBuffer();
		Iterator<Long> piIt = processInstances.iterator();
		while (piIt.hasNext()){
			Long pi = piIt.next();			
			sb.append(pi+",");
		}
		
		sb.append("-1");
		// 1,2,..,-1
		final String pisStr = sb.toString();
		
				
		this.getJbpmTemplate().getHibernateTemplate().execute(new HibernateCallback(){				

			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				String queryTokenSql = "select id_ from JBPM_TOKEN where PROCESSINSTANCE_ in ("+pisStr+")";
				String queryTaskSql = "select id_ from JBPM_TASKINSTANCE where PROCINST_ in ("+pisStr+")";
				//String queryActorPoolSql = "select POOLEDACTOR_ from JBPM_TASKACTORPOOL where TASKINSTANCE_ in ("+queryTaskSql+")";
				//备份参与者		
				//session.createSQLQuery("insert into ZJBPM_POOLEDACTOR (select * FROM JBPM_POOLEDACTOR where id_ in ("+queryActorPoolSql+"))")
					//.executeUpdate();
				session.createSQLQuery("insert into ZJBPM_TASKACTOR (select * FROM JBPM_TASKACTOR where TI_ID in ("+queryTaskSql+"))")
					.executeUpdate();
				
				//备份参与者池				
				//session.createSQLQuery("insert into ZJBPM_TASKACTORPOOL (select * FROM JBPM_TASKACTORPOOL where TASKINSTANCE_ in ("+queryTaskSql+"))")
					//.executeUpdate();				
				//备份变量map
				session.createSQLQuery("insert into ZJBPM_TOKENVARIABLEMAP (select * FROM JBPM_TOKENVARIABLEMAP where TOKEN_ in ("+queryTokenSql+"))")
					.executeUpdate();
				//备份变量
				session.createSQLQuery("insert into ZJBPM_VARIABLEINSTANCE (select * FROM JBPM_VARIABLEINSTANCE where PROCESSINSTANCE_ in ("+pisStr+"))")
				.executeUpdate();
				//备份日志
				session.createSQLQuery("insert into ZJBPM_LOG (select * FROM JBPM_LOG where TOKEN_ in ("+queryTokenSql+"))")
				.executeUpdate();
				//备份模块实例
				session.createSQLQuery("insert into ZJBPM_MODULEINSTANCE (select * FROM JBPM_MODULEINSTANCE where PROCESSINSTANCE_ in ("+pisStr+"))")
				.executeUpdate();
				//备份任务实例
				session.createSQLQuery("insert into ZJBPM_TASKINSTANCE (select * FROM JBPM_TASKINSTANCE where PROCINST_ in ("+pisStr+"))")
				.executeUpdate();
				//备份令牌
				session.createSQLQuery("insert into ZJBPM_TOKEN (select * FROM JBPM_TOKEN where PROCESSINSTANCE_ in ("+pisStr+"))")
				.executeUpdate();
				//备份流程实例
				session.createSQLQuery("insert into ZJBPM_PROCESSINSTANCE (select * FROM JBPM_PROCESSINSTANCE where ID_ in ("+pisStr+"))")
				.executeUpdate();
				
				
				
				session.createSQLQuery("Delete from JBPM_LOG where TOKEN_ in ("+queryTokenSql+")")
					.executeUpdate();
				session.createSQLQuery("Delete from JBPM_VARIABLEINSTANCE where PROCESSINSTANCE_ in ("+pisStr+")")
					.executeUpdate();
				session.createSQLQuery("Delete from JBPM_MODULEINSTANCE where PROCESSINSTANCE_ in ("+pisStr+")")
					.executeUpdate();
				session.createSQLQuery("Delete from JBPM_TASKACTORPOOL where TASKINSTANCE_ in ("+queryTaskSql+")")
					.executeUpdate();
				session.createSQLQuery("Delete from JBPM_TOKENVARIABLEMAP where TOKEN_ in ("+queryTokenSql+")")
					.executeUpdate();
				session.createSQLQuery("Delete from ZJBPM_TASKACTOR  where TI_ID in ("+queryTaskSql+")")
					.executeUpdate();
				//session.createSQLQuery("Delete from JBPM_POOLEDACTOR where ID_ in ()")
					//.executeUpdate();
				session.createSQLQuery("Delete from JBPM_TASKINSTANCE where PROCINST_ in ("+pisStr+")")
					.executeUpdate();
				session.createSQLQuery("update JBPM_PROCESSINSTANCE set roottoken_ = null where id_ in ("+pisStr+")")
					.executeUpdate();
				session.createSQLQuery("Delete from JBPM_TOKEN where PROCESSINSTANCE_ in ("+pisStr+")")
					.executeUpdate();
				session.createSQLQuery("Delete from JBPM_PROCESSINSTANCE where id_ in ("+pisStr+")")
					.executeUpdate();
				
				
			
			
				return null;
				
			}});
				
	}	

	public Long queryEndProcessInstanceCountByDate(Date endDate) {

		return this.getBpmDataDao().queryEndProcessInstanceCountByDate(endDate);
		

	}

	public Long queryProcessInstanceCount() {

		return this.getBpmDataDao().queryProcessInstanceCount();

	}

	public Long queryEndProcessInstanceCount() {	
		return this.getBpmDataDao().queryEndProcessInstanceCount();

	}

	
	public Long queryUnEndProcessInstanceCount() {

		return this.getBpmDataDao().queryUnEndProcessInstanceCount();
	}

}

 