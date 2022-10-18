package com.lin.boke7qianduan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lin.boke7qianduan.controller.para.FriendsUserVo;
import com.lin.boke7qianduan.pojo.FriendsUser;
import com.lin.boke7qianduan.mapper.FriendsUserMapper;
import com.lin.boke7qianduan.service.FriendsUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author linSheng
 * @since 2022-10-16
 */
@Slf4j
@Service
public class FriendsUserServiceImpl extends ServiceImpl<FriendsUserMapper, FriendsUser> implements FriendsUserService {

    @Autowired
    FriendsUserMapper friendsUserMapper;

    public List<FriendsUser> getFriendsUser(Long formUserId){
        LambdaQueryWrapper<FriendsUser> friendsUserLambdaQueryWrapper = new LambdaQueryWrapper<>();
        friendsUserLambdaQueryWrapper.eq(FriendsUser::getFormUserId,formUserId);
        List<FriendsUser> friendsUsers = friendsUserMapper.selectList(friendsUserLambdaQueryWrapper);
        return friendsUsers;
    }
    public List<FriendsUserVo> getListFriendsUserVo(Long formUserId){
        List<FriendsUserVo> listFriendsVoByFormUserId = friendsUserMapper.getListFriendsVoByFormUserId(formUserId);
        System.out.println(listFriendsVoByFormUserId);
        return listFriendsVoByFormUserId;
    }
}
