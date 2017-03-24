package com.upg.finance.log.core;

import java.util.List;

import com.upg.finance.mapping.local.FinanceCallLog;
import com.upg.ucars.framework.base.IBaseDAO;

public interface IFinanceCallLogDao extends IBaseDAO<FinanceCallLog, Long> {

	public FinanceCallLog getByBusinessIdentifier(String businessIdentifier);

	public List<FinanceCallLog> findByStatuses(String businessIdentifier, List<String> status);

}
