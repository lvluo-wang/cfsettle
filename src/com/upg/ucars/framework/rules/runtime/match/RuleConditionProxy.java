package com.upg.ucars.framework.rules.runtime.match;

import com.upg.ucars.constant.ErrorCodeConst;
import com.upg.ucars.framework.exception.ExceptionManager;
import com.upg.ucars.framework.exception.SysException;
import com.upg.ucars.framework.rules.context.ContextInfo;
import com.upg.ucars.framework.rules.runtime.IRuleCondition;
/**
 * 规则条件的代理 
 *
 * @author shentuwy
 */
public class RuleConditionProxy implements IRuleCondition{
	private final static int Type_Class = 1;
	private final static int Type_Express = 2;
	
	private IRuleCondition target;
	private String express;
	private int proxyType;//代理类型
	
	
	
	public RuleConditionProxy(IRuleCondition ruleCondition ){
		target=ruleCondition;
		this.proxyType = Type_Class;
	}
	
	public RuleConditionProxy(String express){
		this.express = express;
		this.proxyType = Type_Express;
	}

	public boolean isMatch(ContextInfo context) throws SysException {
		if (this.proxyType == Type_Class){
			return this.target.isMatch(context);
		}else if (this.proxyType == Type_Express){
			try {
				Boolean obj = (Boolean)context.getInterpreter().eval(this.express);
				return obj.booleanValue();
			} catch (Exception e) {
				throw ExceptionManager.getException(SysException.class, ErrorCodeConst.SYS_ERROR, this.express+" execute failure or return non-Boolean.", e);
				
			}
		}
		
		return false;
		
	}
	
	

}
