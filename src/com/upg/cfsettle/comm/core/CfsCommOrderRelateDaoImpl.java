package com.upg.cfsettle.comm.core;

import com.upg.cfsettle.mapping.prj.CfsCommOrderRelate;
import com.upg.ucars.framework.annotation.Dao;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.SysBaseDao;
import com.upg.ucars.util.DateTimeUtil;
import com.upg.ucars.util.SQLCreater;

import org.apache.commons.collections.map.HashedMap;

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
        String sql = "select sys_id,sum(comm_account)/100 as sum_comm_amount from cfs_comm_order_relate where status=1 and comm_id is null" +
                " group by sys_id";
        List<Map<String, Object>> result = (List<Map<String, Object>>) this.findListByMap(sql,new HashedMap(),null,Map.class);
        return result;
    }

    @Override
    public List<Map<String, Object>> findCommDetailByCommId(Long commId, Page page) {
        String sql = "select order.contract_no,order.invest_time,cust.real_name,prj.prj_name,order.money," +
                " comm.comm_rate,comm.comm_account from cfs_comm_order_relate comm,cfs_prj_order order,cfs_prj prj" +
                " cfs_cust cust where comm.prj_order_id=order.id and order.prj_id=prj.id" +
                " and order.cust_id=cust.id ";
        SQLCreater sqlCreater = new SQLCreater(sql,true);
        sqlCreater.and("comm.comm_id=:commId","commId",commId,true);
        sqlCreater.orderBy("order.ctime",true);
        return this.getMapListByStanderdSQL(sqlCreater.getSql(),sqlCreater.getParameterMap(),page);
    }
}
