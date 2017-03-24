package com.upg.ucars.framework.rules.runtime.agenda;

import java.util.Comparator;

import com.upg.ucars.framework.rules.runtime.RuleInstance;
/**
 * 规则排序
 * 
 * @author shentuwy
 */
public class RuleComparator implements Comparator<RuleInstance>{
	
	public int compare(RuleInstance o1, RuleInstance o2) {
		return o2.getRule().getLevel()-o1.getRule().getLevel();
	}
}
