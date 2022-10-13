package com.lin.boke7qianduan.handler;

import com.alibaba.fastjson.JSON;
import com.lin.boke7qianduan.ThreadLocal.UserThreadLocal;
import com.lin.boke7qianduan.common.Result;
import com.lin.boke7qianduan.pojo.User;
import com.lin.boke7qianduan.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lin
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        String token = request.getHeader("Authorization");//获取token
        if (StringUtils.isBlank(token)) {
            Result result = Result.fail(401, "未登录", null);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(result));
            return false;
        }
        Result userByToken = userService.findUserByToken(token);//效验token
        User user = (User) userByToken.getData();
        if (user == null) {
            Result result = Result.fail(401, "未登录", null);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(result));
            return false;
        }
        UserThreadLocal.put(user); //放入user
        return true;  //登录效验成功
    }

    //代表所有的方法执行完了,再执行这个收尾
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //使用完了user进行删除,否则会内存泄漏
        UserThreadLocal.remove();
    }
}
