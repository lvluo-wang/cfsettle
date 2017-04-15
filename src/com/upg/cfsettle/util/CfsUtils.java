package com.upg.cfsettle.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;

import com.upg.ucars.basesystem.UcarsHelper;
import com.upg.ucars.util.DateTimeUtil;
/**
 * 
 * 鑫合汇后台工具类
 * @author renzhuolun
 * @date 2014年8月5日 下午3:03:10
 * @version <b>1.0.0</b>
 */
public class CfsUtils {
	
	/**
	 * 字符串数组转long数组
	 * @author renzhuolun
	 * @date 2014年8月5日 下午3:03:32
	 * @param stringList
	 * @return
	 */
	public static List<Long> StringToLong(List<String> stringList) {
		List<Long> reLongList=new ArrayList<Long>();
		for(String str:stringList){
			reLongList.add(Long.valueOf(str));
		}
		return reLongList;
	}
	
	/**
	 * 字符串数组转long数组
	 * @author renzhuolun
	 * @date 2014年8月5日 下午3:03:32
	 * @param stringList
	 * @return
	 */
	public static List<Long> StringToLong(String[] stringList) {
		List<Long> reLongList=new ArrayList<Long>();
		for(String str:stringList){
			reLongList.add(Long.valueOf(str));
		}
		return reLongList;
	}

	/**
	 * 根据数字和单位计算天数(起始时间为startDate).
	 * @author yuanfengjian
	 * @date 2015年8月28日
	 */
	public static Integer getDayTime(Date startDate, Integer num, String unit) {
		if (null == startDate || null == num || null == unit) {
			UcarsHelper.throwServiceException("传入参数不能为空！");
		}
		if (UtilConstant.TIME_LIMIT_YEAR.equals(unit)) {
			Date endDate = DateUtils.addYears(startDate, num);
			return DateTimeUtil.getDaysBetween(startDate, endDate);
		} else if (UtilConstant.TIME_LIMIT_MONTH.equals(unit)) {
			Date endDate = DateUtils.addMonths(startDate, num);
			return DateTimeUtil.getDaysBetween(startDate, endDate);
		} else {
			return num;
		}
	}
	
	public static BigDecimal calcSumRealAmount(BigDecimal actualAmount,BigDecimal timeLimitDay,BigDecimal rate){
		BigDecimal fee = BigDecimal.ZERO;
		fee=actualAmount.multiply(rate.divide(new BigDecimal("100"),4,BigDecimal.ROUND_FLOOR)).
                multiply(timeLimitDay).divide(BigDecimal.valueOf(365), 2, BigDecimal.ROUND_FLOOR);
		return fee;
	}

}
