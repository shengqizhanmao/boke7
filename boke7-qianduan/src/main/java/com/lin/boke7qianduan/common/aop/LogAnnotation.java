package com.lin.boke7qianduan.common.aop;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author lin
 * 开发注解
 * 作用:是生成log日志,加在类或者方法上,类或者方法,日志记录module和operator请求的方法名和请求的参数
 * 参数:moudlue为模块名,operator为方法名或者作用名,Param是否记录方法参数
 * 记录:module,operator,请求的方法名,[请求的参数],ip,耗时多久
 */
@Target({ElementType.TYPE, ElementType.METHOD})  //注解放置的目标位置,TYPE代表放类上,Method代表放在方法上,
@Retention(RetentionPolicy.RUNTIME) //注解在哪个阶段执行
@Documented //生成文档
public @interface LogAnnotation {
    String module() default "";

    String operator() default "";

    boolean Param() default true;
}
