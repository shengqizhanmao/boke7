package com.lin.boke7qianduan.service;

import com.lin.boke7qianduan.controller.para.FriendsUserVo;
import com.lin.boke7qianduan.pojo.FriendsUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author linSheng
 * @since 2022-10-16
 */
public interface FriendsUserService extends IService<FriendsUser> {
    List<FriendsUser> getFriendsUser(Long formUserId);
    List<FriendsUserVo> getListFriendsUserVo(Long formUserId);
}
