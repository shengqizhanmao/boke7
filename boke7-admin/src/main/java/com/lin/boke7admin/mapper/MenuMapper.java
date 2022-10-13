package com.lin.boke7admin.mapper;

import com.lin.boke7admin.pojo.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lin.boke7admin.pojo.Resource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author linSheng
 * @since 2022-09-18
 */
@Mapper
@Repository
public interface MenuMapper extends BaseMapper<Menu> {

    List<Menu> getListMenuByUserId(@Param("userId")String userId);
}
