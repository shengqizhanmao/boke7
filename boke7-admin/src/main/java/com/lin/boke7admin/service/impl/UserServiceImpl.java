package com.lin.boke7admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lin.boke7admin.common.Result;
import com.lin.boke7admin.controller.Param.AddUser;
import com.lin.boke7admin.controller.Param.UserUpdateEnableFlag;
import com.lin.boke7admin.mapper.UserMapper;
import com.lin.boke7admin.pojo.User;
import com.lin.boke7admin.pojo.vo.UserTokenVo;
import com.lin.boke7admin.service.UserService;
import com.lin.boke7admin.shiro.Jwt.JwtToken;
import com.lin.boke7admin.utils.JWTUtils;
import com.lin.boke7admin.utils.Md5Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author linSheng
 * @since 2022-09-07
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    RedisTemplate<String,String> redisTemplate;
    @Autowired
    UserMapper userMapper;
    //获取用户根据Username
    public User getUserByUsername(String username){
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getLoginName,username);
        User user = userMapper.selectOne(userLambdaQueryWrapper);
        return user;
    }
    //获取用户,根据Token
    public UserTokenVo findUserByToken(String token) {
        /*
         * 1.token合法性校验,是否为空,解析是否成功,redis是否存在
         * 2.如果校验失败,返回错误
         * 3.如果成功,返回对应的结果Login
         * */
        if (StringUtils.isBlank(token)) {
            return null;
        }
        //解析token
        try{
            Map<String, Object> stringObjectMap = JWTUtils.checkToken(token);
            if (stringObjectMap == null) {
                return null;
            }
        }catch (Exception e){
            return null;
        }
        //获取redis是否存在
        String userJson = redisTemplate.opsForValue().get("TOKEN_" + token);
        if (StringUtils.isBlank(userJson)) {
            return null;
        }
        UserTokenVo user = JSON.parseObject(userJson, UserTokenVo.class);
        return user;
    }
    //添加用户
    public Result addUser(User user){
        String username = user.getLoginName();
        if(StringUtils.isEmpty(username)){
            return Result.fail("不能缺少登录名");
        }
        String password = user.getPassWord();
        if(StringUtils.isEmpty(password)){
            password="123456";
        }
        String salt = Md5Utils.CretaeMd5();
        String password2 = Md5Utils.md5Encryption(password, salt);
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getLoginName,username);

        User user2 = userMapper.selectOne(userLambdaQueryWrapper);
        if (user2!=null){
            return Result.fail(401,"用户已经存在");
        }
        user.setEnableFlag("1");
        user.setSalt(salt);
        user.setPassWord(password2);
        int insert = userMapper.insert(user);
        if (insert==0){
            return Result.fail(401,"添加用户失败");
        }
        return Result.succ("添加成功");
    }
    //修改用户
    public Result updateUser(User user){
        User user1 = userMapper.selectById(user.getId());
        if(!StringUtils.isEmpty(user.getAddress())){
            user1.setAddress(user.getAddress());//地址
        }
        if(!StringUtils.isEmpty(user.getEmail())){
            user1.setEmail(user.getEmail());//邮箱
        }
        if(!StringUtils.isEmpty(user.getNickName())){
            user1.setNickName(user.getNickName());//昵称
        }
        if(!StringUtils.isEmpty(user.getMobil())){
            user1.setMobil(user.getMobil());//电话
        }
        if(!StringUtils.isEmpty(user.getSex())){
            user1.setSex(user.getSex());//性别
        }
        try {
            int update = userMapper.updateById(user1);
            return Result.succ("修改成功",update);
        }catch (Exception e){
            log.info("修改失败,原因是:"+e);
            return Result.fail("修改失败,原因是:"+e);
        }
    }
    public Result updateEnableFlag(UserUpdateEnableFlag userUpdateEnableFlag) {
        String enableFlag = userUpdateEnableFlag.getEnableFlag();
        String id = userUpdateEnableFlag.getId();
        if(StringUtils.isEmpty(id)){
            return Result.fail(403,"id参数不能为空");
        }
        if (StringUtils.isEmpty(enableFlag)){
            return Result.fail(403,"enableFlag参数不能为空");
        }
        LambdaUpdateWrapper<User> lambdaUpdateWrapper =new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(User::getId,id);
        lambdaUpdateWrapper.set(User::getEnableFlag,enableFlag);
        int update = userMapper.update(null, lambdaUpdateWrapper);
        if (update==0) {
            if(enableFlag.equals("1")){
                return Result.fail("启动用户失败");
            }
            return Result.fail( "禁用用户失败");
        }
        if(enableFlag.equals("1")){
            return Result.succ("启动用户成功");
        }
        return Result.succ("禁用用户成功");
    }
    //删除用户
    public Result deleteUser(String id){
        try {
            int i = userMapper.deleteById(id);
            if (i==0){
                return Result.fail("删除失败,用户不存在");
            }
            return Result.succ("删除成功");
        }catch (Exception e){
            return Result.fail("删除失败,原因是:"+e);
        }
    }

}
