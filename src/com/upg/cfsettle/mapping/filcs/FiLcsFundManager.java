package com.upg.cfsettle.mapping.filcs;

import java.io.Serializable;
import java.util.Date;

/**
 * 基金管理者
 * @author renzhuolun
 * @date 2016年6月3日 下午3:24:38
 * @version <b>1.0.0</b>
 */

public class FiLcsFundManager implements Serializable {

	// Fields
	/**
	 * 序号
	 */
	private static final long serialVersionUID = -7167833454136731314L;
	private Long id;
	private Long fundId;
	private Byte manageType;
	private String xname;
	private String position;
	private Long workYear;
	private Long avgRate;
	private String describe;
	private Long headAttach;
	private Date ctime;
	private Date mtime;
	
	private String fundManagerPerExt;
	private String fundManagerTeam;
	private String fundManagerTeamExt;
	private String fundIndex;

	// Constructors

	/** default constructor */
	public FiLcsFundManager() {
	}

	/** minimal constructor */
	public FiLcsFundManager(Long id) {
		this.id = id;
	}

	/** full constructor */
	public FiLcsFundManager(Long id, Long fundId, Byte manageType,
			String xname, String position, Long workYear, Long avgRate,
			String describe, Date ctime, Date mtime) {
		this.id = id;
		this.fundId = fundId;
		this.manageType = manageType;
		this.xname = xname;
		this.position = position;
		this.workYear = workYear;
		this.avgRate = avgRate;
		this.describe = describe;
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

	public Long getFundId() {
		return this.fundId;
	}

	public void setFundId(Long fundId) {
		this.fundId = fundId;
	}

	public Byte getManageType() {
		return this.manageType;
	}

	public void setManageType(Byte manageType) {
		this.manageType = manageType;
	}

	public String getXname() {
		return this.xname;
	}

	public void setXname(String xname) {
		this.xname = xname;
	}

	public String getPosition() {
		return this.position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Long getWorkYear() {
		return this.workYear;
	}

	public void setWorkYear(Long workYear) {
		this.workYear = workYear;
	}

	public Long getAvgRate() {
		return this.avgRate;
	}

	public void setAvgRate(Long avgRate) {
		this.avgRate = avgRate;
	}

	public String getDescribe() {
		return this.describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
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

	public Long getHeadAttach() {
		return headAttach;
	}

	public void setHeadAttach(Long headAttach) {
		this.headAttach = headAttach;
	}

	public String getFundManagerPerExt() {
		return fundManagerPerExt;
	}

	public void setFundManagerPerExt(String fundManagerPerExt) {
		this.fundManagerPerExt = fundManagerPerExt;
	}

	public String getFundManagerTeam() {
		return fundManagerTeam;
	}

	public void setFundManagerTeam(String fundManagerTeam) {
		this.fundManagerTeam = fundManagerTeam;
	}

	public String getFundManagerTeamExt() {
		return fundManagerTeamExt;
	}

	public void setFundManagerTeamExt(String fundManagerTeamExt) {
		this.fundManagerTeamExt = fundManagerTeamExt;
	}

	public String getFundIndex() {
		return fundIndex;
	}

	public void setFundIndex(String fundIndex) {
		this.fundIndex = fundIndex;
	}
}