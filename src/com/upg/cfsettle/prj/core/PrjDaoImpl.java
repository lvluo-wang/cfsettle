package com.upg.cfsettle.prj.core;

import java.util.*;

import com.upg.cfsettle.mapping.prj.CfsPrj;
import com.upg.cfsettle.util.CfsConstant;
import com.upg.cfsettle.util.UtilConstant;
import com.upg.ucars.framework.annotation.Dao;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.QueryCondition;
import com.upg.ucars.framework.base.SysBaseDao;
import com.upg.ucars.util.DateTimeUtil;
import com.upg.ucars.util.SQLCreater;
import com.upg.ucars.util.StringUtil;

/**
 * Created by zuo on 2017/3/30.
 */
@Dao
public class PrjDaoImpl extends SysBaseDao<CfsPrj,Long> implements IPrjDao {


    @Override
    public List<Map<String, Object>> findByCondition(CfsPrj searchBean, Page page) {
        String sql = "select prj.id,prj.prj_name,prj.demand_amount/100 as demand_amount,prj.remaining_amount/100 as remaining_amount,prj.time_limit,prj.time_limit_unit," +
                " prj.status,prj.last_repay_time,prj.year_rate,prj.repay_way,prj.start_bid_time,end_bid_time," +
                " ext.tenant_name,ext.tenant_bank,ext.account_no,ext.sub_bank from cfs_prj prj left join cfs_prj_ext ext" +
                " on prj.id=ext.prj_id";
        SQLCreater sqlCreater = new SQLCreater(sql,false);
        if(searchBean != null){
            String prjName = searchBean.getPrjName();
            if(!StringUtil.isEmpty(prjName)){
                sqlCreater.and("prj.prj_name like :prjName","prjName","%"+prjName+"%",true);
            }
            Byte status = searchBean.getStatus();
            if(status != null){
                sqlCreater.and("prj.status =:status","status",status,true);
            }
        }
        sqlCreater.orderBy("prj.status",false);
        return getMapListByStanderdSQL(sqlCreater.getSql(),sqlCreater.getParameterMap(),page);
    }

    @Override
    public List<CfsPrj> findPrjByStatus(Byte status) {
        String hql = "from CfsPrj prj where prj.status=? and prj.startBidTime<=? and prj.endBidTime>=? order by ctime desc";
        Date now = DateTimeUtil.getNowDateTime();
        return this.find(hql,new Object[]{status,now,now});
    }

	@Override
	public List<Map<String, Object>> findLoanPrjByCondition(CfsPrj searchBean,
			Page page) {
		String sql = "select prj.id,prj.prj_name,prj.demand_amount/100 as demand_amount,prj.remaining_amount/100 as remaining_amount,prj.time_limit,prj.time_limit_unit," +
                " prj.status,prj.last_repay_time,prj.year_rate,prj.repay_way,prj.start_bid_time,end_bid_time,prj.loaned_amount/100 as loaned_amount,(prj.demand_amount-prj.remaining_amount-loaned_amount)/100 as loaning_amount," +
                " ext.tenant_name,ext.tenant_bank,ext.account_no,ext.sub_bank from cfs_prj prj left join cfs_prj_ext ext" +
                " on prj.id=ext.prj_id";
        SQLCreater sqlCreater = new SQLCreater(sql,false);
        if(searchBean != null){
            String prjName = searchBean.getPrjName();
            if(!StringUtil.isEmpty(prjName)){
                sqlCreater.and("prj.prj_name like :prjName","prjName",prjName,true);
            }
            Byte status = searchBean.getStatus();
            if(status != null){
                sqlCreater.and("prj.status =:status","status",status,true);
            }
        }
        sqlCreater.orderBy("prj.ctime",true);
        return getMapListByStanderdSQL(sqlCreater.getSql(),sqlCreater.getParameterMap(),page);
	}

	@Override
	public List<CfsPrj> findRepayPlanPrj() {
		Long nowSec = Long.valueOf(DateTimeUtil.getNowSeconds());
		String hql = "from CfsPrj prj where prj.status=4 and prj.endBidTime <= "+nowSec+"order by ctime desc";
        return this.find(hql);
	}

    @Override
    public List<CfsPrj> findAllSucceedPrjLastMonth() {
        String hql = "from CfsPrj cfsPrj where cfsPrj.status not in (1,2,8) and" +
                " cfsPrj.buildTime>=? and cfsPrj.buildTime<=?";
        Date now = DateTimeUtil.getNowDateTime();
//        Calendar c = Calendar.getInstance();
//        // 得到本月的那一天
//        int today = c.get(c.DAY_OF_MONTH);
//        if(today !=1){
//            return Collections.EMPTY_LIST;
//        }
//        Date lastDay = DateTimeUtil.getSpecifiedDayBefore(now);
        //当前时间上个月的第一天和最后一天
        Date fromDate = DateTimeUtil.getFirstDayOfLastMonth(now);
        Date toDate = DateTimeUtil.getLastDayOfLastMonth(now);
        //项目成立时间
        return this.find(hql, new Object[]{fromDate, toDate});
    }

	@Override
	public List<CfsPrj> findFailedPrj() {
        String hql = "from CfsPrj prj where prj.endBidTime < ? and prj.demandAmount-prj.remainingAmount < prj.minLoanAmount and prj.status<>?";
        Date now = DateTimeUtil.getNowDateTime();
        Byte failed = CfsConstant.PRJ_STATUS_FAILED;
        return this.find(hql,new Object[]{now,failed});
	}
}
