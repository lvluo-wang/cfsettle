package com.upg.finance.mapping.yrzif;

public class FiPrjOrderRepayPlan {

	/** 成功 */
	public static final String	TRUST_REPAY_STATUS_SUCCESS	= "1";
	/** 失败 */
	public static final String	TRUST_REPAY_STATUS_FAIL		= "2";
	/** 疑账 */
	public static final String	TRUST_REPAY_STATUS_DOUBT	= "3";

	private Long				id;
	private Long				prjId;
	private Long				prjOrderId;
	private Integer				repayPeriods;
	private Integer				status;
	private String				trustRepayStatus;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPrjId() {
		return prjId;
	}

	public void setPrjId(Long prjId) {
		this.prjId = prjId;
	}

	public Long getPrjOrderId() {
		return prjOrderId;
	}

	public void setPrjOrderId(Long prjOrderId) {
		this.prjOrderId = prjOrderId;
	}

	public Integer getRepayPeriods() {
		return repayPeriods;
	}

	public void setRepayPeriods(Integer repayPeriods) {
		this.repayPeriods = repayPeriods;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getTrustRepayStatus() {
		return trustRepayStatus;
	}

	public void setTrustRepayStatus(String trustRepayStatus) {
		this.trustRepayStatus = trustRepayStatus;
	}

}
