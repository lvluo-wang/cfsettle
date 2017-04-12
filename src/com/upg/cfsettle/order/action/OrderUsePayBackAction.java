package com.upg.cfsettle.order.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.upg.cfsettle.common.CodeItemUtil;
import com.upg.cfsettle.cust.core.ICfsCustService;
import com.upg.cfsettle.cust.core.ICfsPrjOrderRepayPlanService;
import com.upg.cfsettle.cust.core.ICfsPrjOrderService;
import com.upg.cfsettle.mapping.ficode.FiCodeItem;
import com.upg.cfsettle.mapping.prj.CfsCust;
import com.upg.cfsettle.mapping.prj.CfsPrj;
import com.upg.cfsettle.mapping.prj.CfsPrjExt;
import com.upg.cfsettle.mapping.prj.CfsPrjOrder;
import com.upg.cfsettle.mapping.prj.CfsPrjOrderPaybackLog;
import com.upg.cfsettle.mapping.prj.CfsPrjOrderRepayPlan;
import com.upg.cfsettle.order.order.ICfsPrjOrderPaybackLogService;
import com.upg.cfsettle.prj.core.IPrjExtService;
import com.upg.cfsettle.prj.core.IPrjService;
import com.upg.cfsettle.util.UtilConstant;
import com.upg.ucars.basesystem.security.core.user.IUserService;
import com.upg.ucars.factory.DynamicPropertyTransfer;
import com.upg.ucars.framework.base.BaseAction;
import com.upg.ucars.mapping.basesystem.security.Buser;
import com.upg.ucars.util.PropertyTransVo;

@SuppressWarnings("serial")
public class OrderUsePayBackAction extends BaseAction {
	
private CfsPrjOrderPaybackLog searchBean;
	
	private CfsPrjOrderPaybackLog orderPayLog;
	
	@Autowired
	private ICfsPrjOrderRepayPlanService orderPlanService;
	@Autowired
	private IPrjService prjService;
	@Autowired
	private IPrjExtService prjExtService;
	@Autowired
	private ICfsPrjOrderService orderService;
	@Autowired
	private ICfsCustService custService;
	@Autowired
	private IUserService userService;
	@Autowired
	private ICfsPrjOrderPaybackLogService orderPaybackLogService;
	
	private List<FiCodeItem> prjStatus;
	
	private List<FiCodeItem> planStatus;
	
	private List<FiCodeItem> bankTypes;
	
	private CfsPrj prj;
	
	private CfsPrjExt prjExt;
	
	private CfsPrjOrderRepayPlan orderRepayPlan;
	
	private CfsPrjOrder prjOrder;
	
	private CfsCust cfsCust;
	
	private Buser buser;
	
	
	
	
	
	
	/**
	 * 跳转CfsPrjOrder主页
	 * @author renzhuolun
	 * @date 2017年4月4日 下午2:08:53
	 * @return
	 */
	public String main(){
		prjStatus = CodeItemUtil.getCodeItemsByKey(UtilConstant.CFS_PRJ_STATUS);
		planStatus = CodeItemUtil.getCodeItemsByKey(UtilConstant.CFS_PRJ_REPAY_PLAN_STATUS);
		return SUCCESS;
	}
	
	/**
	 * 订单回款查询
	 * @author renzhuolun
	 * @date 2014年8月5日 下午12:36:58
	 * @return
	 */
	public String list(){
		return setDatagridInputStreamData(orderPlanService.findByCondition(searchBean, getPg()), getPg());
	}
	
