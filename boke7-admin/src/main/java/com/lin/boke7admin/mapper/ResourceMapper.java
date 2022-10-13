package com.lin.boke7admin.mapper;

import com.lin.boke7admin.pojo.Resource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 资源表 Mapper 接口
 * </p>
 *
 * @author linSheng
 * @since 2022-09-07
 */
@Mapper
@Repository
public interface ResourceMapper extends BaseMapper<Resource> {

    List<Resource> getListResourceByUserId(@Param("id")String id);
    List<Resource> getListResourceByRoleId(@Param("id")String id);
}
