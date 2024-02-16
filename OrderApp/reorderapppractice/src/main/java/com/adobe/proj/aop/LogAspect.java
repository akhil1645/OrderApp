package com.adobe.proj.aop;

import java.util.Date;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
//import org.apache.logging.log4j.Logger;
import org.aspectj.lang.annotation.Aspect; // we are using spring layer on top of aspectj. Spring aspect build on aspectj only
import org.aspectj.lang.annotation.Before;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactoryFriend;
import org.springframework.stereotype.Component;

import com.adobe.proj.service.NotFoundException;

@Component
@Aspect 
public class LogAspect {
	Logger logger= LoggerFactory.getLogger(LogAspect.class);
	
	@Before("execution(* com.adobe.proj.service.*.*(..))")  //  within service class all the function are eligible for logging if(* * *(..)) then would have failed because there are many final classes present in the spring libraries also. so you can't create proxies for building spring apis and all
	// so avoid using * * for spring as it includes spring libraries also where proxies are not supported
	// 
	public void logBefore(JoinPoint jp) { 
		// so where it is geeting viewed are all our join points. like all the methods od service are joinpoints for above
		// using joinpoint i can know what is the method, argument, return type etc. to get complete info about the complete method use join points.
		logger.info("Called : "+ jp.getSignature());
		Object[] args=jp.getArgs(); // getting all the arguments of function
		for(Object arg: args) {
			logger.info("arguments : "+ arg);
		}
	}
	
	@After("execution(* com.adobe.proj.service.*.*(..))") 
	public void logAfter(JoinPoint jp) { 
		logger.info(" ********* ");
	}

	// only difference b/w around and before/after is i should use ProceedingJoinPoint instead of JoinPoint
	@Around("execution(* com.adobe.proj.service.*.*(..))") 
	public Object doProfile(ProceedingJoinPoint pjp) throws Throwable {
		long startTime=new Date().getTime();
		Object ret=pjp.proceed(); // this will just delegate the call to the methods 
		// even void can be returned as an object. so just throw the exception. Don't handle by try catch since controller should recieve the exception to handle actual flow
		// this is just a proxy sitting on top. only differen is we are taking profile her. how much each method is taking
		long endTime= new Date().getTime();
		logger.info("Time take is : "+ (endTime-startTime) + "ms");
		return ret;
	}
	
	
	@AfterThrowing(value="execution(* com.adobe.proj.service.*.*(..))" , throwing="ex")
	public void exceptionHanldler(JoinPoint jp,NotFoundException ex) {
		logger.info(ex.getMessage()+ " from "+ jp.getSignature().getName());
		
	}
	
}
