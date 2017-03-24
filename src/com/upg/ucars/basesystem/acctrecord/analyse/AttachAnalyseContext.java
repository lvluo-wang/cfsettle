package com.upg.ucars.basesystem.acctrecord.analyse;

import com.upg.ucars.basesystem.acctrecord.runtime.AcctInfoCollector;
import com.upg.ucars.mapping.basesystem.acctrecord.AcctTran;

/**
 * 附加上下文
 * 
 * @author yangjun (mailto:yangjun@leadmind.com.cn)
 */
public class AttachAnalyseContext {
	
	
	private static AbstractAttachEventContext createAttachEventContext(String prodNo, String eventNo){
		AbstractAttachEventContext attachContext = null;
		try {
			Class clz = Class
					.forName("com.leadmind.bbsp.bc.acctrecord.core.analyse.context.AttachEventContext_"
							+ prodNo + "_" + eventNo);
			attachContext = (AbstractAttachEventContext) clz.newInstance();

		} catch (Exception e) {
			// 无须添加上下文
		}
		return attachContext;
	}

	/**
	 * 添加记帐事件上下文
	 * 
	 * @param eventColler
	 */
	public static void attachAcctEventContext(String prodNo, String eventNo, AcctInfoCollector infoColler) {

		AbstractAttachEventContext attachContext = createAttachEventContext(prodNo, eventNo);		
		if (attachContext != null) {
			attachContext.attachContext(infoColler);
		}

	}
	
	
	private static AbstractAttachTranContext createAttachTranContext(String tranNo){		
		AbstractAttachTranContext tranContext = null;
		try {
			Class clz = Class
					.forName("com.leadmind.bbsp.bc.acctrecord.core.analyse.context.AttachTranContext_"
							+ tranNo);
			tranContext = (AbstractAttachTranContext) clz.newInstance();

		} catch (Exception e) {
			// 无须添加上下文
		}
		return tranContext;
	}
	/**
	 * 添加记帐交易上下文
	 * 
	 * @param eventColler
	 */
	public static void attachTranContext(AcctTran tran,	AcctInfoCollector infoColler) {
		AbstractAttachTranContext tranContext = createAttachTranContext(tran.getTranNo());		
		if (tranContext != null) {
			tranContext.attachContext(tran, infoColler);
		}

	}

}
