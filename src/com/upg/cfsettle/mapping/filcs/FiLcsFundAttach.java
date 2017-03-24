package com.upg.cfsettle.mapping.filcs;

import java.io.Serializable;
import java.util.Date;

/**
 * 基金产品可视材料
 * @author renzhuolun
 * @date 2016年6月3日 下午3:23:35
 * @version <b>1.0.0</b>
 */

public class FiLcsFundAttach implements Serializable {

	/**
	 * 序号
	 */
	private static final long serialVersionUID = -3600295206845767979L;
	private Long id;
	private Long fundId;
	private String path;
	private String fileName;
	private String luyanDesc;
	private Long sortNo;
	private String fileType;
	private Long attachId;
	private Date ctime;
	private Date mtime;

	// Constructors

	/** default constructor */
	public FiLcsFundAttach() {
	}

	/** minimal constructor */
	public FiLcsFundAttach(Long id) {
		this.id = id;
	}
	
	public FiLcsFundAttach(Long id,Long fundId) {
		this.id = id;
		this.fundId = fundId;
	}

	/** full constructor */
	public FiLcsFundAttach(Long id, Long fundId,
			String path, String fileName, String luyanDesc, Long sortNo,
			Date ctime, Date mtime) {
		this.id = id;
		this.fundId = fundId;
		this.path = path;
		this.fileName = fileName;
		this.luyanDesc = luyanDesc;
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

	public Long getFundId() {
		return this.fundId;
	}

	public void setFundId(Long fundId) {
		this.fundId = fundId;
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

	public Long getSortNo() {
		return this.sortNo;
	}

	public void setSortNo(Long sortNo) {
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

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getLuyanDesc() {
		return luyanDesc;
	}

	public void setLuyanDesc(String luyanDesc) {
		this.luyanDesc = luyanDesc;
	}

	public Long getAttachId() {
		return attachId;
	}

	public void setAttachId(Long attachId) {
		this.attachId = attachId;
	}
}