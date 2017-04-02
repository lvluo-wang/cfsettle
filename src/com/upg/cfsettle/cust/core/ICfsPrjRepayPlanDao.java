package com.upg.cfsettle.cust.core;

import com.upg.cfsettle.mapping.prj.CfsPrjRepayPlan;
import com.upg.ucars.framework.base.IBaseDAO;

import java.util.List;

/**
 * Created by zuobaoshi on 2017/4/2.
 */
public interface ICfsPrjRepayPlanDao extends IBaseDAO<CfsPrjRepayPlan,Long> {

    /**
     * 根据项目id查询未还款的还款计划
     * @param prjId
     * @return
     */
    List<CfsPrjRepayPlan> findNotPaidOffByPrjId( Long prjId);

    /**
     * 根据项目id查询还款计划
     * @param prjId
     * @return
     */
    List<CfsPrjRepayPlan> findByPrjId(Long prjId);
}
