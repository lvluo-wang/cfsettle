package com.upg.ucars.framework.log.core;

import org.apache.log4j.PatternLayout;
import org.apache.log4j.helpers.PatternParser;
/**
 * TODO 
 *
 * @author yangjun (mailto:yangjun@leadmind.com.cn)
 */
public class SaaSPatternLayout extends PatternLayout {

	 public SaaSPatternLayout(String pattern) {
	  super(pattern);
	 }

	 public SaaSPatternLayout() {
	  super();
	 }
	 
	 /**
	  * 重写createPatternParser方法，返回PatternParser的子类
	  */
	 @Override
	 protected PatternParser createPatternParser(String pattern) {
		 return new SaaSPatternParser(pattern);
	 }
	}
