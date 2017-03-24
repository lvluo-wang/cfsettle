package com.upg.finance.xhh.core;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.upg.finance.icbc.IcbcFundService;
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
import com.upg.finance.icbc.bean.req.CancelProjectAccountReq;
import com.upg.finance.icbc.bean.req.CancelUserAccountReq;
import com.upg.finance.icbc.bean.req.ConfirmTransferVerifyCodeReq;
import com.upg.finance.icbc.bean.req.DistributeInvestmentIncomeReq;
import com.upg.finance.icbc.bean.req.LoanReq;
import com.upg.finance.icbc.bean.req.OpenProjectAccountReq;
import com.upg.finance.icbc.bean.req.OpenUserAccountReq;
import com.upg.finance.icbc.bean.req.PaymentTransferReq;
import com.upg.finance.icbc.bean.req.QueryBalanceReq;
import com.upg.finance.icbc.bean.req.RechargeReq;
import com.upg.finance.icbc.bean.req.RepaymentReq;
import com.upg.finance.icbc.bean.req.SendTransferVerifyCodeReq;
import com.upg.finance.icbc.bean.req.TransferTransactionReq;
import com.upg.finance.log.core.IFinanceCallLogService;
import com.upg.finance.mapping.local.FinanceCallLog;
import com.upg.finance.mapping.yrzif.FiLoanbankAccount;
import com.upg.finance.mapping.yrzif.FiOutTicket;
import com.upg.finance.mapping.yrzif.FiPrj;
import com.upg.finance.mapping.yrzif.FiPrjOrder;
import com.upg.finance.mapping.yrzif.FiPrjOrderRepayPlan;
import com.upg.finance.mapping.yrzif.FiPrjTrustAccount;
import com.upg.finance.mapping.yrzif.FiUser;
import com.upg.finance.mapping.yrzif.FiUserAccount;
import com.upg.ucars.framework.annotation.Service;
import com.upg.ucars.framework.base.BaseService;

/**
 * 
 * TODO
 * 
 * <pre>
 * 和普通的service一样，其它到外层转化。
 * 
 * 1、鑫合汇返回基本类型数据Bean
 * 2、异常处理
 * 
 * </pre>
 */
@Service
public class XhhFinanceServiceImpl extends BaseService implements IXhhFinanceService {

	private static final String		ID_TYPE	= "01";

	@Resource
	private IcbcFundService			icbcFundService;

	@Resource
	private IFinanceCallLogService	financeCallLogService;

	@Resource
	private IXhhFinanceDao			xhhFinanceDao;

	private boolean isContinue() {
		boolean result = true;
		FinanceCallLog log = financeCallLogService.get();
		if (log != null && FinanceCallLog.STATUS_SUCCESS.equals(log.getStatus())) {
			result = false;
		}
		return result;
	}

	private static final void throwRuntimeExceptionIfNull(Object obj, String message) {
		if (obj == null) {
			throw new RuntimeException(message);
		}
	}

	@Override
	public OpenUserAccountAnsPackage openUserAccount(Long userId) {
		// 校验参数
		throwRuntimeExceptionIfNull(userId, "用户ID为空");

		OpenUserAccountAnsPackage ansPackage = null;

		boolean isContinue = isContinue();

		if (isContinue) {
			FiUser user = xhhFinanceDao.get(FiUser.class, userId);
			throwRuntimeExceptionIfNull(user, "用户(" + userId + ")不存在");

			FiUserAccount userAccount = xhhFinanceDao.get(FiUserAccount.class, userId);
			throwRuntimeExceptionIfNull(userAccount, "用户(" + userId + ")账号记录不存在");

			// 调用工行开户接口
			OpenUserAccountReq req = new OpenUserAccountReq();
			req.setUid(userId.toString());
			req.setRealName(user.getRealName());
			req.setIdtype(ID_TYPE);
			req.setIdno(user.getPersonId());
			req.setMobileNo(user.getMobile());
			// TODO 提现银行卡号
			ansPackage = icbcFundService.openUserAccount(req);

			// 设置用户的托管账号
			userAccount.setTrusteeBank("icbc");
			String trusteeAccount = ansPackage.getAns().getAccountNo();
			userAccount.setTrusteeAccount(trusteeAccount);
			userAccount.setTrusteeStatus(FiUserAccount.TRUSTEE_STATUS_EFFECTIVE);
			xhhFinanceDao.update(userAccount);
		}

		return ansPackage;
	}

