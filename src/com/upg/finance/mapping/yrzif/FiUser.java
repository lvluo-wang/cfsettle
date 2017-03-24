package com.upg.finance.mapping.yrzif;

/**
 * 用户 <br/>
 * 调用工行服务模块专用
 * 
 */
public class FiUser {
	private Long	uid;
	/** 真实名称 */
	private String	realName;
	/** 身份证 */
	private String	personId;
	/** 手机号码 */
	private String	mobile;
	/** 用户类型:1-个人 2-企业 */
	private Integer	uidType;

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getUidType() {
		return uidType;
	}

	public void setUidType(Integer uidType) {
		this.uidType = uidType;
	}

}
