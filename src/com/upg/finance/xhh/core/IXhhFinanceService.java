package com.upg.finance.xhh.core;

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

/**
 * 鑫合汇服务接口
 * 
 * 
 * 
 */
public interface IXhhFinanceService {

	/**
	 * 用户开户
	 * 
	 * @param userId
	 *            鑫合汇用户ID
	 * @return
	 */
	public OpenUserAccountAnsPackage openUserAccount(Long userId);

	/**
	 * 用户销户
	 * 
	 * @param userId
	 * @return
	 */
	public CancelUserAccountAnsPackage cancelUserAccount(Long userId);

	/**
	 * 项目开户
	 * 
	 * @param prjId
	 * @return
	 */
	public OpenProjectAccountAnsPackage openProjectAccount(Long prjId);

	/**
	 * 项目销户
	 * 
	 * @param prjId
	 * @return
	 */
	public CancelProjectAccountAnsPackage cancelProjectAccount(Long prjId);

	/**
	 * 充值交易
	 * 
	 * @param userId
	 * @return
	 */
	public RechargeAnsPackage recharge(String ticketNo);

	/**
	 * 余额查询
	 * 
	 * @param userId
	 * @return
	 */
	public QueryBalanceAnsPackage queryBalance(Long userId);

	/**
	 * 支付划款
	 * 
	 * @param prjId
	 * @return
	 */
	public PaymentTransferAnsPackage paymentTransfer(Long prjId);

	/**
	 * 放款交易
	 * 
	 * @param prjId
	 * @return
	 */
	public LoanAnsPackage loan(Long prjId);

	/**
	 * 转让申请-发送手机验证码
	 * 
	 * @param uid
	 * @param prjId
	 * 
	 * @return
	 */
	public SendTransferVerifyCodeAnsPackage sendTransferVerifyCode(Long uid, Long orderId);

	/**
	 * 转让申请-确认手机验证码
	 * 
	 * @param uid
	 *            用户ID
	 * @param orderId
	 *            订单ID
	 * @param verifyCode
	 *            验证码
	 * 
	 * @return
	 */
	public ConfirmTransferVerifyCodeAnsPackage confirmTransferVerifyCode(Long uid, Long orderId, String verifyCode);

	/**
	 * 转让成交
	 * 
	 * @param buyUid
	 * @param orderId
	 * @return
	 */
	public TransferTransactionAnsPackage transferTransaction(Long buyUid, Long orderId);

	/**
	 * 到期还款交易
	 * 
	 * @param investId
	 * @return
	 */
	public RepaymentAnsPackage repayment(Long prjId, Integer repayPeriod);

	/**
	 * 投资收益分配
	 * 
	 * @param prjId
	 * @param repayPeriod
	 * 
	 * @return
	 */
	public DistributeInvestmentIncomeAnsPackage distributeInvestmentIncome(Long prjId, Integer repayPeriod);

	/**
	 * 银行卡绑定-发送手机验证码
	 * 
	 * TODO 确定参数
	 * 
	 * @return
	 */
	public SendBindBankCardVerifyCodeAnsPackage sendBindBankCardVerifyCode();

	/**
	 * 银行卡绑定
	 * 
	 * TODO 确认参数
	 * 
	 * @return
	 */
	public BindBankCardAnsPackage bindBankCard();

	/**
	 * 银行卡解除绑定
	 * 
	 * TODO 确认参数
	 * 
	 * @return
	 */
	public BindBankCardAnsPackage unbindBankCard();

	/**
	 * 提现交易
	 * 
	 * @param userId
	 * @return
	 */
	public WithdrawCashAnsPackage withdrawCash(Long userId);

	/**
	 * 更换手机号-发送手机验证码
	 * 
	 * @param userId
	 * @return
	 */
	public SendChangeMobileVerifyCodeAnsPackage sendChangeMobileVerifyCode(Long userId);

	/**
	 * 更换手机号
	 * 
	 * @param userId
	 * @return
	 */
	public ChangeMobileAnsPackage changeMobile(Long userId);
}
