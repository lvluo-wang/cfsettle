package com.upg.cfsettle.order.order;

/**
 * Created by zuo on 2017/4/5.
 */
public class OrderAuditLogBean {

    private Long prjId;
    private String contractNo;

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
