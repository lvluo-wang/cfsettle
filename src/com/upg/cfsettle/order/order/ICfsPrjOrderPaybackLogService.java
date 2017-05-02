package com.upg.cfsettle.order.order;

import java.util.List;

import com.upg.cfsettle.mapping.prj.CfsPrjOrderPaybackLog;
import com.upg.ucars.framework.base.IBaseService;
import com.upg.ucars.framework.base.Page;


public interface ICfsPrjOrderPaybackLogService extends IBaseService{

	List<CfsPrjOrderPaybackLog> findByOrderRepayPlanId(CfsPrjOrderPaybackLog searchBean, Page page);
	
	List<CfsPrjOrderPaybackLog> findByCondition(CfsPrjOrderPaybackLog searchBean,Page page);
	
	/**
	 * 创建订单还款日志
	 * @author renzhuolun
	 * @date 2017年4月11日 上午11:43:10
	 * @param orderPayLog
	 */
	void addOrderPaybackLog(CfsPrjOrderPaybackLog orderPayLog);
}
