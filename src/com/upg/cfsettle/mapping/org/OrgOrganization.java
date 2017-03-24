package com.upg.cfsettle.mapping.org;

import java.io.Serializable;
import java.util.Date;

/**
 * 投资机构实体类
 * @author renzhuolun
 * @date 2016年8月22日 下午4:07:42
 * @version <b>1.0.0</b>
 */

public class OrgOrganization implements Serializable {
	/**
	 * 序号
	 */
	private static final long serialVersionUID = 2228888803416969114L;
	private Long id;
	private String orgName;
	private String orgShortName;
	private Long invitationOrgId;
	private String invitationCode;
	private String invitedCode;
	private Date registionTime;
	private Byte approvalStatus;
	private Date approvalTime;
	private Long approvedBy;
	private Long lastUpdatedBy;
	private Date mtime;
	private Date ctime;
	
	private Long regCert;//登记证书
	private Long busLicense;//营业执照
	private Long taxLicense;//税务登记证
	private Long idCard;//法人身份证
	private Long busCard;//法人名片
	

	// Constructors

	/** default constructor */
	public OrgOrganization() {
	}

	/** full constructor */
	public OrgOrganization(String orgName, String orgShortName,
			Long invitationOrgId, String invitationCode, String invitedCode,
			Date registionTime, Byte approvalStatus, Date approvalTime,
			Long approvedBy, Long lastUpdatedBy, Date mtime,
			Date ctime) {
		this.orgName = orgName;
		this.orgShortName = orgShortName;
		this.invitationOrgId = invitationOrgId;
		this.invitationCode = invitationCode;
		this.invitedCode = invitedCode;
		this.registionTime = registionTime;
		this.approvalStatus = approvalStatus;
		this.approvalTime = approvalTime;
		this.approvedBy = approvedBy;
		this.lastUpdatedBy = lastUpdatedBy;
		this.mtime = mtime;
		this.ctime = ctime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgShortName() {
		return orgShortName;
	}

	public void setOrgShortName(String orgShortName) {
		this.orgShortName = orgShortName;
	}

	public Long getInvitationOrgId() {
		return invitationOrgId;
	}

	public void setInvitationOrgId(Long invitationOrgId) {
		this.invitationOrgId = invitationOrgId;
	}

	public String getInvitationCode() {
		return invitationCode;
	}

	public void setInvitationCode(String invitationCode) {
		this.invitationCode = invitationCode;
	}

	public String getInvitedCode() {
		return invitedCode;
	}

	public void setInvitedCode(String invitedCode) {
		this.invitedCode = invitedCode;
	}

	public Date getRegistionTime() {
		return registionTime;
	}

	public void setRegistionTime(Date registionTime) {
		this.registionTime = registionTime;
	}

	public Byte getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(Byte approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public Date getApprovalTime() {
		return approvalTime;
	}

	public void setApprovalTime(Date approvalTime) {
		this.approvalTime = approvalTime;
	}

	public Long getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(Long approvedBy) {
		this.approvedBy = approvedBy;
	}

	public Long getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(Long lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Date getMtime() {
		return mtime;
	}

	public void setMtime(Date mtime) {
		this.mtime = mtime;
	}

	public Date getCtime() {
		return ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public Long getRegCert() {
		return regCert;
	}

	public void setRegCert(Long regCert) {
		this.regCert = regCert;
	}

	public Long getBusLicense() {
		return busLicense;
	}

	public void setBusLicense(Long busLicense) {
		this.busLicense = busLicense;
	}

	public Long getTaxLicense() {
		return taxLicense;
	}

	public void setTaxLicense(Long taxLicense) {
		this.taxLicense = taxLicense;
	}

	public Long getIdCard() {
		return idCard;
	}

	public void setIdCard(Long idCard) {
		this.idCard = idCard;
	}

	public Long getBusCard() {
		return busCard;
	}

	public void setBusCard(Long busCard) {
		this.busCard = busCard;
	}
}