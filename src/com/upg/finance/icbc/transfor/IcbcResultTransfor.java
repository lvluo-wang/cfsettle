package com.upg.finance.icbc.transfor;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ucars.tsc.TransportInvocation;
import com.ucars.tsc.transfor.ResultTransfor;
import com.ucars.tsc.util.BeanXmlUtils;
import com.ucars.tsc.util.CodeUtils;
import com.upg.finance.icbc.IcbcConstants;
import com.upg.finance.icbc.bean.SignatureKeyFile;
import com.upg.finance.log.core.IFinanceCallLogService;
import com.upg.finance.mapping.local.FinanceCallLog;

@Component
public class IcbcResultTransfor implements ResultTransfor {

	private static final String	ANS_START_TAG		= "<ans>";
	private static final String	ANS_END_TAG			= "</ans>";
	private static final String	PUB_START_TAG		= "<pub>";
	private static final String	PUB_END_TAG			= "</pub>";
	private static final String	SIGNATURE_START_TAG	= "<signature>";
	private static final String	SIGNATURE_END_TAG	= "</signature>";

	@Resource
	private IFinanceCallLogService financeCallLogService;
	
	@Override
	public Object transfor(Object arg0, Map<String, Object> map) {
		String xml = (String) arg0;
		
		//记录调用返回值
		FinanceCallLog log = financeCallLogService.get();
		if (log != null){
			log.setExtsysReturnContent("content=" + String.valueOf(xml));
		}
		

		// 校验是否被修改
		int pubStartIndex = xml.indexOf(PUB_START_TAG);
		int pubEndIndex = xml.indexOf(PUB_END_TAG);
		String pubStr = xml.substring(pubStartIndex, pubEndIndex + PUB_END_TAG.length());

		int ansStartIndex = xml.indexOf(ANS_START_TAG);
		int ansEndIndex = xml.indexOf(ANS_END_TAG);
		String ansStr = xml.substring(ansStartIndex, ansEndIndex + ANS_END_TAG.length());

		int signatureStartIndex = xml.indexOf(SIGNATURE_START_TAG);
		int signatureEndIndex = xml.indexOf(SIGNATURE_END_TAG);
		String signatureStr = xml.substring(signatureStartIndex + SIGNATURE_START_TAG.length(), signatureEndIndex);

		boolean verifyResult = CodeUtils.verify(SignatureKeyFile.getPublicKeyFile(),
				CodeUtils.SIGNATURE_ALGORITHM_SHA1_WITH_RSA, pubStr + ansStr,
				IcbcConstants.SIGNATURE_MESSAGE_CHARSETNAME, signatureStr);
		if (!verifyResult) { // 数据被修改
			throw new RuntimeException("数据在传输中被修改");
		}
		//

		TransportInvocation invocation = (TransportInvocation) map.get(TransportInvocation.TRANSPORT_INVOCATION);
		Class<?> cls = invocation.getResultType();
		Object result = BeanXmlUtils.xml2Object(xml, cls);
		
		//TODO 记录调用返回流水号
		log = financeCallLogService.get();
		if (log != null){
			
		}
		
		return result;
	}

}
