package com.upg.cfsettle.cust.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.upg.cfsettle.code.core.ICodeItemService;
import com.upg.cfsettle.common.CodeItemUtil;
import com.upg.cfsettle.cust.core.CustOrderBean;
import com.upg.cfsettle.cust.core.ICfsCustService;
import com.upg.cfsettle.cust.core.ICfsPrjOrderRepayPlanService;
import com.upg.cfsettle.cust.core.ICfsPrjOrderService;
import com.upg.cfsettle.cust.core.ICfsPrjRepayPlanService;
import com.upg.cfsettle.mapping.ficode.FiCodeItem;
import com.upg.cfsettle.mapping.prj.CfsCust;
import com.upg.cfsettle.mapping.prj.CfsPrj;
import com.upg.cfsettle.mapping.prj.CfsPrjOrder;
import com.upg.cfsettle.mapping.prj.CfsPrjOrderRepayPlan;
import com.upg.cfsettle.prj.core.IPrjService;
import com.upg.cfsettle.util.CfsConstant;
import com.upg.cfsettle.util.UtilConstant;
import com.upg.ucars.basesystem.security.core.user.IUserService;
import com.upg.ucars.factory.DynamicPropertyTransfer;
import com.upg.ucars.framework.base.BaseAction;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.SessionTool;
import com.upg.ucars.mapping.basesystem.security.Buser;
import com.upg.ucars.model.security.UserLogonInfo;
import com.upg.ucars.util.DateTimeUtil;
import com.upg.ucars.util.PropertyTransVo;

/**
 * Created by zuobaoshi on 2017/4/2.
 */
public class CfsCustOrderAction extends BaseAction {

    @Autowired
    private ICfsPrjOrderService prjOrderService;
    @Autowired
    private ICfsCustService custService;
    @Autowired
    private IPrjService prjService;
    private ICodeItemService codeItemService;
    @Autowired
    private ICfsPrjOrderRepayPlanService prjOrderRepayPlanService;
    @Autowired
    ICfsPrjRepayPlanService prjRepayPlanService;
    @Autowired
    private IUserService userService;


    private List<FiCodeItem> buserPosCodeList;
    private UserLogonInfo logonInfo;
    private CustOrderBean searchBean;
    private CfsCust cust;
    private CfsPrjOrder prjOrder;
    private CfsPrj prj;
    private List<FiCodeItem> bankList;
    private List<CfsPrj> prjList;
    private List<FiCodeItem> orderStatusList;
    private Long prjOrderId;
    //募集期订单还款计划
    private CfsPrjOrderRepayPlan raisePrjOrderRepayPlan;
    //还款计划总期数
    private Integer totalPeriod;

    private int raiseDay;


    public String main(){
        orderStatusList = codeItemService.getCodeItemByKey(UtilConstant.CFS_PRJ_ORDER_STATUS);
        return MAIN;
    }


    public String list(){
    	searchBean.setByLogonInfo(false);
        searchBean.setMyselfInfo(true);
        //只能看自己名下的订单,管理员除外
        return setDatagridInputStreamData(prjOrderService.findByCondition(searchBean,getPg()),getPg());
    }

    public String toView(){
        prjOrder = prjOrderService.getPrjOrderById(getPKId());
        List<CfsPrjOrder> list = new ArrayList<>();
        list.add(prjOrder);
        List<PropertyTransVo> trans = new ArrayList<PropertyTransVo>();
        trans.add(new PropertyTransVo("contractAuidtSysid", Buser.class, "userId", "userName","contractAuidtSysName"));
        trans.add(new PropertyTransVo("collectAuditSysid", Buser.class, "userId", "userName","collectAuditSysName"));
        list = DynamicPropertyTransfer.transform(list, trans);
        prjOrder = list.get(0);
        cust = custService.queryCfsCustById(prjOrder.getCustId());
        prj = prjService.getPrjById(prjOrder.getPrjId());
        //募集期利息和募集期利息状态
        raisePrjOrderRepayPlan = prjOrderRepayPlanService.getRaiseOrderRepayPlan(prjOrder.getId());
        //总期数
        totalPeriod  = prjRepayPlanService.getTotalPeriodByPrjId(prjOrder.getPrjId());
        raiseDay = DateTimeUtil.getDaysBetween(prj.getStartBidTime(),prj.getEndBidTime());
        return VIEW;
    }

    public String toAdd(){
        cust = custService.queryCfsCustById(getPKId());
        //募资中prj
        prjList = prjService.findPrjByStatus(CfsConstant.PRJ_STATUS_INVESTING);
        for(CfsPrj cfsPrj:prjList){
        	String prjName = cfsPrj.getTimeLimit()+CodeItemUtil.getCodeNameByKey(UtilConstant.CFS_TIMELIMIT_UNIT, cfsPrj.getTimeLimitUnit())+"-"+
        			cfsPrj.getYearRate()+"%-"+CodeItemUtil.getCodeNameByKey(UtilConstant.CFS_REPAYMENT_TYPE, cfsPrj.getRepayWay());
        	cfsPrj.setPrjName(cfsPrj.getPrjName()+"-"+prjName);
        }
        bankList = codeItemService.getCodeItemByKey(UtilConstant.CFS_BANK_TYPE);
        return ADD;
    }

