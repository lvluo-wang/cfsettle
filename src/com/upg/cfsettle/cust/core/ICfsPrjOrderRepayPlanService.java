package com.upg.cfsettle.cust.core;

import java.util.List;
import java.util.Map;

import com.upg.cfsettle.mapping.prj.CfsPrjOrderPaybackLog;
import com.upg.cfsettle.mapping.prj.CfsPrjOrderRepayPlan;
import com.upg.ucars.framework.base.IBaseService;
import com.upg.ucars.framework.base.Page;

/**
 * Created by zuobaoshi on 2017/4/4.
 */
public interface ICfsPrjOrderRepayPlanService extends IBaseService {

    List<CfsPrjOrderRepayPlan> findByOrderIdAndType(Long prjOrderId,Byte ptype);


    //募集期利息
    CfsPrjOrderRepayPlan getRaiseOrderRepayPlan(Long prjOrderId);


	void addPrjOrderRepayPlan(CfsPrjOrderRepayPlan orderPlan);

	/**
	 * 订单还款查询
	 * @author renzhuolun
	 * @date 2017年4月9日 下午5:48:31
	 * @param searchBean
	 * @param pg
	 * @return
	 */
	List<Map<String,Object>> findByCondition(CfsPrjOrderPaybackLog searchBean, Page page);
	
	/**
	 * 主键查询
	 * @author renzhuolun
	 * @date 2017年4月9日 下午8:26:41
	 * @param id
	 * @return
	 */
	CfsPrjOrderRepayPlan getprjOrderPlanById(Long id);
}
