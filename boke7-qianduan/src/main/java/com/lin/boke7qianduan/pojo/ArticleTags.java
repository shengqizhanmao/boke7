package com.lin.boke7qianduan.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * @TableName m_article_tags
 */
@TableName(value = "m_article_tags")
@ApiModel(value = "ArticleTags", description = "")
public class ArticleTags implements Serializable {
    private static final long serialVersionUID = 1L;


    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long articleId;

    private Long tagsId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public Long getTagsId() {
        return tagsId;
    }

    public void setTagsId(Long tagsId) {
        this.tagsId = tagsId;
    }


    @Override
    public String toString() {
        return "ArticleTags{" +
                "id=" + id +
                ", articleId=" + articleId +
                ", tagsId=" + tagsId +
                '}';
    }
}