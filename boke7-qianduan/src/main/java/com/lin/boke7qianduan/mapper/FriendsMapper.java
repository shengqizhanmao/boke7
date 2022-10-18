package com.lin.boke7qianduan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lin.boke7qianduan.pojo.Friends;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lin
 */
@Mapper
@Repository
public interface FriendsMapper extends BaseMapper<Friends> {

    List<Friends> getListFriendsByFormUserId(@Param("form_user_id")Long formUserId);
}

