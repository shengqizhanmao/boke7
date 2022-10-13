package com.lin.boke7qianduan.controller;

import com.lin.boke7qianduan.common.Result;
import com.lin.boke7qianduan.common.aop.CacheAnnotation;
import com.lin.boke7qianduan.common.aop.LogAnnotation;
import com.lin.boke7qianduan.controller.param.ArticleByCategoryParam;
import com.lin.boke7qianduan.controller.param.ArticleParam;
import com.lin.boke7qianduan.controller.param.UpdateArticleParam;
import com.lin.boke7qianduan.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 操作文章
 *
 * @author lin
 */
@RestController
@RequestMapping("/articles")
public class ArticleController {

    @Autowired
    ArticleService articleService;

//前端:

    @CachePut
    @CacheAnnotation(expire = 5 * 60 * 1000, name = "articleTotal")
    @LogAnnotation(module = "文章", operator = "获取文章的数量")
    @GetMapping("/getTotal")
    public Result getTotal() {
        return articleService.getTotal();
    }

    @CacheAnnotation(expire = 5 * 60 * 1000, name = "article")
    @LogAnnotation(module = "文章", operator = "查询全部文章,有条件的分页查询,以最新时间排序,带时间,有作者,标签")
    @GetMapping("/getAll3/{page}/{pageSize}")
    public Result getAllPage3(@PathVariable("page") Integer page,
                              @PathVariable("pageSize") Integer pageSize) {
        return articleService.getAll3(page, pageSize);
    }

    @CacheAnnotation(name = "article", update = true)
    @LogAnnotation(module = "文章", operator = "查询文章内容,根据id")
    @GetMapping("/get/view/{id}")
    public Result getArticleViewById(@PathVariable("id") Long id) {
        return articleService.getArticleViewById(id);
    }

    @CacheAnnotation(update = true, names = {"article", "articleTotal"})
    @LogAnnotation(module = "文章", operator = "用户创建文章")
    @PostMapping("/createArticle")
    public Result createArticle(@RequestBody ArticleParam articleParam) {
        return articleService.createArticle(articleParam);
    }

    @CacheAnnotation(expire = 5 * 60 * 1000, name = "articleBody")
    @LogAnnotation(module = "文章", operator = "查询文章内容,根据文章id")
    @GetMapping("/getArticleBodyByArticleId/{id}")
    public Result getArticleBodyByArticleId(@PathVariable("id") Long id) {
        return articleService.getArticleBodyByArticleId(id);
    }

    @CacheAnnotation(names = {"article", "articleBody"}, update = true)
    @LogAnnotation(module = "文章", operator = "用户修改文章", Param = false)
    @PostMapping("/updateArticle")
    public Result updateArticle(@RequestBody UpdateArticleParam updateArticleParam) {
        return articleService.updateArticle(updateArticleParam);
    }

    @CacheAnnotation(expire = 5 * 60 * 1000, name = "article")
    @LogAnnotation(module = "文章", operator = "查询文章,根据用户id")
    @GetMapping("/getArticleByUserId/{page}/{pageSize}")
    public Result getArticleByUserId(@PathVariable("page") Integer page,
                                     @PathVariable("pageSize") Integer pageSize) {
        return articleService.getArticleByUserId(page, pageSize);
    }

    @CacheAnnotation(expire = 5 * 60 * 1000, name = "articleTotal")
    @LogAnnotation(module = "文章", operator = "获取文章的数量,根据用户id")
    @GetMapping("/getTotalByUserId")
    public Result getTotalByUserId() {
        return articleService.getTotalByUserId();
    }

    @CacheAnnotation(update = true, names = {"article", "articleTotal"})
    @LogAnnotation(module = "文章", operator = "删除文章,根据文章id")
    @GetMapping("/deleteArticle/{id}")
    public Result deleteArticleByArticleId(@PathVariable("id") Long id) {
        return articleService.deleteArticle(id);
    }

    @CacheAnnotation(expire = 5 * 60 * 1000, name = "article")
    @LogAnnotation(module = "文章", operator = "获取文章的时间段")
    @GetMapping("/getArticleDate")
    public Result getArticleDate() {
        return articleService.getArticleDate();
    }

    @CacheAnnotation(expire = 5 * 60 * 1000, name = "article")
    @LogAnnotation(module = "文章", operator = "获取全部文章,根据文章分类,时间,时间升序或者降序,按热度或时间")
    @PostMapping("/getArticleByCategory")
    public Result getArticleByCategory(@RequestBody ArticleByCategoryParam articleByCategoryParam) {
        return articleService.getArticleByCategory(articleByCategoryParam);
    }

    @CacheAnnotation(expire = 5 * 60 * 1000, name = "article")
    @LogAnnotation(module = "文章", operator = "获取文章的年份和月份以及对应的文章数量")
    @GetMapping("/getListArticleYearMonth")
    public Result getListArticleYearMonth() {
        return articleService.getListArticleYearMonth();
    }

    @CacheAnnotation(expire = 5 * 60 * 1000, name = "article")
    @LogAnnotation(module = "文章", operator = "获取文章,根据年份和月份")
    @GetMapping("/getListArticleByYearMonth/{year}/{month}")
    public Result getListArticleByYearMonth(@PathVariable("year") Integer year, @PathVariable("month") Integer month) {
        return articleService.getListArticleByYearMonth(year, month);
    }

}
