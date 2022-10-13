package com.lin.boke7admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lin.boke7admin.pojo.MMenu;
import com.lin.boke7admin.pojo.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lin
 */
@Mapper
@Repository
public interface MMenuMapper extends BaseMapper<MMenu> {
}
