package com.upg.ucars.framework.rules.stage;

import com.upg.ucars.framework.rules.graph.mng.RuleDefManager;
/**
 * 规则会话工厂
 * @author shentuwy
 */
public class RuleSessionFactory {
	/**
	 * 创建一个规则分析会话
	 * @param nodeName 规则结点
	 * @deprecated
	 * @return
	 */
	public static IRuleSession createRuleSession(String nodeName){
		
		RuleSessionImpl session = new RuleSessionImpl(RuleDefManager.getRules(nodeName));
		return session;
		
	}
	
	/**
	 * 为接入点创建一个规则分析会话
	 * @param nodeName 规则结点
	 * @param memberNo 接入点编号
	 * @return
	 */
	public static IRuleSession createRuleSession(String nodeName, String memberNo){
		
		RuleSessionImpl session = new RuleSessionImpl(RuleDefManager.getRules(nodeName), memberNo);	
		return session;
		
	}

}
