package com.upg.cfsettle.cust.core;

import com.upg.cfsettle.mapping.prj.CfsPrjOrder;
import com.upg.ucars.framework.base.IBaseDAO;
import com.upg.ucars.framework.base.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by zuobaoshi on 2017/4/2.
 */
public interface ICfsPrjOrderDao extends IBaseDAO<CfsPrjOrder,Long> {

    List<Map<String, Object>> findByCondition(CustOrderBean searchBean, Page page);

    /**
     * 根据合同编号查询状态为退回重签的prjOrder
     * @param contractNo
     * @return
     */
    CfsPrjOrder getPrjOrderByContractNo(String contractNo);

	List<CfsPrjOrder> getPrjOrdersByPrjId(Long prjId);

    List<CfsPrjOrder> getPrjOrdersByPrjIdDesc(Long prjId);
}
