package com.lin.boke7qianduan.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lin.boke7qianduan.controller.para.ListFriendsVo;
import com.lin.boke7qianduan.pojo.Friends;

import java.util.Comparator;
import java.util.List;

/**
 * @author lin
 */
public interface FriendsService  extends IService<Friends> {
    List<Friends> getFriendsByFormUserIdAnToUserId(Long formUserId, Long toUserId);
    List<Friends> getListFriendsByFormUserId(Long formUserId);
    ListFriendsVo getListFriendsVoByFormUserId(Long formUserId);
    Comparator getComparator();
}
