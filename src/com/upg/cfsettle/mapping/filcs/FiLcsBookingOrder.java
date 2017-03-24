package com.upg.cfsettle.mapping.filcs;

import java.io.Serializable;
import java.util.Date;

/**
 * 理财师订单表
 * @author renzhuolun
 * @date 2016年7月12日 上午9:53:42
 * @version <b>1.0.0</b>
 */

public class FiLcsBookingOrder implements Serializable {

	/**
	 * 序号
	 */
	private static final long serialVersionUID = -3150220454889167894L;

	private Long id;
	private String orderNo;
	private Short orderType;
	private Long fundId;
	private Long amount;
	private Long lcsUid;
	private Long fromLcsUid;
	private String status;
	private Byte muserFlag;
	private Long dealUid;
	private String dealRemark;
	private Long dealTime;
	private Date ctime;
	private Date mtime;

	// Constructors

	/** default constructor */
	public FiLcsBookingOrder() {
	}

	/** minimal constructor */
	public FiLcsBookingOrder(Long fundId) {
		this.fundId = fundId;
	}

	/** full constructor */
	public FiLcsBookingOrder(String orderNo, Short orderType, Long fundId,
			Long amount, Long lcsUid, Long fromLcsUid, String status,
			Byte muserFlag, Long dealUid, String dealRemark,
			Long dealTime, Date ctime, Date mtime) {
		this.orderNo = orderNo;
		this.orderType = orderType;
		this.fundId = fundId;
		this.amount = amount;
		this.lcsUid = lcsUid;
		this.fromLcsUid = fromLcsUid;
		this.status = status;
		this.muserFlag = muserFlag;
		this.dealUid = dealUid;
		this.dealRemark = dealRemark;
		this.dealTime = dealTime;
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

	public String getOrderNo() {
		return this.orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Short getOrderType() {
		return this.orderType;
	}

	public void setOrderType(Short orderType) {
		this.orderType = orderType;
	}

	public Long getFundId() {
		return this.fundId;
	}

	public void setFundId(Long fundId) {
		this.fundId = fundId;
	}

	public Long getAmount() {
		return this.amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public Long getLcsUid() {
		return this.lcsUid;
	}

	public void setLcsUid(Long lcsUid) {
		this.lcsUid = lcsUid;
	}

	public Long getFromLcsUid() {
		return this.fromLcsUid;
	}

	public void setFromLcsUid(Long fromLcsUid) {
		this.fromLcsUid = fromLcsUid;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Byte getMuserFlag() {
		return this.muserFlag;
	}

	public void setMuserFlag(Byte muserFlag) {
		this.muserFlag = muserFlag;
	}

	public Long getDealUid() {
		return this.dealUid;
	}

	public void setDealUid(Long dealUid) {
		this.dealUid = dealUid;
	}

	public String getDealRemark() {
		return this.dealRemark;
	}

	public void setDealRemark(String dealRemark) {
		this.dealRemark = dealRemark;
	}

	public Long getDealTime() {
		return this.dealTime;
	}

	public void setDealTime(Long dealTime) {
		this.dealTime = dealTime;
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

}