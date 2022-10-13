package com.lin.boke7admin.controller;

import com.lin.boke7admin.common.Result;
import com.lin.boke7admin.controller.Param.AddUser;
import com.lin.boke7admin.controller.Param.UserUpdateEnableFlag;
import com.lin.boke7admin.pojo.User;
import com.lin.boke7admin.pojo.vo.UserTokenVo;
import com.lin.boke7admin.pojo.vo.UserVo;
import com.lin.boke7admin.service.UserService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author linSheng
 * @since 2022-09-07
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @RequiresAuthentication
    @GetMapping("/getUserByToken")
    public Result getUserByToken(
            @RequestHeader("Authorization") String token
    ) {
        UserTokenVo userByToken = userService.findUserByToken(token);
        return Result.succ("获取user信息成功",userByToken);
    }

    @RequiresPermissions("user:get")
    @GetMapping("/get")
    public Result get(){
        List<User> list = userService.list();
        List<UserVo> list1=new ArrayList<>();
        for (User user:list) {
            UserVo userVo= new UserVo();
            BeanUtils.copyProperties(user, userVo);
            list1.add(userVo);
        }
        return Result.succ("查询用户成功",list1);
    }

    @RequiresPermissions("user:add")
    @PostMapping("/add")
    public Result add(@RequestBody User user){
        return userService.addUser(user);
    }
    @RequiresPermissions("user:update")
    @PostMapping("/update")
    public Result update(@RequestBody User user){
        return userService.updateUser(user);
    }

    @RequiresPermissions("user:update")
    @PostMapping("/updateEnableFlag")
    public Result UpdateEnableFlag(@RequestBody UserUpdateEnableFlag userUpdateEnableFlag){
        return userService.updateEnableFlag(userUpdateEnableFlag);
    }
    @RequiresPermissions("user:delete")
    @PostMapping("/delete")
    public Result delete(@RequestBody User user){
        return userService.deleteUser(user.getId());
    }



}
