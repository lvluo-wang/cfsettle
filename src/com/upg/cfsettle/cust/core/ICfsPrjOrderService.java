package com.upg.cfsettle.cust.core;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

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
     * 根据合同编号查询退回重签的订单
     * @param contractNo
     * @return
     */
    CfsPrjOrder getPrjOrderByContractNo(String contractNo);

	void doAuditPrjOrder(CfsPrjOrder cfsPrjOrder);
	
	/**
	 * 获取项目订单
	 * @author renzhuolun
	 * @date 2017年4月6日 下午1:01:28
	 * @param id
	 * @return
	 */
	List<CfsPrjOrder> getPrjOrdersByPrjId(Long prjId);
	
	/**
	 * 查询结算订单明细
	 * @author renzl123
	 * @date 2017年4月12日 下午11:06:11
	 * @param prjOrder
	 * @param page
	 * @return
	 */
	List<CfsPrjOrder> findCommByCondition(CfsPrjOrder prjOrder, Page page);

	List<CfsPrjOrder> getOKPrjOrdersByPrjId(Long prjId);

	HSSFWorkbook generatOrderAuidtData(OutputStream os, CustOrderBean searchBean, String[] headers, String title, Page pg);

}
