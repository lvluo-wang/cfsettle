package com.upg.cfsettle.cust.core;

import com.upg.cfsettle.mapping.prj.CfsPrjRepayPlan;
import com.upg.ucars.framework.annotation.Dao;
import com.upg.ucars.framework.base.SysBaseDao;

import java.util.List;

/**
 * Created by zuobaoshi on 2017/4/2.
 */
@Dao
public class CfsPrjRepayPlanDaoImpl extends SysBaseDao<CfsPrjRepayPlan,Long> implements ICfsPrjRepayPlanDao {

    @Override
    public List<CfsPrjRepayPlan> findNotPaidOffByPrjId(Long prjId) {
        String hql = "from CfsPrjRepayPlan plan where " +
                "plan.prjId =? and plan.status=1 and plan.repay_periods <> 0 order by plan.repay_periods asc";
        return this.find(hql,prjId);
    }

    @Override
    public List<CfsPrjRepayPlan> findByPrjId(Long prjId) {
        String hql = "from CfsPrjRepayPlan plan where " +
                "plan.prjId =? and plan.repay_periods <> 0 order by plan.repay_periods asc";
        return this.find(hql,prjId);
    }

}
