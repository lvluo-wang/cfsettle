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
}
