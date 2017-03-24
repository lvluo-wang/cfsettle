package com.upg.finance.icbc.bean.req;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.upg.finance.icbc.annotation.ReqPubConfig;

/**
 * 余额查询请求
 * 
 * 
 */
@ReqPubConfig("X0202")
@XmlRootElement(name = "req")
public class QueryBalanceReq extends BaseRequest {

	/** 用户标识 */
	private String	uid;
	/** 用户标识 */
	@XmlElement(name = "account_no")
	private String	accountNo;
	/** 证件类型 */
	private String	idtype;
	/** 证件号码 */
	private String	idno;

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

	public String getIdtype() {
		return idtype;
	}

	public void setIdtype(String idtype) {
		this.idtype = idtype;
	}

	public String getIdno() {
		return idno;
	}

	public void setIdno(String idno) {
		this.idno = idno;
	}

}
