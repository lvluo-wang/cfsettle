package com.upg.ucars.basesystem.busidate.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.upg.ucars.framework.base.SessionTool;

/**
 * 
 * BusiDateUtil
 * 
 * @author shentuwy
 * 
 */
public final class BusiDateUtil {

	private static final String	DATE_FORMATE_STR	= "yyyy-MM-dd";

	public static final Date getCurBusiDate() {
		return getCurBusiDate(SessionTool.getUserLogonInfo().getMiNo());
	}

	public static final Date getCurBusiDate(String miNo) {
		Date ret = null;
		/*
		BusiDate bd = SourceTemplate.getBean(IBusiDateService.class, BeanNameConstants.BUSI_DATE_SERVICE)
				.getBusiDateByMino(miNo);
		if (bd != null) {
			ret = bd.getCurDate();
		}
		*/
		ret = new Date();
		return ret;
	}

	public static final String getCurBusiDateStr() {
		Date bd = getCurBusiDate();
		if (bd == null) {
			bd = new Date();
		}
		return new SimpleDateFormat(DATE_FORMATE_STR).format(bd);
	}

}
