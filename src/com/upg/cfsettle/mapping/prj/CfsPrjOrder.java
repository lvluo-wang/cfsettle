package com.upg.cfsettle.mapping.prj;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * CfsPrjOrder entity. @author MyEclipse Persistence Tools
 */

public class CfsPrjOrder implements Serializable {

	// Fields

	/**
	 * TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = 8099491973260154103L;
	private Long id;
	private Long custId;
	private Long prjId;
	private String prjName;
	private BigDecimal money;
	private Byte status;
	private String contractNo;
	private Date investTime;
	private String payBank;
	private String payAccountNo;
	private Long payNotesAttid;
	private String paySerialNum;
	private String remark;
	private Date contractAuditTime;
	private Long contractAuidtSysid;
	private Date collectAuditTime;
	private Long collectAuditSysid;
	private Date repayAuditTime;
	private Long repayAuidtSysid;
	private Long serviceSysType;
	private Long serviceSysid;
	private String serviceSysName;
	private Long ownedTeam;
	private String ownedTeamName;
	private Long ownedDept;
	private String ownedDeptName;
	private Long ownedArea;
	private String ownedAreaName;
	private Long csysid;
	private Date ctime;
	private Long msysid;
	private Date mtime;

	// Constructors

	/** default constructor */
	public CfsPrjOrder() {
	}

	/** full constructor */
	public CfsPrjOrder(Long custId, Long prjId, String prjName,
			BigDecimal money, Byte status, String contractNo,
			Date investTime, String payBank, String payAccountNo,
			Long payNotesAttid, String paySerialNum, String remark,
			Date contractAuditTime, Long contractAuidtSysid,
			Date collectAuditTime, Long collectAuditSysid,
			Date repayAuditTime, Long repayAuidtSysid,
			Long serviceSysType, Long serviceSysid,
			String serviceSysName, Long ownedTeam, String ownedTeamName,
			Long ownedDept, String ownedDeptName, Long ownedArea,
			String ownedAreaName, Long csysid, Date ctime,
			Long msysid, Date mtime) {
		this.custId = custId;
		this.prjId = prjId;
		this.prjName = prjName;
		this.money = money;
		this.status = status;
		this.contractNo = contractNo;
		this.investTime = investTime;
		this.payBank = payBank;
		this.payAccountNo = payAccountNo;
		this.payNotesAttid = payNotesAttid;
		this.paySerialNum = paySerialNum;
		this.remark = remark;
		this.contractAuditTime = contractAuditTime;
		this.contractAuidtSysid = contractAuidtSysid;
		this.collectAuditTime = collectAuditTime;
		this.collectAuditSysid = collectAuditSysid;
		this.repayAuditTime = repayAuditTime;
		this.repayAuidtSysid = repayAuidtSysid;
		this.serviceSysType = serviceSysType;
		this.serviceSysid = serviceSysid;
		this.serviceSysName = serviceSysName;
		this.ownedTeam = ownedTeam;
		this.ownedTeamName = ownedTeamName;
		this.ownedDept = ownedDept;
		this.ownedDeptName = ownedDeptName;
		this.ownedArea = ownedArea;
		this.ownedAreaName = ownedAreaName;
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

	public Long getCustId() {
		return this.custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public Long getPrjId() {
		return this.prjId;
	}

	public void setPrjId(Long prjId) {
		this.prjId = prjId;
	}

	public String getPrjName() {
		return this.prjName;
	}

	public void setPrjName(String prjName) {
		this.prjName = prjName;
	}

	public BigDecimal getMoney() {
		return this.money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public Byte getStatus() {
		return this.status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public String getContractNo() {
		return this.contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public Date getInvestTime() {
		return this.investTime;
	}

	public void setInvestTime(Date investTime) {
		this.investTime = investTime;
	}

	public String getPayBank() {
		return this.payBank;
	}

	public void setPayBank(String payBank) {
		this.payBank = payBank;
	}

	public String getPayAccountNo() {
		return this.payAccountNo;
	}

	public void setPayAccountNo(String payAccountNo) {
		this.payAccountNo = payAccountNo;
	}

	public Long getPayNotesAttid() {
		return this.payNotesAttid;
	}

	public void setPayNotesAttid(Long payNotesAttid) {
		this.payNotesAttid = payNotesAttid;
	}

	public String getPaySerialNum() {
		return this.paySerialNum;
	}

	public void setPaySerialNum(String paySerialNum) {
		this.paySerialNum = paySerialNum;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getContractAuditTime() {
		return this.contractAuditTime;
	}

	public void setContractAuditTime(Date contractAuditTime) {
		this.contractAuditTime = contractAuditTime;
	}

	public Long getContractAuidtSysid() {
		return this.contractAuidtSysid;
	}

	public void setContractAuidtSysid(Long contractAuidtSysid) {
		this.contractAuidtSysid = contractAuidtSysid;
	}

	public Date getCollectAuditTime() {
		return this.collectAuditTime;
	}

	public void setCollectAuditTime(Date collectAuditTime) {
		this.collectAuditTime = collectAuditTime;
	}

	public Long getCollectAuditSysid() {
		return this.collectAuditSysid;
	}

	public void setCollectAuditSysid(Long collectAuditSysid) {
		this.collectAuditSysid = collectAuditSysid;
	}

	public Date getRepayAuditTime() {
		return this.repayAuditTime;
	}

	public void setRepayAuditTime(Date repayAuditTime) {
		this.repayAuditTime = repayAuditTime;
	}

	public Long getRepayAuidtSysid() {
		return this.repayAuidtSysid;
	}

	public void setRepayAuidtSysid(Long repayAuidtSysid) {
		this.repayAuidtSysid = repayAuidtSysid;
	}

	public Long getServiceSysType() {
		return this.serviceSysType;
	}

	public void setServiceSysType(Long serviceSysType) {
		this.serviceSysType = serviceSysType;
	}

	public Long getServiceSysid() {
		return this.serviceSysid;
	}

	public void setServiceSysid(Long serviceSysid) {
		this.serviceSysid = serviceSysid;
	}

	public String getServiceSysName() {
		return this.serviceSysName;
	}

	public void setServiceSysName(String serviceSysName) {
		this.serviceSysName = serviceSysName;
	}

	public Long getOwnedTeam() {
		return this.ownedTeam;
	}

	public void setOwnedTeam(Long ownedTeam) {
		this.ownedTeam = ownedTeam;
	}

	public String getOwnedTeamName() {
		return this.ownedTeamName;
	}

	public void setOwnedTeamName(String ownedTeamName) {
		this.ownedTeamName = ownedTeamName;
	}

	public Long getOwnedDept() {
		return this.ownedDept;
	}

	public void setOwnedDept(Long ownedDept) {
		this.ownedDept = ownedDept;
	}

	public String getOwnedDeptName() {
		return this.ownedDeptName;
	}

	public void setOwnedDeptName(String ownedDeptName) {
		this.ownedDeptName = ownedDeptName;
	}

	public Long getOwnedArea() {
		return this.ownedArea;
	}

	public void setOwnedArea(Long ownedArea) {
		this.ownedArea = ownedArea;
	}

	public String getOwnedAreaName() {
		return this.ownedAreaName;
	}

	public void setOwnedAreaName(String ownedAreaName) {
		this.ownedAreaName = ownedAreaName;
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

}