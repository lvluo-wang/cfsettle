package com.upg.finance.icbc.bean.req;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.upg.finance.icbc.annotation.ReqPubConfig;

/**
 * 到期还款交易请求
 * 
 * 
 */
@ReqPubConfig("X0501")
@XmlRootElement(name = "req")
public class RepaymentReq extends BaseRequest {
	/** 项目编号 */
	@XmlElement(name = "prj_no")
	private String	prjNo;
	/** 还款期数 */
	@XmlElement(name = "repay_periods")
	private String	repayPeriods;
	/** 用款人结算账户 */
	@XmlElement(name = "repay_account_no")
	private String	repayAccountNo;
	/** 用款人证件类型 */
	@XmlElement(name = "repay_idtype")
	private String	repayIdtype;
	/** 用款人证件号码 */
	@XmlElement(name = "repay_idno")
	private String	repayIdno;
	/** 还款金额 */
	@XmlElement(name = "repay_amount")
	private String	repayAmount;
	/** 还款份额 */
	@XmlElement(name = "repay_lot")
	private String	repayLot;

	public String getPrjNo() {
		return prjNo;
	}

	public void setPrjNo(String prjNo) {
		this.prjNo = prjNo;
	}

	public String getRepayPeriods() {
		return repayPeriods;
	}

	public void setRepayPeriods(String repayPeriods) {
		this.repayPeriods = repayPeriods;
	}

	public String getRepayAccountNo() {
		return repayAccountNo;
	}

	public void setRepayAccountNo(String repayAccountNo) {
		this.repayAccountNo = repayAccountNo;
	}

	public String getRepayIdtype() {
		return repayIdtype;
	}

	public void setRepayIdtype(String repayIdtype) {
		this.repayIdtype = repayIdtype;
	}

	public String getRepayIdno() {
		return repayIdno;
	}

	public void setRepayIdno(String repayIdno) {
		this.repayIdno = repayIdno;
	}

	public String getRepayAmount() {
		return repayAmount;
	}

	public void setRepayAmount(String repayAmount) {
		this.repayAmount = repayAmount;
	}

	public String getRepayLot() {
		return repayLot;
	}

	public void setRepayLot(String repayLot) {
		this.repayLot = repayLot;
	}

}
