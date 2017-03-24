package com.upg.ucars.basesystem.acctrecord.analyse;

import com.upg.ucars.basesystem.acctrecord.runtime.AcctInfoCollector;

/**
 * 
 * 记帐附加上下文
 *
 * @author shentuwy
 */
public abstract class AbstractAttachEventContext {	
	
	/**
	 * 添加上下文
	 * @param eventColler
	 */
	public abstract void attachContext(AcctInfoCollector eventColler);
	
	

}
