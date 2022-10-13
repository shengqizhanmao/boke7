package com.lin.boke7admin.controller;

import com.lin.boke7admin.common.Result;
import com.lin.boke7admin.pojo.MUser;
import com.lin.boke7admin.pojo.User;
import com.lin.boke7admin.pojo.vo.MUserVo;
import com.lin.boke7admin.pojo.vo.UserVo;
import com.lin.boke7admin.service.MUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author linSheng
 * @since 2022-09-20
 */
@RestController
@RequestMapping("/mUser")
public class MUserController {
    @Autowired
    MUserService mUserService;
    @RequiresPermissions("mUser:get")
    @GetMapping("/get")
    public Result get(){
        List<MUser> list = mUserService.list();
        List<MUserVo> list1=new ArrayList<>();
        for (MUser Muser:list) {
            MUserVo mUserVo= new MUserVo();
            BeanUtils.copyProperties(Muser, mUserVo);
            list1.add(mUserVo);
        }
        return Result.succ("查询普通用户成功",list1);
    }
    @RequiresPermissions("mUser:add")
    @PostMapping("/add")
    public Result add(@RequestBody MUser mUser){
        return mUserService.addMUser(mUser);
    }
    @RequiresPermissions("mUser:update")
    @PostMapping("/update")
    public Result update(@RequestBody MUser mUser){
        return mUserService.updateMUser(mUser);
    }
    @RequiresPermissions("mUser:update")
    @PostMapping("/updateMUserAvatar")
    public Result updateMUserAvatar(MultipartFile file,Long id) {
        return mUserService.upAvatar(file,id);
    }
    @RequiresPermissions("mUser:delete")
    @PostMapping("/delete")
    public Result delete(@RequestBody MUser mUser){
        return mUserService.deleteMUser(mUser.getId());
    }
}
