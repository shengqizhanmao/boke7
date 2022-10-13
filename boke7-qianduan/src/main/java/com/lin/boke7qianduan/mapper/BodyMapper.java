package com.lin.boke7qianduan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lin.boke7qianduan.pojo.Body;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author linSheng
 * @since 2022-08-09
 */
@Mapper
@Repository
public interface BodyMapper extends BaseMapper<Body> {

}
