package com.upg.ucars.mapping.basesystem.product;


/**
 * 自动编码配置类
 * @author alw
 *
 */

public class AutoCodeConfig implements java.io.Serializable {

	// Fields

	private Long id;
	private String codeKey;   // 编码的KEY定义
	private String codeDesc;  //编码描述
	private String miNo;      //接入号，为空或0，所有接入点共用
	private String prefixNoRule;  //编码前缀规则
	private Integer keepBit;   //序号需要保留位数
	private Long curCount;     //当前序号值
	private String curDate;    //当前计数日
	private String circleType;  // 循环类型   0:不循环，1：按年  2：按月，3：按天
	
	public static String CIRCLE_TYPE_YEAR="1";
	public static String CIRCLE_TYPE_MONTH="2";
	public static String CIRCLE_TYPE_DAY="3";

	// Constructors

	/** default constructor */
	public AutoCodeConfig() {
	}

	/** full constructor */
	public AutoCodeConfig(String codeKey, String codeDesc, String miNo,
			String prefixNoRule, Integer keepBit, Long curCount, String curDate) {
		this.codeKey = codeKey;
		this.codeDesc = codeDesc;
		this.miNo = miNo;
		this.prefixNoRule = prefixNoRule;
		this.keepBit = keepBit;
		this.curCount = curCount;
		this.curDate = curDate;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodeKey() {
		return this.codeKey;
	}

	public void setCodeKey(String codeKey) {
		this.codeKey = codeKey;
	}

	public String getCodeDesc() {
		return this.codeDesc;
	}

	public void setCodeDesc(String codeDesc) {
		this.codeDesc = codeDesc;
	}

	public String getMiNo() {
		return this.miNo;
	}

	public void setMiNo(String miNo) {
		this.miNo = miNo;
	}

	public String getPrefixNoRule() {
		return this.prefixNoRule;
	}

	public void setPrefixNoRule(String prefixNoRule) {
		this.prefixNoRule = prefixNoRule;
	}

	public Integer getKeepBit() {
		return this.keepBit;
	}

	public void setKeepBit(Integer keepBit) {
		this.keepBit = keepBit;
	}

	public Long getCurCount() {
		return this.curCount;
	}

	public void setCurCount(Long curCount) {
		this.curCount = curCount;
	}

	public String getCurDate() {
		return this.curDate;
	}

	public void setCurDate(String curDate) {
		this.curDate = curDate;
	}

	public String getCircleType() {
		return circleType;
	}

	public void setCircleType(String circleType) {
		this.circleType = circleType;
	}


	
}