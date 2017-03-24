package com.upg.ucars.constant;
/**
 * 通用常量 
 *
 * @author shentuwy
 */
public class CommonConst {
	
	/** 是 */
	public static final String	isTrue										= "1";
	/** 否 */
	public static final String	isFalse										= "0";
	
	public static final String	INSTANCE_STATUS_CODE	= "C0300";	//流程状态
	
	public static final String	APPROVAL_OPINION		= "C0801";
	
	/** 数据删除标志状态控制　转换条件： 0->1->-1　**/
	 public static final String DEL_FLAG_0 = "0"; // 默认状态，允许物理删除
	 public static final String DEL_FLAG_1 = "1"; // 产生业务过程，不允许物理删除，只能逻辑删除
	
	 /**--  负阶段   -1 逻辑删除　可用于业务实体状态和删除标志　*/
	public static final String STATUS_NEGATIVE_1 = "-1"; // 逻辑删除,垃圾数据
	
	
	
	/** 美国英语 */
	public static final String LANGUAGE_EN_US = "en_US";
	/** 简体中文 */
	public static final String LANGUAGE_ZH_CN = "zh_CN";
	
	
	/**逻辑-是*/
	public static final String LOGIC_YES="1";
	/**逻辑-否*/
	public static final String LOGIC_NO="0";
	
	
	/**有效*/
	public static final String EFFECTIVE="1";
	/**无效*/
	public static final String UNEFFECTIVE="0";
	
	
	/** 性别男 */
	public static final String SEX_MAN = "0";
	/** 性别女 */
	public static final String SEX_FEMALE = "1";
	
	
	
	/** 未婚 */
	public static final String MARRAY_N = "0";
	/** 已婚 */
	public static final String MARRY_Y = "1";
	
	
	/** 学位-博士后 */
	public static final String DEGREE_POST_DOCTOR = "0";
	/** 学位-博士 */
	public static final String DEGREE_DOCTOR = "1";
	/** 学位-硕士 */
	public static final String DEGREE_MASTER = "2";
	/** 学位-研究生 */
	public static final String DEGREE_GRADUATE = "3";
	/** 学位-本科 */
	public static final String DEGREE_UNDERGRADUATE = "4";
	/** 学位-大学 */
	public static final String DEGREE_COLLEGE = "5";
	/** 学位-高中 */
	public static final String DEGREE_MIDDLE = "6";
	/** 学位-其它 */
	public static final String DEGREE_OTHER = "7";
	
	
	/** 国家-澳大利亚 */
	public static final String AUSTRALIA = "AU";
	/** 国家-白俄罗斯 */
	public static final String BELARUS = "BY";
	/** 国家-巴西 */
	public static final String BRAZIL = "BR";
	/** 国家-缅甸 */
	public static final String MYANMAR = "MM";
	/** 国家-中国 */
	public static final String CHINA = "CN";
	
	
	/** 币种-人名币 */
	public static final String RMB = "1";
	/** 币种-美元 */
	public static final String USD = "2";
	/** 币种-港元 */
	public static final String HKD = "3";
	/** 币种-欧元 */
	public static final String EUR = "4";
	/** 币种-日元 */
	public static final String JPY = "5";
	
	/** xdatagrid数据源datasrc的key */
	public static final String XDATA_GRID_SRC_KEY = "XDATA_GRID_SRC_KEY";
	/** 逗号字符串 */
	public static final String COMMA = ",";
}
