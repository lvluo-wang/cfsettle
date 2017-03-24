package com.upg.ucars.mapping.basesystem.security;


/**
 * SysConfig entity.
 * 
 * @author shentuwy
 * @author MyEclipse Persistence Tools
 */

public class SysConfig implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4581487582885558516L;
	
	/** 初始化密码 */
	public static final String DEFAULT_INIT_PWD="888888x";;
	/** 允许登录失败的次数 */
	public static final long DEFAULT_PWD_ALLOW_ERR_NUM=3;
	/** 密码的有效天数 */
	public static final long DEFAULT_PWD_EFFECT_DAYS=30;
	/** 可以在线登录 */
	public static final String DEFAULT_IS_ONLINE_LOGON="1";
	/** 可以多ip登录 */
	public static final String DEFAULT_IS_MULTIIP_LOGON="1";
	/** 参数需要审核 */
	public static final String DEFAULT_SYS_PARAM_CHECK = "1";
	
	// Fields
	
	/** ID */
	private Long scId;
	public static final String SC_ID = "scId";
	/** 密码有效天数 */
	private Long pwdEffectDays;
	public static final String PWD_EFFECT_DAYS = "pwdEffectDays";
	/** 登录失败允许的次数 */
	private Long errAllowNum;
	public static final String ERR_ALLOW_NUM = "errAllowNum";
	/** 初始密码 */
	private String pwdInit;
	public static final String PWD_INIT = "pwdInit";
	/**　是否可以多IP登录 */
	private String isMultiIp;
	public static final String IS_MULTI_IP = "isMultiIp";
	/**　是否可以在线登录　 */
	private String isOnlineLogon;
	public static final String IS_ONLINE_LOGON = "isOnlineLogon";
	/** 参数是否需要审核 */
	private String sysParamCheck;
	public static final String SYS_PARAM_CHECK = "sysParamCheck";
	/** 接入点 */
	private String miNo;
	public static final String MI_NO = "miNo";
	/**　版本号 */
	private Long version;
	public static final String VERSION = "version";
	public SysConfig() {
	}
	public SysConfig(Long pwdEffectDays,
			Long errAllowNum, String pwdInit, String isMultiIp,
			String isOnlineLogon,  String miNo, Long version) {
		this.pwdEffectDays = pwdEffectDays;
		this.errAllowNum = errAllowNum;
		this.pwdInit = pwdInit;
		this.isMultiIp = isMultiIp;
		this.isOnlineLogon = isOnlineLogon;
		this.miNo = miNo;
		this.version = version;
	}

	// Property accessors

	public Long getScId() {
		return this.scId;
	}

	public void setScId(Long scId) {
		this.scId = scId;
	}

	public Long getPwdEffectDays() {
		return this.pwdEffectDays;
	}

	public void setPwdEffectDays(Long pwdEffectDays) {
		this.pwdEffectDays = pwdEffectDays;
	}

	public Long getErrAllowNum() {
		return this.errAllowNum;
	}

	public void setErrAllowNum(Long errAllowNum) {
		this.errAllowNum = errAllowNum;
	}

	public String getPwdInit() {
		return this.pwdInit;
	}

	public void setPwdInit(String pwdInit) {
		this.pwdInit = pwdInit;
	}

	public String getIsMultiIp() {
		return this.isMultiIp;
	}

	public void setIsMultiIp(String isMultiIp) {
		this.isMultiIp = isMultiIp;
	}

	public String getIsOnlineLogon() {
		return this.isOnlineLogon;
	}

	public void setIsOnlineLogon(String isOnlineLogon) {
		this.isOnlineLogon = isOnlineLogon;
	}
	
	public String getSysParamCheck() {
		return sysParamCheck;
	}
	public void setSysParamCheck(String sysParamCheck) {
		this.sysParamCheck = sysParamCheck;
	}
	public String getMiNo() {
		return this.miNo;
	}

	public void setMiNo(String miNo) {
		this.miNo = miNo;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

}