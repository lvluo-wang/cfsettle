package com.upg.finance.icbc;

import com.ucars.tsc.annotation.Interceptor;
import com.ucars.tsc.annotation.Processor;
import com.ucars.tsc.annotation.ResultTransfor;
import com.ucars.tsc.annotation.Transportor;
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
import com.upg.finance.icbc.bean.req.BindBankCardReq;
import com.upg.finance.icbc.bean.req.CancelProjectAccountReq;
import com.upg.finance.icbc.bean.req.CancelUserAccountReq;
import com.upg.finance.icbc.bean.req.ChangeMobileReq;
import com.upg.finance.icbc.bean.req.ConfirmTransferVerifyCodeReq;
import com.upg.finance.icbc.bean.req.DistributeInvestmentIncomeReq;
import com.upg.finance.icbc.bean.req.LoanReq;
import com.upg.finance.icbc.bean.req.OpenProjectAccountReq;
import com.upg.finance.icbc.bean.req.OpenUserAccountReq;
import com.upg.finance.icbc.bean.req.PaymentTransferReq;
import com.upg.finance.icbc.bean.req.QueryBalanceReq;
import com.upg.finance.icbc.bean.req.RechargeReq;
import com.upg.finance.icbc.bean.req.RepaymentReq;
import com.upg.finance.icbc.bean.req.SendBindBankCardVerifyCodeReq;
import com.upg.finance.icbc.bean.req.SendChangeMobileVerifyCodeReq;
import com.upg.finance.icbc.bean.req.SendTransferVerifyCodeReq;
import com.upg.finance.icbc.bean.req.TransferTransactionReq;
import com.upg.finance.icbc.bean.req.WithdrawCashReq;

/**
 * 
 * 调用工商银行服务接口
 * 
 * 
 */
@Interceptor(beanName = { "appendUrlInterceptor", "resultErrorInterceptor" })
@Processor(beanName = "icbcRequestProcoessor")
@ResultTransfor(beanName = "icbcResultTransfor")
public interface IcbcFundService {

	/**
	 * 用户开户
	 * 
	 * @param openUserAccountReq
	 * @return
	 */
	@Transportor(beanName = "openUserAccountTransportorTest")
	public OpenUserAccountAnsPackage openUserAccount(OpenUserAccountReq openUserAccountReq);

	/**
	 * 用户销户
	 * 
	 * @param cancelUserAccountReq
	 * @return
	 */
	@Transportor(beanName = "cancelUserAccountTransportorTest")
	public CancelUserAccountAnsPackage cancelUserAccount(CancelUserAccountReq cancelUserAccountReq);

	/**
	 * 项目开户
	 * 
	 * @param openProjectAccountReq
	 * @return
	 */
	@Transportor(beanName = "openProjectAccountTansportorTest")
	public OpenProjectAccountAnsPackage openProjectAccount(OpenProjectAccountReq openProjectAccountReq);

	/**
	 * 项目销户
	 * 
	 * @param cancelProjectAccountReq
	 * @return
	 */
	@Transportor(beanName="cancelProjectAccountTransportorTest")
	public CancelProjectAccountAnsPackage cancelProjectAccount(CancelProjectAccountReq cancelProjectAccountReq);

	/**
	 * 充值交易
	 * 
	 * @param rechargeReq
	 * @return
	 */
	public RechargeAnsPackage recharge(RechargeReq rechargeReq);

	/**
	 * 余额查询
	 * 
	 * @param queryBalanceReq
	 * @return
	 */
	public QueryBalanceAnsPackage queryBalance(QueryBalanceReq queryBalanceReq);

	/**
	 * 支付划款
	 * 
	 * @param paymentTransferReq
	 * @return
	 */
	public PaymentTransferAnsPackage paymentTransfer(PaymentTransferReq paymentTransferReq);

	/**
	 * 放款交易
	 * 
	 * @param loanReq
	 * @return
	 */
	public LoanAnsPackage loan(LoanReq loanReq);

	/**
	 * 转让申请-发送手机验证码
	 * 
	 * @param sendTransferVerifyCodeReq
	 * @return
	 */
	public SendTransferVerifyCodeAnsPackage sendTransferVerifyCode(SendTransferVerifyCodeReq sendTransferVerifyCodeReq);

	/**
	 * 转让申请-确认手机验证码
	 * 
	 * @param confirmTransferVerifyCodeReq
	 * @return
	 */
	public ConfirmTransferVerifyCodeAnsPackage confirmTransferVerifyCode(
			ConfirmTransferVerifyCodeReq confirmTransferVerifyCodeReq);

	/**
	 * 转让成交
	 * 
	 * @param transferTransactionReq
	 * @return
	 */
	public TransferTransactionAnsPackage transferTransaction(TransferTransactionReq transferTransactionReq);

	/**
	 * 到期还款交易
	 * 
	 * @param repaymentReq
	 * @return
	 */
	public RepaymentAnsPackage repayment(RepaymentReq repaymentReq);

	/**
	 * 投资收益分配
	 * 
	 * @param distributeInvestmentIncomeReq
	 * @return
	 */
	public DistributeInvestmentIncomeAnsPackage distributeInvestmentIncome(
			DistributeInvestmentIncomeReq distributeInvestmentIncomeReq);

	/**
	 * 银行卡绑定-发送手机验证码
	 * 
	 * @param sendBindBankCardVerifyCodeReq
	 * @return
	 */
	public SendBindBankCardVerifyCodeAnsPackage sendBindBankCardVerifyCode(
			SendBindBankCardVerifyCodeReq sendBindBankCardVerifyCodeReq);

	/**
	 * 银行卡绑定/解除绑定
	 * 
	 * @param bindBankCardReq
	 * @return
	 */
	public BindBankCardAnsPackage bindBankCard(BindBankCardReq bindBankCardReq);

	/**
	 * 提现交易
	 * 
	 * @param withdrawCashReq
	 * @return
	 */
	public WithdrawCashAnsPackage withdrawCash(WithdrawCashReq withdrawCashReq);

	/**
	 * 更换手机号-发送手机验证码
	 * 
	 * @param sendChangeMobileVerifyCodeReq
	 * @return
	 */
	public SendChangeMobileVerifyCodeAnsPackage sendChangeMobileVerifyCode(
			SendChangeMobileVerifyCodeReq sendChangeMobileVerifyCodeReq);

	/**
	 * 更换手机号
	 * 
	 * @param changeMobileReq
	 * @return
	 */
	public ChangeMobileAnsPackage changeMobile(ChangeMobileReq changeMobileReq);
}
