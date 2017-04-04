package com.upg.cfsettle.cust.core;

import com.upg.cfsettle.mapping.prj.CfsPrjRepayPlan;
import com.upg.ucars.framework.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by zuobaoshi on 2017/4/4.
 */
@Service
public class CfsPrjRepayPlanServiceImpl implements ICfsPrjRepayPlanService {

    @Autowired
    private ICfsPrjRepayPlanDao prjRepayPlanDao;

    @Override
    public Integer getTotalPeriodByPrjId(Long prjId) {
        List<CfsPrjRepayPlan> prjRepayPlanList = prjRepayPlanDao.findNoRaisePlanByPrjId(prjId);
        return prjRepayPlanList == null ? null : prjRepayPlanList.size();
    }
}
