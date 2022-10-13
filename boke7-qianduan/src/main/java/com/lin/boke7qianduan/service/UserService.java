package com.lin.boke7qianduan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lin.boke7qianduan.common.Result;
import com.lin.boke7qianduan.controller.param.UserPasswordParam;
import com.lin.boke7qianduan.controller.param.UserUpdateParam;
import com.lin.boke7qianduan.pojo.User;
import com.lin.boke7qianduan.pojo.vo.UserVo;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author linSheng
 * @since 2022-08-01
 */
public interface UserService extends IService<User> {
    //查询全部用户
    Result getAllUser();

    //查询全部用户,使用分页,page:当前页,pageSize:展示多少个
    Result getAllUser(Long page, Long pageSize);

    //查询一个用户,根据id
    Result getUserById(Long id);

    //添加用户,用户名不能重复
    Result addUser(User user);

    //修改用户,根据username
//     Result updateUserByUsername( User user);
    //删除用户,根据id
    Result deleteUser(Long id);

    //查询用户,根据用户和密码
    User findUser(String username, String password);

    //获取用户,根据Token
    Result findUserByToken(String token);

    //修改用户,根据id
    Result updateUserById(UserUpdateParam userUpdateParam);

    //修改用户头像,下载用户上传的头像,
    Result upAvatar(MultipartFile file);

    //获取头像
    Result getAvatar();

    //修改密码
    Result upPassword(UserPasswordParam userPasswordParam);

    //vo
    //获取用户基本信息,根据id
    UserVo findGetUserVoById(Long id);


}
