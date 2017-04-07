package com.upg.cfsettle.cust.core;

import com.upg.cfsettle.mapping.prj.CfsCust;
import com.upg.cfsettle.mapping.prj.CfsPrj;
import com.upg.ucars.framework.base.IBaseDAO;

import java.util.List;

public interface ICfsCustDao extends IBaseDAO<CfsCust, Long> {

    /**
     * 员工名下的所有客户
     * @param buserId
     * @return
     */
    List<CfsCust> findAllCustByBuserId(Long buserId);

}
