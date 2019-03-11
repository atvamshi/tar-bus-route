package com.target.cs.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Project: tar-bus-time-arrival-calc
 * Package: com.target.cs.aop
 * <p>
 * User: vamshi
 * Date: 2019-03-10
 * Time: 16:38
 * <p>
 * Created with IntelliJ IDEA
 * To change this template use File | Settings | File Templates.
 */
@Component
@Aspect
public class LoggingAspect {

    /**
     * beforeAllMethodsLoggingAdvice
     *
     * @param joinPoint
     */
    @Before("includeAllPointCuts()")
    // Dynamic logger for the passed Joint point rather than using this classes
    public synchronized void beforeAllMethodsLoggingAdvice(JoinPoint joinPoint) {

        Logger LOGGER = LoggerFactory.getLogger(joinPoint.getThis().getClass());
        CodeSignature codeSignature = (CodeSignature) joinPoint.getSignature();

        String exceptionTypes = ((codeSignature.getExceptionTypes() == null || codeSignature.getExceptionTypes().length == 0) ?
                "No exception types" : Arrays.toString(codeSignature.getExceptionTypes()));

        String parameterNames = ((codeSignature.getParameterNames() == null || codeSignature.getParameterNames().length == 0) ?
                "No parameters" : Arrays.toString(codeSignature.getParameterNames()));

        String tobePrintedString = "\n*****START ==> " + joinPoint.getSignature() + " , "
                + exceptionTypes + " , "
                + parameterNames;

        tobePrintedString = tobePrintedString +" , " +
                (joinPoint.getArgs() == null || joinPoint.getArgs().length == 0 ?
                        "No Args" : Arrays.toString(joinPoint.getArgs()));


        LOGGER.info(tobePrintedString);

    }

    @After("includeAllPointCuts()")
    // Dynamic logger for the passed Joint point rather than using this classes
    public synchronized void afterAllMethodsLoggingAdvice(JoinPoint joinPoint) {

        Logger LOGGER = LoggerFactory.getLogger(joinPoint.getThis().getClass());
        LOGGER.info("\n****END ");

    }


    @Pointcut("within(com.target.cs..*)")
    public synchronized void apiControllerPointCut() {
    }

    /**
     * includeAllPointCuts
     *
     * @throws java.lang.UnsupportedOperationException
     */
    @Pointcut("apiControllerPointCut()")
    public synchronized void includeAllPointCuts() {
    }

}
