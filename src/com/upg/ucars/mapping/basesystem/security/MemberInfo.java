package com.upg.ucars.mapping.basesystem.security;

import java.util.Date;

/**
 * 
 * 
 * @author shentuwy
 * @date 2011-2-13 下午09:49:24
 *
 */
public class MemberInfo implements java.io.Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6160390640169378271L;
	
	/** 开启 */
	public static final String OPEN="1";
	/** 关闭 */
	public static final String NOT_OPEN="0";
	// Fields
	/** 接入编号 */
	private String miNo;
	public static final String MI_NO = "miNo";
	/** 名称 */
	private String miName;
	public static final String MI_NAME = "miName";
	/** 是否认证开放 0关闭 1开放 */
	private String isOpen;
	public static final String IS_OPEN = "isOpen";
	/** 接入密钥 */
	private String miKey;
	public static final String MI_KEY = "miKey";
	/** 网银密钥 */
	private String ebKey;
	public static final String EB_KEY = "ebKey";
	/** 接入类型1：银行，2：财务公司，3：企业 */
	private String miType;
	public static final String MI_TYPE = "miType";
	/** 行号 */
	private String bankNo; 
	public static final String BANK_NO = "bankNo";
	/** 组织机构代码 */
	private String orgCode;
	public static final String ORG_CODE = "orgCode";
	/** 接入日期 */
	private Date miDt;
	public static final String MI_DT = "miDt";
	/** 版本 */
	private Long version;
	public static final String VERSION = "version";
	public MemberInfo() {
	}

	public MemberInfo(String miNo, String miName) {
		this.miNo = miNo;
		this.miName = miName;
	}

	public MemberInfo(String miNo, String miName, String isOpen, String miKey,
			String ebKey, String miType, String bankNo, String orgCode,
			Date miDt) {
		super();
		this.miNo = miNo;
		this.miName = miName;
		this.isOpen = isOpen;
		this.miKey = miKey;
		this.ebKey = ebKey;
		this.miType = miType;
		this.bankNo = bankNo;
		this.orgCode = orgCode;
		this.miDt = miDt;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public String getMiNo() {
		return miNo;
	}

	public void setMiNo(String miNo) {
		this.miNo = miNo;
	}

	public String getMiName() {
		return miName;
	}

	public void setMiName(String miName) {
		this.miName = miName;
	}

	public String getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(String isOpen) {
		this.isOpen = isOpen;
	}

	public String getMiKey() {
		return miKey;
	}

	public void setMiKey(String miKey) {
		this.miKey = miKey;
	}

	public String getEbKey() {
		return ebKey;
	}

	public void setEbKey(String ebKey) {
		this.ebKey = ebKey;
	}

	public String getMiType() {
		return miType;
	}

	public void setMiType(String miType) {
		this.miType = miType;
	}

	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public Date getMiDt() {
		return miDt;
	}

	public void setMiDt(Date miDt) {
		this.miDt = miDt;
	}

}