package com.upg.cfsettle.cust.core;

import com.upg.cfsettle.mapping.prj.CfsPrjRepayPlan;
import com.upg.ucars.framework.base.IBaseDAO;

import java.util.List;

/**
 * Created by zuobaoshi on 2017/4/2.
 */
public interface ICfsPrjRepayPlanDao extends IBaseDAO<CfsPrjRepayPlan,Long> {

    /**
     * 根据项目id和状态查询的还款计划（不包含募集期还款计划）
     * @param prjId
     * @return
     */
    List<CfsPrjRepayPlan> findByPrjIdAndStatus( Long prjId,Byte status);

    /**
     * 根据项目id查询所有还款计划（不包含募集期还款计划）
     * @param prjId
     * @return
     */
    List<CfsPrjRepayPlan> findNoRaisePlanByPrjId(Long prjId);
}
