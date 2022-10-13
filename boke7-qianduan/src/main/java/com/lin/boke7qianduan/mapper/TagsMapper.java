package com.lin.boke7qianduan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lin.boke7qianduan.pojo.Tags;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Entity boke7qianduan.pojo.Tags
 */
@Mapper
public interface TagsMapper extends BaseMapper<Tags> {

    //查询,根据articleId查询m_tags与m_article_tags左连接表(根据a.id=b.tags_id)的数据
    @Select("select * from m_tags a left join m_article_tags b on a.id=b.tags_id where b.article_id=#{articleId}")
    List<Tags> getListByArticleId(Long articledId);

}
