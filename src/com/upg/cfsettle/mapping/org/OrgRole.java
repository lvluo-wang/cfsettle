package com.upg.cfsettle.mapping.org;

/**
 * OrgRole entity. @author MyEclipse Persistence Tools
 */

public class OrgRole implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer orgId;
	private String roleCode;
	private String roleName;
	private String description;
	private Short isInitial;
	private Integer createdBy;
	private Integer lastUpdatedBy;
	private Integer ctime;
	private Integer mtime;

	// Constructors

	/** default constructor */
	public OrgRole() {
	}

	/** full constructor */
	public OrgRole(Integer orgId, String roleCode, String roleName,
			String description, Short isInitial, Integer createdBy,
			Integer lastUpdatedBy, Integer ctime, Integer mtime) {
		this.orgId = orgId;
		this.roleCode = roleCode;
		this.roleName = roleName;
		this.description = description;
		this.isInitial = isInitial;
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

	public Integer getOrgId() {
		return this.orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public String getRoleCode() {
		return this.roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Short getIsInitial() {
		return this.isInitial;
	}

	public void setIsInitial(Short isInitial) {
		this.isInitial = isInitial;
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