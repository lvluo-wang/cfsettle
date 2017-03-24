package com.upg.finance.icbc.processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ucars.tsc.TransportInvocation;
import com.ucars.tsc.protocol.HttpProtocolProcessor;
import com.ucars.tsc.transportor.HttpTransportor;
import com.ucars.tsc.util.BeanXmlUtils;
import com.ucars.tsc.util.CodeUtils;
import com.upg.finance.icbc.IcbcConstants;
import com.upg.finance.icbc.annotation.ReqPubConfig;
import com.upg.finance.icbc.bean.SignatureKeyFile;
import com.upg.finance.icbc.bean.factory.RequestPubFactory;
import com.upg.finance.icbc.bean.req.BaseRequest;
import com.upg.finance.icbc.bean.req.RequestPackage;
import com.upg.finance.icbc.bean.req.RequestPub;
import com.upg.finance.log.core.IFinanceCallLogService;
import com.upg.finance.mapping.local.FinanceCallLog;

@Component
public class IcbcRequestProcoessor implements HttpProtocolProcessor {

	private static final Map<Class<? extends BaseRequest>, String>	TX_CODE_MAP	= new HashMap<Class<? extends BaseRequest>, String>();

	@Resource
	private IFinanceCallLogService									financeCallLogService;

	@Override
	public String protocol(Map<String, Object> map) {
		TransportInvocation invocation = (TransportInvocation) map.get(TransportInvocation.TRANSPORT_INVOCATION);
		BaseRequest req = (BaseRequest) invocation.getParameters()[0];
		RequestPackage requestPackage = buildRequestPackage(req);
		String xml = BeanXmlUtils.bean2Xml(requestPackage);
		map.put(HttpProtocolProcessor.HTTP_PROTOCOL_CONTENT_KEY, xml);

		// 记录调用参数
		FinanceCallLog log = financeCallLogService.get();
		if (log != null) {
			String url = (String) map.get(HttpTransportor.PARAMETER_URL);
			String cmpTxsNo = ((RequestPub) requestPackage.getPub()).getCmptxsno();
			log.setCmpTxsNo(cmpTxsNo);
			log.setExtsysCallContent("url=" + String.valueOf(url) + ",content=" + String.valueOf(xml));
		}
		return xml;
	}

	private static final String getTxCode(BaseRequest req) {
		String result = null;
		ReqPubConfig reqPubConfig = req.getClass().getAnnotation(ReqPubConfig.class);
		if (reqPubConfig != null) {
			result = reqPubConfig.value();
		}
		return result;
	}

	private final RequestPackage buildRequestPackage(BaseRequest req) {
		RequestPackage result = new RequestPackage();
		Class<? extends BaseRequest> cls = req.getClass();
		String txCode = TX_CODE_MAP.get(cls);
		if (txCode == null) {
			txCode = getTxCode(req);
			TX_CODE_MAP.put(cls, txCode);
		}
		RequestPub pub = RequestPubFactory.generateRequestPub(txCode);
		// 重新设置公司流水号
		FinanceCallLog log = financeCallLogService.get();
		if (log != null) {
			pub.setCmptxsno(log.getCmpTxsNo());
		}

		List<Object> signBeanList = new ArrayList<Object>();
		signBeanList.add(pub);
		signBeanList.add(req);
		String signature = getSignResult(signBeanList);

		result.setPub(pub);
		result.setReq(req);
		result.setSignature(signature);

		return result;
	}

	public static final String getSignResult(List<Object> signBeanList) {
		StringBuilder sb = new StringBuilder();
		if (signBeanList != null) {
			for (Object obj : signBeanList) {
				String objXml = BeanXmlUtils.bean2Xml(obj, BEAN_TO_XML_PROPERTIES);
				if (objXml != null) {
					sb.append(objXml);
				}
			}
		}
		String signature = CodeUtils
				.sign(SignatureKeyFile.getPrivateKeyFile(), CodeUtils.SIGNATURE_ALGORITHM_SHA1_WITH_RSA, sb.toString(),
						IcbcConstants.SIGNATURE_MESSAGE_CHARSETNAME);
		return signature;
	}

	private static final Map<String, Object>	BEAN_TO_XML_PROPERTIES	= new HashMap<String, Object>();

	static {
		BEAN_TO_XML_PROPERTIES.put(BeanXmlUtils.FRAGMENT, true);
	}

}
