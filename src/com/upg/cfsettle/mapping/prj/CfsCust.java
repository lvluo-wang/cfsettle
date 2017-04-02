package com.upg.cfsettle.mapping.prj;

import java.io.Serializable;
import java.util.Date;

/**
 * CfsCust entity. @author MyEclipse Persistence Tools
 */

public class CfsCust implements Serializable {

	// Fields

	/**
	 * TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = -6934379539608295212L;
	private Long id;
	private String realName;
	private String mobile;
	private String idCard;
	private Byte sex;
	private Long cardFace;
	private Long cardBack;
	private Byte isValid;
	private Long csysid;
	private Date ctime;
	private Long msysid;
	private Date mtime;

	// Constructors

	/** default constructor */
	public CfsCust() {
	}

	/** full constructor */
	public CfsCust(String realName, String mobile, String idCard, Byte sex,
			Long cardFace, Long cardBack, Long csysid, Date ctime,
			Long msysid, Date mtime) {
		this.realName = realName;
		this.mobile = mobile;
		this.idCard = idCard;
		this.sex = sex;
		this.cardFace = cardFace;
		this.cardBack = cardBack;
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

	public String getRealName() {
		return this.realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getIdCard() {
		return this.idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public Byte getSex() {
		return this.sex;
	}

	public void setSex(Byte sex) {
		this.sex = sex;
	}

	public Long getCardFace() {
		return this.cardFace;
	}

	public void setCardFace(Long cardFace) {
		this.cardFace = cardFace;
	}

	public Long getCardBack() {
		return this.cardBack;
	}

	public void setCardBack(Long cardBack) {
		this.cardBack = cardBack;
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

	public Byte getIsValid() {
		return isValid;
	}

	public void setIsValid(Byte isValid) {
		this.isValid = isValid;
	}
}