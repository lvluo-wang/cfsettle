package com.upg.finance.icbc.bean.ans;

import javax.xml.bind.annotation.XmlRootElement;

import com.upg.finance.icbc.bean.BasePackage;

@XmlRootElement(name = "package")
public class OpenUserAccountAnsPackage extends BasePackage {

	private OpenUserAccountAns	ans;
	/** 签名结果 */
	private String				signature;

	public OpenUserAccountAns getAns() {
		return ans;
	}

	public void setAns(OpenUserAccountAns ans) {
		this.ans = ans;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

}
