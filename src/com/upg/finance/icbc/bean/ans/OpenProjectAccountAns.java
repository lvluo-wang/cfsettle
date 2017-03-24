package com.upg.finance.icbc.bean.ans;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 项目开户响应
 * 
 */
@XmlRootElement(name = "ans")
public class OpenProjectAccountAns extends BaseAnswer {
	private String	prjNo;

	public String getPrjNo() {
		return prjNo;
	}

	public void setPrjNo(String prjNo) {
		this.prjNo = prjNo;
	}

}
