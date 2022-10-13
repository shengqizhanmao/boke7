package com.lin.boke7qianduan.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lin.boke7qianduan.controller.para.ListArticleYearMonthVo;
import com.lin.boke7qianduan.pojo.Article;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Entity com.lin.boke7qianduan.pojo.Article
 */
@Mapper
@Repository
public interface ArticleMapper extends BaseMapper<Article> {
    //查询total的数量
    @Select("select count(*) as total from m_article")
    Long getTotal();

    //查询文章并分页,有条件,带作者和标签,分类的
    @Select("select * from m_article where ${ew.sqlSegment} LIMIT #{page},#{pageSize}")
    @Results({@Result(column = "id", property = "id"),
            @Result(column = "author_id", property = "authorId"),
            @Result(column = "category_id", property = "categoryId"),
            @Result(column = "id", property = "tags", many = @Many(
                    select = "com.lin.boke7qianduan.mapper.TagsMapper.getListByArticleId")
            ), @Result(column = "author_id", property = "author", many = @Many(
            select = "com.lin.boke7qianduan.mapper.UserMapper.getListByAuthorId"
    )),
            @Result(column = "category_id", property = "category", many = @Many(
                    select = "com.lin.boke7qianduan.mapper.CategoryMapper.getListCategoryById"
            ))
    })
    List<Article> getListPageWrapper2(@Param("page") int page, @Param("pageSize") int pageSize, @Param("ew") LambdaQueryWrapper wrapper);

    //查询文章并分页,有条件,带作者和标签,分类的
    @Select("select count(*) from m_article where ${ew.sqlSegment}")
    Integer getListPageWrapper3(@Param("ew") LambdaQueryWrapper wrapper);

    //    获取文章根据id
    @Select("select * from m_article where ${ew.sqlSegment}")
    @Results({@Result(column = "id", property = "id"),
            @Result(column = "author_id", property = "authorId"),
            @Result(column = "id", property = "tags", many = @Many(
                    select = "com.lin.boke7qianduan.mapper.TagsMapper.getListByArticleId")
            ), @Result(column = "author_id", property = "author", many = @Many(
            select = "com.lin.boke7qianduan.mapper.UserMapper.getListByAuthorId"
    ))
    })
    Article getArticleById(@Param("ew") LambdaQueryWrapper wrapper);

    //查询文章,根据用户id
    @Select("select * from m_article Where ${ew.sqlSegment} LIMIT #{page},#{pageSize}")
    @Results({@Result(column = "id", property = "id"),
            @Result(column = "author_id", property = "authorId"),
            @Result(column = "id", property = "tags", many = @Many(
                    select = "com.lin.boke7qianduan.mapper.TagsMapper.getListByArticleId")
            ), @Result(column = "author_id", property = "author", many = @Many(
            select = "com.lin.boke7qianduan.mapper.UserMapper.getListByAuthorId"
    ))
    })
    List<Article> getListPageByUserIdWrapper(@Param("page") int page, @Param("pageSize") int pageSize, @Param("ew") LambdaQueryWrapper wrapper);

    //获取文章数量,根据用户id
    @Select("select count(*) as total from m_article where author_id=#{id}")
    Long getTotalByUserId(@Param("id") Long id);

    //查询文章的全部年份
    @Select("select DISTINCT year(create_date) from m_article")
    List<Integer> getArticleDate();

    //查询文章并分页,有条件,带作者和标签,分类的,标签
    @Select("select DISTINCT b.id,b.title,b.summary,b.comment_counts,b.view_counts,b.weight,b.create_date,b.author_id,b.category_id,b.body_id,a.article_id from m_article b left JOIN m_article_tags a on a.article_id=b.id where ${ew.sqlSegment} LIMIT #{page},#{pageSize}")
    @Results({@Result(column = "id", property = "id"),
            @Result(column = "author_id", property = "authorId"),
            @Result(column = "category_id", property = "categoryId"),
            @Result(column = "id", property = "tags", many = @Many(
                    select = "com.lin.boke7qianduan.mapper.TagsMapper.getListByArticleId")
            ), @Result(column = "author_id", property = "author", many = @Many(
            select = "com.lin.boke7qianduan.mapper.UserMapper.getListByAuthorId"
    )),
            @Result(column = "category_id", property = "category", many = @Many(
                    select = "com.lin.boke7qianduan.mapper.CategoryMapper.getListCategoryById"
            ))
    })
    List<Article> getListPageWrapper4(@Param("page") int page, @Param("pageSize") int pageSize, @Param("ew") LambdaQueryWrapper wrapper);

    //获取文章数量,getListPageWrapper4
    @Select("select count(DISTINCT b.id) from m_article b left JOIN m_article_tags a on a.article_id=b.id where ${ew.sqlSegment}")
    Integer getListPageWrapperSize(@Param("ew") LambdaQueryWrapper wrapper);

    //获取文章的年份和月份
    @Select("select DISTINCT year(create_date) AS year,month(create_date) AS month from m_article ORDER BY year Desc,month Desc")
    List<ListArticleYearMonthVo> getListArticleYearMonth();

    //获取文章的数量,根据年份和月份的数量
    @Select("select count(*) from m_article where year(create_date)=#{year} And month(create_date)=#{month}")
    Integer getListArticleCountByYearMonth(@Param("year") Integer year, @Param("month") Integer month);

    //获取文章,根据年份和月份
    @Select("select * from m_article where year(create_date)=#{year} And month(create_date)=#{month}")
    @Results({@Result(column = "id", property = "id"),
            @Result(column = "author_id", property = "authorId"),
            @Result(column = "id", property = "tags", many = @Many(
                    select = "com.lin.boke7qianduan.mapper.TagsMapper.getListByArticleId")
            ), @Result(column = "author_id", property = "author", many = @Many(
            select = "com.lin.boke7qianduan.mapper.UserMapper.getListByAuthorId"
    )),
            @Result(column = "category_id", property = "category", many = @Many(
                    select = "com.lin.boke7qianduan.mapper.CategoryMapper.getListCategoryById"
            ))
    })
    List<Article> getListArticleByYearMonth(@Param("year") Integer year, @Param("month") Integer month);
}
