package com.upg.ucars.framework.rules.runtime.execute;

import bsh.EvalError;

import com.upg.ucars.constant.ErrorCodeConst;
import com.upg.ucars.framework.exception.ExceptionManager;
import com.upg.ucars.framework.exception.SysException;
import com.upg.ucars.framework.exception.core.BaseAppRuntimeException;
import com.upg.ucars.framework.rules.context.ContextInfo;
import com.upg.ucars.framework.rules.runtime.IRuleAction;
/**
 * 规则动作的代理
 * 
 * @author shentuwy
 */
public class RuleActionProxy implements IRuleAction {
	private final static int Type_Class = 1;//类代理
	private final static int Type_Express = 2;//表达式代理
	
	private IRuleAction target;//
	private String express;
	private int proxyType;//代理类型
	
	public RuleActionProxy(IRuleAction ruleAction){
		this.target = ruleAction;
		this.proxyType = Type_Class;
	}
	
	public RuleActionProxy(String express){
		this.express = express;
		this.proxyType = Type_Express;
	}

	public Object execute(ContextInfo context) throws SysException {
		if (this.proxyType == Type_Class){
			return this.target.execute(context);
		}
		
		if (this.proxyType == Type_Express){
			try {
				return context.getInterpreter().eval(this.express);
			} catch (EvalError e) {
				if (e.getCause() instanceof BaseAppRuntimeException)
					throw (BaseAppRuntimeException)e.getCause();
				else
					throw ExceptionManager.getException(SysException.class, ErrorCodeConst.SYS_ERROR, this.express+" execute failure", e);
			}
		}
		return null;
	}

}
