package com.lin.boke7admin.mapper;

import com.lin.boke7admin.pojo.MUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author linSheng
 * @since 2022-09-20
 */
@Mapper
@Repository
public interface MUserMapper extends BaseMapper<MUser> {

}
