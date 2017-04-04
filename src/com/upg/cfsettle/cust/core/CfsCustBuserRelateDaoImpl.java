package com.upg.cfsettle.cust.core;

import com.upg.cfsettle.mapping.prj.CfsCustBuserRelate;
import com.upg.ucars.framework.annotation.Dao;
import com.upg.ucars.framework.base.SysBaseDao;

import java.util.List;

/**
 * Created by zuobaoshi on 2017/4/4.
 */
@Dao
public class CfsCustBuserRelateDaoImpl extends SysBaseDao<CfsCustBuserRelate,Long> implements ICfsCustBuserRelateDao {

    @Override
    public List<CfsCustBuserRelate> findByCustId(Long custId) {
        String hql = "from CfsCustBuserRelate relate where relate.custId=? order by relate.id desc";
        return this.find(hql,custId);
    }
}
