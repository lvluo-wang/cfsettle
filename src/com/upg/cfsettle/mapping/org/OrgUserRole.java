package com.upg.cfsettle.mapping.org;

/**
 * OrgUserRole entity. @author MyEclipse Persistence Tools
 */

public class OrgUserRole implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer uid;
	private Integer rid;
	private Integer createdBy;
	private Integer lastUpdatedBy;
	private Integer ctime;
	private Integer mtime;

	// Constructors

	/** default constructor */
	public OrgUserRole() {
	}

	/** full constructor */
	public OrgUserRole(Integer uid, Integer rid, Integer createdBy,
			Integer lastUpdatedBy, Integer ctime, Integer mtime) {
		this.uid = uid;
		this.rid = rid;
		this.createdBy = createdBy;
		this.lastUpdatedBy = lastUpdatedBy;
		this.ctime = ctime;
		this.mtime = mtime;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUid() {
		return this.uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Integer getRid() {
		return this.rid;
	}

	public void setRid(Integer rid) {
		this.rid = rid;
	}

	public Integer getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Integer getLastUpdatedBy() {
		return this.lastUpdatedBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Integer getCtime() {
		return this.ctime;
	}

	public void setCtime(Integer ctime) {
		this.ctime = ctime;
	}

	public Integer getMtime() {
		return this.mtime;
	}

	public void setMtime(Integer mtime) {
		this.mtime = mtime;
	}

}