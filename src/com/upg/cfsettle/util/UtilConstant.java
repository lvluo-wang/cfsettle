package com.upg.cfsettle.util;

import java.math.BigDecimal;

/**
 * 常用基本数据状态定义
 * @author renzhuolun
 * @date 2014年7月24日 下午3:23:59
 * @version <b>1.0.0</b>
 */
public final class UtilConstant {
	public static final String YES_NO = "COM001";			//是否
	public static final String XXH_CUSTOMER_SEX = "A001";//客户性别
	public static final String XXH_CUSTOMER_MARITAL = "A002";//婚姻状况
	public static final String XXH_CUSTOMER_UIDTYPE = "F0001";//客户类型（企业、个人）
	public static final String XXH_CUSTOMER_MINO = "F0003";//接入点
	public static final String XXH_CUSTOMER_INFOSOURCE = "F0008";//信息来源
	public static final String XXH_SYS_TYPE = "F0009";	//权限的系统类型
	public static final String XXH_FUN_TYPE = "F0010";	//权限的功能类型
	public static final String XXH_PROTOCOL_TYPE = "F0011"; //协议类型
	public static final String XXH_BANNER_POSITION = "E022"; //Banner位置
	public static final String XXH_BANNER_SORT = "F0013"; //Banner序号
	public static final String XXH_INMESSAGE_TYPE = "F0014"; //站内信的类型
	public static final String XXH_ANNOUNCE_ISIMPORTANT = "F0015"; //公告管理：公告是否为重要。
	public static final String XXH_ANNOUNCE_ISRECOMMEND = "F0016"; //公告管理：公告是否为推荐。
	public static final String XXH_CUSTOMER_REGCLIENT = "F0017"; //注册客户端
	public static final String XXH_COMPUTETYPE = "E036";//计算类型
	public static final String XXH_COMPUTEMEMO = "E037";//计算类型标注
	public static final String XXH_PAYMOMENT = "E038";//缴费时机
	public static final String XXH_PAYOBJECT = "E039";//缴费对象
	public static final String CFS_BANK_TYPE = "E009"; //银行类型
	public static final String CFS_REPAYMENT_TYPE = "E012";//还款方式
	public static final String CFS_TIMELIMIT_UNIT = "E010";//项目期限
	public static final String CFS_PRJ_STATUS = "E011";//项目状态
	public static final String CFS_COMM_PAY_STATUS = "E013";//佣金支付状态
	public static final String CFS_PRJ_ORDER_STATUS = "E014";//订单状态
	public static final String CFS_BUSER_POS_CODE = "COM003";//岗位名称
	public static final String CFS_PRJ_REPAY_PLAN_STATUS = "E015"; //还款计划状态
	
	public static final String CFS_CUST_MANAGER = "01";//客户经理
	public static final String CFS_TEAM_MANAGER = "02";//团队长
	public static final String CFS_DEPT_MANAGER = "03";//营业部负责人
	public static final String CFS_AREA_MANAGER = "04";//区域经理

	public static final String TIME_LIMIT_YEAR = "Y"; //期限-年
	public static final String TIME_LIMIT_MONTH = "M";//期限-月
	public static final String TIME_LIMIT_WEEK = "W";//期限-周
	
	public static final Byte   PTYPE_NORMAL = 1; //正常利息
	public static final Byte   PTYPE_PERIODS = 2; //募集期利息
	
	public static final Byte   REPAY_STATUS_1 = 1; //待还款
	public static final Byte   REPAY_STATUS_2 = 2; //已还款
	public static final Byte   REPAY_STATUS_3 = 3; //还款结束

	public static final Byte   COMM_STATUS_1 = 1; //待付款
	public static final Byte   COMM_STATUS_2 = 2; //已付款
	
	
	public static final BigDecimal DEFAULT_ZERO = new BigDecimal(0); //默认0值
	


	//常用数字字符串
	public static final String NUMBER_0 = "0";
	public static final String NUMBER_1 = "1";
	public static final String NUMBER_2 = "2";
	public static final String NUMBER_3 = "3";
	public static final String NUMBER_4 = "4";
	public static final String NUMBER_5 = "5";
	public static final String NUMBER_6 = "6";
	public static final String NUMBER_7 = "7";
	public static final String NUMBER_8 = "8";
	public static final String NUMBER_9 = "9";
}
