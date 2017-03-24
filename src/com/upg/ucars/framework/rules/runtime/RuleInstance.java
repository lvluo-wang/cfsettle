package com.upg.ucars.framework.rules.runtime;

import com.upg.ucars.constant.ErrorCodeConst;
import com.upg.ucars.framework.exception.ExceptionManager;
import com.upg.ucars.framework.exception.SysException;
import com.upg.ucars.framework.rules.graph.def.Rule;
import com.upg.ucars.framework.rules.runtime.execute.RuleActionProxy;
import com.upg.ucars.framework.rules.runtime.match.RuleConditionProxy;

/**
 * 规则实例 
 *
 * @author shentuwy
 */
public class RuleInstance {
	private Rule rule;
	private IRuleCondition ruleConditon;
	private IRuleAction ruleAction;
	
	public RuleInstance(Rule rule){
		this.rule = rule;
		
		this.init();
		
	}

	public Rule getRule() {
		return rule;
	}
	
	private void init(){
		if (this.rule.getRuleClass()!=null){
			try {
				Object obj = Class.forName(rule.getRuleClass()).newInstance();
				IRuleCondition rc = (IRuleCondition)obj;
				this.ruleConditon = new RuleConditionProxy(rc);//统一由代理处理
				
				IRuleAction ra = (IRuleAction)obj;
				this.ruleAction = new RuleActionProxy(ra);//统一由代理处理
				return;
			} catch (Exception e) {
				throw ExceptionManager.getException(SysException.class, ErrorCodeConst.SYS_ERROR, rule.getRuleClass()+" isn't implements IRuleCondition and IRuleAction.");
			} 
		}else if (this.rule.getConditionClass()!=null){
			try {
				ruleConditon = (IRuleCondition)Class.forName(rule.getConditionClass()).newInstance();
				
				ruleConditon = new RuleConditionProxy(ruleConditon);
				
				return;
			} catch (Exception e) {
				throw ExceptionManager.getException(SysException.class, ErrorCodeConst.SYS_ERROR, rule.getConditionClass()+" isn't implements IRuleCondition.");
			} 
		}else{
			ruleConditon = new RuleConditionProxy(this.getRule().getConditionExpr());
		}
			
	}
	
	public IRuleCondition getRuleCondition(){
		
		return ruleConditon;		
	}
	
	public IRuleAction getRuleAction(){
		if (this.ruleAction != null)
			return ruleAction;
		if (this.getRule().getExecuteClass()!=null){
			try {
				ruleAction = (IRuleAction)Class.forName(rule.getExecuteClass()).newInstance();
				
				return new RuleActionProxy(ruleAction);
				
			} catch (Exception e) {
				throw ExceptionManager.getException(SysException.class, ErrorCodeConst.SYS_ERROR, rule.getExecuteClass()+" isn't implements IRuleAction.");
			} 
		}
		
		return new RuleActionProxy(this.rule.getExecuteExpr());
	}
}
