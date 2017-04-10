package com.upg.cfsettle.prj.core;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.upg.cfsettle.mapping.prj.CfsPrjPaybackLog;
import com.upg.ucars.framework.base.IBaseDAO;
import com.upg.ucars.framework.base.Page;

public interface IPrjPaybackLogDao extends IBaseDAO<CfsPrjPaybackLog,Long> {

	List<CfsPrjPaybackLog> getByPrjId(Long prjId);

	List<Map<String, Object>> findByCondition(CfsPrjPaybackLog searchBean, Page page);

	List<Map<String, Object>> findByPrjPaybackLogByPrjId(Long prjId);

	/**
	 * 项目已还总额
	 * @param prjId
	 * @return
	 */
	public BigDecimal getPayBackTotalAmount(Long prjId);

	/**
	 * 项目应还总额
	 * @param prjId
	 * @return
	 */
	BigDecimal getPrjRepayTotalAmount(Long prjId);
}
