package com.upg.finance.icbc.bean.ans;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 放款交易响应
 * 
 */
@XmlRootElement(name = "ans")
public class LoanAns extends BaseAnswer {

	/** 项目编号 */
	@XmlElement(name = "prj_no")
	private String	prjNo;
	/** 疑账标志 */
	private String	doubtflag;

	public String getPrjNo() {
		return prjNo;
	}

	public void setPrjNo(String prjNo) {
		this.prjNo = prjNo;
	}

	public String getDoubtflag() {
		return doubtflag;
	}

	public void setDoubtflag(String doubtflag) {
		this.doubtflag = doubtflag;
	}

}