	@Override
	public CancelUserAccountAnsPackage cancelUserAccount(Long userId) {
		// 校验参数
		throwRuntimeExceptionIfNull(userId, "用户ID为空");
		CancelUserAccountAnsPackage ansPackage = null;

		boolean isContinue = isContinue();
		if (isContinue) {

			FiUser user = xhhFinanceDao.get(FiUser.class, userId);
			throwRuntimeExceptionIfNull(user, "用户(" + userId + ")不存在");

			FiUserAccount userAccount = xhhFinanceDao.get(FiUserAccount.class, userId);
			throwRuntimeExceptionIfNull(userAccount, "用户(" + userId + ")账号记录不存在");

			// 工行调用
			CancelUserAccountReq req = new CancelUserAccountReq();
			req.setUid(userId.toString());
			req.setRealName(user.getRealName());
			req.setIdtype(ID_TYPE);
			req.setIdno(user.getPersonId());
			ansPackage = icbcFundService.cancelUserAccount(req);

			// 设置用户账号已注销
			userAccount.setTrusteeStatus(FiUserAccount.TRUSTEE_STATUS_CANCEL);
			xhhFinanceDao.update(userAccount);

		}
		return ansPackage;
	}

	@Override
	public OpenProjectAccountAnsPackage openProjectAccount(Long prjId) {
		// 校验参数
		throwRuntimeExceptionIfNull(prjId, "项目ID为空");
		OpenProjectAccountAnsPackage ansPackage = null;

		boolean isContinue = isContinue();
		if (isContinue) {

			FiPrj prj = xhhFinanceDao.get(FiPrj.class, prjId);
			throwRuntimeExceptionIfNull(prj, "项目(" + prjId + ")不存在");

			Long fundAccount = prj.getFundAccount();
			FiLoanbankAccount loanbankAccount = xhhFinanceDao.get(FiLoanbankAccount.class, fundAccount);
			throwRuntimeExceptionIfNull(loanbankAccount, "项目(" + prjId + ")没有银行账号信息");

			FiUser user = xhhFinanceDao.get(FiUser.class, prj.getUid());
			throwRuntimeExceptionIfNull(user, "项目(" + prjId + ")没有用户信息");

			OpenProjectAccountReq req = new OpenProjectAccountReq();
			req.setPrjNo(prj.getPrjNo());
			req.setPrjName(prj.getPrjName());
			// TODO 项目总份额
			DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			Date startBidTime = prj.getStartBidTime();
			req.setStartBidTime(dateFormat.format(startBidTime));
			Date endBidTime = prj.getEndBidTime();
			req.setEndBidTime(dateFormat.format(endBidTime));
			// TODO 放款日期

			req.setLoanManType(user.getUidType() == null ? "1" : user.getUidType().toString());
			req.setLoanManName(user.getRealName());
			req.setIdType(ID_TYPE);
			req.setIdNo(user.getPersonId());

			req.setLoanAccount(loanbankAccount.getAccountNo());
			req.setLoanAccountName(loanbankAccount.getAccountName());
			// TODO 借款用途

			ansPackage = icbcFundService.openProjectAccount(req);

			// 记录项目开户信息
			FiPrjTrustAccount trustAccount = new FiPrjTrustAccount();
			trustAccount.setPrjId(prjId);
			trustAccount.setIdType(user.getUidType() == null ? null : user.getUidType().toString());
			trustAccount.setIdNo(user.getPersonId());
			// TODO 开通账号、名称
			// trustAccount.setLoanAccount(loanbankAccount.getAccountNo());
			// trustAccount.setLoanAccountName(loanbankAccount.getAccountName());
			trustAccount.setStatus(FiPrjTrustAccount.STATUS_OPEN_SUCCESS);
			xhhFinanceDao.save(trustAccount);

		}

		return ansPackage;
	}

