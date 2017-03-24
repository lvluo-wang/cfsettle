package com.upg.finance.icbc.bean.ans;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 用户销户响应
 *
 */
@XmlRootElement(name = "ans")
public class CancelUserAccountAns extends BaseAnswer {
	/** 用户标识 */
	private String	uid;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
}
