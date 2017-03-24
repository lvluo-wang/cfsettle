package com.upg.finance.mapping.yrzif;

import java.util.Date;

public class FiPrjTrustAccount {

	/** 开户成功 */
	public static final String	STATUS_OPEN_SUCCESS			= "1";
	/** 归集成功 */
	public static final String	STATUS_FUND_SUCCESS			= "2";
	/** 支付成功 */
	public static final String	STATUS_LOAN_SUCCESS			= "3";
	/** 还款中 */
	public static final String	STATUS_REPAYMENT_DEAL		= "4";
	/** 已经还款 */
	public static final String	STATUS_REPAYMENT_SUCCESS	= "5";
	/** 已销户 */
	public static final String	STATUS_CANCEL				= "6";

	private Long				id;
	private Date				ctime;
	private Date				mtime;
	private Long				prjId;
	private String				idType;
	private String				idNo;
	private String				loanAccount;
	private String				loanAccountName;
	private String				status;
	private Date				cancelTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCtime() {
		return ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public Date getMtime() {
		return mtime;
	}

	public void setMtime(Date mtime) {
		this.mtime = mtime;
	}

	public Long getPrjId() {
		return prjId;
	}

	public void setPrjId(Long prjId) {
		this.prjId = prjId;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getLoanAccount() {
		return loanAccount;
	}

	public void setLoanAccount(String loanAccount) {
		this.loanAccount = loanAccount;
	}

	public String getLoanAccountName() {
		return loanAccountName;
	}

	public void setLoanAccountName(String loanAccountName) {
		this.loanAccountName = loanAccountName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
	}

}
