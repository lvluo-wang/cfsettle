package com.upg.ucars.basesystem.autotask.core;

import org.quartz.CronExpression;

/**
 * 日终任务验证相关
 * @deprecated
 * @author 常小龙  add by changxl 20101018
 *
 */
public class AutoTaskCheckDWR {

	//检查新增/修改日终任务时判断输入项
	public String autoTaskCheck(String className,String cronExpression){
		String msg = "";
		try {
			if(null != className && !"".equals(className)){
				Class clazz = Class.forName(className);
				if(!(clazz.newInstance() instanceof AbstractAutoTask)){
					msg = "该执行类未继承自动任务基类!";
				}
			}else{
				msg = "任务执行类为空,请确认!";
			}
		} catch (Exception e) {
			msg = "该执行类不存在或缺少无参构造器!";
		}
		if(null != cronExpression && !"".equals(cronExpression)){
			if(!CronExpression.isValidExpression(cronExpression)){
				msg = "Cron表达式格式不正确!";
			}
		}else{
			msg = "任务表达式为空,请确认!";
		}
		return msg;
	}
	
}
