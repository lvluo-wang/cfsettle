package com.upg.cfsettle.cust.core;

import java.util.List;
import java.util.Map;

import com.upg.cfsettle.mapping.prj.CfsPrjRepayPlan;
import com.upg.ucars.framework.base.IBaseDAO;
import com.upg.ucars.framework.base.Page;

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
    
    /**
     * 查询还款项目
     * @author renzl123
     * @date 2017年4月8日 上午10:33:26
     * @param searchBean
     * @param page
     * @return
     */
	List<Map<String, Object>> findByCondition(CfsPrjRepayPlan searchBean,Page page);
	
	/**
	 * 查询某一期的项目还款计划
	 * @author renzhuolun
	 * @date 2017年4月15日 下午7:24:03
	 * @param prjId
	 * @param repayPeriod
	 * @return
	 */
	CfsPrjRepayPlan getPrjPlanByPrjIdAndPeriod(Long prjId, Long repayPeriod);

	List<CfsPrjRepayPlan> getPrjPlanByPrjId(Long prjId);
}
