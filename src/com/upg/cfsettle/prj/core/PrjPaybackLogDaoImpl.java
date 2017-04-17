package com.upg.cfsettle.prj.core;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.upg.cfsettle.mapping.prj.CfsPrjPaybackLog;
import com.upg.cfsettle.mapping.prj.CfsPrjRepayPlan;
import com.upg.ucars.framework.annotation.Dao;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.SysBaseDao;
import com.upg.ucars.util.SQLCreater;
import org.apache.commons.collections.map.HashedMap;

@Dao
public class PrjPaybackLogDaoImpl extends SysBaseDao<CfsPrjPaybackLog,Long> implements IPrjPaybackLogDao {

	@Override
	public List<CfsPrjPaybackLog> getByPrjId(Long prjId) {
		String hql = "from CfsPrjPaybackLog where prjId=? order by loanTimes desc";
		return this.find(hql, prjId);
	}

	@Override
	public List<Map<String, Object>> findByCondition(CfsPrjPaybackLog searchBean, Page page) {
		String sql = "select p.`repay_periods`,from_unixtime(p.`repay_date`) as repay_date,from_unixtime(l.payback_time) as payback_time,l.payback_bank,"
				+ "(l.`payback_amount`/100) as payback_amount,l.payback_account_name,l.`payback_account_no`,l.`payback_serial_num`,l.`status`,l.csysid,"
				+ "l.`remark` from cfs_prj_repay_plan p left join cfs_prj_payback_log l on p.id = l.prj_repay_plan_id  where 1=1 ";
        SQLCreater sqlCreater = new SQLCreater(sql,false);
        if(searchBean != null){
            Long prjId = searchBean.getPrjId();
            if(prjId != null){
                sqlCreater.and("p.prj_id =:prjId","prjId",prjId,true);
            }
            Byte status = searchBean.getStatus();
            if(status != null){
                sqlCreater.and("p.status =:status","status",status,true);
            }
        }
        sqlCreater.orderBy("p.repay_periods",false);
        return getMapListByStanderdSQL(sqlCreater.getSql(),sqlCreater.getParameterMap(),page);
	}

    @Override
    public List<Map<String, Object>> findByPrjPaybackLogByPrjId(Long prjId) {
        String sql = "select p.`repay_periods`,from_unixtime(p.`repay_date`) as repay_date,from_unixtime(l.payback_time) as payback_time,"
                + "(l.`payback_amount`/100) as payback_amount,l.csysid,"
                + "l.`remark` from cfs_prj_payback_log l left join cfs_prj_repay_plan p on p.id = l.prj_repay_plan_id  where 1=1 ";
        SQLCreater sqlCreater = new SQLCreater(sql,true);
            if(prjId != null){
                sqlCreater.and("and p.prj_id=:prjId","prjId",prjId,true);
                sqlCreater.orderBy("p.repay_periods",true);
                return getMapListByStanderdSQL(sqlCreater.getSql(),sqlCreater.getParameterMap(),null);
            }else{
                return Collections.emptyList();
            }
    }

    @Override
    public BigDecimal getPayBackTotalAmount(Long prjId){
	    String hql = "from CfsPrjPaybackLog log where log.prjId=?";
        List<CfsPrjPaybackLog> list = this.find(hql,prjId);
        BigDecimal amount = BigDecimal.ZERO;
        for(CfsPrjPaybackLog prjPaybackLog : list){
            amount=amount.add(prjPaybackLog.getPaybackAmount());
        }
        return amount.divide(new BigDecimal(100));
    }

    @Override
    public BigDecimal getPrjRepayTotalAmount(Long prjId){
        String sql = "select plan.pri_interest/100 as priInterest,plan.principal/100 as principal" +
                " from cfs_prj_repay_plan plan where plan.prj_id=:prjId";
        Map<String,Object> param = new HashedMap();
        param.put("prjId",prjId);
        List<CfsPrjRepayPlan> list = (List<CfsPrjRepayPlan>) this.findListByMap(sql,param,null, CfsPrjRepayPlan.class);
        BigDecimal amount = BigDecimal.ZERO;
        for(CfsPrjRepayPlan prjRepayPlan : list){
            amount=amount.add(prjRepayPlan.getPriInterest().add(prjRepayPlan.getPrincipal()));
        }
        return amount;
    }



}
