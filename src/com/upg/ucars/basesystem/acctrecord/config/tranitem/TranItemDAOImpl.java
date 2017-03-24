/*
 * 源程序名称: TranItemDAOImpl.java
 * 软件著作权: 投融长富金融服务集团有限公司 版权所有
 * 系统名称: XXXX业务系统平台(UBSP)
 * 模块名称: 账务配置-交易项
 * 
 */
package com.upg.ucars.basesystem.acctrecord.config.tranitem;

import java.util.List;

import com.upg.ucars.framework.base.BaseDAO;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.QueryCondition;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.mapping.basesystem.acctrecord.TranItem;
import com.upg.ucars.model.ConditionBean;
import com.upg.ucars.model.OrderBean;

/**
 * 
 * 功能说明：交易项数据库操作DAO实现
 * @author shentuwy  
 * @date Jun 27, 2011 08:46:20 AM 
 *
 */
public class TranItemDAOImpl extends BaseDAO<TranItem, Long> implements ITranItemDAO{

	@Override
	public Class getEntityClass() {
		return TranItem.class;
	}

	public List queryTranItem(List<ConditionBean> cblist,
			List<OrderBean> oblist, Page page) throws DAOException {
		String hql = "select tranItem from TranItem tranItem, AcctTran acctTran where tranItem.tranId=acctTran.id";
		
		QueryCondition qc = new QueryCondition();
		qc.setHql(hql);
		qc.addConditionList(cblist);
		qc.addOrderList(oblist);
		return super.queryByCondition(qc, page);
	}
	
	public List<TranItem> queryTranItemByAcctItemId(Long acctItemId) throws DAOException {
		String hql = "from TranItem tranItem where tranItem.acctItemId=?";
		return super.find(hql, acctItemId);
	}

	public List<TranItem> queryTranItemByAcctTranId(Long acctTranId)
			throws DAOException {
		String hql = "from TranItem tranItem where tranItem.tranId=?";
		return super.find(hql, acctTranId);
	}

	public List<TranItem> queryTranItemByRecordBodyId(Long recordBodyId)
			throws DAOException {
		String hql = "from TranItem tranItem where tranItem.recordBodyId=?";
		return super.find(hql, recordBodyId);
	}

	public void deleteRowTranItem(Long tranId, String groupNo, int rowNo)
			throws DAOException {
		final String hql = "delete from TranItem where tranId=? and groupNo=? and rowNo=?";
		
		this.getHibernateTemplate().bulkUpdate(hql, new Object[]{tranId, groupNo, rowNo});
		
		final String hql2 = "update TranItem set rowNo=rowNo-1 where tranId=? and groupNo=? and rowNo>?";
		this.getHibernateTemplate().bulkUpdate(hql2, new Object[]{tranId, groupNo, rowNo});
		
		
	}

	public void exchangeRowIndex(Long tranId, String groupNo, int rowNo1,
			int rowNo2) throws DAOException {
		int tempRowNo = -1;
		
		final String hql = "update TranItem set rowNo=? where tranId=? and groupNo=? and rowNo=?";		
		this.getHibernateTemplate().bulkUpdate(hql, new Object[]{tempRowNo, tranId, groupNo, rowNo1});// rowNo1-->temp
		this.getHibernateTemplate().bulkUpdate(hql, new Object[]{rowNo1, tranId, groupNo, rowNo2});//rowNo2-->rowNo1
		this.getHibernateTemplate().bulkUpdate(hql, new Object[]{rowNo2, tranId, groupNo, tempRowNo});//temp-->rowNo2
		
		
	}

	public int getMaxRowIndex(Long tranId, String groupNo) throws DAOException {
		String hql = "select max(rowNo) from TranItem where tranId=? and groupNo=? ";
		List list = this.getHibernateTemplate().find(hql, new Object[]{tranId, groupNo});
		if (list.isEmpty()){
			return 0;
		}else{
			Number n = (Number)list.get(0);
			return n.intValue();
		}
		
		
	}

}
