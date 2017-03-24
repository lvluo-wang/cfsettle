package com.upg.finance.icbc.bean.ans;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 充值交易响应 
 *
 */
@XmlRootElement(name = "ans")
public class RechargeAns extends BaseAnswer {
	/** 用户标识 */
	private String	uid;
	/** 用户托管子账户 */
	@XmlElement(name = "account_no")
	private String	accountNo;
	/** 账户余额 */
	private String	amount;
	/** 工行交易流水号 */
	private String	banktxsno;
	/** 疑账标志 */
	private String	doubtflag;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getBanktxsno() {
		return banktxsno;
	}

	public void setBanktxsno(String banktxsno) {
		this.banktxsno = banktxsno;
	}

	public String getDoubtflag() {
		return doubtflag;
	}

	public void setDoubtflag(String doubtflag) {
		this.doubtflag = doubtflag;
	}

}
