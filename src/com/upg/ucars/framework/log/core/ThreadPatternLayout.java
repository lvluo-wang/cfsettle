package com.upg.ucars.framework.log.core;

import org.apache.log4j.PatternLayout;
import org.apache.log4j.helpers.PatternParser;

public class ThreadPatternLayout extends PatternLayout {

	 public ThreadPatternLayout(String pattern) {
	  super(pattern);
	 }

	 public ThreadPatternLayout() {
	  super();
	 }
	 
	 /**
	  * 重写createPatternParser方法，返回PatternParser的子类
	  */
	 @Override
	 protected PatternParser createPatternParser(String pattern) {
		 return new ThreadPatternParser(pattern);
	 }
	}
