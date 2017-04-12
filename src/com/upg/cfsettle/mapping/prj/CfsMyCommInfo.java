package com.upg.cfsettle.mapping.prj;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * CfsMyCommInfo entity. @author MyEclipse Persistence Tools
 */

public class CfsMyCommInfo implements Serializable {

	// Fields

	/**
	 * TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = -8401744569661929519L;
	private Long id;
	private Long sysid;
	private String sysUserName;
	private String posCode;
	private String mobile;
	private Date commSettleDate;
	private BigDecimal commMoney;
	private Date payTime;
	private Long paySysid;
	private Byte payStatus;
	private String collectAccountName;
	private String collectBank;
	private String collectBankNo;
	private String collectSubBank;
	private BigDecimal payMoney;
	private String payBank;
	private String payBankNo;
	private Long csysid;
	private Date ctime;
	private Long msysid;
	private Date mtime;

	// Constructors

	/** default constructor */
	public CfsMyCommInfo() {
	}

	/** full constructor */
	public CfsMyCommInfo(Long sysid, Date commSettleDate,
			BigDecimal commMoney, Date payTime, Long paySysid,
			Byte payStatus, String collectAccountName, String collectBank,
			String collectBankNo, String collectSubBank, BigDecimal payMoney,
			String payBank, String payBankNo, Long csysid, Date ctime,
			Long msysid, Date mtime) {
		this.sysid = sysid;
		this.commSettleDate = commSettleDate;
		this.commMoney = commMoney;
		this.payTime = payTime;
		this.paySysid = paySysid;
		this.payStatus = payStatus;
		this.collectAccountName = collectAccountName;
		this.collectBank = collectBank;
		this.collectBankNo = collectBankNo;
		this.collectSubBank = collectSubBank;
		this.payMoney = payMoney;
		this.payBank = payBank;
		this.payBankNo = payBankNo;
		this.csysid = csysid;
		this.ctime = ctime;
		this.msysid = msysid;
		this.mtime = mtime;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSysid() {
		return this.sysid;
	}

	public void setSysid(Long sysid) {
		this.sysid = sysid;
	}

	public Date getCommSettleDate() {
		return this.commSettleDate;
	}

	public void setCommSettleDate(Date commSettleDate) {
		this.commSettleDate = commSettleDate;
	}

	public BigDecimal getCommMoney() {
		return this.commMoney;
	}

	public void setCommMoney(BigDecimal commMoney) {
		this.commMoney = commMoney;
	}

	public Date getPayTime() {
		return this.payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public Long getPaySysid() {
		return this.paySysid;
	}

	public void setPaySysid(Long paySysid) {
		this.paySysid = paySysid;
	}

	public Byte getPayStatus() {
		return this.payStatus;
	}

	public void setPayStatus(Byte payStatus) {
		this.payStatus = payStatus;
	}

	public String getCollectAccountName() {
		return this.collectAccountName;
	}

	public void setCollectAccountName(String collectAccountName) {
		this.collectAccountName = collectAccountName;
	}

	public String getCollectBank() {
		return this.collectBank;
	}

	public void setCollectBank(String collectBank) {
		this.collectBank = collectBank;
	}

	public String getCollectBankNo() {
		return this.collectBankNo;
	}

	public void setCollectBankNo(String collectBankNo) {
		this.collectBankNo = collectBankNo;
	}

	public String getCollectSubBank() {
		return this.collectSubBank;
	}

	public void setCollectSubBank(String collectSubBank) {
		this.collectSubBank = collectSubBank;
	}

	public BigDecimal getPayMoney() {
		return this.payMoney;
	}

	public void setPayMoney(BigDecimal payMoney) {
		this.payMoney = payMoney;
	}

	public String getPayBank() {
		return this.payBank;
	}

	public void setPayBank(String payBank) {
		this.payBank = payBank;
	}

	public String getPayBankNo() {
		return this.payBankNo;
	}

	public void setPayBankNo(String payBankNo) {
		this.payBankNo = payBankNo;
	}

	public Long getCsysid() {
		return this.csysid;
	}

	public void setCsysid(Long csysid) {
		this.csysid = csysid;
	}

	public Date getCtime() {
		return this.ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public Long getMsysid() {
		return this.msysid;
	}

	public void setMsysid(Long msysid) {
		this.msysid = msysid;
	}

	public Date getMtime() {
		return this.mtime;
	}

	public void setMtime(Date mtime) {
		this.mtime = mtime;
	}

	public String getSysUserName() {
		return sysUserName;
	}

	public void setSysUserName(String sysUserName) {
		this.sysUserName = sysUserName;
	}

	public String getPosCode() {
		return posCode;
	}

	public void setPosCode(String posCode) {
		this.posCode = posCode;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}