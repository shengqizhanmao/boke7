package com.lin.boke7admin.service;

import com.lin.boke7admin.common.Result;
import com.lin.boke7admin.controller.Param.AddUser;
import com.lin.boke7admin.controller.Param.UserUpdateEnableFlag;
import com.lin.boke7admin.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lin.boke7admin.pojo.vo.UserTokenVo;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author linSheng
 * @since 2022-09-07
 */
public interface UserService extends IService<User> {
    User getUserByUsername(String username);
    UserTokenVo findUserByToken(String token);
    Result addUser(User user);
    Result updateUser(User user);
    Result deleteUser(String id);
    Result updateEnableFlag(UserUpdateEnableFlag userUpdateEnableFlag);
}
