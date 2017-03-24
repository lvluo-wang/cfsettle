package com.upg.cfsettle.mapping.filcs;

import java.io.Serializable;
import java.util.Date;

/**
 * 理财师答案
 * @author renzhuolun
 * @date 2016年6月16日 上午10:32:21
 * @version <b>1.0.0</b>
 */
public class FiLcsAnswer implements Serializable {

	/**
	 * 序号
	 */
	private static final long serialVersionUID = 7079887447851498431L;
	private Long id;
	private Long questionId;
	private String answer;
	private Long answerValue;
	private String remark;
	private String sortNo;
	private Date ctime;
	private Date mtime;

	// Constructors

	/** default constructor */
	public FiLcsAnswer() {
	}

	/** full constructor */
	public FiLcsAnswer(Long questionId, String answer, Long answerValue,
			String remark, String sortNo, Date ctime, Date mtime) {
		this.questionId = questionId;
		this.answer = answer;
		this.answerValue = answerValue;
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

	public Long getQuestionId() {
		return this.questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public String getAnswer() {
		return this.answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Long getAnswerValue() {
		return this.answerValue;
	}

	public void setAnswerValue(Long answerValue) {
		this.answerValue = answerValue;
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