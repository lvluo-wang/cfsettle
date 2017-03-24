package com.upg.finance.icbc.bean.ans;

import javax.xml.bind.annotation.XmlRootElement;

import com.upg.finance.icbc.bean.BasePackage;

@XmlRootElement(name = "package")
public class CancelProjectAccountAnsPackage extends BasePackage {

	private CancelProjectAccountAns	ans;
	/** 签名结果 */
	private String					signature;

	public CancelProjectAccountAns getAns() {
		return ans;
	}

	public void setAns(CancelProjectAccountAns ans) {
		this.ans = ans;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

}
