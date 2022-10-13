package com.lin.boke7qianduan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lin.boke7qianduan.common.Result;
import com.lin.boke7qianduan.controller.param.ArticleByCategoryParam;
import com.lin.boke7qianduan.controller.param.ArticleParam;
import com.lin.boke7qianduan.controller.param.UpdateArticleParam;
import com.lin.boke7qianduan.pojo.Article;

/**
 *
 */
public interface ArticleService extends IService<Article> {
    //    Result getAll();    //没有标签查询
//    Result getArticle(Long id);   //有条件的标签查询
//    Result getAll();   //无条件标签查询
//    Result getAll(int page, int pageSize);
//    Result getAll2(int page, int pageSize);
    //查询所有文章
    Result getAll3(Integer page, Integer pageSize);

    //查询所有文章数量
    Result getTotal();

    //查询文章和内容,根据文章id
    Result getArticleViewById(Long id);

    //创建文章
    Result createArticle(ArticleParam articleParam);

    //查询文章,根据用户id
    Result getArticleByUserId(Integer page, Integer pageSize);

    //查询文章数量,根据用户id
    Result getTotalByUserId();

    //查询文章和文章内容,根据文章id,用户修改之前查询
    Result getArticleBodyByArticleId(Long articleId);

    //修改文章,用户操作
    Result updateArticle(UpdateArticleParam updateArticleParam);

    //删除文章
    Result deleteArticle(Long id);

    //获取文章的所有时间段(仅年份)
    Result getArticleDate();

    //获取文章,根据分类
    Result getArticleByCategory(ArticleByCategoryParam articleByCategoryParam);

    //获取文章的年份和月份
    Result getListArticleYearMonth();

    //获取年份和月份对应的文章,根据年份和月份
    Result getListArticleByYearMonth(Integer year, Integer month);

}
