package com.upg.finance.xhh.action;

import com.upg.finance.icbc.annotation.BusinessIdentifier;
import com.upg.finance.icbc.bean.ans.BindBankCardAnsPackage;
import com.upg.finance.icbc.bean.ans.CancelProjectAccountAnsPackage;
import com.upg.finance.icbc.bean.ans.CancelUserAccountAnsPackage;
import com.upg.finance.icbc.bean.ans.ChangeMobileAnsPackage;
import com.upg.finance.icbc.bean.ans.ConfirmTransferVerifyCodeAnsPackage;
import com.upg.finance.icbc.bean.ans.DistributeInvestmentIncomeAnsPackage;
import com.upg.finance.icbc.bean.ans.LoanAnsPackage;
import com.upg.finance.icbc.bean.ans.OpenProjectAccountAnsPackage;
import com.upg.finance.icbc.bean.ans.OpenUserAccountAnsPackage;
import com.upg.finance.icbc.bean.ans.PaymentTransferAnsPackage;
import com.upg.finance.icbc.bean.ans.QueryBalanceAnsPackage;
import com.upg.finance.icbc.bean.ans.RechargeAnsPackage;
import com.upg.finance.icbc.bean.ans.RepaymentAnsPackage;
import com.upg.finance.icbc.bean.ans.SendBindBankCardVerifyCodeAnsPackage;
import com.upg.finance.icbc.bean.ans.SendChangeMobileVerifyCodeAnsPackage;
import com.upg.finance.icbc.bean.ans.SendTransferVerifyCodeAnsPackage;
import com.upg.finance.icbc.bean.ans.TransferTransactionAnsPackage;
import com.upg.finance.icbc.bean.ans.WithdrawCashAnsPackage;
import com.upg.finance.xhh.core.IXhhFinanceService;
import com.upg.finance.xhh.result.XhhFinanceResult;
import com.upg.ucars.framework.base.BaseAction;

@SuppressWarnings("serial")
public class XhhFinanceAction extends BaseAction {

	/** 鑫合汇用户id */
	private Long				uid;
	/** 鑫合汇项目ID */
	private Long				prjId;
	/** ticket流水号 */
	private String				ticketNo;
	/** 订单Id */
	private Long				orderId;
	/** 手机验证码 */
	private String				verifyCode;
	/** 分期 */
	private Integer				repayPeriod;

	private IXhhFinanceService	xhhFinanceService;

	/**
	 * 用户开户
	 * 
	 * @return
	 */
	@BusinessIdentifier(prefix = "oua", paramName = { "uid" })
	public String openUserAccount() throws Exception {
		String result = null;
		XhhFinanceResult xhhResult = new XhhFinanceResult();
		try {
			OpenUserAccountAnsPackage ansPackage = xhhFinanceService.openUserAccount(uid);
			if (ansPackage != null) {
				ansPackage.setSignature(null);
				xhhResult.setResult(ansPackage);
			}
		} catch (Exception ex) {
			xhhResult.setSuccess(false);
			xhhResult.setErrorMsg(ex.getMessage());
			throw ex;
		} finally {
			result = setInputStreamData(xhhResult);
		}
		return result;
	}

	/**
	 * 用户销户
	 * 
	 * @return
	 */
	@BusinessIdentifier(prefix = "cua", paramName = { "uid" })
	public String cancelUserAccount() throws Exception {
		String result = null;
		XhhFinanceResult xhhResult = new XhhFinanceResult();
		try {
			CancelUserAccountAnsPackage ansPackage = xhhFinanceService.cancelUserAccount(uid);
			if (ansPackage != null) {
				ansPackage.setSignature(null);
				xhhResult.setResult(ansPackage);
			}
		} catch (Exception ex) {
			xhhResult.setSuccess(false);
			xhhResult.setErrorMsg(ex.getMessage());
			throw ex;
		} finally {
			result = setInputStreamData(xhhResult);
		}
		return result;
	}

