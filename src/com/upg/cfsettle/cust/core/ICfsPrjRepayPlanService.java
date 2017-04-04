package com.upg.cfsettle.cust.core;

import com.upg.ucars.framework.base.IBaseService;

/**
 * Created by zuobaoshi on 2017/4/4.
 */
public interface ICfsPrjRepayPlanService extends IBaseService {


    /**
     * 还款总期数
     * @param prjId
     * @return
     */
    Integer getTotalPeriodByPrjId(Long prjId);
}