	@Override
	public CancelProjectAccountAnsPackage cancelProjectAccount(Long prjId) {
		// 校验参数
		throwRuntimeExceptionIfNull(prjId, "项目ID为空");
		CancelProjectAccountAnsPackage ansPackage = null;

		boolean isContinue = isContinue();
		if (isContinue) {

			FiPrj prj = xhhFinanceDao.get(FiPrj.class, prjId);
			throwRuntimeExceptionIfNull(prj, "项目(" + prjId + ")不存在");

			FiUser user = xhhFinanceDao.get(FiUser.class, prj.getUid());
			throwRuntimeExceptionIfNull(user, "项目(" + prjId + ")没有用户信息");

			CancelProjectAccountReq req = new CancelProjectAccountReq();
			req.setPrjNo(prj.getPrjNo());
			req.setPrjName(prj.getPrjName());
			req.setLoanManType(user.getUidType() == null ? "1" : user.getUidType().toString());
			req.setLoanManName(user.getRealName());
			req.setIdType(ID_TYPE);
			req.setIdNo(user.getPersonId());

			ansPackage = icbcFundService.cancelProjectAccount(req);

			// 记录项目注销信息
			FiPrjTrustAccount trustAccount = getFiPrjTrustAccountByPrjId(prjId);
			trustAccount.setStatus(FiPrjTrustAccount.STATUS_CANCEL);
			trustAccount.setCancelTime(new Date());
			xhhFinanceDao.update(trustAccount);
		}

		return ansPackage;
	}

	@Override
	public RechargeAnsPackage recharge(String ticketNo) {
		// 校验参数
		throwRuntimeExceptionIfNull(ticketNo, "ticketNo为空");
		RechargeAnsPackage ansPackage = null;

		boolean isContinue = isContinue();
		if (isContinue) {

			FiOutTicket ticket = null;
			String ticketHql = "from FiOutTicket where ticketNo=?";
			List<FiOutTicket> ticketList = xhhFinanceDao.findEntities(ticketHql, ticketNo);
			if (ticketList != null && !ticketList.isEmpty()) {
				ticket = ticketList.get(0);
			}
			throwRuntimeExceptionIfNull(ticket, "ticketNo(" + ticketNo + ")没有对外单据流水信息");

			FiUser user = xhhFinanceDao.get(FiUser.class, ticket.getUid());
			throwRuntimeExceptionIfNull(user, "ticketNo(" + ticketNo + ")没有用户信息");

			FiUserAccount userAccount = xhhFinanceDao.get(FiUserAccount.class, user.getUid());
			throwRuntimeExceptionIfNull(userAccount, "ticketNo(" + ticketNo + ")没有账号记录");

			RechargeReq req = new RechargeReq();
			req.setUid("" + user.getUid());
			req.setAccountNo(userAccount.getTrusteeAccount());
			req.setIdtype(ID_TYPE);
			req.setIdno(user.getPersonId());
			req.setRechargeAmount(ticket.getAmount().toString());
			req.setThirdNo(ticketNo);
			ansPackage = icbcFundService.recharge(req);

			// TODO 疑账
			// 更新对外单据流水信息
			String trustStatus = FiOutTicket.TRUST_STATUS_SUCCESS;
			ticket.setTrustStatus(trustStatus);
			ticket.setBankTxsno(ansPackage.getAns().getBanktxsno());
			xhhFinanceDao.update(ticket);

		}

		return ansPackage;
	}