	/**
	 * 项目开户
	 * 
	 * @return
	 */
	@BusinessIdentifier(prefix = "opa", paramName = { "prjId" })
	public String openProjectAccount() throws Exception {
		String result = null;
		XhhFinanceResult xhhResult = new XhhFinanceResult();
		try {
			OpenProjectAccountAnsPackage ansPackage = xhhFinanceService.openProjectAccount(prjId);
			if (ansPackage != null) {
				ansPackage.setSignature(null);
				xhhResult.setResult(ansPackage);
			}
		} catch (Exception ex) {
			xhhResult.setSuccess(false);
			xhhResult.setErrorMsg(ex.getMessage());
			throw ex;
		} finally {
			result = setInputStreamData(xhhResult);
		}
		return result;
	}

	/**
	 * 项目销户
	 * 
	 * @return
	 */
	@BusinessIdentifier(prefix = "cpa", paramName = { "prjId" })
	public String cancelProjectAccount() throws Exception {
		String result = null;
		XhhFinanceResult xhhResult = new XhhFinanceResult();
		try {
			CancelProjectAccountAnsPackage ansPackage = xhhFinanceService.cancelProjectAccount(prjId);
			if (ansPackage != null) {
				ansPackage.setSignature(null);
				xhhResult.setResult(ansPackage);
			}
		} catch (Exception ex) {
			xhhResult.setSuccess(false);
			xhhResult.setErrorMsg(ex.getMessage());
			throw ex;
		} finally {
			result = setInputStreamData(xhhResult);
		}
		return result;
	}

	/**
	 * 充值交易
	 * 
	 * @return
	 */
	@BusinessIdentifier(prefix = "rc", paramName = { "ticketNo" })
	public String recharge() throws Exception {
		String result = null;
		XhhFinanceResult xhhResult = new XhhFinanceResult();
		try {
			RechargeAnsPackage ansPackage = xhhFinanceService.recharge(ticketNo);
			if (ansPackage != null) {
				ansPackage.setSignature(null);
				xhhResult.setResult(ansPackage);
			}
		} catch (Exception ex) {
			xhhResult.setSuccess(false);
			xhhResult.setErrorMsg(ex.getMessage());
			throw ex;
		} finally {
			result = setInputStreamData(xhhResult);
		}
		return result;
	}

	/**
	 * 余额查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@BusinessIdentifier(prefix = "", paramName = { "uid" })
	public String queryBalance() throws Exception {
		String result = null;
		XhhFinanceResult xhhResult = new XhhFinanceResult();
		try {
			QueryBalanceAnsPackage ansPackage = xhhFinanceService.queryBalance(uid);
			if (ansPackage != null) {
				ansPackage.setSignature(null);
				xhhResult.setResult(ansPackage);
			}
		} catch (Exception ex) {
			xhhResult.setSuccess(false);
			xhhResult.setErrorMsg(ex.getMessage());
			throw ex;
		} finally {
			result = setInputStreamData(xhhResult);
		}
		return result;
	}

	/**
	 * 支付划款
	 * 
	 * @return
	 * @throws Exception
	 */
	public String paymentTransfer() throws Exception {
		String result = null;
		XhhFinanceResult xhhResult = new XhhFinanceResult();
		try {
			PaymentTransferAnsPackage ansPackage = xhhFinanceService.paymentTransfer(prjId);
			if (ansPackage != null) {
				ansPackage.setSignature(null);
				xhhResult.setResult(ansPackage);
			}
		} catch (Exception ex) {
			xhhResult.setSuccess(false);
			xhhResult.setErrorMsg(ex.getMessage());
			throw ex;
		} finally {
			result = setInputStreamData(xhhResult);
		}
		return result;
	}

	/**
	 * 放款交易
	 * 
	 * @return
	 * @throws Exception
	 */
	public String loan() throws Exception {
		String result = null;
		XhhFinanceResult xhhResult = new XhhFinanceResult();
		try {
			LoanAnsPackage ansPackage = xhhFinanceService.loan(prjId);
			if (ansPackage != null) {
				ansPackage.setSignature(null);
				xhhResult.setResult(ansPackage);
			}
		} catch (Exception ex) {
			xhhResult.setSuccess(false);
			xhhResult.setErrorMsg(ex.getMessage());
			throw ex;
		} finally {
			result = setInputStreamData(xhhResult);
		}
		return result;
	}

