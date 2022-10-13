package com.lin.boke7qianduan.handler;

/**
 * @author lin
 */

import com.lin.boke7qianduan.common.Result;
import com.sun.mail.smtp.SMTPSendFailedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;
import java.net.UnknownHostException;

//对Controller注解的方法进行拦截,AOP的实现
@Slf4j
@ControllerAdvice
public class AllExceptionHandler {
//邮箱发送异常
    //1.连接smtp.163.com异常，报错：java.net.UnknownHostException: smtp.163.com
    @ExceptionHandler(UnknownHostException.class)
    @ResponseBody //返回json数据
    public Result doException(UnknownHostException ex) {
        log.error(String.valueOf(ex));
        return Result.fail(163500, "连接smtp.163.com异常", null);
    }
    //2.认证错误或者用户权限不足，报错：javax.mail.AuthenticationFailedException: 550 User has no permission
    @ExceptionHandler(AuthenticationFailedException.class)
    @ResponseBody //返回json数据
    public Result doExceptionAuthenticationFailedException(AuthenticationFailedException ex) {
        log.error(String.valueOf(ex));
        return Result.fail(163550, "用户权限不足或者认证错误", null);
    }
    //3.发送内容错误，报错：com.sun.mail.smtp.SMTPSendFailedException: 554 DT:SPM
    @ExceptionHandler(SMTPSendFailedException.class)
    @ResponseBody //返回json数据
    public Result doExceptionSMTPSendFailedException(SMTPSendFailedException ex) {
        log.error(String.valueOf(ex));
        return Result.fail(163554, "发送内容错误", null);
    }
    //4.发送附件错误，报错：javax.mail.MessagingException: Empty multipart: multipart/related;
    @ExceptionHandler(MessagingException.class)
    @ResponseBody //返回json数据
    public Result doExceptionMessagingException(MessagingException ex) {
        log.error(String.valueOf(ex));
        return Result.fail(163554, "发送附件错误", null);
    }
//全局Exception捕获
    //进行异常处理,对Exception.class的异常处理
    @ExceptionHandler(Exception.class)
    @ResponseBody //返回json数据
    public Result doException(Exception ex) {
        log.error(String.valueOf(ex));
        return Result.fail(500, "系统Exception异常", null);
    }
}