	/**
	 * 募集期还款添加
	 * @author renzhuolun
	 * @date 2017年4月9日 下午8:17:00
	 * @return
	 */
	public String toAdd(){
		orderRepayPlan = orderPlanService.getprjOrderPlanById(getPKId());
		prj = prjService.getPrjById(orderRepayPlan.getPrjId());
		prjExt = prjExtService.getPrjExtByPrjId(orderRepayPlan.getPrjId());
		prjOrder = orderService.getPrjOrderById(orderRepayPlan.getPrjOrderId());
		cfsCust = custService.queryCfsCustById(prjOrder.getCustId());
		buser = userService.getUserById(prjOrder.getServiceSysid());
		bankTypes = CodeItemUtil.getCodeItemsByKey(UtilConstant.CFS_BANK_TYPE);
		return SUCCESS;
	}
	
	/**
	 * 创建订单还款日志
	 * @author renzhuolun
	 * @date 2017年4月11日 上午11:42:31
	 */
	public void doAdd(){
		orderPaybackLogService.addOrderPaybackLog(orderPayLog);
	}
	
	/**
	 * 募集期还款详情
	 * @author renzhuolun
	 * @date 2017年4月9日 下午8:17:21
	 * @return
	 */
	public String toView(){
		orderRepayPlan = orderPlanService.getprjOrderPlanById(getPKId());
		orderRepayPlan.setTotalPeriods(orderPlanService.findByOrderId(orderRepayPlan.getPrjOrderId()).size());
		prj = prjService.getPrjById(orderRepayPlan.getPrjId());
		prjExt = prjExtService.getPrjExtByPrjId(orderRepayPlan.getPrjId());
		prjOrder = orderService.getPrjOrderById(orderRepayPlan.getPrjOrderId());
		cfsCust = custService.queryCfsCustById(prjOrder.getCustId());
		buser = userService.getUserById(prjOrder.getServiceSysid());
		return SUCCESS;
	}
	
	public String listUse(){
		List<CfsPrjOrderPaybackLog> list = orderPaybackLogService.findByOrderRepayPlanId(orderPayLog,getPg());
		List<PropertyTransVo> trans = new ArrayList<PropertyTransVo>();
    	trans.add(new PropertyTransVo("csysid", Buser.class, "userId", "userName","sysUserName"));
		return setDatagridInputStreamData(DynamicPropertyTransfer.transform(list, trans), getPg());
	}

	public CfsPrjOrderPaybackLog getSearchBean() {
		return searchBean;
	}

	public void setSearchBean(CfsPrjOrderPaybackLog searchBean) {
		this.searchBean = searchBean;
	}

	public List<FiCodeItem> getPrjStatus() {
		return prjStatus;
	}

	public void setPrjStatus(List<FiCodeItem> prjStatus) {
		this.prjStatus = prjStatus;
	}

	public List<FiCodeItem> getPlanStatus() {
		return planStatus;
	}

	public void setPlanStatus(List<FiCodeItem> planStatus) {
		this.planStatus = planStatus;
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

	public CfsPrjOrderRepayPlan getOrderRepayPlan() {
		return orderRepayPlan;
	}

	public void setOrderRepayPlan(CfsPrjOrderRepayPlan orderRepayPlan) {
		this.orderRepayPlan = orderRepayPlan;
	}

	public CfsPrjOrder getPrjOrder() {
		return prjOrder;
	}

	public void setPrjOrder(CfsPrjOrder prjOrder) {
		this.prjOrder = prjOrder;
	}

	public CfsCust getCfsCust() {
		return cfsCust;
	}

	public void setCfsCust(CfsCust cfsCust) {
		this.cfsCust = cfsCust;
	}

	public Buser getBuser() {
		return buser;
	}

	public void setBuser(Buser buser) {
		this.buser = buser;
	}

	public List<FiCodeItem> getBankTypes() {
		return bankTypes;
	}

	public void setBankTypes(List<FiCodeItem> bankTypes) {
		this.bankTypes = bankTypes;
	}

	public CfsPrjOrderPaybackLog getOrderPayLog() {
		return orderPayLog;
	}

	public void setOrderPayLog(CfsPrjOrderPaybackLog orderPayLog) {
		this.orderPayLog = orderPayLog;
	}
}