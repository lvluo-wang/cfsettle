package com.upg.finance.icbc.bean.ans;

import javax.xml.bind.annotation.XmlRootElement;

import com.upg.finance.icbc.bean.BasePackage;

@XmlRootElement(name = "package")
public class QueryBalanceAnsPackage extends BasePackage {
	private QueryBalanceAns	ans;
	/** 签名结果 */
	private String		signature;

	public QueryBalanceAns getAns() {
		return ans;
	}

	public void setAns(QueryBalanceAns ans) {
		this.ans = ans;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

}
