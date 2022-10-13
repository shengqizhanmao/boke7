package com.lin.boke7admin.mapper;

import com.lin.boke7admin.pojo.UserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 用户角色表 Mapper 接口
 * </p>
 *
 * @author linSheng
 * @since 2022-09-07
 */
@Mapper
@Repository
public interface UserRoleMapper extends BaseMapper<UserRole> {

}
