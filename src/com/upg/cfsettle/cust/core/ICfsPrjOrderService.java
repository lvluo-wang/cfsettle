package com.upg.cfsettle.cust.core;

import java.util.List;
import java.util.Map;

import com.upg.cfsettle.mapping.prj.CfsPrjOrder;
import com.upg.ucars.framework.base.IBaseService;
import com.upg.ucars.framework.base.Page;

/**
 * Created by zuobaoshi on 2017/4/2.
 */
public interface ICfsPrjOrderService extends IBaseService {


    List<Map<String, Object>> findByCondition(CustOrderBean searchBean, Page page);

    void addPrjOrder(CfsPrjOrder prjOrder);

    CfsPrjOrder getPrjOrderById(Long id);

	void batchDelete(List<Long> ids);

	void updatePrjOrder(CfsPrjOrder cfsPrjOrder);


    /**
     * 根据合同编号查询状态为退回重签的prjOrder
     * @param contractNo
     * @return
     */
    CfsPrjOrder getPrjOrderByContractNo(String contractNo);

}
