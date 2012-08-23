package com.realdolmen.springjsf.services.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.realdolmen.springjsf.common.util.AspectLoggingUtil;

/**
 * 
 * @author JARAK33
 * 
 */
@Component
@Aspect
public class ServiceLogger {

	@Around("execution(* com.realdolmen.springjsf.services..*.*(..))")
	@AfterThrowing("execution(* com.realdolmen.springjsf.services..*.*(..))")
	public Object logExecution(ProceedingJoinPoint pjp) throws Throwable {
		return AspectLoggingUtil.logMethodExecution(pjp);
	}

}
