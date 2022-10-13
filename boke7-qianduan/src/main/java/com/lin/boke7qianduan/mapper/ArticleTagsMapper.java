package com.lin.boke7qianduan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lin.boke7qianduan.pojo.ArticleTags;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Entity com.lin.boke7qianduan.pojo.ArticleTags
 */
@Mapper
@Repository
public interface ArticleTagsMapper extends BaseMapper<ArticleTags> {

}
