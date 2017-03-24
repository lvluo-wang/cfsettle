package com.upg.ucars.framework.rules.stage;

import java.util.ArrayList;
import java.util.List;

import bsh.EvalError;
import bsh.Interpreter;

import com.upg.ucars.framework.rules.context.ContextInfo;
import com.upg.ucars.framework.rules.context.ResultInfo;
import com.upg.ucars.framework.rules.graph.def.Rule;
import com.upg.ucars.framework.rules.graph.def.RuleNode;
import com.upg.ucars.framework.rules.runtime.RuleInstance;
import com.upg.ucars.framework.rules.runtime.agenda.AgandaProcessor;
import com.upg.ucars.framework.rules.runtime.execute.ExecuteProcessor;
import com.upg.ucars.framework.rules.runtime.match.PatternMatchProcessor;
import com.upg.ucars.util.StringUtil;

/**
 * 规则会话 实现类
 *
 * @author shentuwy
 */
public class RuleSessionImpl implements IRuleSession{

	private Interpreter itpr = new Interpreter();// 	
	private RuleNode ruleNode;
	private ContextInfo context;
	private String memberNo;
	
	
	public RuleSessionImpl(RuleNode ruleNode){
		this.ruleNode = ruleNode;
	}
	
	public RuleSessionImpl(RuleNode ruleNode, String memberNo){
		this.ruleNode = ruleNode;
		this.memberNo = memberNo;
	}
	
	public void insertFact(String key, Object value){
		try {
			itpr.set(key, value);
		} catch (EvalError e) {
			//TODO 暂不处理此异常
		}
	}

	public ResultInfo fireAllRules(){
		//构造会话的上下文
		context = new ContextInfo(ruleNode, itpr);
		
		if (ruleNode == null)
			return null;
		
		//创建规则实例
		List<RuleInstance> riList = this.createRuleInstances();
		
		//规则匹配
		riList = PatternMatchProcessor.passMatchRule(context, riList);
		
		//规则议程
		riList = AgandaProcessor.sortRule(context, riList);
		
		//规则执行
		ResultInfo rs = ExecuteProcessor.execute(context, riList);
		
		return rs;
	}
	
	private List<RuleInstance> createRuleInstances(){
		ArrayList<RuleInstance> riList = new ArrayList<RuleInstance>(ruleNode.getRuleList().size());
		for (Rule rule : this.getAppRules()) {
			
			RuleInstance ri = new RuleInstance(rule);
			riList.add(ri);
		}
		return riList;
	}
	
	private List<Rule> getAppRules(){
		if (StringUtil.isEmpty(memberNo))
			return this.ruleNode.getRuleList();
		else{
			ArrayList<Rule> ruleList = new ArrayList<Rule>();
			for (Rule rule : ruleNode.getRuleList()) {
				boolean b1=true; boolean b2=true;
				if (StringUtil.isEmpty(rule.getIncludeMember())){
					b1 = true;
				}else{
					b1 = (","+rule.getIncludeMember()+",").indexOf(","+memberNo+",") >= 0;
				}
				
				if (StringUtil.isEmpty(rule.getExcludeMember())){
					b2 = true;
				}else{
					b2 = (","+rule.getExcludeMember()+",").indexOf(","+memberNo+",") < 0;
				}
				if (b1 && b2)
					ruleList.add(rule);
			}
			
			return ruleList;
		}
	}
	
}
