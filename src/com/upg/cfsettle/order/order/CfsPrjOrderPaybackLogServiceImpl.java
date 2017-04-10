package com.upg.cfsettle.order.order;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.upg.cfsettle.mapping.prj.CfsPrjOrderPaybackLog;
import com.upg.ucars.framework.annotation.Service;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.QueryCondition;
import com.upg.ucars.model.ConditionBean;
import com.upg.ucars.model.OrderBean;


@Service
public class CfsPrjOrderPaybackLogServiceImpl implements ICfsPrjOrderPaybackLogService {
	
	@Autowired
	private ICfsPrjOrderPaybackLogDao orderPaybackLogDao;

	@Override
	public List<CfsPrjOrderPaybackLog> findByOrderRepayPlanId(Long pkId, Page page) {
		CfsPrjOrderPaybackLog searchBean  = new CfsPrjOrderPaybackLog();
		searchBean.setPrjOrderRepayPlanId(pkId);
		return this.findByCondition(searchBean, page);
	}

	@Override
	public List<CfsPrjOrderPaybackLog> findByCondition(CfsPrjOrderPaybackLog searchBean, Page page) {
		String hql = "from CfsPrjOrderPaybackLog cfsPrjOrderPaybackLog";
		QueryCondition condition = new QueryCondition(hql);
		if (searchBean != null) {
			Long prjOrderRepayPlanId = searchBean.getPrjOrderRepayPlanId();
			if (prjOrderRepayPlanId != null) {
				condition.addCondition(new ConditionBean("cfsPrjOrderPaybackLog.prjOrderRepayPlanId", ConditionBean.EQUAL, prjOrderRepayPlanId));
			}
		}
		List<OrderBean> orderList = new ArrayList<OrderBean>();
		orderList.add(new OrderBean("cfsPrjOrderPaybackLog.paybackTimes", false));
		return orderPaybackLogDao.queryEntity( condition.getConditionList(),orderList, page);
	}
}
