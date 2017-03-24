package com.upg.ucars.framework.exception.core;

import java.lang.reflect.Method;

import org.springframework.aop.ThrowsAdvice;

import com.upg.ucars.framework.exception.ExceptionManager;
/**
 * spring容器异常拦截器，
 * spring容器托管的bean，在方法调用时产生异常后，进入此拦截器
 * 拦截器不影响原异常的抛出行为，只在异常发生后，调用异常管理器，记录异常；
 * 
 * @author shentuwy
 *
 */
public class ExceptionLogAOP implements ThrowsAdvice {
	public void afterThrowing(Method method,Object[] args,Object target,Throwable t) throws Throwable{
		ExceptionManager.logException(t);
	}
}
