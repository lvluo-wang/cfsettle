package com.upg.cfsettle.cust.core;

import java.util.Date;

/**
 * Created by zuobaoshi on 2017/4/2.
 */
public class CustOrderBean {

    private String prjName;
    private String realName;
    private String mobile;
    private Byte   status;
    private String contractNo;
    private Date startDate;
    private Date endDate;

    //待审核合同信息
    private boolean isFromNeedAudit;
    private String serviceSysName;

    public boolean isFromNeedAudit() {
        return isFromNeedAudit;
    }

    public void setFromNeedAudit(boolean fromNeedAudit) {
        isFromNeedAudit = fromNeedAudit;
    }

    public String getServiceSysName() {
        return serviceSysName;
    }

    public void setServiceSysName(String serviceSysName) {
        this.serviceSysName = serviceSysName;
    }

    public String getPrjName() {
        return prjName;
    }

    public void setPrjName(String prjName) {
        this.prjName = prjName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
