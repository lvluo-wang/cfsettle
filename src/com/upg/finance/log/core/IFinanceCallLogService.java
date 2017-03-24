package com.upg.finance.log.core;

import java.util.List;

import com.upg.finance.mapping.local.FinanceCallLog;
import com.upg.ucars.framework.base.IBaseService;

public interface IFinanceCallLogService extends IBaseService {

	public void save(FinanceCallLog log);

	public void edit(FinanceCallLog log);

	public void saveOrEdit(FinanceCallLog log);

	// public void delete(FinanceCallLog log);

	public FinanceCallLog getFinanceCallLogById(Long id);

	public FinanceCallLog getByBusinessIdentifier(String businessIdentifier);

	public List<FinanceCallLog> findByStatuses(String businessIdentifier, List<String> status);

	// ========特殊用途，不要调用=================
	/**
	 * 设置log，此方法调用之后,后续一定要调用{@link #remove()}进行清除
	 * 
	 * <pre>
	 * 
	 * </pre>
	 * 
	 */
	public void set(FinanceCallLog log);
	
	public FinanceCallLog get();

	/**
	 * 移除log
	 */
	public void remove();

	// =============================================

}
