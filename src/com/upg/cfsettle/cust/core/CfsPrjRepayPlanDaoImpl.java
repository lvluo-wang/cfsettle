package com.upg.cfsettle.cust.core;

import com.upg.cfsettle.mapping.prj.CfsPrjRepayPlan;
import com.upg.ucars.framework.annotation.Dao;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.SysBaseDao;
import com.upg.ucars.util.SQLCreater;
import com.upg.ucars.util.StringUtil;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;

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

	@Override
	public List<Map<String, Object>> findByCondition(CfsPrjRepayPlan searchBean, Page page) {
		String sql = "select prj.id,prj.prj_name,prj.prj_mobile,(prj.demand_amount-prj.remaining_amount)/100 as act_amount,prj.time_limit,prj.time_limit_unit,prj.year_rate,"
				+ "(plan.pri_interest/100) as pri_interest ,(plan.principal/100)as principal,(plan.yield/100) as yield,FROM_UNIXTIME(plan.repay_date) as repay_date,plan.id as planid,"
				+ "plan.repay_periods,plan.status from cfs_prj_repay_plan plan join cfs_prj prj on plan.prj_id = prj.id";
        SQLCreater sqlCreater = new SQLCreater(sql,false);
        if(searchBean != null){
            String prjName = searchBean.getPrjName();
            if(!StringUtil.isEmpty(prjName)){
                sqlCreater.and("prj.prj_name like :prjName","prjName",prjName,true);
            }
            Byte status = searchBean.getStatus();
            if(status != null){
                sqlCreater.and("plan.status =:status","status",status,true);
            }
            Date startTime = searchBean.getStartDate();
            if(null != startTime){
                long ctimeStartLong = startTime.getTime()/1000;
                sqlCreater.and(" plan.repay_date >= :ctimeStartLong", "ctimeStartLong", ctimeStartLong , true);
            }

            Date endTime = searchBean.getEndDate();
            if(null != endTime){
                long ctimeEndLong = DateUtils.addDays(endTime, 1).getTime()/1000;
                sqlCreater.and(" plan.repay_date < :ctimeEndLong", "ctimeEndLong", ctimeEndLong , true);
            }
        }
        sqlCreater.orderBy("plan.repay_date",true);
        List<Map<String, Object>> result = getMapListByStanderdSQL(sqlCreater.getSql(),sqlCreater.getParameterMap(),page);
        return result;
	}
}