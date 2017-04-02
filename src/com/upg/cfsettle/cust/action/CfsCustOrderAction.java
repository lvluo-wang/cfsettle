package com.upg.cfsettle.cust.action;

import com.upg.cfsettle.code.core.ICodeItemService;
import com.upg.cfsettle.cust.core.CustOrderBean;
import com.upg.cfsettle.cust.core.ICfsCustService;
import com.upg.cfsettle.cust.core.ICfsPrjOrderService;
import com.upg.cfsettle.mapping.ficode.FiCodeItem;
import com.upg.cfsettle.mapping.prj.CfsCust;
import com.upg.cfsettle.mapping.prj.CfsPrj;
import com.upg.cfsettle.mapping.prj.CfsPrjOrder;
import com.upg.cfsettle.prj.core.IPrjService;
import com.upg.cfsettle.util.UtilConstant;
import com.upg.ucars.framework.base.BaseAction;

import java.util.List;

/**
 * Created by zuobaoshi on 2017/4/2.
 */
public class CfsCustOrderAction extends BaseAction {

    private ICfsPrjOrderService prjOrderService;

    private CustOrderBean searchBean;

    private CfsCust cust;
    private CfsPrjOrder prjOrder;

    private ICfsCustService custService;
    private List<FiCodeItem> bankList;
    private ICodeItemService codeItemService;
    private List<CfsPrj> prjList;
    private IPrjService prjService;
    private List<FiCodeItem> orderStatusList;


    public String main(){
        orderStatusList = codeItemService.getCodeItemByKey(UtilConstant.CFS_PRJ_ORDER_STATUS);
        return MAIN;
    }


    public String list(){
        System.out.print("xxx");
        //todo
        return setDatagridInputStreamData(prjOrderService.findByCondition(searchBean,getPg()),getPg());
    }

    public String toView(){
        //TODO
        return VIEW;
    }

    public String toAdd(){
        cust = custService.queryCfsCustById(getPKId());
        prjList = prjService.findPrjByStatus(Byte.valueOf("3"));
        bankList = codeItemService.getCodeItemByKey(UtilConstant.CFS_BANK_TYPE);
        return ADD;
    }

    public void doAdd(){
        prjOrderService.addPrjOrder(prjOrder);
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
}
