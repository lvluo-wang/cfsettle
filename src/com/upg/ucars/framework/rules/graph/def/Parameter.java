package com.upg.ucars.framework.rules.graph.def;
/**
 * 参数
 * @author shentuwy
 */
public class Parameter {
	private String name;
	private String desc;
	private String paraExpr;
	private String paraClass;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getParaExpr() {
		return paraExpr;
	}
	public void setParaExpr(String paraExpr) {
		this.paraExpr = paraExpr;
	}
	public String getParaClass() {
		return paraClass;
	}
	public void setParaClass(String paraClass) {
		this.paraClass = paraClass;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	

}