	@Override
	public QueryBalanceAnsPackage queryBalance(Long userId) {
		// 校验参数
		throwRuntimeExceptionIfNull(userId, "用户ID为空");
		QueryBalanceAnsPackage ansPackage = null;

		boolean isContinue = isContinue();
		if (isContinue) {
			FiUser user = xhhFinanceDao.get(FiUser.class, userId);
			throwRuntimeExceptionIfNull(user, "用户(" + userId + ")不存在");

			FiUserAccount userAccount = xhhFinanceDao.get(FiUserAccount.class, userId);
			throwRuntimeExceptionIfNull(userAccount, "用户(" + userId + ")没有账号记录");

			QueryBalanceReq req = new QueryBalanceReq();
			req.setUid(userId.toString());
			req.setAccountNo(userAccount.getTrusteeAccount());
			req.setIdtype(ID_TYPE);
			req.setIdno(user.getPersonId());
			ansPackage = icbcFundService.queryBalance(req);

			// do nothing

		}

		return ansPackage;
	}

	@Override
	public PaymentTransferAnsPackage paymentTransfer(Long prjId) {
		// 校验参数
		throwRuntimeExceptionIfNull(prjId, "prjId为空");
		PaymentTransferAnsPackage ansPackage = null;

		boolean isContinue = isContinue();
		if (isContinue) {

			FiPrj prj = xhhFinanceDao.get(FiPrj.class, prjId);
			throwRuntimeExceptionIfNull(prj, "项目(" + prjId + ")不存在");

			FiPrjTrustAccount prjAccount = null;
			String prjAccountHql = "from FiPrjTrustAccount where prjId=? and status=?";
			List<FiPrjTrustAccount> prjAccountList = xhhFinanceDao.findEntities(prjAccountHql, new Object[] { prjId,
					FiPrjTrustAccount.STATUS_OPEN_SUCCESS });
			if (prjAccountList != null && prjAccountList.size() == 1) {
				prjAccount = prjAccountList.get(0);
			}
			throwRuntimeExceptionIfNull(prjAccount, "项目(" + prjId + ")没有开通成功的托管账号信息");

			String ordeListHql = "from FiPrjOrder where prjId=? and trustPayStatus in ('2','3')";
			List<FiPrjOrder> ordeList = xhhFinanceDao.findEntities(ordeListHql, new Object[] { prjId });

			if (ordeList != null && !ordeList.isEmpty()) {
				for (FiPrjOrder order : ordeList) {

					FiUser user = xhhFinanceDao.get(FiUser.class, order.getUid());
					FiUserAccount userAccount = xhhFinanceDao.get(FiUserAccount.class, order.getUid());

					PaymentTransferReq req = new PaymentTransferReq();
					req.setUid(order.getUid().toString());
					req.setAccountNo(userAccount.getTrusteeAccount());
					req.setIdtype(ID_TYPE);
					req.setIdno(user.getPersonId());
					req.setOrderNo(order.getOrderNo());
					req.setAmount(order.getMoney().toString());
					// TODO 购买份额
					req.setLot(order.getMoney().toString());
					req.setPrjNo(prj.getPrjNo());
					req.setLoanAccount(prjAccount.getLoanAccount());
					ansPackage = icbcFundService.paymentTransfer(req);
				}
			}
		}
		return ansPackage;
	}

	@Override
	public LoanAnsPackage loan(Long prjId) {
		// 校验参数
		throwRuntimeExceptionIfNull(prjId, "prjId为空");

		LoanAnsPackage ansPackage = null;

		boolean isContinue = isContinue();
		if (isContinue) {

			FiPrj prj = xhhFinanceDao.get(FiPrj.class, prjId);
			throwRuntimeExceptionIfNull(prj, "项目(" + prjId + ")不存在");

			FiUser user = xhhFinanceDao.get(FiUser.class, prj.getUid());
			throwRuntimeExceptionIfNull(user, "项目(" + prjId + ")没有用户信息");

			FiLoanbankAccount loanbankAccount = xhhFinanceDao.get(FiLoanbankAccount.class, prj.getFundAccount());
			throwRuntimeExceptionIfNull(loanbankAccount, "项目(" + prjId + ")没有借款人银行卡信息");

			FiPrjTrustAccount trustAccount = getFiPrjTrustAccountByPrjId(prjId);
			throwRuntimeExceptionIfNull(trustAccount, "项目(" + prjId + ")没有托管子账号信息");

			String totalAmountHql = "select count(money) from FiPrjOrder where prjId=? and status='2' and trustPayStatus='1' ";
			Long totalAmount = ((Number) xhhFinanceDao.findEntities(totalAmountHql, new Object[] { prjId }).iterator())
					.longValue();

			LoanReq req = new LoanReq();
			req.setPrjNo(prj.getPrjNo());
			req.setIdType(ID_TYPE);
			req.setIdNo(user.getPersonId());
			req.setLoanAccount(loanbankAccount.getAccountNo());
			req.setLoanAccountName(loanbankAccount.getAccountName());
			req.setLoanAmount(totalAmount.toString());
			req.setLoanLot(totalAmount.toString());

			ansPackage = icbcFundService.loan(req);

			// 更新项目托管子账号信息
			trustAccount.setStatus(FiPrjTrustAccount.STATUS_LOAN_SUCCESS);
			xhhFinanceDao.update(trustAccount);

		}

		return ansPackage;
	}

