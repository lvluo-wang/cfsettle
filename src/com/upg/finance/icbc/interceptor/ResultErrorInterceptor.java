package com.upg.finance.icbc.interceptor;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ucars.tsc.TransportInvocation;
import com.ucars.tsc.interceptor.AbstractInterceptor;
import com.upg.finance.icbc.bean.BasePackage;
import com.upg.finance.icbc.bean.ans.AnswerPub;

@Component
public class ResultErrorInterceptor extends AbstractInterceptor {

	private static final Map<String, String>	ERROR_CODE_MAP	= new HashMap<String, String>();

	static {
		ERROR_CODE_MAP.put("B0001", "通讯报文错");
		ERROR_CODE_MAP.put("B0002", "系统错");
//		ERROR_CODE_MAP.put("B0033", "原交易已成功");
		ERROR_CODE_MAP.put("B0241", "原交易不正确");
		ERROR_CODE_MAP.put("B0245", "金额不符");
		ERROR_CODE_MAP.put("B0249", "相同公司流水号交易正在处理");
		ERROR_CODE_MAP.put("B9998", "（特殊错误，具体错误内容不定）");
		ERROR_CODE_MAP.put("B9997", "疑账");
		ERROR_CODE_MAP.put("99999", "未知");
	}

	@Override
	protected boolean doIntercept(TransportInvocation invocation) {
		invocation.invoke();
		// 校验调用错误编号
		BasePackage ansBasePackage = (BasePackage) invocation.getResult();
		if (ansBasePackage != null) {
			AnswerPub pub = (AnswerPub) ansBasePackage.getPub();
			String retCode = pub.getRetcode();
			if (ERROR_CODE_MAP.containsKey(retCode)) {
				String retmsg = pub.getRetmsg();
				throw new RuntimeException(retmsg == null ? ERROR_CODE_MAP.get(retCode) : retmsg);
			}
		}
		return true;
	}
}
