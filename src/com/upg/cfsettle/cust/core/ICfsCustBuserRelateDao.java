package com.upg.cfsettle.cust.core;

import com.upg.cfsettle.mapping.prj.CfsCustBuserRelate;
import com.upg.ucars.framework.base.IBaseDAO;
import com.upg.ucars.framework.exception.DAOException;

import java.util.List;

/**
 * Created by zuobaoshi on 2017/4/4.
 */
public interface ICfsCustBuserRelateDao extends IBaseDAO<CfsCustBuserRelate,Long> {

    /**
     * 客户关联的服务人员
     * @param custId
     * @return
     */
    List<CfsCustBuserRelate> findByCustId(Long custId);


    /**
     *服务人员名下的客户数量
     * @param buserId
     * @return
     */
    Integer getCustCount(Long buserId);


    List<CfsCustBuserRelate> getCustBuserRelateListBycustIds(String custIds) throws DAOException;
}
