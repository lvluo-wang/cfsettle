package com.upg.cfsettle.cust.core;

import com.upg.cfsettle.mapping.prj.CfsCustBuserRelate;
import com.upg.ucars.constant.ErrorCodeConst;
import com.upg.ucars.framework.annotation.Dao;
import com.upg.ucars.framework.base.SysBaseDao;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.framework.exception.ExceptionManager;
import com.upg.ucars.mapping.basesystem.security.Buser;

import java.util.ArrayList;
import java.util.Collections;
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

    @Override
    public Integer getCustCount(Long buserId) {
        String hql = "from CfsCustBuserRelate relate where relate.sysId=?";
        List<CfsCustBuserRelate> list = this.find(hql,buserId);
        if(list != null && list.size() >0){
            return list.size();
        }
        return 0;
    }

    @Override
    public List<CfsCustBuserRelate> getCustBuserRelateListBycustIds(String custIds) throws DAOException {
        StringBuilder hql = new StringBuilder("from CfsCustBuserRelate user where user.custId in (");
        List<CfsCustBuserRelate> ret = null;
        try{
            hql.append(custIds+")");
            ret =  this.find(hql.toString());
        }catch(Throwable t){
            ExceptionManager.throwException(DAOException.class, ErrorCodeConst.DB_OPERATION_ERROR, new String[]{hql.toString()}, t);
        }
        return ret == null ? new ArrayList<CfsCustBuserRelate>(0) : ret;
    }
}