	/**
	 * 转让申请-发送手机验证码
	 * 
	 * @return
	 * @throws Exception
	 */
	public String sendTransferVerifyCode() throws Exception {
		String result = null;
		XhhFinanceResult xhhResult = new XhhFinanceResult();
		try {
			SendTransferVerifyCodeAnsPackage ansPackage = xhhFinanceService.sendTransferVerifyCode(uid, orderId);
			if (ansPackage != null) {
				ansPackage.setSignature(null);
				xhhResult.setResult(ansPackage);
			}
		} catch (Exception ex) {
			xhhResult.setSuccess(false);
			xhhResult.setErrorMsg(ex.getMessage());
			throw ex;
		} finally {
			result = setInputStreamData(xhhResult);
		}
		return result;
	}

	/**
	 * 转让申请-确认手机验证码
	 * 
	 * @return
	 * @throws Exception
	 */
	public String confirmTransferVerifyCode() throws Exception {
		String result = null;
		XhhFinanceResult xhhResult = new XhhFinanceResult();
		try {
			ConfirmTransferVerifyCodeAnsPackage ansPackage = xhhFinanceService.confirmTransferVerifyCode(uid, orderId,
					verifyCode);
			if (ansPackage != null) {
				ansPackage.setSignature(null);
				xhhResult.setResult(ansPackage);
			}
		} catch (Exception ex) {
			xhhResult.setSuccess(false);
			xhhResult.setErrorMsg(ex.getMessage());
			throw ex;
		} finally {
			result = setInputStreamData(xhhResult);
		}
		return result;
	}

	/**
	 * 转让成交
	 * 
	 * @return
	 * @throws Exception
	 */
	public String transferTransaction() throws Exception {
		String result = null;
		XhhFinanceResult xhhResult = new XhhFinanceResult();
		try {
			TransferTransactionAnsPackage ansPackage = xhhFinanceService.transferTransaction(uid, orderId);
			if (ansPackage != null) {
				ansPackage.setSignature(null);
				xhhResult.setResult(ansPackage);
			}
		} catch (Exception ex) {
			xhhResult.setSuccess(false);
			xhhResult.setErrorMsg(ex.getMessage());
			throw ex;
		} finally {
			result = setInputStreamData(xhhResult);
		}
		return result;
	}

	/**
	 * 到期还款交易
	 * 
	 * @return
	 * @throws Exception
	 */
	public String repayment() throws Exception {
		String result = null;
		XhhFinanceResult xhhResult = new XhhFinanceResult();
		try {
			RepaymentAnsPackage ansPackage = xhhFinanceService.repayment(prjId, repayPeriod);
			if (ansPackage != null) {
				ansPackage.setSignature(null);
				xhhResult.setResult(ansPackage);
			}
		} catch (Exception ex) {
			xhhResult.setSuccess(false);
			xhhResult.setErrorMsg(ex.getMessage());
			throw ex;
		} finally {
			result = setInputStreamData(xhhResult);
		}
		return result;
	}

	/**
	 * 投资收益分配
	 * 
	 * @return
	 * @throws Exception
	 */
	public String distributeInvestmentIncome() throws Exception {
		String result = null;
		XhhFinanceResult xhhResult = new XhhFinanceResult();
		try {
			DistributeInvestmentIncomeAnsPackage ansPackage = xhhFinanceService.distributeInvestmentIncome(prjId,
					repayPeriod);
			if (ansPackage != null) {
				ansPackage.setSignature(null);
				xhhResult.setResult(ansPackage);
			}
		} catch (Exception ex) {
			xhhResult.setSuccess(false);
			xhhResult.setErrorMsg(ex.getMessage());
			throw ex;
		} finally {
			result = setInputStreamData(xhhResult);
		}
		return result;
	}