	@Override
	public SendTransferVerifyCodeAnsPackage sendTransferVerifyCode(Long uid, Long orderId) {

		// 校验参数
		throwRuntimeExceptionIfNull(uid, "uid为空");
		throwRuntimeExceptionIfNull(orderId, "orderId为空");

		SendTransferVerifyCodeAnsPackage ansPackage = null;

		boolean isContinue = isContinue();
		if (isContinue) {

			FiUser user = xhhFinanceDao.get(FiUser.class, uid);
			throwRuntimeExceptionIfNull(user, "用户(" + uid + ")不存在");

			FiUserAccount userAccount = xhhFinanceDao.get(FiUserAccount.class, uid);
			throwRuntimeExceptionIfNull(userAccount, "账号(" + uid + ")不存在");

			FiPrjOrder order = xhhFinanceDao.get(FiPrjOrder.class, orderId);
			throwRuntimeExceptionIfNull(order, "订单(" + orderId + ")不存在");

			SendTransferVerifyCodeReq req = new SendTransferVerifyCodeReq();
			req.setUid(uid.toString());
			req.setAccountNo(userAccount.getTrusteeAccount());
			req.setMobileNo(user.getMobile());
			req.setPrjNo(order.getOrderNo());
			// TODO 转让份额
			req.setTransLot(order.getRestMoney().toString());
			req.setTransAmount(order.getRestMoney().toString());
			// TODO 发送短信编号

			ansPackage = icbcFundService.sendTransferVerifyCode(req);

		}
		return ansPackage;
	}

	@Override
	public ConfirmTransferVerifyCodeAnsPackage confirmTransferVerifyCode(Long uid, Long orderId, String verifyCode) {
		// 校验参数
		throwRuntimeExceptionIfNull(uid, "uid为空");
		throwRuntimeExceptionIfNull(orderId, "orderId为空");
		throwRuntimeExceptionIfNull(verifyCode, "verifyCode为空");

		ConfirmTransferVerifyCodeAnsPackage ansPackage = null;

		boolean isContinue = isContinue();
		if (isContinue) {

			FiUser user = xhhFinanceDao.get(FiUser.class, uid);
			throwRuntimeExceptionIfNull(user, "用户(" + uid + ")不存在");

			FiUserAccount userAccount = xhhFinanceDao.get(FiUserAccount.class, uid);
			throwRuntimeExceptionIfNull(userAccount, "账号(" + uid + ")不存在");

			FiPrjOrder order = xhhFinanceDao.get(FiPrjOrder.class, orderId);
			throwRuntimeExceptionIfNull(order, "订单(" + orderId + ")不存在");

			ConfirmTransferVerifyCodeReq req = new ConfirmTransferVerifyCodeReq();
			req.setUid(uid.toString());
			req.setAccountNo(userAccount.getTrusteeAccount());
			req.setMobileNo(user.getMobile());
			req.setOrderNo(order.getOrderNo());
			req.setMobileValidateCode(verifyCode);

			ansPackage = icbcFundService.confirmTransferVerifyCode(req);

		}

		return ansPackage;
	}

