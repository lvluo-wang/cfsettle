package com.upg.finance.log.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.upg.finance.mapping.local.FinanceCallLog;
import com.upg.ucars.framework.annotation.Dao;
import com.upg.ucars.framework.base.BaseDAO;

@Dao
public class FinanceCallLogDaoImpl extends BaseDAO<FinanceCallLog, Long> implements IFinanceCallLogDao {

	@Override
	public FinanceCallLog getByBusinessIdentifier(String businessIdentifier) {
		FinanceCallLog result = null;
		String hql = "from FinanceCallLog where businessIdentifier=?";
		List<FinanceCallLog> list = find(hql, new Object[] { businessIdentifier });
		if (list != null && !list.isEmpty()) {
			result = list.get(0);
		}
		return result;
	}

	@Override
	public List<FinanceCallLog> findByStatuses(String businessIdentifier, List<String> status) {
		List<FinanceCallLog> result = null;
		if (status != null && !status.isEmpty()) {
			String hql = "from FinanceCallLog where businessIdentifier = :businessIdentifier and status in (:status) ";
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("businessIdentifier", businessIdentifier);
			param.put("status", status);
			result = queryByParam(hql, param, null);
		}
		return result;
	}
}
