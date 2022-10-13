package com.lin.boke7admin.mapper;

import com.lin.boke7admin.pojo.Resource;
import com.lin.boke7admin.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lin.boke7admin.pojo.UserExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author linSheng
 * @since 2022-09-07
 */
@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {
}
