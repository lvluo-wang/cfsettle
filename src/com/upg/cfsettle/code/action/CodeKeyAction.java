package com.upg.cfsettle.code.action;

import com.upg.ucars.framework.base.BaseAction;
import com.upg.cfsettle.code.core.ICodeKeyService;
import com.upg.cfsettle.mapping.ficode.FiCodeKey;

@SuppressWarnings("serial")
public class CodeKeyAction extends BaseAction {
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= -9139979612774399910L;

	/** 多个key的字符串 */
	private String keys;

	private FiCodeKey fiCodeKey;

	/** 代码服务 */
	private ICodeKeyService codeKeyService;

	
	
}
