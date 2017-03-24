package com.upg.ucars.framework.rules.context;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
/**
 * 结果信息
 * @author shentuwy
 */
public class ResultInfo {
	private HashMap<String, Object> map;
	
	public ResultInfo(){
		this(10);//结果信息的数量默认为10
	}
	/**
	 * 构造器
	 * @param size 结果信息的数量
	 */
	public ResultInfo(int size){
		map = new HashMap<String, Object>(size);
	}
	/**
	 * 获取结果信息
	 * 
	 * @return
	 */
	public Collection<Object> getResults(){
		return map.values();
	}
	
	
	public void putRuleResult(String ruleName, Object rs){
		this.map.put(ruleName, rs);
	}
	
	public Set<String> getRuleNames(){
		return this.map.keySet();
	}
	/**
	 * 获取单个规则的分析结果 
	 *
	 * @param ruleName 规则名称
	 * @return
	 */
	public Object getRuleResult(String ruleName){
		return this.map.get(ruleName);
	}
	

}
