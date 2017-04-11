package com.upg.cfsettle.order.action;

import com.upg.cfsettle.code.core.ICodeItemService;
import com.upg.cfsettle.cust.core.CustOrderBean;
import com.upg.cfsettle.cust.core.ICfsCustService;
import com.upg.cfsettle.cust.core.ICfsPrjOrderService;
import com.upg.cfsettle.mapping.ficode.FiCodeItem;
import com.upg.cfsettle.mapping.prj.CfsCust;
import com.upg.cfsettle.mapping.prj.CfsPrj;
import com.upg.cfsettle.mapping.prj.CfsPrjOrder;
import com.upg.cfsettle.mapping.prj.CfsPrjOrderAuditLog;
import com.upg.cfsettle.order.order.ICfsPrjOrderAuditLogService;
import com.upg.cfsettle.order.order.OrderAuditLogBean;
import com.upg.cfsettle.prj.core.IPrjService;
import com.upg.cfsettle.util.CfsConstant;
import com.upg.cfsettle.util.UtilConstant;
import com.upg.ucars.basesystem.UcarsHelper;
import com.upg.ucars.framework.base.BaseAction;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by zuo on 2017/4/5.
 */
public class CfsOrderContractAction extends BaseAction {

    @Autowired
    private ICfsPrjOrderService prjOrderService;
    @Autowired
    private ICodeItemService codeItemService;
    @Autowired
    private ICfsCustService custService;
    @Autowired
    private IPrjService prjService;
    @Autowired
    private ICfsPrjOrderAuditLogService prjOrderAuditLogService;




    private CustOrderBean orderBean;
    private List<FiCodeItem> orderStatusList;
    private CfsCust cust;
    private CfsPrjOrder prjOrder;
    private CfsPrj prj;
    private CfsPrjOrderAuditLog prjOrderAuditLog;
    private OrderAuditLogBean auditLogBean;

    private Long prjId;
    private String contractNo;



    public String main(){
        orderStatusList = codeItemService.getCodeItemByKey(UtilConstant.CFS_PRJ_ORDER_STATUS);
        return SUCCESS;
    }

    public String toReview(){
        prjOrder = prjOrderService.getPrjOrderById(getPKId());
        if(!prjOrder.getStatus().equals(CfsConstant.PRJ_ORDER_STATUS_AUDIT)
                || !prjOrder.getServiceSysid().equals(CfsConstant.PRJ_ORDER_STATUS_REJECT)){
            UcarsHelper.throwActionException("该订单状态不能审核");
        }
        cust = custService.queryCfsCustById(prjOrder.getCustId());
        prj = prjService.getPrjById(prjOrder.getPrjId());
        return SUCCESS;
    }

    public String list(){
        orderBean.setFromNeedAudit(true);
        return setDatagridInputStreamData(prjOrderService.findByCondition(orderBean,getPg()),getPg());
    }


    public void doReview(){
        prjOrderAuditLogService.prjOrderAudit(prjOrder,prjOrderAuditLog);
    }


    public String toOrderLog(){
        return SUCCESS;
    }

    public String orderLogList(){
        return setDatagridInputStreamData(prjOrderAuditLogService.findOrderLog(auditLogBean,getPg()),getPg());
    }

    public CustOrderBean getOrderBean() {
        return orderBean;
    }

    public void setOrderBean(CustOrderBean orderBean) {
        this.orderBean = orderBean;
    }

    public List<FiCodeItem> getOrderStatusList() {
        return orderStatusList;
    }

    public void setOrderStatusList(List<FiCodeItem> orderStatusList) {
        this.orderStatusList = orderStatusList;
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

    public CfsPrj getPrj() {
        return prj;
    }

    public void setPrj(CfsPrj prj) {
        this.prj = prj;
    }

    public CfsPrjOrderAuditLog getPrjOrderAuditLog() {
        return prjOrderAuditLog;
    }

    public void setPrjOrderAuditLog(CfsPrjOrderAuditLog prjOrderAuditLog) {
        this.prjOrderAuditLog = prjOrderAuditLog;
    }

    public OrderAuditLogBean getAuditLogBean() {
        return auditLogBean;
    }

    public void setAuditLogBean(OrderAuditLogBean auditLogBean) {
        this.auditLogBean = auditLogBean;
    }

    public Long getPrjId() {
        return prjId;
    }

    public void setPrjId(Long prjId) {
        this.prjId = prjId;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }
}
