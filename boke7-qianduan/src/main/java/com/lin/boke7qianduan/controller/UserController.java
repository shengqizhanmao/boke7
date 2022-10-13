package com.lin.boke7qianduan.controller;


import com.lin.boke7qianduan.ThreadLocal.UserThreadLocal;
import com.lin.boke7qianduan.common.Result;
import com.lin.boke7qianduan.controller.param.UserPasswordParam;
import com.lin.boke7qianduan.controller.param.UserUpdateParam;
import com.lin.boke7qianduan.pojo.User;
import com.lin.boke7qianduan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 操作用户
 *
 * @author linSheng
 * @
 * @since 2022-08-01
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    //后台:
    //查询全部用户
    @GetMapping("/getAll")
    public Result getAll() {
        return userService.getAllUser();
    }

    //查询全部用户,使用分页,page:当前页,pageSize:展示多少个
    @GetMapping("/getAll/{page}/{pageSize}")
    public Result getAll(@PathVariable("page") Long page,
                         @PathVariable("pageSize") Long pageSize) {
        return userService.getAllUser(page, pageSize);
    }

    //查询一个用户,根据id
    @GetMapping("/{id}")
    public Result edit(@PathVariable("id") Long id) {
        return userService.getUserById(id);
    }

    //添加用户,用户名不能重复
    @PostMapping("/add")
    public Result add(@Validated User user) {
        return userService.addUser(user);
    }

    //    //修改用户,根据username
//    @PostMapping("/updateByUsername")
//    public Result updateByUsername(@Validated User user) {
//        return userService.updateUserByUsername(user);
//    }
//    //修改用户2,根据id
//    @PostMapping("/updateById")
//    public Result updateById(@Validated User user) {
//        return userService.updateUserById(user);
//    }
    //删除用户,根据id
    @PostMapping("/delete")
    public Result delete(@RequestParam("id") Long id) {
        return userService.deleteUser(id);
    }

    //前端:
    //获取个人信息:
    @GetMapping("/getUser")
    public Result getUser() {
        Long id = UserThreadLocal.get().getId();
        return Result.succ("查询用户成功", userService.getById(id));
    }

    //修改个人信息:
    @PostMapping("/updateUser")
    public Result updateUser(@RequestBody UserUpdateParam userUpdateParam) {
        return userService.updateUserById(userUpdateParam);
    }

    //获取用户,根据String Authorization,头部信息(token)
    @GetMapping("/getUserByToken")
    public Result getUserByToken(
            @RequestHeader("Authorization") String token
    ) {
        // System.out.println("Authorization:"+token);
        return userService.findUserByToken(token);
    }

    //修改头像
    @PostMapping("/setUserAvatar")
    public Result setUserAvatar(MultipartFile file) {
        return userService.upAvatar(file);
    }

    //获取头像
    @GetMapping("/getUserAvatar")
    public Result getUserAvatar() {
        return userService.getAvatar();
    }

    //修改密码
    @PostMapping("/setUserPassword")
    public Result setUserPassword(@RequestBody UserPasswordParam userPasswordParam) {
        return userService.upPassword(userPasswordParam);
    }
}
