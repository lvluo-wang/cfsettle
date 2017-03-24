package com.upg.ucars.mapping.basesystem.dictionary;

/**
 * DictArea entity. @author MyEclipse Persistence Tools
 */

public class DictArea implements java.io.Serializable {

	// Fields

	private Long id;
	private Long pid;
	private String code;
	private String tempPcode;
	private String nameCn;
	private String allNameCn;
	private String nameEn;
	private String allNameEn;
	private String status;
	private Integer areaLevel;

	// Constructors

	/** default constructor */
	public DictArea() {
	}

	

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPid() {
		return this.pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTempPcode() {
		return this.tempPcode;
	}

	public void setTempPcode(String tempPcode) {
		this.tempPcode = tempPcode;
	}

	public String getNameCn() {
		return this.nameCn;
	}

	public void setNameCn(String nameCn) {
		this.nameCn = nameCn;
	}

	public String getAllNameCn() {
		return this.allNameCn;
	}

	public void setAllNameCn(String allNameCn) {
		this.allNameCn = allNameCn;
	}

	public String getNameEn() {
		return this.nameEn;
	}

	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}

	public String getAllNameEn() {
		return this.allNameEn;
	}

	public void setAllNameEn(String allNameEn) {
		this.allNameEn = allNameEn;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}



	public Integer getAreaLevel() {
		return areaLevel;
	}



	public void setAreaLevel(Integer areaLevel) {
		this.areaLevel = areaLevel;
	}

	
}