    public void doAdd(){
    	CfsCust cfsCust = custService.queryCfsCustById(prjOrder.getCustId());
    	if(cfsCust.getCardBack() ==null){
    		cfsCust.setCardBack(cust.getCardBack());
    		custService.updateCfsCust(cfsCust);
    	}
    	if(cfsCust.getCardFace() ==null){
    		cfsCust.setCardFace(cust.getCardFace());
    		custService.updateCfsCust(cfsCust);
    	}
        prjOrderService.addPrjOrder(prjOrder);
    }

    public String orderRepayList(){
        Page page = new Page();
        page.setPageSize(50);
       return setDatagridInputStreamData(prjOrderRepayPlanService.findByOrderIdAndType(prjOrderId,null),page);

    }

    public String infoMain(){
        logonInfo = SessionTool.getUserLogonInfo();
        orderStatusList = codeItemService.getCodeItemByKey(UtilConstant.CFS_PRJ_ORDER_STATUS);
        buserPosCodeList = codeItemService.getCodeItemByKey(UtilConstant.CFS_BUSER_POS_CODE);
        return "orderInfo";
    }


    /**
     * 判断登录人能看到的订单
     * @return
     */
    public String orderInfoList(){
        searchBean.setByLogonInfo(true);
        searchBean.setMyselfInfo(false);
        List<Map<String,Object>> list = prjOrderService.findByCondition(searchBean,getPg());
        for(Map<String,Object> map :list){
            if(map.containsKey("SERVICE_SYSID") && map.get("SERVICE_SYSID")!=null){
               Buser buser = userService.getUserById(Long.valueOf(map.get("SERVICE_SYSID").toString()));
               map.put("BUSER_STATUS",buser.getStatus());
            }
        }
        return setDatagridInputStreamData(list,getPg());
    }

    public List<FiCodeItem> getOrderStatusList() {
        return orderStatusList;
    }

    public void setOrderStatusList(List<FiCodeItem> orderStatusList) {
        this.orderStatusList = orderStatusList;
    }

    public ICfsPrjOrderService getPrjOrderService() {
        return prjOrderService;
    }

    public void setPrjOrderService(ICfsPrjOrderService prjOrderService) {
        this.prjOrderService = prjOrderService;
    }

    public CustOrderBean getSearchBean() {
        return searchBean;
    }

    public void setSearchBean(CustOrderBean searchBean) {
        this.searchBean = searchBean;
    }

    public CfsCust getCust() {
        return cust;
    }

    public void setCust(CfsCust cust) {
        this.cust = cust;
    }

    public CfsPrjOrder getPrjOrder() {
        return prjOrder;
    }

    public void setPrjOrder(CfsPrjOrder prjOrder) {
        this.prjOrder = prjOrder;
    }

    public ICfsCustService getCustService() {
        return custService;
    }

    public void setCustService(ICfsCustService custService) {
        this.custService = custService;
    }

    public List<FiCodeItem> getBankList() {
        return bankList;
    }

    public void setBankList(List<FiCodeItem> bankList) {
        this.bankList = bankList;
    }

    public ICodeItemService getCodeItemService() {
        return codeItemService;
    }

    public void setCodeItemService(ICodeItemService codeItemService) {
        this.codeItemService = codeItemService;
    }

    public List<CfsPrj> getPrjList() {
        return prjList;
    }

    public void setPrjList(List<CfsPrj> prjList) {
        this.prjList = prjList;
    }

    public IPrjService getPrjService() {
        return prjService;
    }

    public void setPrjService(IPrjService prjService) {
        this.prjService = prjService;
    }

    public CfsPrj getPrj() {
        return prj;
    }

    public void setPrj(CfsPrj prj) {
        this.prj = prj;
    }

    public ICfsPrjOrderRepayPlanService getPrjOrderRepayPlanService() {
        return prjOrderRepayPlanService;
    }

    public void setPrjOrderRepayPlanService(ICfsPrjOrderRepayPlanService prjOrderRepayPlanService) {
        this.prjOrderRepayPlanService = prjOrderRepayPlanService;
    }

    public Long getPrjOrderId() {
        return prjOrderId;
    }

    public void setPrjOrderId(Long prjOrderId) {
        this.prjOrderId = prjOrderId;
    }

    public CfsPrjOrderRepayPlan getRaisePrjOrderRepayPlan() {
        return raisePrjOrderRepayPlan;
    }

    public void setRaisePrjOrderRepayPlan(CfsPrjOrderRepayPlan raisePrjOrderRepayPlan) {
        this.raisePrjOrderRepayPlan = raisePrjOrderRepayPlan;
    }

    public Integer getTotalPeriod() {
        return totalPeriod;
    }

    public void setTotalPeriod(Integer totalPeriod) {
        this.totalPeriod = totalPeriod;
    }

    public int getRaiseDay() {
        return raiseDay;
    }

    public void setRaiseDay(int raiseDay) {
        this.raiseDay = raiseDay;
    }

    public List<FiCodeItem> getBuserPosCodeList() {
        return buserPosCodeList;
    }

    public void setBuserPosCodeList(List<FiCodeItem> buserPosCodeList) {
        this.buserPosCodeList = buserPosCodeList;
    }

    public UserLogonInfo getLogonInfo() {
        return logonInfo;
    }

    public void setLogonInfo(UserLogonInfo logonInfo) {
        this.logonInfo = logonInfo;
    }
}
