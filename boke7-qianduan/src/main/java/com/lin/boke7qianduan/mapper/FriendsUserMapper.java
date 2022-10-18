package com.lin.boke7qianduan.mapper;

import com.lin.boke7qianduan.controller.para.FriendsUserVo;
import com.lin.boke7qianduan.pojo.FriendsUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author linSheng
 * @since 2022-10-16
 */
@Mapper
@Repository
public interface FriendsUserMapper extends BaseMapper<FriendsUser> {
    List<FriendsUserVo> getListFriendsVoByFormUserId(@Param("formUserId")Long formUserId);
}
