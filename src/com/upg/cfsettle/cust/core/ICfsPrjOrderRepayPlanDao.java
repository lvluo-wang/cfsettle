package com.upg.cfsettle.cust.core;

import com.upg.cfsettle.mapping.prj.CfsPrjOrderRepayPlan;
import com.upg.ucars.framework.base.IBaseDAO;

import java.util.List;

/**
 * Created by zuobaoshi on 2017/4/2.
 */
public interface ICfsPrjOrderRepayPlanDao extends IBaseDAO<CfsPrjOrderRepayPlan,Long> {

    /**
     *
     * @param hql
     * @return
     */
    List<CfsPrjOrderRepayPlan> findNotPaidOff(String hql);



}
