package com.upg.cfsettle.order.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.upg.cfsettle.common.CodeItemUtil;
import com.upg.cfsettle.cust.core.CustOrderBean;
import com.upg.cfsettle.cust.core.ICfsCustService;
import com.upg.cfsettle.cust.core.ICfsPrjOrderService;
import com.upg.cfsettle.mapping.ficode.FiCodeItem;
import com.upg.cfsettle.mapping.prj.CfsCust;
import com.upg.cfsettle.mapping.prj.CfsPrj;
import com.upg.cfsettle.mapping.prj.CfsPrjExt;
import com.upg.cfsettle.mapping.prj.CfsPrjOrder;
import com.upg.cfsettle.prj.core.IPrjExtService;
import com.upg.cfsettle.prj.core.IPrjService;
import com.upg.cfsettle.util.UtilConstant;
import com.upg.ucars.factory.DynamicPropertyTransfer;
import com.upg.ucars.framework.base.BaseAction;
import com.upg.ucars.mapping.basesystem.security.Buser;
import com.upg.ucars.util.PropertyTransVo;

@SuppressWarnings("serial")
public class CfsPrjOrderAction extends BaseAction {
	
	private CustOrderBean searchBean;
	
	@Autowired
	private ICfsPrjOrderService prjOrderService;
	
	private CfsPrjOrder cfsPrjOrder;
	
	private CfsPrj prj;
	
	private CfsPrjExt prjExt;
	
	private CfsCust cfsCust;
	@Autowired
	private IPrjService prjService;
	@Autowired
	private IPrjExtService prjExtService;
	@Autowired
	private ICfsCustService custService;
	
	private List<FiCodeItem> orderStatus;
	
	
	
	/**
	 * 跳转CfsPrjOrder主页
	 * @author renzhuolun
	 * @date 2017年4月4日 下午2:08:53
	 * @return
	 */
	public String main(){
		orderStatus = CodeItemUtil.getCodeItemsByKey(UtilConstant.CFS_PRJ_ORDER_STATUS);
		return MAIN;
	}
	
	/**
	 * 查询CfsPrjOrder信息
	 * @author renzhuolun
	 * @date 2014年8月5日 下午12:36:58
	 * @return
	 */
	public String list(){
		return setDatagridInputStreamData(prjOrderService.findByCondition(searchBean, getPg()), getPg());
	}
	
	/**
	 * 跳转新增页面
	 * @author renzhuolun
	 * @date 2014年8月5日 下午12:38:01
	 * @return
	 */
	public String toAdd(){
		cfsPrjOrder = prjOrderService.getPrjOrderById(getPKId());
		cfsCust = custService.queryCfsCustById(cfsPrjOrder.getCustId());
		prj = prjService.getPrjById(cfsPrjOrder.getPrjId());
		prjExt = prjExtService.getPrjExtByPrjId(cfsPrjOrder.getPrjId());
		List<PropertyTransVo> trans = new ArrayList<PropertyTransVo>();
    	trans.add(new PropertyTransVo("csysid", Buser.class, "userId", "userNo","mobile"));
    	trans.add(new PropertyTransVo("collectAuditSysid", Buser.class, "userId", "userName","contractAuditUser"));
    	cfsPrjOrder = DynamicPropertyTransfer.transform(cfsPrjOrder, trans);
		return SUCCESS;
	}
	
	/**
	 * 跳转编辑页面
	 * @author renzhuolun
	 * @date 2014年8月8日 上午9:35:23
	 * @return
	 */
	public String toEdit(){
		cfsPrjOrder = prjOrderService.getPrjOrderById(getPKId());
		cfsCust = custService.queryCfsCustById(cfsPrjOrder.getCustId());
		prj = prjService.getPrjById(cfsPrjOrder.getPrjId());
		prjExt = prjExtService.getPrjExtByPrjId(cfsPrjOrder.getPrjId());
		List<PropertyTransVo> trans = new ArrayList<PropertyTransVo>();
    	trans.add(new PropertyTransVo("csysid", Buser.class, "userId", "userNo","mobile"));
    	trans.add(new PropertyTransVo("collectAuditSysid", Buser.class, "userId", "userName","contractAuditUser"));
    	cfsPrjOrder = DynamicPropertyTransfer.transform(cfsPrjOrder, trans);
		return SUCCESS;
	}
	
	/**
	 * 批量删除CfsPrjOrder信息
	 * @author renzhuolun
	 * @date 2014年8月8日 上午9:28:16
	 */
	public void batchDelete(){
		prjOrderService.batchDelete(getIdList());
	}
	
	/**
	 * 审核CfsPrjOrder信息
	 * @author renzhuolun
	 * @date 2017年4月5日 下午6:39:00
	 */
	public void doAudit(){
		prjOrderService.doAuditPrjOrder(cfsPrjOrder);
	}
	
	/**
	 * 新增CfsPrjOrder信息
	 * @author renzhuolun
	 * @date 2014年8月11日 上午10:42:31
	 */
	public void doAdd(){
		prjOrderService.addPrjOrder(cfsPrjOrder);
	}
	
	/**
	 * 查看CfsPrjOrder
	 * @author renzhuolun
	 * @date 2016年8月19日 下午2:33:51
	 * @return
	 */
	public String toView(){
		cfsPrjOrder = prjOrderService.getPrjOrderById(getPKId());
		cfsCust = custService.queryCfsCustById(cfsPrjOrder.getCustId());
		prj = prjService.getPrjById(cfsPrjOrder.getPrjId());
		prjExt = prjExtService.getPrjExtByPrjId(cfsPrjOrder.getPrjId());
		List<PropertyTransVo> trans = new ArrayList<PropertyTransVo>();
    	trans.add(new PropertyTransVo("csysid", Buser.class, "userId", "userNo","mobile"));
    	trans.add(new PropertyTransVo("collectAuditSysid", Buser.class, "userId", "userName","contractAuditUser"));
    	cfsPrjOrder = DynamicPropertyTransfer.transform(cfsPrjOrder, trans);
		return SUCCESS;
	}
	
	public CustOrderBean getSearchBean() {
		return searchBean;
	}

	public void setSearchBean(CustOrderBean searchBean) {
		this.searchBean = searchBean;
	}

	public CfsPrjOrder getCfsPrjOrder() {
		return cfsPrjOrder;
	}

	public void setCfsPrjOrder(CfsPrjOrder cfsPrjOrder) {
		this.cfsPrjOrder = cfsPrjOrder;
	}

	public CfsPrj getPrj() {
		return prj;
	}

	public void setPrj(CfsPrj prj) {
		this.prj = prj;
	}

	public CfsPrjExt getPrjExt() {
		return prjExt;
	}

	public void setPrjExt(CfsPrjExt prjExt) {
		this.prjExt = prjExt;
	}

	public CfsCust getCfsCust() {
		return cfsCust;
	}

	public void setCfsCust(CfsCust cfsCust) {
		this.cfsCust = cfsCust;
	}

	public List<FiCodeItem> getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(List<FiCodeItem> orderStatus) {
		this.orderStatus = orderStatus;
	}
}