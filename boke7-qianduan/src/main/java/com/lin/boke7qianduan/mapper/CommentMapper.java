package com.lin.boke7qianduan.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lin.boke7qianduan.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author linSheng
 * @since 2022-08-11
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
    //    where article_id=#{articleId} and level=#{level} and to_uid = #{toUid}
    @Select("select max(layer) from m_comment where ${ew.sqlSegment}")
    Long selectLayerByLevelByArticleIdByToUid(@Param("ew") LambdaQueryWrapper wrapper);

    @Select("select count(*) from m_comment where ${ew.sqlSegment}")
    Long selectCommentCountComment(@Param("ew") LambdaQueryWrapper<Comment> comment2LambdaQueryWrapper);
}