	/**
	 * 银行卡绑定-发送手机验证码
	 * 
	 * @return
	 * @throws Exception
	 */
	public String sendBindBankCardVerifyCode() throws Exception {
		String result = null;
		XhhFinanceResult xhhResult = new XhhFinanceResult();
		try {
			SendBindBankCardVerifyCodeAnsPackage ansPackage = xhhFinanceService.sendBindBankCardVerifyCode();
			if (ansPackage != null) {
				ansPackage.setSignature(null);
				xhhResult.setResult(ansPackage);
			}
		} catch (Exception ex) {
			xhhResult.setSuccess(false);
			xhhResult.setErrorMsg(ex.getMessage());
			throw ex;
		} finally {
			result = setInputStreamData(xhhResult);
		}
		return result;
	}

	/**
	 * 银行卡绑定/解除绑定
	 * 
	 * @return
	 * @throws Exception
	 */
	public String bindBankCard() throws Exception {
		String result = null;
		XhhFinanceResult xhhResult = new XhhFinanceResult();
		try {
			BindBankCardAnsPackage ansPackage = xhhFinanceService.bindBankCard();
			if (ansPackage != null) {
				ansPackage.setSignature(null);
				xhhResult.setResult(ansPackage);
			}
		} catch (Exception ex) {
			xhhResult.setSuccess(false);
			xhhResult.setErrorMsg(ex.getMessage());
			throw ex;
		} finally {
			result = setInputStreamData(xhhResult);
		}
		return result;
	}

	/**
	 * 提现交易
	 * 
	 * @return
	 * @throws Exception
	 */
	public String withdrawCash() throws Exception {
		String result = null;
		XhhFinanceResult xhhResult = new XhhFinanceResult();
		try {
			WithdrawCashAnsPackage ansPackage = xhhFinanceService.withdrawCash(uid);
			if (ansPackage != null) {
				ansPackage.setSignature(null);
				xhhResult.setResult(ansPackage);
			}
		} catch (Exception ex) {
			xhhResult.setSuccess(false);
			xhhResult.setErrorMsg(ex.getMessage());
			throw ex;
		} finally {
			result = setInputStreamData(xhhResult);
		}
		return result;
	}

	/**
	 * 更换手机号-发送手机验证码
	 * 
	 * @return
	 * @throws Exception
	 */
	public String sendChangeMobileVerifyCode() throws Exception {
		String result = null;
		XhhFinanceResult xhhResult = new XhhFinanceResult();
		try {
			SendChangeMobileVerifyCodeAnsPackage ansPackage = xhhFinanceService.sendChangeMobileVerifyCode(uid);
			if (ansPackage != null) {
				ansPackage.setSignature(null);
				xhhResult.setResult(ansPackage);
			}
		} catch (Exception ex) {
			xhhResult.setSuccess(false);
			xhhResult.setErrorMsg(ex.getMessage());
			throw ex;
		} finally {
			result = setInputStreamData(xhhResult);
		}
		return result;
	}

	/**
	 * 更换手机号
	 * 
	 * @return
	 * @throws Exception
	 */
	public String changeMobile() throws Exception {
		String result = null;
		XhhFinanceResult xhhResult = new XhhFinanceResult();
		try {
			ChangeMobileAnsPackage ansPackage = xhhFinanceService.changeMobile(uid);
			if (ansPackage != null) {
				ansPackage.setSignature(null);
				xhhResult.setResult(ansPackage);
			}
		} catch (Exception ex) {
			xhhResult.setSuccess(false);
			xhhResult.setErrorMsg(ex.getMessage());
			throw ex;
		} finally {
			result = setInputStreamData(xhhResult);
		}
		return result;
	}

	public void setXhhFinanceService(IXhhFinanceService xhhFinanceService) {
		this.xhhFinanceService = xhhFinanceService;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public void setPrjId(Long prjId) {
		this.prjId = prjId;
	}

	public String getTicketNo() {
		return ticketNo;
	}

	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	public void setRepayPeriod(Integer repayPeriod) {
		this.repayPeriod = repayPeriod;
	}

}
