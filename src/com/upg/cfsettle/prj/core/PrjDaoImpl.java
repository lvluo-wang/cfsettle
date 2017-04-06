package com.upg.cfsettle.prj.core;

import com.upg.cfsettle.mapping.prj.CfsPrj;
import com.upg.finance.mapping.yrzif.FiPrj;
import com.upg.ucars.framework.annotation.Dao;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.SysBaseDao;
import com.upg.ucars.util.DateTimeUtil;
import com.upg.ucars.util.SQLCreater;
import com.upg.ucars.util.StringUtil;

import java.util.List;
import java.util.Map;

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
    public List<CfsPrj> findPrjByStatus(Byte status) {
        String hql = "from CfsPrj prj where prj.status=? order by ctime desc";
        return this.find(hql,status);
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
}
