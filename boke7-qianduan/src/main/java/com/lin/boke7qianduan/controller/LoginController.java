package com.lin.boke7qianduan.controller;

import com.lin.boke7qianduan.common.Result;
import com.lin.boke7qianduan.common.aop.LogAnnotation;
import com.lin.boke7qianduan.controller.param.LoginForm;
import com.lin.boke7qianduan.controller.param.GetEmailCode;
import com.lin.boke7qianduan.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

/**
 * 操作登录退出注册
 *
 * @author lin
 */
@RestController
public class LoginController {

    @Autowired
    LoginService loginService;

    //登录
    @LogAnnotation(module = "登录", operator = "用户登录", Param = false)
    @PostMapping("/login")
    public Result Login(@RequestBody LoginForm loginForm) {
        String username = loginForm.getUsername();
        String password = loginForm.getPassword();
//        System.out.println("username:"+username+"-----password:"+password);
        return loginService.login(username, password);
    }

    //邮箱获取验证码
    @LogAnnotation(module = "登录", operator = "邮箱获取验证码", Param = false)
    @PostMapping("/getEmailCode")
    public Result getEmailCode(@RequestBody GetEmailCode getEmailCode) {
        String email = getEmailCode.getEmail();
        return loginService.getEmailCode(email);
    }
    //邮箱登录
    @LogAnnotation(module = "登录", operator = "邮箱登录", Param = false)
    @PostMapping("/LoginEmailCode")
    public Result LoginEmailCode(@RequestBody GetEmailCode getEmailCode) {
        String email = getEmailCode.getEmail();
        String code = getEmailCode.getCode();
        return loginService.LoginEmail(email,code);
    }
    //退出
    @GetMapping("/logout")
    public Result Logout(@RequestHeader("Authorization") String token) {
        return loginService.logout(token);
    }

    //注册
    @PostMapping("/register")
    public Result register(@RequestBody LoginForm loginForm) {
        String username = loginForm.getUsername();
        String password = loginForm.getPassword();
        return loginService.register(username, password);
    }
}
