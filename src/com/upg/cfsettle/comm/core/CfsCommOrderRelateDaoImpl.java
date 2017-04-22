package com.upg.cfsettle.comm.core;

import com.upg.cfsettle.mapping.prj.CfsCommOrderRelate;
import com.upg.ucars.framework.annotation.Dao;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.SysBaseDao;
import com.upg.ucars.util.DateTimeUtil;
import com.upg.ucars.util.SQLCreater;

import org.apache.commons.collections.map.HashedMap;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by zuobaoshi on 2017/4/12.
 */
@Dao
public class CfsCommOrderRelateDaoImpl extends SysBaseDao<CfsCommOrderRelate,Long> implements ICfsCommOrderRelateDao {

    @Override
    public List<CfsCommOrderRelate> findBySysIdAndCommIdIsNull(Long sysId) {
        String hql = "from CfsCommOrderRelate where sysId=? and status=1 and commId is null";
        List<CfsCommOrderRelate> list = this.find(hql,sysId);
        if(list != null && list.size() >0){
            return list;
        }
        return Collections.emptyList();
    }

    @Override
    public List<Map<String, Object>> findTotalAmountGroupBySysId() {
        String sql = "select sysid,sum(comm_account)/100 as sum_comm_amount from cfs_comm_order_relate where status=1 and comm_id is null" +
                " group by sysid";
        List<Map<String, Object>> result = this.getMapListByStanderdSQL(sql,new HashedMap(),null);
        return result;
    }

    @Override
    public List<Map<String, Object>> findCommDetailByCommId(Long commId, Page page) {
        String sql = "select prjOrder.contract_no,prjOrder.invest_time,cust.real_name,prj.prj_name,prjOrder.money/100 as money," +
                " comm.comm_rate/100 as comm_rate,comm.comm_account/100 as comm_account from cfs_comm_order_relate comm,cfs_prj_order prjOrder,cfs_prj prj," +
                " cfs_cust cust where comm.prj_order_id=prjOrder.id and prjOrder.prj_id=prj.id" +
                " and prjOrder.cust_id=cust.id ";
        SQLCreater sqlCreater = new SQLCreater(sql,true);
        sqlCreater.and("and comm.comm_id=:commId","commId",commId,true);
        sqlCreater.orderBy("prjOrder.ctime",true);
        return this.getMapListByStanderdSQL(sqlCreater.getSql(),sqlCreater.getParameterMap(),page);
    }

    @Override
    public CfsCommOrderRelate findIsExits(CfsCommOrderRelate cfsCommOrderRelate){
        String hql = "from CfsCommOrderRelate where prjId=? and prjOrderId=?" +
                " and sysid=? and commRate=? and commAccount=? and commId is not null";
        List<CfsCommOrderRelate> list = this.find(hql,new Object[]{cfsCommOrderRelate.getPrjId(),
                cfsCommOrderRelate.getPrjOrderId(),
                cfsCommOrderRelate.getSysid(),
                cfsCommOrderRelate.getCommRate(),
                cfsCommOrderRelate.getCommAccount()});
        if(list != null && list.size()>0){
            return list.get(0);
        }
        return null;
    }
}
