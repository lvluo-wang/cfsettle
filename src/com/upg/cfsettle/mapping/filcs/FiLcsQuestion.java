package com.upg.cfsettle.mapping.filcs;

import java.io.Serializable;
import java.util.Date;

/**
 * 理财师问题
 * @author renzhuolun
 * @date 2016年6月16日 上午10:33:58
 * @version <b>1.0.0</b>
 */

public class FiLcsQuestion implements Serializable {

	/**
	 * 序号
	 */
	private static final long serialVersionUID = 5217200749696911169L;
	private Long id;
	private String question;
	private String typeCode;
	private String remark;
	private String sortNo;
	private Date ctime;
	private Date mtime;

	// Constructors

	/** default constructor */
	public FiLcsQuestion() {
	}

	/** full constructor */
	public FiLcsQuestion(String question, String typeCode, String remark,
			String sortNo, Date ctime, Date mtime) {
		this.question = question;
		this.typeCode = typeCode;
		this.remark = remark;
		this.sortNo = sortNo;
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

	public String getQuestion() {
		return this.question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getTypeCode() {
		return this.typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSortNo() {
		return this.sortNo;
	}

	public void setSortNo(String sortNo) {
		this.sortNo = sortNo;
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