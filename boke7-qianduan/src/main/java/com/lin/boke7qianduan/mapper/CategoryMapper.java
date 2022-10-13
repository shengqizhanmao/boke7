package com.lin.boke7qianduan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lin.boke7qianduan.pojo.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
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
public interface CategoryMapper extends BaseMapper<Category> {
    @Select("select * from m_category where id=#{id} LIMIT 1")
    Category getListCategoryById(Long id);
}
