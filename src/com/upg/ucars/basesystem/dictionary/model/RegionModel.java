package com.upg.ucars.basesystem.dictionary.model;

/**
 * 行政区域模型 
 *
 * @author shentuwy
 */
public class RegionModel {
	private String codeNo;//程序编号(无实际意义)
	private String regionName;//名称
	
	private String busiNo;//业务编号(实际意义)

	public RegionModel(String codeNo, String regionName, String busiNo) {
		super();
		this.codeNo = codeNo;
		this.regionName = regionName;
		this.busiNo = busiNo;
	}

	public RegionModel() {
		super();
	}

	public String getCodeNo() {
		return codeNo;
	}

	public void setCodeNo(String codeNo) {
		this.codeNo = codeNo;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getBusiNo() {
		return busiNo;
	}

	public void setBusiNo(String busiNo) {
		this.busiNo = busiNo;
	}
	
	
	

}
