package com.upg.finance.icbc.bean.req;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.upg.finance.icbc.annotation.ReqPubConfig;

/**
 * 项目销户请求
 * 
 */
@ReqPubConfig("X0104")
@XmlRootElement(name = "req")
public class CancelProjectAccountReq extends BaseRequest {

	/** 项目编号 */
	@XmlElement(name = "prj_no")
	private String	prjNo;
	/** 项目名称 */
	@XmlElement(name = "prj_name")
	private String	prjName;
	/** 用款人类型 */
	@XmlElement(name = "loan_man_type")
	private String	loanManType;
	/** 用款人名称 */
	@XmlElement(name = "loan_man_name")
	private String	loanManName;
	/** 用款人证件类型 */
	@XmlElement(name = "id_type")
	private String	idType;
	/** 用款人证件号码 */
	@XmlElement(name = "id_no")
	private String	idNo;

	public String getPrjNo() {
		return prjNo;
	}

	public void setPrjNo(String prjNo) {
		this.prjNo = prjNo;
	}

	public String getPrjName() {
		return prjName;
	}

	public void setPrjName(String prjName) {
		this.prjName = prjName;
	}

	public String getLoanManType() {
		return loanManType;
	}

	public void setLoanManType(String loanManType) {
		this.loanManType = loanManType;
	}

	public String getLoanManName() {
		return loanManName;
	}

	public void setLoanManName(String loanManName) {
		this.loanManName = loanManName;
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

}
