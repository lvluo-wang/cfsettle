package com.upg.ucars.basesystem.acctrecord.runtime;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.upg.ucars.framework.base.BaseDAO;
import com.upg.ucars.mapping.basesystem.acctrecord.AcctRecordFlow;
import com.upg.ucars.mapping.basesystem.acctrecord.AcctRecordInfo;
/**
 * TODO 
 *
 * @author shentuwy
 */
public class AcctRecordInfoDAO extends BaseDAO{
	
	public Long addFlow(AcctRecordFlow flow){		
		Long id = (Long)this.getHibernateTemplate().save(flow);
		return id;
	}
	
	public void addInfo(AcctRecordInfo info){		
		this.getHibernateTemplate().save(info);
		
	}
	
	public AcctRecordFlow getAcctRecordFlow(Long id){		
		return (AcctRecordFlow)this.getHibernateTemplate().get(AcctRecordFlow.class, id);
	}
	
	/**
	 * 获取同一流水号下的分录实体流水信息列表 
	 *
	 * @param acctFlowId
	 * @return
	 */
	public List<AcctRecordFlow> queryAcctRecordFlowByAcctFlowId(Long acctFlowId){
		
		String hql = "from AcctRecordFlow o where o.acctFlowId=? ";
		
		return this.getHibernateTemplate().find(hql, acctFlowId);
		
	}
	
	/**
	 * 获取实体流水信息对应的分录信息
	 * @param flowId
	 * @return
	 */
	public List<AcctRecordInfo> queryAcctRecordInfoByTranFlowId(Long tranFlowId){
		
		String hql = "from AcctRecordInfo o where o.flowId=? order by o.rowNo ";
		return this.getHibernateTemplate().find(hql, tranFlowId);
		
	}
	
	public void deleteRecordInfoByFlow(final Long acctFlowId){
		
		this.getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				//删除分录信息
				String hql = "delete from ACCT_RECORD_INFO where FLOW_ID in (select id from ACCT_RECORD_FLOW where ACCT_FLOW_ID=:acctFlowId)";
				session.createSQLQuery(hql).setParameter("acctFlowId", acctFlowId).executeUpdate();
				//删除分录流水
				hql = "delete from ACCT_RECORD_FLOW where ACCT_FLOW_ID=:acctFlowId";
				session.createSQLQuery(hql).setParameter("acctFlowId", acctFlowId).executeUpdate();
				
				return null;
			}
		});
	}

	@Override
	public Class getEntityClass() {
		
		return AcctRecordInfo.class;
	}
	
	

}
