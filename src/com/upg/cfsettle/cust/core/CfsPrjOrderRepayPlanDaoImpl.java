package com.upg.cfsettle.cust.core;

import com.upg.cfsettle.mapping.prj.CfsPrjOrderRepayPlan;
import com.upg.ucars.framework.annotation.Dao;
import com.upg.ucars.framework.base.SysBaseDao;

import java.util.List;

/**
 * Created by zuobaoshi on 2017/4/2.
 */
@Dao
public class CfsPrjOrderRepayPlanDaoImpl extends SysBaseDao<CfsPrjOrderRepayPlan,Long> implements ICfsPrjOrderRepayPlanDao {


    @Override
    public List<CfsPrjOrderRepayPlan> findNotPaidOff(String hql) {
        return null;
    }
}