	@Override
	public TransferTransactionAnsPackage transferTransaction(Long buyUid, Long orderId) {
		// 校验参数
		throwRuntimeExceptionIfNull(buyUid, "buyUid为空");
		throwRuntimeExceptionIfNull(orderId, "orderId为空");

		TransferTransactionAnsPackage ansPackage = null;

		boolean isContinue = isContinue();
		if (isContinue) {

			FiUser buyUser = xhhFinanceDao.get(FiUser.class, buyUid);
			throwRuntimeExceptionIfNull(buyUser, "购买用户(" + buyUid + ")不存在");

			FiUserAccount buyUserAccount = xhhFinanceDao.get(FiUserAccount.class, buyUid);
			throwRuntimeExceptionIfNull(buyUserAccount, "购买账号(" + buyUid + ")不存在");

			FiPrjOrder order = xhhFinanceDao.get(FiPrjOrder.class, orderId);
			throwRuntimeExceptionIfNull(order, "订单(" + orderId + ")不存在");

			FiPrj prj = xhhFinanceDao.get(FiPrj.class, order.getPrjId());
			throwRuntimeExceptionIfNull(prj, "项目(" + order.getPrjId() + ")不存在");

			Long saleUid = order.getUid();

			FiUser saleUser = xhhFinanceDao.get(FiUser.class, saleUid);
			throwRuntimeExceptionIfNull(saleUser, "转让用户(" + saleUid + ")不存在");

			FiUserAccount saleUserAccount = xhhFinanceDao.get(FiUserAccount.class, saleUid);
			throwRuntimeExceptionIfNull(saleUserAccount, "转让账号(" + saleUid + ")不存在");

			TransferTransactionReq req = new TransferTransactionReq();
			req.setBuyUid(buyUid.toString());
			req.setBuyAccountNo(buyUserAccount.getTrusteeAccount());
			req.setBuyIdtype(ID_TYPE);
			req.setBuyIdno(buyUser.getPersonId());
			req.setPrjNo(prj.getPrjNo());
			// 支付金额
			req.setAmount(order.getRestMoney().toString());
			req.setBuyLot(order.getRestMoney().toString());
			req.setOrderNo(order.getOrderNo());
			req.setInAccountNo(saleUserAccount.getTrusteeAccount());
			req.setInAccountName(saleUser.getRealName());

			ansPackage = icbcFundService.transferTransaction(req);

			// 银行流水号、疑账处理

		}

		return ansPackage;
	}

	@Override
	public RepaymentAnsPackage repayment(Long prjId, Integer repayPeriod) {
		// 校验参数
		throwRuntimeExceptionIfNull(prjId, "prjId为空");
		throwRuntimeExceptionIfNull(repayPeriod, "repayPeriod为空");

		RepaymentAnsPackage ansPackage = null;

		boolean isContinue = isContinue();
		if (isContinue) {

			FiPrj prj = xhhFinanceDao.get(FiPrj.class, prjId);
			throwRuntimeExceptionIfNull(prj, "项目(" + prjId + ")不存在");

			Long fundAccount = prj.getFundAccount();
			FiLoanbankAccount loanbankAccount = xhhFinanceDao.get(FiLoanbankAccount.class, fundAccount);
			throwRuntimeExceptionIfNull(loanbankAccount, "项目(" + prjId + ")没有银行卡信息");

			Long uid = prj.getUid();
			FiUser user = xhhFinanceDao.get(FiUser.class, uid);
			throwRuntimeExceptionIfNull(user, "用款人(" + uid + ")不存在");

			// TODO 获取还款金额
			String repayAmountSql = "select sum()";
			Long repayAmount = 0l;

			RepaymentReq req = new RepaymentReq();
			req.setPrjNo(prj.getPrjNo());
			req.setRepayPeriods(repayPeriod == null ? null : repayPeriod.toString());
			req.setRepayAccountNo(loanbankAccount.getAccountNo());
			req.setRepayIdtype(ID_TYPE);
			req.setRepayIdno(user.getPersonId());
			req.setRepayAmount(repayAmount.toString());
			req.setRepayLot(repayAmount.toString());

			ansPackage = icbcFundService.repayment(req);

		}
		return ansPackage;
	}

