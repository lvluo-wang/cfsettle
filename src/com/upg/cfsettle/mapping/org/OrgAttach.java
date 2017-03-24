package com.upg.cfsettle.mapping.org;

import java.io.Serializable;
import java.util.Date;

/**
 * 组织机构附件表
 * @author renzhuolun
 * @date 2016年8月23日 下午2:27:37
 * @version <b>1.0.0</b>
 */

public class OrgAttach implements Serializable {

	/**
	 * 序号
	 */
	private static final long serialVersionUID = -3737534119235436615L;
	private Long id;
	private String path;
	private String fileName;
	private String fileType;
	private String comments;
	private Short property;
	private Long orgId;
	private Long createdBy;
	private Long lastUpdatedBy;
	private Date mtime;
	private Date ctime;

	// Constructors

	/** default constructor */
	public OrgAttach() {
	}
	
	public OrgAttach(Long orgId,Short property) {
		this.orgId = orgId;
		this.property = property;
	}

	/** full constructor */
	public OrgAttach(String path, String fileName, String fileType,
			String comments, Short property, Long orgId, Long createdBy,
			Long lastUpdatedBy, Date mtime, Date ctime) {
		this.path = path;
		this.fileName = fileName;
		this.fileType = fileType;
		this.comments = comments;
		this.property = property;
		this.orgId = orgId;
		this.createdBy = createdBy;
		this.lastUpdatedBy = lastUpdatedBy;
		this.mtime = mtime;
		this.ctime = ctime;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return this.fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Short getProperty() {
		return this.property;
	}

	public void setProperty(Short property) {
		this.property = property;
	}

	public Long getOrgId() {
		return this.orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Long getLastUpdatedBy() {
		return this.lastUpdatedBy;
	}

	public void setLastUpdatedBy(Long lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Date getMtime() {
		return this.mtime;
	}

	public void setMtime(Date mtime) {
		this.mtime = mtime;
	}

	public Date getCtime() {
		return this.ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

}