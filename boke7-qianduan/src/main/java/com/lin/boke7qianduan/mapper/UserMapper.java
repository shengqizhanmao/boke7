package com.lin.boke7qianduan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lin.boke7qianduan.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author linSheng
 * @since 2022-08-01
 */
@Repository
@Mapper
public interface UserMapper extends BaseMapper<User> {
    //查询,根据articleId查询m_tags与m_article_tags左连接表(根据a.id=b.tags_id)的数据
    @Select("select nickname from m_user where id=#{authorId} LIMIT 1")
    String getListByAuthorId(Long authorId);
}
