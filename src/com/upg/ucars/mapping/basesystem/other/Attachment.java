package com.upg.ucars.mapping.basesystem.other;

import com.upg.ucars.framework.base.BaseEntity;
import com.upg.ucars.framework.exception.ValidateException;

/**
 * 附件
 * 
 * @author shentuwy
 * @date 2012-6-29
 * 
 */
public class Attachment extends BaseEntity {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -3808893603087375664L;

	private Long				id;
	private String				name;
	private String				saveName;
	private String				attachPath;
	private Long				attachSize;
	private String				uploadType;
	private String				domain;
	private String				result;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSaveName() {
		return saveName;
	}

	public void setSaveName(String saveName) {
		this.saveName = saveName;
	}

	public String getAttachPath() {
		return attachPath;
	}

	public void setAttachPath(String attachPath) {
		this.attachPath = attachPath;
	}

	public Long getAttachSize() {
		return attachSize;
	}

	public void setAttachSize(Long attachSize) {
		this.attachSize = attachSize;
	}
	public String getUploadType() {
		return uploadType;
	}

	public void setUploadType(String uploadType) {
		this.uploadType = uploadType;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}
	
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	@Override
	public boolean canDelete() throws ValidateException {
		return true;
	}

}
