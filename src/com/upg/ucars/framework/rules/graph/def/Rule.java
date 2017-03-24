package com.upg.ucars.framework.rules.graph.def;
/**
 * 规则
 *
 * @author shentuwy
 */
public class Rule {
	private String name;//rule-node-->rule[name]
	private String desc;
	private int level;//rule-node-->rule[level]
	private String ruleClass;//rule-node-->rule[class]
	private String conditionClass;//rule-node-->rule-->condition[class]
	private String conditionExpr;//rule-node-->rule-->condition
	private String executeClass;//rule-node-->rule-->execute[class]
	private String executeExpr;//rule-node-->rule-->execute
	
	private String includeMember;//rule-node-->rule[include-member]
	private String excludeMember;//rule-node-->rule[exclude-member]
	
	public String getIncludeMember() {
		return includeMember;
	}
	public void setIncludeMember(String includeMember) {
		this.includeMember = includeMember;
	}
	public String getExcludeMember() {
		return excludeMember;
	}
	public void setExcludeMember(String excludeMember) {
		this.excludeMember = excludeMember;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getConditionClass() {
		return conditionClass;
	}
	public void setConditionClass(String conditionClass) {
		this.conditionClass = conditionClass;
	}
	public String getConditionExpr() {
		return conditionExpr;
	}
	public void setConditionExpr(String conditionExpr) {
		this.conditionExpr = conditionExpr;
	}
	public String getExecuteClass() {
		return executeClass;
	}
	public void setExecuteClass(String executeClass) {
		this.executeClass = executeClass;
	}
	public String getExecuteExpr() {
		return executeExpr;
	}
	public void setExecuteExpr(String executeExpr) {
		this.executeExpr = executeExpr;
	}
	public String getRuleClass() {
		return ruleClass;
	}
	public void setRuleClass(String ruleClass) {
		this.ruleClass = ruleClass;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
