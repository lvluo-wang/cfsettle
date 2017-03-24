package com.upg.finance.icbc.bean.ans;

import javax.xml.bind.annotation.XmlRootElement;

import com.upg.finance.icbc.bean.Pub;

/**
 * 回应公共信息
 * 
 */
@XmlRootElement(name = "pub")
public class AnswerPub extends Pub {

	/** 清算日期 */
	private String	settledate;
	/** 返回代码 */
	private String	retcode;
	/** 返回信息 */
	private String	retmsg;
	/** 公司方流水号 */
	private String	cmptxsno;

	public String getSettledate() {
		return settledate;
	}

	public void setSettledate(String settledate) {
		this.settledate = settledate;
	}

	public String getRetcode() {
		return retcode;
	}

	public void setRetcode(String retcode) {
		this.retcode = retcode;
	}

	public String getRetmsg() {
		return retmsg;
	}

	public void setRetmsg(String retmsg) {
		this.retmsg = retmsg;
	}

	public String getCmptxsno() {
		return cmptxsno;
	}

	public void setCmptxsno(String cmptxsno) {
		this.cmptxsno = cmptxsno;
	}

}
