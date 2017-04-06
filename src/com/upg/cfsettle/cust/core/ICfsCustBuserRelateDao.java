package com.upg.cfsettle.cust.core;

import com.upg.cfsettle.mapping.prj.CfsCustBuserRelate;
import com.upg.ucars.framework.base.IBaseDAO;

import java.util.List;

/**
 * Created by zuobaoshi on 2017/4/4.
 */
public interface ICfsCustBuserRelateDao extends IBaseDAO<CfsCustBuserRelate,Long> {

    /**
     * 客户的关联服务人员
     * @param custId
     * @return
     */
    List<CfsCustBuserRelate> findByCustId(Long custId);


    /**
     * 服务人员名下的客户
     * @param buserId
     * @return
     */
    List<CfsCustBuserRelate> findByBuserId(Long buserId);
}
