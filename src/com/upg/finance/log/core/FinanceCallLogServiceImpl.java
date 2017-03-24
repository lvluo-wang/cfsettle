package com.upg.finance.log.core;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.upg.finance.mapping.local.FinanceCallLog;
import com.upg.ucars.framework.annotation.Service;
import com.upg.ucars.framework.base.BaseService;

@Service
public class FinanceCallLogServiceImpl extends BaseService implements IFinanceCallLogService {

	private static final ThreadLocal<FinanceCallLog>	FINANCE_CALL_LOG_CONTEXT	= new ThreadLocal<FinanceCallLog>();

	@Resource
	private IFinanceCallLogDao							financeCallLogDao;

	@Resource
	private JdbcTemplate								jdbcTemplate;

	@Override
	public void save(FinanceCallLog log) {
		financeCallLogDao.save(log);
	}

	@Override
	public void edit(FinanceCallLog log) {
		financeCallLogDao.update(log);
	}

	@Override
	public void saveOrEdit(FinanceCallLog log) {
		financeCallLogDao.saveOrUpdate(log);
	}

	@Override
	public FinanceCallLog getFinanceCallLogById(Long id) {
		return financeCallLogDao.get(id);
	}

	@Override
	public FinanceCallLog getByBusinessIdentifier(String businessIdentifier) {
		return financeCallLogDao.getByBusinessIdentifier(businessIdentifier);
	}

	@Override
	public List<FinanceCallLog> findByStatuses(String businessIdentifier, List<String> status) {
		return financeCallLogDao.findByStatuses(businessIdentifier, status);
	}

	// =======特殊用途，不要调用=================

	@Override
	public void set(FinanceCallLog log) {
		FINANCE_CALL_LOG_CONTEXT.set(log);
	}

	@Override
	public FinanceCallLog get() {
		return FINANCE_CALL_LOG_CONTEXT.get();
	}

	@Override
	public void remove() {
		FINANCE_CALL_LOG_CONTEXT.remove();
	}

	// ==========================

}
