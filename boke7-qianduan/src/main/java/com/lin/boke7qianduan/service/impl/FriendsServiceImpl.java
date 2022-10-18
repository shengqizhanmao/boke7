package com.lin.boke7qianduan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lin.boke7qianduan.controller.para.ListFriendsVo;
import com.lin.boke7qianduan.mapper.FriendsMapper;
import com.lin.boke7qianduan.pojo.Friends;
import com.lin.boke7qianduan.service.FriendsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;

/**
 * @author lin
 */
@Slf4j
@Service(value = "friendsService")
public class FriendsServiceImpl extends ServiceImpl<FriendsMapper, Friends> implements FriendsService {

    @Resource
    private FriendsMapper friendsMapper;

    public List<Friends> getFriendsByFormUserIdAnToUserId(Long formUserId,Long toUserId) {
        LambdaQueryWrapper<Friends> friendsLambdaQueryWrapper = new LambdaQueryWrapper<>();
        friendsLambdaQueryWrapper.eq(Friends::getFormUserId,formUserId);
        friendsLambdaQueryWrapper.eq(Friends::getToUserId,toUserId);
        List<Friends> friends = friendsMapper.selectList(friendsLambdaQueryWrapper);
        return friends;
    }
    public List<Friends> getListFriendsByFormUserId(Long formUserId) {
        LambdaQueryWrapper<Friends> friendsLambdaQueryWrapper = new LambdaQueryWrapper<>();
        friendsLambdaQueryWrapper.eq(Friends::getFormUserId,formUserId);
        List<Friends> friends = friendsMapper.selectList(friendsLambdaQueryWrapper);
        return friends;
    }

    @Override
    public ListFriendsVo getListFriendsVoByFormUserId(Long formUserId) {
        List<Friends> friends = friendsMapper.getListFriendsByFormUserId(formUserId);
        log.info(friends.toString());
//        ListFriendsVo listFriendsVo = new ListFriendsVo();
        return null;
    }

    public Comparator getComparator(){
       return new Comparator<Friends>() {
           @Override
           public int compare(Friends o1, Friends o2) {
               long time = o1.getCreated().getTime();
               long time1 = o2.getCreated().getTime();
               return (int) (time - time1);
           }
       };
    }
}
