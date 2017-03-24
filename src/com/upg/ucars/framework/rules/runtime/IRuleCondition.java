package com.upg.ucars.framework.rules.runtime;

import com.upg.ucars.framework.exception.SysException;
import com.upg.ucars.framework.rules.context.ContextInfo;
/**
 * 规则条件匹配接口
 * 
 * @author shentuwy
 */
public interface IRuleCondition {
	/**
	 * 条件是否匹配 
	 *
	 * @param context
	 * @return
	 */
	public boolean isMatch(ContextInfo context) throws SysException;

}
