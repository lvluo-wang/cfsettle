package com.upg.cfsettle.foundation.core;

import java.util.List;

import com.upg.ucars.framework.base.IBaseService;
import com.upg.ucars.framework.base.Page;
import com.upg.cfsettle.mapping.filcs.FiLcsBookingOrder;

/**
 * 理财师订单
 * @author renzhuolun
 * @date 2016年7月12日 上午10:36:25
 * @version <b>1.0.0</b>
 */
public interface IFiLcsBookingOrderService extends IBaseService {
	
	/**
	 * 条件查询
	 * @author renzhuolun
	 * @date 2016年7月12日 上午10:39:34
	 * @param searchBean
	 * @param page
	 * @return
	 */
	List<FiLcsBookingOrder> findByCondition(FiLcsBookingOrder searchBean, Page page);
}
