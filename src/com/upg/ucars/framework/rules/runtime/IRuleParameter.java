package com.upg.ucars.framework.rules.runtime;

import com.upg.ucars.framework.rules.context.ContextInfo;

/**
 * 规则参数接口
 * @author shentuwy
 */
public interface IRuleParameter {
	/**
	 * 参数接口的返回值
	 * @return
	 */
	public Object get(ContextInfo context);

}
