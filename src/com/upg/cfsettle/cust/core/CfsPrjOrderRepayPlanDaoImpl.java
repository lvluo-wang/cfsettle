package com.upg.cfsettle.cust.core;

import com.upg.cfsettle.mapping.prj.CfsPrjOrderRepayPlan;
import com.upg.ucars.framework.annotation.Dao;
import com.upg.ucars.framework.base.SysBaseDao;
import org.apache.commons.collections.map.HashedMap;

import java.util.*;

/**
 * Created by zuobaoshi on 2017/4/2.
 */
@Dao
public class CfsPrjOrderRepayPlanDaoImpl extends SysBaseDao<CfsPrjOrderRepayPlan,Long> implements ICfsPrjOrderRepayPlanDao {



    @Override
    public List<CfsPrjOrderRepayPlan> findByOrderIdAndType(Long prjOrderId,Byte ptype) {
        String sql = "select orderPlan.*,log.payback_audit_sysid,log.pay_back_time" +
                " from cfs_prj_order_repay_plan orderPlan left join cfs_prj_order_payback_log log" +
                " on orderPlan.id=log.prjOrderRepayPlanId where orderPlan.prjOrderId=:prjOrderId and orderPlan.ptype=:ptype " +
                " order by orderPlan.repayPeriods asc";
        Map<String,Object> param = new HashedMap();
        param.put("prjOrderId",prjOrderId);
        param.put("ptype",ptype);
        List<CfsPrjOrderRepayPlan> prjOrderRepayPlanList = (List<CfsPrjOrderRepayPlan>) this.findListByMap(sql,param,null,CfsPrjOrderRepayPlan.class);
        if(prjOrderRepayPlanList != null && prjOrderRepayPlanList.size() >0){
            return null;
        }
        return Collections.emptyList();
    }
}
