package com.upg.cfsettle.util;

public class CfsConstant {
	
	public final static String CFS_COMM_YSE_NO = "COM001";//是否
	public final static String CFS_COMM_SEX = "COM002";//性别
	
	public final static String CFS_TIMELIMIT_UNIT = "E010";
	
	public final static Byte PRJ_STATUS_AUDIT = 1; //待审核
	public final static Byte PRJ_STATUS_REFUSE = 2; //审核驳回
	public final static Byte PRJ_STATUS_INVESTING = 3; //投资中
	public final static Byte PRJ_STATUS_TO_LOAN = 4; //待放款
	public final static Byte PRJ_STATUS_TO_PAYBACK = 5; //待回款
	public final static Byte PRJ_STATUS_PAYBACKING = 6; //回款中
	public final static Byte PRJ_STATUS_PAYBACKED = 7; //已回款
	public final static Byte PRJ_STATUS_FAILED = 8; //流标

	public final static Byte CONTRACT_LOG_STATUS_PASS = 1; //审核通过
	public final static Byte CONTRACT_LOG_STATUS_REJECT = 2; //审核驳回

	public final static Byte PRJ_ORDER_STATUS_AUDIT = 1; //待合同审核
	public final static Byte PRJ_ORDER_STATUS_PAY = 2; //待打款审核
	public final static Byte PRJ_ORDER_STATUS_REJECT = 3; //退回重签
	public final static Byte PRJ_ORDER_STATUS_INVESTING = 4; //投资中
	public final static Byte PRJ_ORDER_STATUS_FAILED = 5; //已流标
	public final static Byte PRJ_ORDER_STATUS_TO_REAPY = 6; //待还款
	public final static Byte PRJ_ORDER_STATUS_REPAYING = 7; //还款中
	public final static Byte PRJ_ORDER_STATUS_REPAYED = 8; //已还款
	
	public final static String PRJ_REPAY_WAY_A = "A"; //到期还本付息
	public final static String PRJ_REPAY_WAY_B = "B"; //每月付息到期还本
	public final static String PRJ_REPAY_WAY_C = "C"; //每季付息到期还本
	public final static String PRJ_REPAY_WAY_D = "D"; //每半年付息到期还本
}
