package com.upg.cfsettle.cust.core;

import com.upg.cfsettle.mapping.prj.CfsPrjRepayPlan;
import com.upg.ucars.framework.annotation.Dao;
import com.upg.ucars.framework.base.SysBaseDao;

import java.util.Collections;
import java.util.List;

/**
 * Created by zuobaoshi on 2017/4/2.
 */
@Dao
public class CfsPrjRepayPlanDaoImpl extends SysBaseDao<CfsPrjRepayPlan,Long> implements ICfsPrjRepayPlanDao {

    @Override
    public List<CfsPrjRepayPlan> findByPrjIdAndStatus(Long prjId,Byte status) {
        String hql = "from CfsPrjRepayPlan plan where " +
                "plan.prjId =? and plan.status=? and plan.repayPeriods <> 0 order by plan.repayPeriods asc";
        List<CfsPrjRepayPlan> prjRepayPlanList = this.find(hql,new Object[]{prjId,status});
        if(prjRepayPlanList != null && prjRepayPlanList.size() >0){
            return prjRepayPlanList;
        }
        return Collections.emptyList();
    }

    @Override
    public List<CfsPrjRepayPlan> findNoRaisePlanByPrjId(Long prjId) {
        String hql = "from CfsPrjRepayPlan plan where " +
                "plan.prjId =? and plan.repayPeriods <> 0 order by plan.repayPeriods asc";
        List<CfsPrjRepayPlan> prjRepayPlanList = this.find(hql,prjId);
        if(prjRepayPlanList != null && prjRepayPlanList.size() >0){
            return prjRepayPlanList;
        }
        return Collections.emptyList();
    }

}
