package com.upg.cfsettle.foundation.core;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.upg.ucars.framework.annotation.Service;
import com.upg.ucars.framework.base.BaseService;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.QueryCondition;
import com.upg.ucars.model.ConditionBean;
import com.upg.cfsettle.mapping.filcs.FiLcsBookingOrder;

/**
 * 理财师订单
 * @author renzhuolun
 * @date 2016年7月12日 上午10:36:11
 * @version <b>1.0.0</b>
 */
@Service
public class FiLcsBookingOrderServiceImpl extends BaseService implements IFiLcsBookingOrderService{
	@Autowired
	private IFiLcsBookingOrderDao bookingOrderDao;

	@Override
	public List<FiLcsBookingOrder> findByCondition(FiLcsBookingOrder searchBean, Page page) {
		String hql = "from FiLcsBookingOrder fiLcsBookingOrder";
		QueryCondition condition = new QueryCondition(hql);
		if (searchBean != null) {
			Long fundId = searchBean.getFundId();
			if (fundId != null) {
				condition.addCondition(new ConditionBean("fiLcsBookingOrder.fundId", ConditionBean.EQUAL, fundId));
			}
		}
		return bookingOrderDao.queryEntity(condition.getConditionList(), page);
	}
}
