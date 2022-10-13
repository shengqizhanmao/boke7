package com.lin.boke7qianduan.controller.para;

import com.lin.boke7qianduan.pojo.Article;

import java.util.List;

/**
 * @author lin
 */
public class ListArticleVo {
    private List<Article> articles;
    private int size;

    @Override
    public String toString() {
        return "ListArticleVo{" +
                "articles=" + articles +
                ", size=" + size +
                '}';
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
