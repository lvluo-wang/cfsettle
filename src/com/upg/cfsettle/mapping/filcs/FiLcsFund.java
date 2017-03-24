package com.upg.cfsettle.mapping.filcs;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * 基金产品基本信息
 * @author renzhuolun
 * @date 2016年6月3日 下午3:20:51
 * @version <b>1.0.0</b>
 */

public class FiLcsFund implements Serializable {

	/**
	 * 序号
	 */
	private static final long serialVersionUID = 1050014456931543237L;
	private Long id;
	private Long fundType;
	private String fundCompany;
	private String fundManagent;
	private String fundInvestAt;
	private String fundHighlight;
	private String fundName;
	private Byte status;
	private Byte fundStatus;
	private String timeLimit;
	private Date startTime;
	private Date endTime;
	private BigDecimal scaleAmount;
	private String rate;
	private Date buildTime;
	private String fundCode;
	private String keepBank;
	private String partner;
	private String manager;
	private String keeper;
	private String netWorth;
	private BigDecimal totalRate;
	private BigDecimal oneMonthRate;
	private BigDecimal threeMonthRate;
	private BigDecimal currentYearRate;
	private BigDecimal rakeBackRate;
	private String minBuyAmount;
	private String stepBuyAmount;
	private String subFeeRule;
	private String applyFeeRule;
	private String manageFeeRule;
	private String redeemFeeRule;
	private String rewardRule;
	private String investScope;
	private String investStrategy;
	private String investRiskCtrl;
	private Byte top;
	private Byte activity;
	private Date ctime;
	private Date mtime;
	private String lastReason;
	private String payWay;
	private String manageOrg;
	private String publishChannel;
	private Long luyanPic;
	private String luyanName;
	
	/**
	 * 以下临时字段不参与数据运算固化
	 */
	private String attachId;
	
	private String delAttachId;

	// Constructors

	/** default constructor */
	public FiLcsFund() {
	}

	/** minimal constructor */
	public FiLcsFund(Byte top, Byte activity) {
		this.top = top;
		this.activity = activity;
	}

