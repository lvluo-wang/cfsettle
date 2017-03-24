package com.upg.finance.icbc.bean.factory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.upg.finance.icbc.bean.req.RequestPub;

public class RequestPubFactory {

	/** 交易发生地信息 */
	private static final String		CITY_NO_VALE	= "1202";
	/** 应用号 */
	private static final String		APPNO_VALUE		= "AP1001";

	private static final DateFormat	CMP_DATE_FORMAT	= new SimpleDateFormat("yyyyMMdd");

	private static final DateFormat	CMP_TIME_FORMAT	= new SimpleDateFormat("HHmmss");

	public static final RequestPub generateRequestPub(String txCode) {
		RequestPub result = new RequestPub();
		Date date = new Date();
		result.setTxcode(txCode);
		result.setCityno(CITY_NO_VALE);
		String cmpdate = getCmpDate(date);
		result.setCmpdate(cmpdate);
		String cmptime = getCmpTime(date);
		result.setCmptime(cmptime);
		
//		String cmptxsno = generateCmptxsno(date);
//		result.setCmptxsno(cmptxsno);
		result.setAppno(APPNO_VALUE);
		return result;
	}

	private static final String getCmpDate(Date date) {
		return CMP_DATE_FORMAT.format(date);
	}

	private static final String getCmpTime(Date date) {
		return CMP_TIME_FORMAT.format(date);
	}

	// TODO 考虑集群、持久化
	private synchronized static final String generateCmptxsno(Date date) {
		String cmpDate = getCmpDate(date);

		String suffix = "" + (++txCodeSuffix);
		int suffixLength = suffix.length();
		for (int i = 0; i < 20 - suffixLength; i++) {
			suffix = "0" + suffix;
		}

		return cmpDate + suffix;
	}

	private static long	txCodeSuffix	= 0;
}
