package com.lin.boke7qianduan.common.aop;

import com.alibaba.fastjson.JSON;
import com.lin.boke7qianduan.utils.HttpContextUtils;
import com.lin.boke7qianduan.utils.IpUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @author lin
 */
@Slf4j
@Component
@Aspect         //切面
public class LogAspect {

    @Pointcut("@annotation(com.lin.boke7qianduan.common.aop.LogAnnotation)")    //定义切点
    public void pt() {
    }

    //环绕通知
    @Around("pt()")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        //记录开启时间
        long beginTime = System.currentTimeMillis();
        //执行原来的方法
        Object result = joinPoint.proceed();
        //记录结束时间
        long time = System.currentTimeMillis() - beginTime;
        //进行记录,执行通知方法
        recordLog(joinPoint, time);
        //返回原来的方法
        return result;
    }

    private void recordLog(ProceedingJoinPoint joinPoint, long time) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        LogAnnotation logAnnotation = method.getAnnotation(LogAnnotation.class);
        log.info("=================log start==============");
        log.info("module:{}", logAnnotation.module());
        log.info("operator:{}", logAnnotation.operator());
        //请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        log.info("request method:{}", className + "." + methodName + "()");

        boolean b = logAnnotation.Param();
        if (b) {
            //请求的参数
            Object[] args = joinPoint.getArgs();
            String params = JSON.toJSONString(args);
            log.info("params:{}", params);
        }
        //请获取request,设置ip地址
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        log.info("ip:{}", IpUtils.getIpAddr(request));

        log.info("excute time : {} ms", time);
        log.info("=================log end==============");
    }
}
