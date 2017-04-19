package com.upg.cfsettle.cust.core;

import java.util.List;
import java.util.Map;

import com.upg.cfsettle.mapping.prj.CfsPrjRepayPlan;
import com.upg.ucars.framework.base.IBaseService;
import com.upg.ucars.framework.base.Page;

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

	void addPrjRepayPlan(CfsPrjRepayPlan plan);

	void updatePrjRepayPlan(CfsPrjRepayPlan plan);

	List<Map<String,Object>> findByCondition(CfsPrjRepayPlan searchBean, Page page);

	CfsPrjRepayPlan getPrjRepayPlanById(Long id);

	CfsPrjRepayPlan getPrjPlanByPrjIdAndPeriod(Long prjId, Long repayPeriod);

	List<CfsPrjRepayPlan> getPrjPlanByPrjId(Long prjId);
}
