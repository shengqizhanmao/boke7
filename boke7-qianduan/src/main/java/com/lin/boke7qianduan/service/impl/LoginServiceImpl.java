package com.lin.boke7qianduan.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lin.boke7qianduan.common.Result;
import com.lin.boke7qianduan.pojo.User;
import com.lin.boke7qianduan.service.LoginService;
import com.lin.boke7qianduan.service.UserService;
import com.lin.boke7qianduan.utils.JWTUtils;
import com.lin.boke7qianduan.utils.SendEmailUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author lin
 */
@Slf4j
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Resource
    private SendEmailUtil emailUtil;
    private static final String slot = "asdjkl";

    public Result login(String username, String password) {
//        System.out.println("username:"+username+",password:"+password);
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return Result.fail(401, "用户名或者密码为空", null);
        }
        //加密
        //password= DigestUtils.md5Hex(password+slot);
        User user = userService.findUser(username, password);
        if (user == null) {
            return Result.fail(401, "登录失败,用户名或者密码错误");
        }
        String token = JWTUtils.createToken(user.getId());
        redisTemplate.opsForValue().set("TOKEN_" + token, JSON.toJSONString(user), 30, TimeUnit.DAYS);
        return Result.succ("登录成功", token);
    }

    public Result getEmailCode(String email) {
        if (StringUtils.isBlank(email)) {
            return Result.fail(401, "邮箱不能为空");
        }
        if (!email.matches("^([a-zA-Z0-9]+[_|\\_|\\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\\_|\\.]?)*[a-zA-Z0-9]+\\.[a-zA-Z]{2,3}$")) {
           return Result.fail(401, "邮箱格式错误");
        }
        String code = redisTemplate.opsForValue().get("Email_" + email);
        if (!StringUtils.isBlank(code)){
            log.info("code"+code);
            return Result.fail(401,"请等待60秒");
        }
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getEmail,email);
        User user = userService.getOne(userLambdaQueryWrapper);
        if (user == null) {
            return Result.fail(401, "邮箱不存在");
        }
        String checkCode = String.valueOf(new Random().nextInt(999999));
        String title = "博客登录验证码";
        String content = "您正在登录博客，本次验证码为:"+checkCode+",有效期1分钟,如非本人操作，请忽略！谢谢！60秒才能发下次验证码";
        emailUtil.sendMessage(user.getEmail(), title, content);
        redisTemplate.opsForValue().set("Email_" + email,checkCode,1,TimeUnit.MINUTES);
        return Result.succ("发送邮箱成功");
    }
    public Result LoginEmail(String email,String code) {
        if (StringUtils.isBlank(email)) {
            return Result.fail(401, "邮箱不能为空");
        }
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getEmail,email);
        User user = userService.getOne(userLambdaQueryWrapper);
        if (user == null) {
            return Result.fail(401, "邮箱不存在");
        }
        if (StringUtils.isBlank(code)) {
            return Result.fail(401, "验证码不能为空");
        }
        String EmailCode = redisTemplate.opsForValue().get("Email_" + email);
        if (StringUtils.isBlank(EmailCode)){
            return Result.fail(401,"验证码已过期，请重新发送验证码");
        }
        if (!EmailCode.equals(code)){
            return Result.fail(401,"验证码错误，请重新输入");
        }
        String token = JWTUtils.createToken(user.getId());
        redisTemplate.opsForValue().set("TOKEN_" + token, JSON.toJSONString(user), 30, TimeUnit.DAYS);
        return Result.succ("登录成功",token);
    }
    public Result logout(String token) {
        try {
            redisTemplate.delete("TOKEN_" + token);
        } catch (Exception e) {
            log.error("redis删除token失败");
            return Result.fail("退出登录失败");
        }
        return Result.succ("退出登录成功");
    }

    public Result register(String username, String password) {
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return Result.fail("用户名为空或者密码为空");
        }
        LambdaQueryWrapper<User> userQueryWrapper = new LambdaQueryWrapper<>();
        userQueryWrapper.eq(User::getUsername, username);
        userQueryWrapper.select(User::getId);
        //查询一条加快查询速度
        userQueryWrapper.last("limit 1");
        User one = userService.getOne(userQueryWrapper);
        if (one != null) {
            return Result.fail("该用户已经存在");
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setStatus(1);
        user.setCreated(new Date());
        boolean save = userService.save(user);
        if (save) {
            return Result.succ("注册成功", save);
        }
        return Result.fail("注册失败");
    }
}
