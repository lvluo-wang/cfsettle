package com.upg.finance.mapping.yrzif;

public class FiOutTicket {

	/** 成功 */
	public static final String	TRUST_STATUS_SUCCESS	= "1";
	/** 失败 */
	public static final String	TRUST_STATUS_FAIL		= "2";
	/** 疑账 */
	public static final String	TRUST_STATUS_DOUBT		= "3";

	private Long				id;
	private String				ticketNo;
	private Long				uid;
	private Long				amount;
	private String				trustStatus;
	private String				bankTxsno;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTicketNo() {
		return ticketNo;
	}

	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public String getTrustStatus() {
		return trustStatus;
	}

	public void setTrustStatus(String trustStatus) {
		this.trustStatus = trustStatus;
	}

	public String getBankTxsno() {
		return bankTxsno;
	}

	public void setBankTxsno(String bankTxsno) {
		this.bankTxsno = bankTxsno;
	}

}
