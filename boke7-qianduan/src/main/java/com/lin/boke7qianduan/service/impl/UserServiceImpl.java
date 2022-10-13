package com.lin.boke7qianduan.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lin.boke7qianduan.ThreadLocal.UserThreadLocal;
import com.lin.boke7qianduan.common.Result;
import com.lin.boke7qianduan.controller.param.UserPasswordParam;
import com.lin.boke7qianduan.controller.param.UserUpdateParam;
import com.lin.boke7qianduan.mapper.UserMapper;
import com.lin.boke7qianduan.pojo.User;
import com.lin.boke7qianduan.pojo.vo.UserVo;
import com.lin.boke7qianduan.service.UserService;
import com.lin.boke7qianduan.utils.JWTUtils;
import com.lin.boke7qianduan.utils.MinioUtils;
import com.lin.boke7qianduan.utils.QiniuUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author linSheng
 * @since 2022-08-01
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    RedisTemplate<String, String> redisTemplate;

    //查询全部用户
    public Result getAllUser() {
        LambdaQueryWrapper<User> userWrapper = new LambdaQueryWrapper<>();
        userWrapper.orderByDesc(User::getCreated);      //根据时间排序
        List<User> list = userMapper.selectList(userWrapper);
        if (list == null) {
            return Result.fail("查询用户失败");
        } else if (list.size() == 0) {
            return Result.succ("无用户");
        }
        return Result.succ("查询全部用户成功", list);
    }

    //查询全部用户,使用分页,page:当前页,pageSize:展示多少个
    public Result getAllUser(Long page, Long pageSize) {
        Page<User> userPage = new Page<>(page, pageSize);
        LambdaQueryWrapper<User> userWrapper = new LambdaQueryWrapper<>();
        userWrapper.orderByDesc(User::getCreated);      //根据时间排序
        Page<User> userPage2 = userMapper.selectPage(userPage, userWrapper);
        List<User> records = userPage2.getRecords();
        if (records == null) {
            return Result.fail("查询用户失败");
        } else if (records.size() == 0) {
            return Result.succ("无用户");
        }
        return Result.succ("查询全部用户成功", records);
    }

    //查询一个用户,根据id
    public Result getUserById(Long id) {
        User byId = userMapper.selectById(id);
        if (byId == null) {
            return Result.fail("用户不存在");
        }
        return Result.succ("根据id查询用户成功", byId);
    }

    //添加用户,用户名不能重复
    public Result addUser(User user) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", user.getUsername());
        User byId = userMapper.selectOne(wrapper);
        if (byId != null) {
            return Result.fail("用户已存在");
        }
        int insert = userMapper.insert(user);
        if (insert == 0) {
            return Result.fail("添加失败");
        }
        return Result.succ("添加成功");
    }

    //删除用户,根据id
    public Result deleteUser(Long id) {
        //查询用户存不存在
        User byId = userMapper.selectById(id);
        if (byId == null) {
            return Result.fail("用户不存在");
        }
        //进行删除
        int b = userMapper.deleteById(id);

        if (b == 0) {
            String deleteSuccse = "删除失败";
            return Result.fail(deleteSuccse, b);
        } else {
            String deleteSuccse = "删除成功";
            return Result.succ(deleteSuccse, b);
        }
    }

    //Login需要使用的,查询username和password
    public User findUser(String username, String password) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, username);
        queryWrapper.eq(User::getPassword, password);
        //查询需要的
        queryWrapper.select(User::getId, User::getUsername, User::getAvatar, User::getNickname);
        //查询一条加快查询速度
        queryWrapper.last("limit 1");
        return userMapper.selectOne(queryWrapper);
    }

    //前端:
    //获取用户,根据Token
    public Result findUserByToken(String token) {
        /*
         * 1.token合法性校验,是否为空,解析是否成功,redis是否存在
         * 2.如果校验失败,返回错误
         * 3.如果成功,返回对应的结果Login
         * */
//       System.out.println("token:"+token);
        if (StringUtils.isBlank(token)) {
            return Result.fail("token为空");
        }
        //解析token
        Map<String, Object> stringObjectMap = JWTUtils.checkToken(token);
        if (stringObjectMap == null) {
            return Result.fail("token不合法");
        }
        //获取redis是否存在
        String userJson = redisTemplate.opsForValue().get("TOKEN_" + token);
        if (StringUtils.isBlank(userJson)) {
            return Result.fail("redis不存在");
        }
        User user = JSON.parseObject(userJson, User.class);
        return Result.succ(user);
    }

    //修改用户,根据id
    public Result updateUserById(UserUpdateParam userUpdateParam) {
        //对用户进行替换
        User user = UserThreadLocal.get();
        String nickname = userUpdateParam.getNickname();
        String avatar = userUpdateParam.getAvatar();
        String email = userUpdateParam.getEmail();
        String password = userUpdateParam.getPassword();
        user.setNickname(nickname);
        user.setAvatar(avatar);
        user.setEmail(email);
        user.setPassword(password);
        //查询用户
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getId, user.getId());
        User byId = userMapper.selectOne(wrapper);
        //进行一一替换
        if (byId == null) {
            return Result.fail("用户不存在");
        }
        if (user.getNickname() != null) {               //昵称
            byId.setNickname(user.getNickname());
        }
        if (user.getAvatar() != null) {               //头像
            byId.setAvatar(user.getAvatar());
        }
        if (user.getPassword() != null) {              //密码
            byId.setPassword(user.getPassword());
        }
        if (user.getEmail() != null) {                 //邮箱
            byId.setEmail(user.getEmail());
        }
        userMapper.updateById(byId);
        return Result.succ("保存个人信息成功");
    }

    @Autowired
    private QiniuUtils qiniuUtils;
    @Autowired
    private MinioUtils minioUtils;
    //修改用户头像
    public Result upAvatar(MultipartFile file) {
        //获取上传文件的文件名
        String fileName = file.getOriginalFilename();
        // 为了避免文件名重复，使用UUID重命名文件，将横杠去掉
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String newFileName = uuid + fileName;
        try{
            String s = minioUtils.putObject(file.getInputStream(), newFileName, file.getContentType());
            // 返回文件名
            String fileName2=s+"/"+newFileName;
            Long id = UserThreadLocal.get().getId();
            User user = userMapper.selectById(id);
            String avatar = user.getAvatar();
            user.setAvatar(fileName2);
            //进行修改头像
            try {
                userMapper.updateById(user);
                minioUtils.removeObject(avatar.split("/")[ avatar.split("/").length]);
            } catch (Exception e) {
                System.out.println("修改头像错误:" + e);
            }
            return Result.succ("修改头像成功");
        }catch (Exception e){
            log.error("上传图片失败，原因是"+e);
            return Result.fail(500,"上传图片失败");
        }
//        if (upload) {
////            System.out.println(qiniuUtils.url+newFileName);
//            fileName = qiniuUtils.url + newFileName;
////            return Result.succ("上传图片成功",qiniuUtils.url+newFileName);
//            //获取用户,根据id
//            Long id = UserThreadLocal.get().getId();
//            User user = userMapper.selectById(id);
//            user.setAvatar(fileName);
//            //进行修改头像
//            try {
//                userMapper.updateById(user);
//            } catch (Exception e) {
//                System.out.println("修改头像错误:" + e);
//            }
//            return Result.succ("修改头像成功");
//        }
//        return Result.fail(20001, "上传图片失败");
//        //获取上传文件的文件名
//        String fileName = file.getOriginalFilename();
//        //获取上传文件的后缀名
//        String suffixName=fileName.substring(fileName.lastIndexOf("."));
//        //uuid获取32位随机数列,
//        String uuid= UUID.randomUUID().toString();
//        //目的:不会有重复的名称,下载到哪里的地址
//        fileName ="/img/avatar/"+uuid+suffixName;
//        String path = System.getProperty("user.dir")+"/src/main/resources/static"+fileName;
//        //进行下载到对应的地址
//        try {
//            file.transferTo(new File(path));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        //获取用户,根据id
//        Long id = UserThreadLocal.get().getId();
//        User user = userMapper.selectById(id);
//
//
//        user.setAvatar(fileName);
//        //进行修改头像
//        try {
//            userMapper.updateById(user);
//        } catch (Exception e) {
//            System.out.println("修改头像错误:"+e);
//        }
//        return Result.succ("修改头像成功");
    }

    //获取头像
    public Result getAvatar() {
        Long id = UserThreadLocal.get().getId();
        String avatar = null;
        try {
            User user = userMapper.selectById(id);
            avatar = user.getAvatar();
        } catch (Exception e) {
            return Result.fail("获取头像失败:" + e);
        }
        return Result.succ("获取头像成功", avatar);
    }

    //修改密码
    public Result upPassword(UserPasswordParam userPasswordParam) {
        String password = userPasswordParam.getPassword();
        String xpassword = userPasswordParam.getXpassword();
        if (password == null || xpassword == null) {
            return Result.fail("修改密码失败,密码不能为空");
        }
        Long id = UserThreadLocal.get().getId();
        User user = userMapper.selectById(id);
        if (!password.equals(user.getPassword())) {
            return Result.fail("修改密码失败,密码错误");
        }
        if (password.equals(xpassword)) {
            return Result.fail("修改密码失败,新密码与旧密码不能相同");
        }
        user.setPassword(xpassword);
        try {
            userMapper.updateById(user);
        } catch (Exception e) {
            return Result.fail("修改密码失败,原因:" + e);
        }
        return Result.succ("修改密码成功");
    }

    //vo
    //获取用户基本信息,根据id
    public UserVo findGetUserVoById(Long id) {
        User user = userMapper.selectById(id);
        if (user == null) {
            return null;
        }
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user, userVo);
        return userVo;
    }

}