	/** full constructor */
	public FiLcsFund(Long fundType, String fundCompany, String fundManagent,
			String fundInvestAt, String fundHighlight, String fundName,
			Byte status, Byte fundStatus, String timeLimit,
			Date startTime, Date endTime, BigDecimal scaleAmount,
			String rate, Date buildTime, String fundCode, String keepBank,
			String partner, String manager, String keeper, String netWorth,
			BigDecimal totalRate, BigDecimal oneMonthRate, BigDecimal threeMonthRate,
			BigDecimal currentYearRate, String minBuyAmount, String stepBuyAmount,
			String subFeeRule, String applyFeeRule, String manageFeeRule,
			String redeemFeeRule, String rewardRule, String investScope,
			String investStrategy, String investRiskCtrl, Byte top,
			Byte activity, Date ctime, Date mtime) {
		this.fundType = fundType;
		this.fundCompany = fundCompany;
		this.fundManagent = fundManagent;
		this.fundInvestAt = fundInvestAt;
		this.fundHighlight = fundHighlight;
		this.fundName = fundName;
		this.status = status;
		this.fundStatus = fundStatus;
		this.timeLimit = timeLimit;
		this.startTime = startTime;
		this.endTime = endTime;
		this.scaleAmount = scaleAmount;
		this.rate = rate;
		this.buildTime = buildTime;
		this.fundCode = fundCode;
		this.keepBank = keepBank;
		this.partner = partner;
		this.manager = manager;
		this.keeper = keeper;
		this.netWorth = netWorth;
		this.totalRate = totalRate;
		this.oneMonthRate = oneMonthRate;
		this.threeMonthRate = threeMonthRate;
		this.currentYearRate = currentYearRate;
		this.minBuyAmount = minBuyAmount;
		this.stepBuyAmount = stepBuyAmount;
		this.subFeeRule = subFeeRule;
		this.applyFeeRule = applyFeeRule;
		this.manageFeeRule = manageFeeRule;
		this.redeemFeeRule = redeemFeeRule;
		this.rewardRule = rewardRule;
		this.investScope = investScope;
		this.investStrategy = investStrategy;
		this.investRiskCtrl = investRiskCtrl;
		this.top = top;
		this.activity = activity;
		this.ctime = ctime;
		this.mtime = mtime;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFundType() {
		return this.fundType;
	}

	public void setFundType(Long fundType) {
		this.fundType = fundType;
	}

	public String getFundCompany() {
		return this.fundCompany;
	}

	public void setFundCompany(String fundCompany) {
		this.fundCompany = fundCompany;
	}

	public String getFundManagent() {
		return this.fundManagent;
	}

	public void setFundManagent(String fundManagent) {
		this.fundManagent = fundManagent;
	}

	public String getFundInvestAt() {
		return this.fundInvestAt;
	}

	public void setFundInvestAt(String fundInvestAt) {
		this.fundInvestAt = fundInvestAt;
	}

	public String getFundHighlight() {
		return this.fundHighlight;
	}

	public void setFundHighlight(String fundHighlight) {
		this.fundHighlight = fundHighlight;
	}

	public String getFundName() {
		return this.fundName;
	}

	public void setFundName(String fundName) {
		this.fundName = fundName;
	}

	public Byte getStatus() {
		return this.status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public Byte getFundStatus() {
		return this.fundStatus;
	}

	public void setFundStatus(Byte fundStatus) {
		this.fundStatus = fundStatus;
	}

	public String getTimeLimit() {
		return this.timeLimit;
	}

	public void setTimeLimit(String timeLimit) {
		this.timeLimit = timeLimit;
	}

	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public BigDecimal getScaleAmount() {
		return this.scaleAmount;
	}

	public void setScaleAmount(BigDecimal scaleAmount) {
		this.scaleAmount = scaleAmount;
	}

	public String getRate() {
		return this.rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public Date getBuildTime() {
		return this.buildTime;
	}

	public void setBuildTime(Date buildTime) {
		this.buildTime = buildTime;
	}

	public String getFundCode() {
		return this.fundCode;
	}

	public void setFundCode(String fundCode) {
		this.fundCode = fundCode;
	}

	public String getKeepBank() {
		return this.keepBank;
	}

	public void setKeepBank(String keepBank) {
		this.keepBank = keepBank;
	}

	public String getPartner() {
		return this.partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public String getManager() {
		return this.manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getKeeper() {
		return this.keeper;
	}

	public void setKeeper(String keeper) {
		this.keeper = keeper;
	}

	public String getNetWorth() {
		return this.netWorth;
	}

	public void setNetWorth(String netWorth) {
		this.netWorth = netWorth;
	}

	public BigDecimal getTotalRate() {
		return this.totalRate;
	}

	public void setTotalRate(BigDecimal totalRate) {
		this.totalRate = totalRate;
	}

	public BigDecimal getOneMonthRate() {
		return this.oneMonthRate;
	}

	public void setOneMonthRate(BigDecimal oneMonthRate) {
		this.oneMonthRate = oneMonthRate;
	}

	public BigDecimal getThreeMonthRate() {
		return this.threeMonthRate;
	}

	public void setThreeMonthRate(BigDecimal threeMonthRate) {
		this.threeMonthRate = threeMonthRate;
	}

	public BigDecimal getCurrentYearRate() {
		return this.currentYearRate;
	}

	public void setCurrentYearRate(BigDecimal currentYearRate) {
		this.currentYearRate = currentYearRate;
	}

	public String getMinBuyAmount() {
		return this.minBuyAmount;
	}

	public void setMinBuyAmount(String minBuyAmount) {
		this.minBuyAmount = minBuyAmount;
	}

	public String getStepBuyAmount() {
		return this.stepBuyAmount;
	}

	public void setStepBuyAmount(String stepBuyAmount) {
		this.stepBuyAmount = stepBuyAmount;
	}

	public String getSubFeeRule() {
		return this.subFeeRule;
	}

	public void setSubFeeRule(String subFeeRule) {
		this.subFeeRule = subFeeRule;
	}

	public String getApplyFeeRule() {
		return this.applyFeeRule;
	}

	public void setApplyFeeRule(String applyFeeRule) {
		this.applyFeeRule = applyFeeRule;
	}

	public String getManageFeeRule() {
		return this.manageFeeRule;
	}

	public void setManageFeeRule(String manageFeeRule) {
		this.manageFeeRule = manageFeeRule;
	}

	public String getRedeemFeeRule() {
		return this.redeemFeeRule;
	}

	public void setRedeemFeeRule(String redeemFeeRule) {
		this.redeemFeeRule = redeemFeeRule;
	}

	public String getRewardRule() {
		return this.rewardRule;
	}

	public void setRewardRule(String rewardRule) {
		this.rewardRule = rewardRule;
	}

	public String getInvestScope() {
		return this.investScope;
	}

	public void setInvestScope(String investScope) {
		this.investScope = investScope;
	}

	public String getInvestStrategy() {
		return this.investStrategy;
	}

	public void setInvestStrategy(String investStrategy) {
		this.investStrategy = investStrategy;
	}

	public String getInvestRiskCtrl() {
		return this.investRiskCtrl;
	}

	public void setInvestRiskCtrl(String investRiskCtrl) {
		this.investRiskCtrl = investRiskCtrl;
	}

	public Byte getTop() {
		return this.top;
	}

	public void setTop(Byte top) {
		this.top = top;
	}

	public Byte getActivity() {
		return this.activity;
	}

	public void setActivity(Byte activity) {
		this.activity = activity;
	}

	public Date getCtime() {
		return this.ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public Date getMtime() {
		return this.mtime;
	}

	public void setMtime(Date mtime) {
		this.mtime = mtime;
	}

	public String getAttachId() {
		return attachId;
	}

	public void setAttachId(String attachId) {
		this.attachId = attachId;
	}

	public String getDelAttachId() {
		return delAttachId;
	}

	public void setDelAttachId(String delAttachId) {
		this.delAttachId = delAttachId;
	}

	public BigDecimal getRakeBackRate() {
		return rakeBackRate;
	}

	public void setRakeBackRate(BigDecimal rakeBackRate) {
		this.rakeBackRate = rakeBackRate;
	}

	public String getLastReason() {
		return lastReason;
	}

	public void setLastReason(String lastReason) {
		this.lastReason = lastReason;
	}

	public String getPayWay() {
		return payWay;
	}

	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}

	public String getManageOrg() {
		return manageOrg;
	}

	public void setManageOrg(String manageOrg) {
		this.manageOrg = manageOrg;
	}

	public String getPublishChannel() {
		return publishChannel;
	}

	public void setPublishChannel(String publishChannel) {
		this.publishChannel = publishChannel;
	}

	public Long getLuyanPic() {
		return luyanPic;
	}

	public void setLuyanPic(Long luyanPic) {
		this.luyanPic = luyanPic;
	}

	public String getLuyanName() {
		return luyanName;
	}

	public void setLuyanName(String luyanName) {
		this.luyanName = luyanName;
	}
}