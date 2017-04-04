package com.upg.cfsettle.cust.action;

import com.upg.cfsettle.code.core.ICodeItemService;
import com.upg.cfsettle.cust.core.*;
import com.upg.cfsettle.mapping.ficode.FiCodeItem;
import com.upg.cfsettle.mapping.prj.CfsCust;
import com.upg.cfsettle.mapping.prj.CfsPrj;
import com.upg.cfsettle.mapping.prj.CfsPrjOrder;
import com.upg.cfsettle.mapping.prj.CfsPrjOrderRepayPlan;
import com.upg.cfsettle.prj.core.IPrjService;
import com.upg.cfsettle.util.UtilConstant;
import com.upg.ucars.framework.base.BaseAction;
import com.upg.ucars.util.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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
        return setDatagridInputStreamData(prjOrderService.findByCondition(searchBean,getPg()),getPg());
    }

    public String toView(){
        prjOrder = prjOrderService.getPrjOrderById(getPKId());
        cust = custService.queryCfsCustById(prjOrder.getCustId());
        prj = prjService.getPrjById(prjOrder.getPrjId());
        //募集期利息和募集期利息状态 todo
        raisePrjOrderRepayPlan = prjOrderRepayPlanService.getRaiseOrderRepayPlan(prjOrder.getId());
        //总期数
        totalPeriod  = prjRepayPlanService.getTotalPeriodByPrjId(prjOrder.getPrjId());
        raiseDay = DateTimeUtil.getDaysBetween(prj.getStartBidTime(),prj.getEndBidTime());
        return VIEW;
    }

    public String toAdd(){
        cust = custService.queryCfsCustById(getPKId());
        //募资中prj
        prjList = prjService.findPrjByStatus(Byte.valueOf("3"));
        bankList = codeItemService.getCodeItemByKey(UtilConstant.CFS_BANK_TYPE);
        return ADD;
    }

    public void doAdd(){
        prjOrderService.addPrjOrder(prjOrder);
    }

    public String orderRepayList(){
       return setDatagridInputStreamData(prjOrderRepayPlanService.findByOrderIdAndType(prjOrderId,Byte.valueOf("1")),getPg());

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
}
