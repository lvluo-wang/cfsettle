package com.upg.ucars.framework.rules.graph.def;

import java.util.ArrayList;
import java.util.List;
/**
 * 规则结点。
 * 代表一个业务点，包含若干个业务规则 
 *
 * @author shentuwy
 */
public class RuleNode {
	private String name;
	private String desc;
	private List<Rule> ruleList = new ArrayList<Rule>();
	private List<Parameter> paraList = new ArrayList<Parameter>();
	private String importExpr;//导入类，
	
	public void addRule(Rule rule){
		ruleList.add(rule);
	}
	

	public List<Rule> getRuleList() {
		return ruleList;
	}

	public List<Parameter> getParaList() {
		return paraList;
	}


	public void addPara(Parameter para) {
		paraList.add(para);
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getImportExpr() {
		return importExpr;
	}


	public void setImportExpr(String importExpr) {
		this.importExpr = importExpr;
	}


	public String getDesc() {
		return desc;
	}


	public void setDesc(String desc) {
		this.desc = desc;
	}

}