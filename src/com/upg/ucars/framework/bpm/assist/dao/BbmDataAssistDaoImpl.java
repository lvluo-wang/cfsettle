/*******************************************************************************
 * Leadmind Technologies, Ltd.
 * All rights reserved.
 * 
 * Created on Nov 24, 2008
 *******************************************************************************/


package com.upg.ucars.framework.bpm.assist.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.jbpm.graph.exe.ProcessInstance;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.upg.ucars.framework.base.BaseDAO;
import com.upg.ucars.util.DateTimeUtil;

public class BbmDataAssistDaoImpl extends BaseDAO<ProcessInstance, Long> implements IBpmDataAssistDao {
		
		
	public List<Long> queryEndProcessInstanceIdsByDate(Date endDate,Long firstNum, Long maxNum) {	
		StringBuffer sqlBuffer = new StringBuffer();	      
		
		sqlBuffer.append("select pi.id  ");
		sqlBuffer.append(" from org.jbpm.graph.exe.ProcessInstance as pi  ");
		sqlBuffer.append(" where pi.end != null and pi.end < ?");
		sqlBuffer.append(" order by pi.end asc ");
		
		final String hql = sqlBuffer.toString();		
		//增加一天，保存查询的日期<=参数endDate
		final Date para1 = DateTimeUtil.getDate(endDate, 1);
		final int size = maxNum.intValue();
		final int firstRs = firstNum.intValue();
		
		return this.getHibernateTemplate().executeFind(new HibernateCallback(){

			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				try {
					Query sq = session.createQuery(hql);
					sq.setParameter(0, para1);
					sq.setMaxResults(size).setFirstResult(firstRs);				
					return sq.list();
				} catch (Exception e) {
					e.printStackTrace();
				}
				return new ArrayList(0);
				
			}
			
		});				
	}

	/**
	 * (non-Javadoc)
	 * @see com.leadmind.basesystem.bpm.assist.dao.IBpmDataAssistDao#queryEndProcessInstanceCountByDate(java.util.Date)
	 */
	public Long queryEndProcessInstanceCountByDate(Date endDate) {		
		StringBuffer sqlBuffer = new StringBuffer();		
		sqlBuffer.append("select count(pi.id)  ");
		sqlBuffer.append(" from org.jbpm.graph.exe.ProcessInstance as pi  ");
		sqlBuffer.append(" where pi.end != null and pi.end < ?");		
		final String hql = sqlBuffer.toString();	
		
		//增加一天，保存查询的日期<=参数endDate
		final Date para1 = DateTimeUtil.getDate(endDate, 1);
		
		List rsList = this.getHibernateTemplate().executeFind(new HibernateCallback(){

			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				try {
					Query sq = session.createQuery(hql);
					sq.setParameter(0, para1);					
					return  sq.list();
				} catch (Exception e) {
					e.printStackTrace();
				}
				return new ArrayList(0);
				
			}
			
		});	
		if (rsList.isEmpty())
			return 0L;
		else
			return (Long)rsList.get(0);
				
	}


	public Long queryProcessInstanceCount() {
		StringBuffer sqlBuffer = new StringBuffer();		
		sqlBuffer.append("select count(pi.id)  ");
		sqlBuffer.append(" from org.jbpm.graph.exe.ProcessInstance as pi  ");
		final String hql = sqlBuffer.toString();
		
		List rsList = this.getHibernateTemplate().executeFind(new HibernateCallback(){

			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				try {
					Query sq = session.createQuery(hql);	
					return  sq.list();
				} catch (Exception e) {
					e.printStackTrace();
				}
				return new ArrayList(0);
				
			}
			
		});	
		if (rsList.isEmpty())
			return 0L;
		else
			return (Long)rsList.get(0);
		
	}
	
	public Long queryEndProcessInstanceCount() {
		StringBuffer sqlBuffer = new StringBuffer();		
		sqlBuffer.append("select count(pi.id)  ");
		sqlBuffer.append(" from org.jbpm.graph.exe.ProcessInstance as pi  ");
		sqlBuffer.append(" where pi.end != null ");		
		final String hql = sqlBuffer.toString();	
		
		List rsList = this.getHibernateTemplate().executeFind(new HibernateCallback(){

			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				try {
					Query sq = session.createQuery(hql);	
					return  sq.list();
				} catch (Exception e) {
					e.printStackTrace();
				}
				return new ArrayList(0);
				
			}
			
		});	
		if (rsList.isEmpty())
			return 0L;
		else
			return (Long)rsList.get(0);
		
	}


	public Long queryUnEndProcessInstanceCount() {
		StringBuffer sqlBuffer = new StringBuffer();		
		sqlBuffer.append("select count(pi.id)  ");
		sqlBuffer.append(" from org.jbpm.graph.exe.ProcessInstance as pi  ");
		sqlBuffer.append(" where pi.end = null ");		
		final String hql = sqlBuffer.toString();	
		
		List rsList = this.getHibernateTemplate().executeFind(new HibernateCallback(){

			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				try {
					Query sq = session.createQuery(hql);	
					return  sq.list();
				} catch (Exception e) {
					e.printStackTrace();
				}
				return new ArrayList(0);
				
			}
			
		});	
		if (rsList.isEmpty())
			return 0L;
		else
			return (Long)rsList.get(0);
		
	}

	@Override
	public Class getEntityClass() {
		// TODO Auto-generated method stub
		return null;
	}


}
