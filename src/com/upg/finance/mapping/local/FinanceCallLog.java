package com.upg.finance.mapping.local;

import com.upg.ucars.framework.base.BaseEntity;

@SuppressWarnings("serial")
public class FinanceCallLog extends BaseEntity {

	/** 处理中 */
	public static final String	STATUS_INIT		= "0";
	/** 成功 */
	public static final String	STATUS_SUCCESS	= "1";
	/** 执行失败 */
	public static final String	STATUS_FAIL		= "-1";

	private String				businessIdentifier;
	private String				cmpTxsNo;
	private String				bankTxsNo;
	private String				extsysCallContent;
	private String				extsysReturnContent;
	private String				remark;
	private String				status;

	public String getBusinessIdentifier() {
		return businessIdentifier;
	}

	public void setBusinessIdentifier(String businessIdentifier) {
		this.businessIdentifier = businessIdentifier;
	}

	public String getCmpTxsNo() {
		return cmpTxsNo;
	}

	public void setCmpTxsNo(String cmpTxsNo) {
		this.cmpTxsNo = cmpTxsNo;
	}

	public String getBankTxsNo() {
		return bankTxsNo;
	}

	public void setBankTxsNo(String bankTxsNo) {
		this.bankTxsNo = bankTxsNo;
	}

	public String getExtsysCallContent() {
		return extsysCallContent;
	}

	public void setExtsysCallContent(String extsysCallContent) {
		this.extsysCallContent = extsysCallContent;
	}

	public String getExtsysReturnContent() {
		return extsysReturnContent;
	}

	public void setExtsysReturnContent(String extsysReturnContent) {
		this.extsysReturnContent = extsysReturnContent;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
