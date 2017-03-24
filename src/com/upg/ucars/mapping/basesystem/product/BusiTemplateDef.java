package com.upg.ucars.mapping.basesystem.product;


public class BusiTemplateDef implements java.io.Serializable {

	
	private Long id;
	private String busiNo;     //交易号
	private String busiName;   //交易名称
	private String busiType;   //交易类型
	private String prodNo;     //产品号
	private String templateFile;  //模板文件路径
	private String templateName;  //模板名称
	private String miNo;          //接入点号
	private Integer version;      //版本号

	// Constructors

	/** default constructor */
	public BusiTemplateDef() {
	}

	/** full constructor */
	public BusiTemplateDef(String busiNo, String busiName, String busiType,
			String prodNo, String templateFile, String templateName,
			String miNo) {
		this.busiNo = busiNo;
		this.busiName = busiName;
		this.busiType = busiType;
		this.prodNo = prodNo;
		this.templateFile = templateFile;
		this.templateName = templateName;
		this.miNo = miNo;
		
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBusiNo() {
		return this.busiNo;
	}

	public void setBusiNo(String busiNo) {
		this.busiNo = busiNo;
	}

	public String getBusiName() {
		return this.busiName;
	}

	public void setBusiName(String busiName) {
		this.busiName = busiName;
	}

	public String getBusiType() {
		return this.busiType;
	}

	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}

	public String getProdNo() {
		return this.prodNo;
	}

	public void setProdNo(String prodNo) {
		this.prodNo = prodNo;
	}

	
	public String getTemplateFile() {
		return templateFile;
	}

	public void setTemplateFile(String templateFile) {
		this.templateFile = templateFile;
	}

	public String getTemplateName() {
		return this.templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getMiNo() {
		return this.miNo;
	}

	public void setMiNo(String miNo) {
		this.miNo = miNo;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	
}