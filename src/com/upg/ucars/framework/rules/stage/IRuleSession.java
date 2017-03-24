package com.upg.ucars.framework.rules.stage;

import com.upg.ucars.framework.exception.ServiceException;
import com.upg.ucars.framework.rules.context.ResultInfo;

/**
 * 规则会话 
 *
 * @author shentuwy
 */
public interface IRuleSession {
	/**
	 * 设置会话信息
	 * @param key
	 * @param value
	 */
	void insertFact(String key, Object value);
	/**
	 * 分析执行
	 *
	 * @throws ServiceException
	 */
	ResultInfo fireAllRules() throws ServiceException;
	

}
