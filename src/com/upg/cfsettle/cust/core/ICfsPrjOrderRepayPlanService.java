package com.upg.cfsettle.cust.core;

import com.upg.cfsettle.mapping.prj.CfsPrjOrderRepayPlan;
import com.upg.ucars.framework.base.IBaseService;

import java.util.List;

/**
 * Created by zuobaoshi on 2017/4/4.
 */
public interface ICfsPrjOrderRepayPlanService extends IBaseService {

    List<CfsPrjOrderRepayPlan> findByOrderIdAndType(Long prjOrderId,Byte ptype);


    //募集期利息
    CfsPrjOrderRepayPlan getRaiseOrderRepayPlan(Long prjOrderId);


	void addPrjOrderRepayPlan(CfsPrjOrderRepayPlan orderPlan);
}
