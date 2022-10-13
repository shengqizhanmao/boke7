package com.lin.boke7qianduan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lin.boke7qianduan.ThreadLocal.UserThreadLocal;
import com.lin.boke7qianduan.common.Result;
import com.lin.boke7qianduan.controller.para.ListArticleVo;
import com.lin.boke7qianduan.controller.para.ListArticleYearMonthVo;
import com.lin.boke7qianduan.controller.param.ArticleByCategoryParam;
import com.lin.boke7qianduan.controller.param.ArticleParam;
import com.lin.boke7qianduan.controller.param.UpdateArticleParam;
import com.lin.boke7qianduan.mapper.ArticleMapper;
import com.lin.boke7qianduan.mapper.ArticleTagsMapper;
import com.lin.boke7qianduan.mapper.BodyMapper;
import com.lin.boke7qianduan.mapper.CategoryMapper;
import com.lin.boke7qianduan.pojo.Article;
import com.lin.boke7qianduan.pojo.ArticleTags;
import com.lin.boke7qianduan.pojo.Body;
import com.lin.boke7qianduan.pojo.Category;
import com.lin.boke7qianduan.pojo.Tags;
import com.lin.boke7qianduan.pojo.User;
import com.lin.boke7qianduan.service.ArticleService;
import com.lin.boke7qianduan.service.BodyService;
import com.lin.boke7qianduan.service.TagsService;
import com.lin.boke7qianduan.service.ThreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 *
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Autowired
    ArticleMapper articleMapper;

    @Autowired
    BodyMapper bodyMapper;

    @Autowired
    private ArticleTagsMapper articleTagsMapper;

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    TagsService tagsService;
    @Autowired
    BodyService bodyService;

    //获取Total
    public Result getTotal() {
        Long total = articleMapper.getTotal();
        if (total == null) {
            return Result.fail("获取文章全部数量失败");
        }
        return Result.succ("获取文章全部数量成功", total);
    }

    //分页查询带作者
    public Result getAll3(Integer page, Integer pageSize) {
        if (page >= 0 && pageSize >= 0) {
            page = (page - 1) * pageSize;
            LambdaQueryWrapper<Article> articleWrapper = new LambdaQueryWrapper<>();
            articleWrapper.eq(Article::getWeight, 1);
            articleWrapper.orderByDesc(Article::getCreateDate);      //根据时间排序
            List<Article> records = articleMapper.getListPageWrapper2(page, pageSize, articleWrapper);
            if (records == null) {
                return Result.fail("查询文章失败");
            } else if (records.size() == 0) {
                return Result.succ("无文章");
            }
//            System.out.println(records);
            return Result.succ("查询文章分页列表成功", records);
        }
        return Result.fail("page和pageSize参数不能小于0");
    }

    @Autowired
    private ThreadService threadService;

    //查询文章内容,根据id，用户进行访问
    @Override
    public Result getArticleViewById(Long id) {
        //获取文章,根据文章id
        LambdaQueryWrapper<Article> articleWrapper = new LambdaQueryWrapper<>();
        articleWrapper.eq(Article::getId, id);
        Article article = articleMapper.getArticleById(articleWrapper);
        if (article == null) {
            Result.fail(402, "查询文章错误");
        }
        //查询文章内容,根据文章的内容id
        Body body = bodyMapper.selectById(article.getBodyId());
        if (body == null) {
            Result.fail(402, "查询文章内容错误");
        }
        article.setBody(body);
        Category category = categoryMapper.selectById(article.getCategoryId());
        if (category == null) {
            Result.fail(402, "查询文章分类错误");
        }
        article.setCategory(category);
        //获取文章标签,根据文章id和中间表
        List<Tags> listByArticleId = tagsService.getListByArticleId(article.getId());
        article.setTags(listByArticleId);
        threadService.updateArticleViewCount(articleMapper, article);
        return Result.succ("查询文章内容成功", article);
    }


    @Override
    @Transactional//事务
    public Result createArticle(ArticleParam articleParam) {
        /*
         * 1.插入文章
         * 2.插入tags与article的中间表,如果没有tags,则不创建
         * 3.插入body数据
         * 4.
         *  */
//        System.out.println("articleParam:"+articleParam);
        User user = UserThreadLocal.get();
//        System.out.println("user:"+user);
        //0.进行数据效验
        if (articleParam.getTitle() == null) {
            return Result.fail("添加失败,标题不能为空");
        }
        if (articleParam.getSummary() == null) {
            return Result.fail("添加失败,简介不能为空");
        }
        if (articleParam.getCategory().getId() == null) {
            return Result.fail("添加失败,分类不能为空");
        }
        if (articleParam.getContentHtml() == null) {
            return Result.fail("添加失败,内容不能为空");
        }
        //1.插入文章
        Article article = new Article();
        article.setAuthorId(user.getId());                              //用户id
        article.setTitle(articleParam.getTitle());                      //标题
        article.setSummary(articleParam.getSummary());                  //简介
        article.setCommentCounts(0L);                                   //评论人数
        article.setViewCounts(0L);                                      //观看人数
        article.setWeight(Article.ARTICLE_WEIGH_COMMON);                //置顶,
        article.setCreateDate(new Date(System.currentTimeMillis()));  //创建时间
        article.setCategoryId(articleParam.getCategory().getId());  //类别id
        articleMapper.insert(article);//插入数据库
//        System.out.println("article.getId:"+article.getId());
        //2.插入tags与article的中间表
        List<Tags> tags = articleParam.getTags();
        if (tags != null) {
            for (Tags tag : tags) {
                Long articleId = article.getId();
                ArticleTags articleTags = new ArticleTags();
                articleTags.setArticleId(articleId);
                articleTags.setTagsId(tag.getId());
                articleTagsMapper.insert(articleTags);
            }
        }
        //3.插入body数据
        Body body = new Body();
        body.setArticleId(article.getId());
        body.setContent(articleParam.getContent());
        body.setContentHtml(articleParam.getContentHtml());
        bodyMapper.insert(body);
//        System.out.println("body.getId():"+body.getId());
        article.setBodyId(body.getId());
        articleMapper.updateById(article);
        return Result.succ("添加文章成功");
    }

    //查询文章列表,根据用户id
    @Override
    public Result getArticleByUserId(Integer page, Integer pageSize) {
        if (page >= 0 && pageSize >= 0) {
            Long id = UserThreadLocal.get().getId();
            page = (page - 1) * pageSize;
            LambdaQueryWrapper<Article> articleWrapper = new LambdaQueryWrapper<>();
            articleWrapper.eq(Article::getAuthorId, id);
            articleWrapper.orderByDesc(Article::getCreateDate);      //根据时间排序
            List<Article> records = articleMapper.getListPageByUserIdWrapper(page, pageSize, articleWrapper);
            if (records == null) {
                return Result.fail("根据用户id查询文章失败");
            } else if (records.size() == 0) {
                return Result.succ("无文章");
            }
//            System.out.println(records);
            return Result.succ("根据用户id查询文章分页列表成功", records);
        }
        return Result.fail("page和pageSize参数不能小于0");
    }

    //查询文章的数量,根据用户id
    @Override
    public Result getTotalByUserId() {
        Long id = UserThreadLocal.get().getId();
        Long total = articleMapper.getTotalByUserId(id);
        if (total == null) {
            return Result.fail("获取文章数量失败");
        }
        return Result.succ("获取文章数量成功,根据用户id", total);
    }

    //查询文章和文章内容,根据文章id
    public Result getArticleBodyByArticleId(Long articleId) {
        //获取文章,根据文章id
        LambdaQueryWrapper<Article> articleWrapper = new LambdaQueryWrapper<>();
        articleWrapper.eq(Article::getId, articleId);
        Article article = articleMapper.getArticleById(articleWrapper);
        if (article == null) {
            return Result.fail(402, "查询文章错误");
        }
        //查询文章内容,根据文章的内容id
        Body body = bodyMapper.selectById(article.getBodyId());
        if (body == null) {
            return Result.fail(402, "查询文章内容错误");
        }
        article.setBody(body);
        Category category = categoryMapper.selectById(article.getCategoryId());
        if (category == null) {
            return Result.fail(402, "查询文章分类错误");
        }
        article.setCategory(category);
        //获取文章标签,根据文章id和中间表
        List<Tags> listByArticleId = tagsService.getListByArticleId(article.getId());
        if (listByArticleId == null) {
            return Result.fail(402, "查询文章标签失败");
        }
        article.setTags(listByArticleId);
        return Result.succ("查询文章内容成功", article);
    }

    //修改文章
    @Override
    public Result updateArticle(UpdateArticleParam updateArticleParam) {
        //0.查询文章
        LambdaQueryWrapper<Article> ArticleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        ArticleLambdaQueryWrapper.eq(Article::getId, updateArticleParam.getId());
        Article articleById = articleMapper.getArticleById(ArticleLambdaQueryWrapper);
        //1.修改文章
        articleById.setTitle(updateArticleParam.getTitle());                      //标题
        articleById.setSummary(updateArticleParam.getSummary());                  //简介
        articleById.setCategoryId(updateArticleParam.getCategory().getId());  //类别id
        articleMapper.updateById(articleById);//修改数据库
//        System.out.println("article.getId:"+article.getId());
        //2.修改tags与article的中间表
        List<Tags> tags = updateArticleParam.getTags();
        //删除全部中间表
        LambdaQueryWrapper<ArticleTags> articleTagsLambdaQueryWrapper = new LambdaQueryWrapper<>();
        articleTagsLambdaQueryWrapper.eq(ArticleTags::getArticleId, articleById.getId());
        articleTagsMapper.delete(articleTagsLambdaQueryWrapper);
        if (tags != null) {
            for (Tags tag : tags) {
                Long articleId = articleById.getId();
                ArticleTags articleTags = new ArticleTags();
                articleTags.setArticleId(articleId);
                articleTags.setTagsId(tag.getId());
                articleTagsMapper.insert(articleTags);
            }
        }
        //3.插入body数据
        LambdaQueryWrapper<Body> bodyLambdaQueryWrapper = new LambdaQueryWrapper<>();
        bodyLambdaQueryWrapper.eq(Body::getArticleId, articleById.getId());
        Body body = bodyService.getOne(bodyLambdaQueryWrapper);
        body.setContent(updateArticleParam.getContent());
        body.setContentHtml(updateArticleParam.getContentHtml());
        bodyMapper.updateById(body);
//        System.out.println("body.getId():"+body.getId());
        return Result.succ("修改文章成功");
    }

    //删除文章
    @Override
    public Result deleteArticle(Long id) {
        //删除ArticleTags中间表
        LambdaQueryWrapper<ArticleTags> articleTagsLambdaQueryWrapper = new LambdaQueryWrapper<>();
        articleTagsLambdaQueryWrapper.eq(ArticleTags::getArticleId, id);
        articleTagsMapper.delete(articleTagsLambdaQueryWrapper);
        //删除ArticleBody的内容
        LambdaQueryWrapper<Body> bodyLambdaQueryWrapper = new LambdaQueryWrapper<>();
        bodyLambdaQueryWrapper.eq(Body::getArticleId, id);
        bodyService.remove(bodyLambdaQueryWrapper);
        //删除Article文章
        LambdaQueryWrapper<Article> articleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        articleLambdaQueryWrapper.eq(Article::getId, id);
        articleMapper.delete(articleLambdaQueryWrapper);
        return Result.succ("删除成功");
    }

    //获取文章所有时间（年份），并进行降序排序
    @Override
    public Result getArticleDate() {
        List<Integer> articleDate = articleMapper.getArticleDate();
        System.out.println(articleDate);
        //进行降序排序，默认是升序
        Collections.sort(articleDate, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        });
        return Result.succ("查询成功", articleDate);
    }

    //查询文章，根据分类条件
    @Override
    public Result getArticleByCategory(ArticleByCategoryParam articleByCategoryParam) {
        Integer page = articleByCategoryParam.getPage();
        Integer pagesize = articleByCategoryParam.getPagesize();
        page = (page - 1) * pagesize;
        String radioCategory = articleByCategoryParam.getRadioCategory();
        String radioDate = articleByCategoryParam.getRadioDate();
        String radioTags = articleByCategoryParam.getRadioTags();
        String radioViewSort = articleByCategoryParam.getRadioViewSort();
        LambdaQueryWrapper<Article> articleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        articleLambdaQueryWrapper.eq(Article::getWeight, 1);
        if (!radioTags.equals("0")) {
            articleLambdaQueryWrapper.apply(" tags_id= " + radioTags);
        }
        if (!radioDate.equals("0")) {
            articleLambdaQueryWrapper.apply("year(create_date) = " + radioDate);
        }
        if (!radioCategory.equals("0")) {
            articleLambdaQueryWrapper.eq(Article::getCategoryId, radioCategory);
        }
        if (radioViewSort.equals("1")) {   //等于1时，是时间排序
            articleLambdaQueryWrapper.orderByDesc(Article::getCreateDate);
        } else {                            //否则按人气观看
            articleLambdaQueryWrapper.orderByDesc(Article::getViewCounts);
        }
        List<Article> listPageWrapper2 = articleMapper.getListPageWrapper4(page, pagesize, articleLambdaQueryWrapper);
        Integer size = articleMapper.getListPageWrapperSize(articleLambdaQueryWrapper);
        ListArticleVo listArticleVo = new ListArticleVo();
        listArticleVo.setArticles(listPageWrapper2);
        listArticleVo.setSize(size);
//        System.out.println(listArticleVo);
        return Result.succ("根据分类查询成功", listArticleVo);
    }

    //查询文章年份和月份和对应的数量
    @Override
    public Result getListArticleYearMonth() {
        List<ListArticleYearMonthVo> listArticleYearMonthVo = null;
        try {
            listArticleYearMonthVo = articleMapper.getListArticleYearMonth();
        } catch (Exception e) {
            System.out.println(e);
            return Result.fail("查询全部文章的年份和月份失败");
        }
        for (ListArticleYearMonthVo item : listArticleYearMonthVo) {
            Integer month = item.getMonth();
            Integer year = item.getYear();
            Integer size = articleMapper.getListArticleCountByYearMonth(year, month);
            item.setSize(size);
        }
        return Result.succ("查询全部文章的年份和月份成功", listArticleYearMonthVo);
    }

    //查询文章,根据年份和月份
    @Override
    public Result getListArticleByYearMonth(Integer year, Integer month) {
        if (month == null || year == null) {
            return Result.fail("年份和月份参数不能为空");
        }
        List<Article> listArticleByYearMonth = articleMapper.getListArticleByYearMonth(year, month);
        if (listArticleByYearMonth == null) {
            return Result.succ("查询" + year + "年" + month + "月的文章成功," + year + "年" + month + "月无文章");
        }
        return Result.succ("查询" + year + "年" + month + "月的文章成功", listArticleByYearMonth);
    }
}