	@Override
	public DistributeInvestmentIncomeAnsPackage distributeInvestmentIncome(Long prjId, Integer repayPeriod) {
		// 校验参数
		throwRuntimeExceptionIfNull(prjId, "prjId为空");
		throwRuntimeExceptionIfNull(repayPeriod, "repayPeriod为空");

		DistributeInvestmentIncomeAnsPackage ansPackage = null;

		boolean isContinue = isContinue();
		if (isContinue) {

			FiPrj prj = xhhFinanceDao.get(FiPrj.class, prjId);
			throwRuntimeExceptionIfNull(prj, "项目(" + prjId + ")不存在");

			Long fundAccount = prj.getFundAccount();
			FiLoanbankAccount loanbankAccount = xhhFinanceDao.get(FiLoanbankAccount.class, fundAccount);
			throwRuntimeExceptionIfNull(loanbankAccount, "项目(" + prjId + ")没有银行卡信息");

			Long uid = prj.getUid();
			FiUser user = xhhFinanceDao.get(FiUser.class, uid);
			throwRuntimeExceptionIfNull(user, "用款人(" + uid + ")不存在");

			String repayPlanListHql = "from FiPrjOrderRepayPlan where prjId=? and repayPeriods=? and status=1 and trustRepayStatus <> '1'";
			List<FiPrjOrderRepayPlan> repayPlanList = xhhFinanceDao.findEntities(repayPlanListHql, new Object[] {
					prjId, repayPeriod });

			if (repayPlanList != null && !repayPlanList.isEmpty()) {
				for (FiPrjOrderRepayPlan plan : repayPlanList) {

					FiPrjOrder order = xhhFinanceDao.get(FiPrjOrder.class, plan.getPrjOrderId());
					Long buyUid = order.getUid();
					FiUser buyUser = xhhFinanceDao.get(FiUser.class, buyUid);
					FiUserAccount buyUserAccount = xhhFinanceDao.get(FiUserAccount.class, buyUid);

					DistributeInvestmentIncomeReq req = new DistributeInvestmentIncomeReq();
					req.setUid(buyUser.getUid().toString());
					req.setAccountNo(buyUserAccount.getTrusteeAccount());
					req.setIdtype(ID_TYPE);
					req.setIdno(buyUser.getPersonId());
					req.setPrjNo(prj.getPrjNo());
					req.setRepayPeriods(repayPeriod.toString());
					req.setRepayLot(repayPeriod.toString());
					req.setOrderNo(order.getOrderNo());
					ansPackage = icbcFundService.distributeInvestmentIncome(req);
					
					//更新订单还款计划托管支付状态、银行流水号、疑账
					plan.setTrustRepayStatus(FiPrjOrderRepayPlan.TRUST_REPAY_STATUS_SUCCESS);
					xhhFinanceDao.update(plan);
					
				}
			}

		}
		return ansPackage;
	}

	@Override
	public SendBindBankCardVerifyCodeAnsPackage sendBindBankCardVerifyCode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BindBankCardAnsPackage bindBankCard() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BindBankCardAnsPackage unbindBankCard() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WithdrawCashAnsPackage withdrawCash(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SendChangeMobileVerifyCodeAnsPackage sendChangeMobileVerifyCode(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChangeMobileAnsPackage changeMobile(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	private FiPrjTrustAccount getFiPrjTrustAccountByPrjId(Long prjId) {
		FiPrjTrustAccount result = null;
		if (prjId != null) {
			String hql = "from FiPrjTrustAccount where prjId=?";
			List<FiPrjTrustAccount> list = xhhFinanceDao.findEntities(hql, prjId);
			// 验证一个项目只能有一个托管子账号
			if (list != null) {
				if (list.size() == 1) {
					result = list.get(0);
				} else {
					// 一个项目多个子账号
					throw new RuntimeException("项目(" + prjId + ")存在多个托管子账号");
				}
			}
		}
		return result;
	}

}
