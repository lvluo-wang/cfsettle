package com.upg.finance.icbc.bean.ans;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 用户销户响应
 * 
 */
@XmlRootElement(name = "ans")
public class CancelProjectAccountAns extends BaseAnswer {

	/** 项目编号 */
	@XmlElement(name = "prj_no")
	private String	prjNo;

	public String getPrjNo() {
		return prjNo;
	}

	public void setPrjNo(String prjNo) {
		this.prjNo = prjNo;
	}

}
