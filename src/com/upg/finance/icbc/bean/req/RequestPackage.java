package com.upg.finance.icbc.bean.req;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

import com.upg.finance.icbc.bean.BasePackage;

@XmlRootElement(name = "package")
public class RequestPackage extends BasePackage {

	private static final String	REQ_NAME	= "req";

	/**
	 * 请求类需要这里注册
	 * 
	 */
	@XmlElements({ @XmlElement(name = REQ_NAME, type = QueryBalanceReq.class),
			@XmlElement(name = REQ_NAME, type = BindBankCardReq.class),
			@XmlElement(name = REQ_NAME, type = SendBindBankCardVerifyCodeReq.class),
			@XmlElement(name = REQ_NAME, type = CancelProjectAccountReq.class),
			@XmlElement(name = REQ_NAME, type = CancelUserAccountReq.class),
			@XmlElement(name = REQ_NAME, type = ChangeMobileReq.class),
			@XmlElement(name = REQ_NAME, type = SendChangeMobileVerifyCodeReq.class),
			@XmlElement(name = REQ_NAME, type = DistributeInvestmentIncomeReq.class),
			@XmlElement(name = REQ_NAME, type = LoanReq.class),
			@XmlElement(name = REQ_NAME, type = OpenProjectAccountReq.class),
			@XmlElement(name = REQ_NAME, type = OpenUserAccountReq.class),
			@XmlElement(name = REQ_NAME, type = PaymentTransferReq.class),
			@XmlElement(name = REQ_NAME, type = RechargeReq.class),
			@XmlElement(name = REQ_NAME, type = RepaymentReq.class),
			@XmlElement(name = REQ_NAME, type = ConfirmTransferVerifyCodeReq.class),
			@XmlElement(name = REQ_NAME, type = SendTransferVerifyCodeReq.class),
			@XmlElement(name = REQ_NAME, type = TransferTransactionReq.class),
			@XmlElement(name = REQ_NAME, type = WithdrawCashReq.class) })
	private BaseRequest			req;

	/** 签名 */
	private String				signature;

	public BaseRequest getReq() {
		return req;
	}

	public void setReq(BaseRequest req) {
		this.req = req;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

}
