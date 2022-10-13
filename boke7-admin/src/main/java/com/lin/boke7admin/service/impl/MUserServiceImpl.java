package com.lin.boke7admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.lin.boke7admin.common.Result;
import com.lin.boke7admin.pojo.MUser;
import com.lin.boke7admin.mapper.MUserMapper;
import com.lin.boke7admin.service.MUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lin.boke7admin.utils.MinioUtils;
import com.lin.boke7admin.utils.QiniuUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author linSheng
 * @since 2022-09-20
 */
@Service
public class MUserServiceImpl extends ServiceImpl<MUserMapper, MUser> implements MUserService {
    @Autowired
    MUserMapper mUserMapper;
    @Override
    public Result addMUser(MUser mUser) {
        String password = mUser.getPassword();
        String email = mUser.getEmail();
        String nickname = mUser.getNickname();
        String username = mUser.getUsername();
        if (StringUtils.isEmpty(username)){
            return Result.fail("登陆名不能为空");
        }
        if (StringUtils.isEmpty(password)){
            password="123456";
        }
        LambdaQueryWrapper<MUser> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(MUser::getUsername,mUser.getUsername());
        try{
            MUser mUser1 = mUserMapper.selectOne(lambdaQueryWrapper);
            if (mUser1!=null){
                return Result.fail("添加失败,用户已存在");
            }
        }catch (Exception e){
            log.error("普通用户添加失败,原因是"+e);
            return Result.fail("添加失败");
        }
        mUser.setCreated(new Date(System.currentTimeMillis()));
        mUser.setStatus(1);
        try {
            int insert = mUserMapper.insert(mUser);
            if (insert == 0) {
                return Result.fail("添加用户失败");
            }
            return Result.succ("添加用户成功");
        }catch (Exception e){
            log.error("普通用户添加失败,原因是"+e);
            return Result.fail("添加失败");
        }
    }

    @Override
    public Result updateMUser(MUser mUser) {
        System.out.println(mUser);
        LambdaUpdateWrapper<MUser> lambdaUpdateWrapper=new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(MUser::getId,mUser.getId()).set(MUser::getNickname,mUser.getNickname())
                .set(MUser::getEmail,mUser.getEmail()).set(MUser::getPassword,mUser.getPassword());
        try{
            int update = mUserMapper.update(mUser, lambdaUpdateWrapper);
            if (update==0){
                Result.fail("用户修改失败");
            }
            return Result.succ("用户修改成功");
        }catch (Exception e){
            log.error("用户修改失败,原因是"+e);
            return Result.fail("用户修改失败");
        }
    }

    @Override
    public Result deleteMUser(Long id) {
        if(id==null){
            return Result.fail("id参数不能为空");
        }
        try{
            MUser mUser1 = mUserMapper.selectById(id);
            if (mUser1==null){
                return Result.fail("用户删除失败,用户不存在");
            }
        }catch (Exception e){
            log.error("用户删除失败,原因是"+e);
            return Result.fail("用户删除失败");
        }
        try {
            int i = mUserMapper.deleteById(id);
            if (i == 0) {
                return Result.fail("删除失败");
            }
            return Result.succ("删除成功");
        }catch (Exception e){
            log.error("用户删除失败,原因是"+e);
            return Result.fail("用户删除失败");
        }
    }
    @Autowired
    private QiniuUtils qiniuUtils;
    @Autowired
    private MinioUtils minioUtils;
    //修改用户头像
    public Result upAvatar(MultipartFile file,Long id) {
        //获取上传文件的文件名
        String fileName = file.getOriginalFilename();
        // 为了避免文件名重复，使用UUID重命名文件，将横杠去掉
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String newFileName = uuid + fileName;
        try{
            String s = minioUtils.putObject(file.getInputStream(), newFileName, file.getContentType());
            // 返回文件名
            String fileName2=s+"/"+newFileName;
            MUser mUser = mUserMapper.selectById(id);
            String avatar = mUser.getAvatar();
            mUser.setAvatar(fileName2);
            //进行修改头像
            try {
                mUserMapper.updateById(mUser);
                minioUtils.removeObject(avatar.split("/")[avatar.split("/").length-1]);
            } catch (Exception e) {
                System.out.println("修改头像错误:" + e);
            }
            return Result.succ("修改头像成功");
        }catch (Exception e){
            log.error("上传图片失败，原因是"+e);
            return Result.fail(20001,"上传图片失败");
        }
    }
}
