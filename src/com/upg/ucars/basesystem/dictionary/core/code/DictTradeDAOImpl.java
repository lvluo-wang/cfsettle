/*
 * 源程序名称: DictTradeDAOImpl.java 
 * 软件著作权: 投融长富金融服务集团有限公司 版权所有
 * 系统名称: UPG业务服务平台(UBSP)
 * 模块名称：数据字典
 * 
 */

package com.upg.ucars.basesystem.dictionary.core.code;

import java.util.List;

import com.upg.ucars.framework.annotation.Dao;
import com.upg.ucars.framework.base.BaseDAO;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.QueryCondition;
import com.upg.ucars.mapping.basesystem.dictionary.DictTrade;
import com.upg.ucars.model.ConditionBean;
import com.upg.ucars.model.OrderBean;

@Dao
public class DictTradeDAOImpl extends BaseDAO<DictTrade, Long> implements IDictTradeDAO{
   
	public List<DictTrade> findTradeByParentid(Long pid)
	{
		StringBuffer hql = new StringBuffer();
		hql.append("from DictTrade t where t.pid=? and t.status='1'");
		 return super.find(hql.toString(), pid);
	}
	
	public DictTrade findTradeByCode(String code)
	{
		StringBuffer hql = new StringBuffer();
		hql.append("from DictTrade t where t.code=? and t.status='1'");
		List<DictTrade> trades=super.find(hql.toString(), code);
		if(trades!=null&&trades.size()>0){
			return trades.get(0);
		}
		return null;
	}
	
	
	public List<DictTrade> queryTrade(DictTrade trade ,Page page)
	{
		//查询条件
		QueryCondition qc = new QueryCondition();
		//Hql
		StringBuffer hql = new StringBuffer();
		hql.append("from DictTrade t");
		//排序对象
		OrderBean ob = new OrderBean("t.id", true);
		//拼装查询条件
		qc.setHql(hql.toString());
		if (trade != null) {
			if (trade.getTradeNameCn() != null) {
				ConditionBean cb = new ConditionBean("t.tradeNameCn", ConditionBean.LIKE, trade.getTradeNameCn());	
				qc.addCondition(cb);
			}
			if (trade.getPid() != null) {
				ConditionBean cbpid = new ConditionBean("t.pid", ConditionBean.EQUAL, trade.getPid());
				qc.addCondition(cbpid);
			}

		}else
		{
				ConditionBean cbpid = new ConditionBean("t.pid", ConditionBean.IS_NULL,null);
				qc.addCondition(cbpid);
	
		}
		ConditionBean cb1 = new ConditionBean("t.status", ConditionBean.EQUAL, "1");
		

		qc.addCondition(cb1);
		qc.addOrder(ob);

		return super.queryByCondition(qc, page);
	}
}
