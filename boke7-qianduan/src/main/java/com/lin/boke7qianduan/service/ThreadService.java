package com.lin.boke7qianduan.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.lin.boke7qianduan.mapper.ArticleMapper;
import com.lin.boke7qianduan.pojo.Article;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author lin
 */
@Component
public class ThreadService {
    //在线程池操作,不影响主线程操作
    @Async("taskExecutor")
    public void updateArticleViewCount(ArticleMapper articleMapper, Article article) {
        Long viewCounts = article.getViewCounts();
        Article articleUpdate = new Article();
        articleUpdate.setViewCounts(viewCounts + 1L);
        LambdaUpdateWrapper<Article> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(Article::getId, article.getId());
        //多线程安全,乐观锁,确定之前viewCounts和现在的viewCounts一致
        lambdaUpdateWrapper.eq(Article::getViewCounts, viewCounts);
        try {
            articleMapper.update(articleUpdate, lambdaUpdateWrapper);
        } catch (Exception e) {
            System.err.println("观看人数+1,更新失败,原因是:" + e);
            e.printStackTrace();
        }
    }
}
