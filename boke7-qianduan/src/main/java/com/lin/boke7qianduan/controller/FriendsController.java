package com.lin.boke7qianduan.controller;

import com.lin.boke7qianduan.common.Result;
import com.lin.boke7qianduan.controller.para.FriendsUserVo;
import com.lin.boke7qianduan.pojo.Friends;
import com.lin.boke7qianduan.pojo.FriendsUser;
import com.lin.boke7qianduan.service.FriendsService;
import com.lin.boke7qianduan.service.FriendsUserService;
import com.lin.boke7qianduan.utils.MinioUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author lin
 */
@RestController
@RequestMapping("/friends")
public class FriendsController {
    @Autowired
    private FriendsService friendsService;
    @Autowired
    private FriendsUserService friendsUserService;
    //获取好友
    @GetMapping("/{formUserId}")
    public Result getFriend(@PathVariable("formUserId") Long formUserId) {
//        List<FriendsUser> friendsUser = friendsUserService.getFriendsUser(formUserId);
        List<FriendsUserVo> listFriendsUserVo = friendsUserService.getListFriendsUserVo(formUserId);
        return Result.succ(200,"查询好友成功",listFriendsUserVo);
    }
    @GetMapping("/{formUserId}/{toUserId}")
    public Result getFriendId(@PathVariable("formUserId") Long formUserId,@PathVariable("toUserId") Long toUserId) {
        List<Friends> friendsByFormUserIdAnToUserId = friendsService.getFriendsByFormUserIdAnToUserId(formUserId, toUserId);
        List<Friends> friendsByFormUserIdAnToUserId1 = friendsService.getFriendsByFormUserIdAnToUserId(toUserId, formUserId);
        friendsByFormUserIdAnToUserId.addAll(friendsByFormUserIdAnToUserId1);
        friendsByFormUserIdAnToUserId.sort(friendsService.getComparator());
        System.out.println(friendsByFormUserIdAnToUserId);
        return Result.succ(200,"查询聊天记录成功",friendsByFormUserIdAnToUserId);
    }
}
