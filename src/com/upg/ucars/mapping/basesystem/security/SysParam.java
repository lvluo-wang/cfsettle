package com.upg.ucars.mapping.basesystem.security;

/**
 * SysParam entity.
 * 
 * @author shentuwy
 */

public class SysParam implements java.io.Serializable {

	private static final long	serialVersionUID		= -3238064078148651429L;
	/** 未复核 */
	public static final String	STATUS_UNCHECK			= "0";
	/** 已复核 */
	public static final String	STATUS_CHECKED			= "1";
	/** 复核中 */
	public static final String	STATUS_CHECKING			= "2";
	/** 参数状态代码 */
	public static final String	CODE_SYS_PARAM_STATUS	= "B017";

	// Fields

	/** ID */
	private Long				paramId;
	public static final String	PARAM_ID				= "paramId";
	/** 参数名称 */
	private String				paramName;
	public static final String	PARAM_NAME				= "paramName";
	/** 参数值 */
	private String				paramValue;
	public static final String	PARAM_VALUE				= "paramValue";
	/** 参数key */
	private String				paramKey;
	public static final String	PARAM_KEY				= "paramKey";
	/** 待复核临时值 */
	private String				tempValue;
	public static final String	TEMP_VALUE				= "tempValue";
	/** 状态 0-未复核，1-已复核，2-复核中 */
	private String				status;
	public static final String	STATUS					= "status";
	/** 接入编号 */
	private String				miNo;
	public static final String	MI_NO					= "miNo";
	/** 创建人 */
	private Long				buildUserId;
	public static final String	BUILD_USER_ID			= "buildUserId";
	/** 修改人 */
	private Long				modifyUserId;
	public static final String	MODIFY_USER_ID			= "modifyUserId";
	/** 复核人 */
	private Long				reviewUserId;
	public static final String	REVIEW_USER_ID			= "reviewUserId";
	/** 录入人名 */
	private String				buildUserName;
	public static final String	BUILD_USER_NAME			= "buildUserName";
	/** 审核人名 */
	private String				reviewUserName;
	public static final String	REVIEW_USER_NAME		= "reviewUserName";
	/** 字典key */
	private String				codeKey;
	public static final String	CODE_KEY				= "codeKey";
	/** 备注 */
	private String				remark;
	public static final String	REMARK					= "remark";
	/** 版本号 */
	private Integer				version;
	public static final String	VERSION					= "version";

	// Constructors

	/** default constructor */
	public SysParam() {
	}

	public SysParam(Long paramId, String paramName, String paramValue, String paramKey, String tempValue,
			String status, String miNo, Long buildUserId, Long reviewUserId, String codeKey, Integer version) {
		super();
		this.paramId = paramId;
		this.paramName = paramName;
		this.paramValue = paramValue;
		this.paramKey = paramKey;
		this.tempValue = tempValue;
		this.status = status;
		this.miNo = miNo;
		this.buildUserId = buildUserId;
		this.reviewUserId = reviewUserId;
		this.codeKey = codeKey;
		this.version = version;
	}

	public Long getParamId() {
		return paramId;
	}

	public void setParamId(Long paramId) {
		this.paramId = paramId;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	public String getParamKey() {
		return paramKey;
	}

	public void setParamKey(String paramKey) {
		this.paramKey = paramKey;
	}

	public String getTempValue() {
		return tempValue;
	}

	public void setTempValue(String tempValue) {
		this.tempValue = tempValue;
	}

	public Long getBuildUserId() {
		return buildUserId;
	}

	public void setBuildUserId(Long buildUserId) {
		this.buildUserId = buildUserId;
	}

	public Long getReviewUserId() {
		return reviewUserId;
	}

	public void setReviewUserId(Long reviewUserId) {
		this.reviewUserId = reviewUserId;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMiNo() {
		return miNo;
	}

	public void setMiNo(String miNo) {
		this.miNo = miNo;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getBuildUserName() {
		return buildUserName;
	}

	public void setBuildUserName(String buildUserName) {
		this.buildUserName = buildUserName;
	}

	public String getReviewUserName() {
		return reviewUserName;
	}

	public void setReviewUserName(String reviewUserName) {
		this.reviewUserName = reviewUserName;
	}

	public String getCodeKey() {
		return codeKey;
	}

	public void setCodeKey(String codeKey) {
		this.codeKey = codeKey;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getModifyUserId() {
		return modifyUserId;
	}

	public void setModifyUserId(Long modifyUserId) {
		this.modifyUserId = modifyUserId;
	}

}
