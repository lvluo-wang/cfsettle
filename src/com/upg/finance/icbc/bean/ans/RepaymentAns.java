package com.upg.finance.icbc.bean.ans;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 到期还款交易响应 
 *
 */
@XmlRootElement(name = "ans")
public class RepaymentAns extends BaseAnswer {
	/** 疑账标志 */
	private String	doubtflag;

	public String getDoubtflag() {
		return doubtflag;
	}

	public void setDoubtflag(String doubtflag) {
		this.doubtflag = doubtflag;
	}

}
