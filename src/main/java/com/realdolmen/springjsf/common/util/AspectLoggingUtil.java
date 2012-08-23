package com.realdolmen.springjsf.common.util;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

public final class AspectLoggingUtil {

	private AspectLoggingUtil() {
	}
	
	public static Object logMethodExecution(ProceedingJoinPoint pjp) throws Throwable {
		final Logger logger = LoggerFactory.getLogger(pjp.getTarget()
				.getClass());
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		Object returnedObject;
		try {
			returnedObject = pjp.proceed();
			stopWatch.stop();
			logger.info("Method execution took: "
					+ stopWatch.getTotalTimeMillis() + " ms - " //
					+ "method: " + pjp.getSignature().getName() + " - "
					+ "args: " + Arrays.toString(pjp.getArgs()));
			return returnedObject;
		} catch (Throwable ex) {
			logger.error("Exception thrown in method: "
					+ pjp.getSignature().getName() + " - " //
					+ "args: " + Arrays.toString((pjp.getArgs())) + " - " //
					+ "exception: " + ex.toString());
			throw ex;
		}
	}
	
}
