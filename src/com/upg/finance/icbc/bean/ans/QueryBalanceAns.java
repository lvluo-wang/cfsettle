package com.upg.finance.icbc.bean.ans;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 余额查询响应 
 *
 */
@XmlRootElement(name = "ans")
public class QueryBalanceAns extends BaseAnswer {

	/** 用户标识 */
	private String	uid;
	/** 用户托管子账户 */
	@XmlElement(name = "account_no")
	private String	accountNo;
	/** 本地账户余额 */
	private String	localamount;
	/** 主机余额 */
	private String	hostamount;

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

	public String getLocalamount() {
		return localamount;
	}

	public void setLocalamount(String localamount) {
		this.localamount = localamount;
	}

	public String getHostamount() {
		return hostamount;
	}

	public void setHostamount(String hostamount) {
		this.hostamount = hostamount;
	}

}
