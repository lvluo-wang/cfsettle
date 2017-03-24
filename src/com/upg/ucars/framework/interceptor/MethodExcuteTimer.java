package com.upg.ucars.framework.interceptor;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;

/**
 * 记录方法的执行时间
 * 
 * @author shentuwy
 * 
 */
public class MethodExcuteTimer {

	protected static final Logger logger = Logger
			.getLogger(MethodExcuteTimer.class);

	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		long procTime = System.currentTimeMillis();
		Signature s = joinPoint.getSignature();
		try {
			Object result = joinPoint.proceed();
			return result;
		} finally {
			procTime = System.currentTimeMillis() - procTime;
			if (logger.isDebugEnabled()) {
				logger.debug("=========={METHOD:" + s.getName() + ",TIME:"
						+ (procTime + 0.0) / 1000 + ",CLASS:"
						+ s.getDeclaringTypeName() + "}==========");
			}
		}
	}

}
