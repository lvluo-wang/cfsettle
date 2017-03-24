package com.upg.ucars.basesystem.acctrecord.analyse;

import com.upg.ucars.basesystem.acctrecord.runtime.AcctInfoCollector;
import com.upg.ucars.mapping.basesystem.acctrecord.AcctTran;
/**
 * 
 * 记帐附加上下文
 *
 * @author shentuwy
 */
public abstract class AbstractAttachTranContext {
	/**
	 * 添加上下文
	 * @param eventColler
	 */
	public abstract void attachContext(AcctTran tran, AcctInfoCollector eventColler);
	

}
