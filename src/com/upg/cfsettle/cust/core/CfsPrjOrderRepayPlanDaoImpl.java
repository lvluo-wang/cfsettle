package com.upg.cfsettle.cust.core;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;

import com.upg.cfsettle.mapping.prj.CfsPrjOrderPaybackLog;
import com.upg.cfsettle.mapping.prj.CfsPrjOrderRepayPlan;
import com.upg.ucars.framework.annotation.Dao;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.SysBaseDao;
import com.upg.ucars.util.SQLCreater;
import com.upg.ucars.util.StringUtil;

/**
 * Created by zuobaoshi on 2017/4/2.
 */
@Dao
public class CfsPrjOrderRepayPlanDaoImpl extends SysBaseDao<CfsPrjOrderRepayPlan,Long> implements ICfsPrjOrderRepayPlanDao {



    @Override
    public List<CfsPrjOrderRepayPlan> findByOrderIdAndType(Long prjOrderId,Byte ptype) {
        String sql = "select from_unixtime(orderPlan.repay_date) as repayDate,orderPlan.principal/100 as principal,orderPlan.yield/100 as yield,orderPlan.pri_interest/100 as priInterest," +
                " orderPlan.status,orderPlan.repay_periods as repayPeriod," +
                " log.payback_audit_sysid as paybackAuditSysid," +
                " from_unixtime(log.payback_time) as paybackTime" +
                " from cfs_prj_order_repay_plan orderPlan left join cfs_prj_order_payback_log log" +
                " on orderPlan.id=log.prj_order_repay_plan_id where orderPlan.prj_order_id=:prjOrderId " ;
        Map<String,Object> param = new HashedMap();
        param.put("prjOrderId",prjOrderId);
        if(ptype != null){
            param.put("ptype",ptype);
            sql += " and orderPlan.ptype=:ptype ";
        }
        sql += " order by orderPlan.repay_periods asc ";
        List<CfsPrjOrderRepayPlan> prjOrderRepayPlanList = (List<CfsPrjOrderRepayPlan>) this.findListByMap(sql,param,null,CfsPrjOrderRepayPlan.class);
        if(prjOrderRepayPlanList != null && prjOrderRepayPlanList.size() >0){
            return prjOrderRepayPlanList;
        }
        return Collections.EMPTY_LIST;
    }

	@Override
	public List<Map<String, Object>> findByCondition(CfsPrjOrderPaybackLog searchBean, Page page) {
		String sql = "select p.id,p.repay_periods,o.contract_no,c.real_name,prj.prj_name,from_unixtime(o.invest_time) as invest_time,from_unixtime(prj.build_time) as build_time,(p.rest_principal/100) as rest_principal,"
				+ "from_unixtime(p.repay_date) as repay_date,p.count_day,prj.period_rate,prj.year_rate,(p.pri_interest/100) as pri_interest ,(p.principal/100) as principal,(p.yield/100) as yield,(o.money/100) as money,"
				+ "prj.status as prjstatus,p.status as planstatus from cfs_prj_order o join cfs_prj_order_repay_plan p on o.id = p.prj_order_id join cfs_cust c on o.cust_id = c.id "
				+ "join cfs_prj prj on o.prj_id = prj.id ";
        SQLCreater sqlCreater = new SQLCreater(sql,false);
        if(searchBean != null){
            String realName = searchBean.getRealName();
            if(!StringUtil.isEmpty(realName)){
                sqlCreater.and("c.real_name like:realName","realName","%"+realName+"%",true);
            }
            String contractNo = searchBean.getContractNo();
            if(!StringUtil.isEmpty(contractNo)){
                sqlCreater.and("o.contract_no like:contractNo","contractNo","%"+contractNo+"%",true);
            }
            String mobile = searchBean.getMobile();
            if(!StringUtil.isEmpty(mobile)){
                sqlCreater.and("c.mobile =:mobile","mobile",mobile,true);
            }
            String prjName = searchBean.getPrjName();
            if(!StringUtil.isEmpty(prjName)){
                sqlCreater.and("prj.prj_name like:prjName","prjName","%"+prjName+"%",true);
            }
            Byte status = searchBean.getStatus(); //还款状态
            if(status != null){
                sqlCreater.and("p.status =:status","status",status,true);
            }
            Byte prjStatus = searchBean.getPrjStatus(); //项目状态
            if(prjStatus != null){
                sqlCreater.and("prj.status =:prjStatus","prjStatus",prjStatus,true);
            }
            Byte isPeriod = searchBean.getIsPeriod();
            if(isPeriod.longValue()==0){
            	sqlCreater.and("p.ptype =:isPeriod","isPeriod",2,true);
            }else{
            	sqlCreater.and("p.ptype =:isPeriod","isPeriod",1,true);
            }
        }
        sqlCreater.orderBy("p.repay_date",false);
        return getMapListByStanderdSQL(sqlCreater.getSql(),sqlCreater.getParameterMap(),page);
	}

	@Override
	public List<CfsPrjOrderRepayPlan> findByOrderId(Long prjOrderId) {
		 String hql = "from CfsPrjOrderRepayPlan plan where plan.prjOrderId=?";
	     return this.find(hql,prjOrderId);
	}
}
