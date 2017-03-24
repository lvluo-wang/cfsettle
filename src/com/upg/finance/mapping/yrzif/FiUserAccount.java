package com.upg.finance.mapping.yrzif;

/**
 * 用户账号
 * <br/>
 * <B>调用工行服务模块专用</B>
 *
 */
public class FiUserAccount {

	/** 有效 */
	public static final String	TRUSTEE_STATUS_EFFECTIVE	= "1";
	/** 已注销 */
	public static final String	TRUSTEE_STATUS_CANCEL		= "0";

	private Long				uid;
	/** 托管银行 */
	private String				trusteeBank;
	/** 托管账号 */
	private String				trusteeAccount;
	/** 托管状态 */
	private String				trusteeStatus;

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getTrusteeBank() {
		return trusteeBank;
	}

	public void setTrusteeBank(String trusteeBank) {
		this.trusteeBank = trusteeBank;
	}

	public String getTrusteeAccount() {
		return trusteeAccount;
	}

	public void setTrusteeAccount(String trusteeAccount) {
		this.trusteeAccount = trusteeAccount;
	}

	public String getTrusteeStatus() {
		return trusteeStatus;
	}

	public void setTrusteeStatus(String trusteeStatus) {
		this.trusteeStatus = trusteeStatus;
	}

}
