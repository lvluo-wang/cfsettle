package com.upg.cfsettle.cust.core;

import com.upg.cfsettle.mapping.prj.CfsPrjOrder;
import com.upg.cfsettle.mapping.prj.CfsPrjRepayPlan;
import com.upg.ucars.framework.annotation.Dao;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.SysBaseDao;
import com.upg.ucars.util.SQLCreater;
import com.upg.ucars.util.StringUtil;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by zuobaoshi on 2017/4/2.
 */
@Dao
public class CfsPrjOrderDaoImpl extends SysBaseDao<CfsPrjOrder,Long> implements ICfsPrjOrderDao {

    @Autowired
    private ICfsPrjRepayPlanDao prjRepayPlanDao;


    @Override
    public List<Map<String, Object>> findByCondition(CustOrderBean searchBean, Page page) {
        String sql = "select o.*,prj.prj_name,cust.real_name,cust.mobile from cfs_prj_order o " +
                " left join cfs_prj prj on o.prj_id=prj.id" +
                " left join cfs_cust cust on o.cust_id=cust.id" +
                " ";
        SQLCreater sqlCreater = new SQLCreater(sql,false);
        if(searchBean != null){
            String prjName = searchBean.getPrjName();
            if(!StringUtil.isEmpty(prjName)){
                sqlCreater.and("prj.prj_name like :prjName","prjName",prjName,true);
            }
            String realName = searchBean.getRealName();
            if(!StringUtil.isEmpty(realName)){
                sqlCreater.and("cust.real_name  =:realName","realName",realName,true);
            }
            String mobile = searchBean.getMobile();
            if(!StringUtil.isEmpty(mobile)){
                sqlCreater.and("cust.mobile  =:mobile","mobile",mobile,true);
            }
            Byte status = searchBean.getStatus();
            if(status != null){
                sqlCreater.and("o.status =:status","status",status,true);
            }
            String contractNo = searchBean.getContractNo();
            if(!StringUtil.isEmpty(contractNo)){
                sqlCreater.and("o.contract_no  =:contractNo","contractNo",contractNo,true);
            }
            Date startTime = searchBean.getStartDate();
            if(null != startTime){
                long ctimeStartLong = startTime.getTime()/1000;
                sqlCreater.and(" o.invest_time >= :ctimeStartLong", "ctimeStartLong", ctimeStartLong , true);
            }

            Date endTime = searchBean.getEndDate();
            if(null != endTime){
                long ctimeEndLong = DateUtils.addDays(endTime, 1).getTime()/1000;
                sqlCreater.and(" o.invest_time < :ctimeEndLong", "ctimeEndLong", ctimeEndLong , true);
            }
        }
        sqlCreater.orderBy("o.invest_time",true);
        List<Map<String, Object>> result = getMapListByStanderdSQL(sqlCreater.getSql(),sqlCreater.getParameterMap(),page);
        addRepayDetail(result);
        return result;
    }

    private void addRepayDetail(List<Map<String, Object>> result) {
        if(result != null && result.size() >0){
            for(Map<String, Object> map : result){
                Long prjId = (Long) map.get("PRJ_ID");
                List<CfsPrjRepayPlan> notPaidRepayPlanList = prjRepayPlanDao.findNotPaidOffByPrjId(prjId);
                if(notPaidRepayPlanList != null && notPaidRepayPlanList.size() >0){
                    CfsPrjRepayPlan prjRepayPlan = notPaidRepayPlanList.get(0);
                    map.put("CURRENT_PERIOD",prjRepayPlan.getRepayPeriods());
                    map.put("CURRENT_REPAY_DATE",prjRepayPlan.getRepayDate());
                }
                List<CfsPrjRepayPlan> repayPlanList = prjRepayPlanDao.findByPrjId(prjId);
                if(repayPlanList != null && repayPlanList.size() >0){
                    map.put("TOTAL_PERIOD",repayPlanList.size());
                }
            }
        }
    }
}
