package com.lin.boke7admin.mapper;

import com.lin.boke7admin.pojo.RoleResource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 角色资源表 Mapper 接口
 * </p>
 *
 * @author linSheng
 * @since 2022-09-07
 */
@Mapper
@Repository
public interface RoleResourceMapper extends BaseMapper<RoleResource> {

}
