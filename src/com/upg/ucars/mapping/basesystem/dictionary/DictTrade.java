package com.upg.ucars.mapping.basesystem.dictionary;

/**
 * DictTrade entity. @author MyEclipse Persistence Tools
 */

public class DictTrade implements java.io.Serializable {

	// Fields

	private Long id;
	private Long pid;
	private String code;
	private String tempPcode;
	private String tradeFullnameCn;
	private String tradeNameCn;
	private String tradeNameEn;
	private String tradeDesc;
	private String status;
	private Integer tradeLevel;

	// Constructors

	/** default constructor */
	public DictTrade() {
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

	public String getTradeFullnameCn() {
		return this.tradeFullnameCn;
	}

	public void setTradeFullnameCn(String tradeFullnameCn) {
		this.tradeFullnameCn = tradeFullnameCn;
	}

	public String getTradeNameCn() {
		return this.tradeNameCn;
	}

	public void setTradeNameCn(String tradeNameCn) {
		this.tradeNameCn = tradeNameCn;
	}

	public String getTradeNameEn() {
		return this.tradeNameEn;
	}

	public void setTradeNameEn(String tradeNameEn) {
		this.tradeNameEn = tradeNameEn;
	}

	public String getTradeDesc() {
		return this.tradeDesc;
	}

	public void setTradeDesc(String tradeDesc) {
		this.tradeDesc = tradeDesc;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public Integer getTradeLevel() {
		return tradeLevel;
	}


	public void setTradeLevel(Integer tradeLevel) {
		this.tradeLevel = tradeLevel;
	}

	

	
}