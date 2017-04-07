package com.upg.cfsettle.cust.core;

import com.upg.cfsettle.mapping.prj.CfsCust;
import com.upg.ucars.framework.annotation.Dao;
import com.upg.ucars.framework.base.SysBaseDao;
import edu.emory.mathcs.backport.java.util.Collections;

import java.util.List;

@Dao
public class CfsCustDaoImpl extends SysBaseDao<CfsCust, Long> implements ICfsCustDao{

    @Override
    public List<CfsCust> findAllCustByBuserId(Long buserId) {
        String hql = "from CfsCust cust,CfsCustBuserRelate relate where " +
                " cust.id=relate.custId and relate.sysId=?";
        List<CfsCust> list = this.find(hql,buserId);
        if(list != null && list.size() >0){
            return list;
        }
        return Collections.emptyList();
    }
}
