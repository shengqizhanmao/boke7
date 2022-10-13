package com.lin.boke7qianduan.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author linSheng
 * @since 2022-08-03
 */
@TableName("m_article")
@ApiModel(value = "Article", description = "")
public class Article implements Serializable {

    public static final Long ARTICLE_WEIGH_COMMON = 1L;
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("文章标题")
    private String title;

    @ApiModelProperty("简介")
    private String summary;

    @ApiModelProperty("评论数量")
    private Long commentCounts;

    @ApiModelProperty("浏览数量")
    private Long viewCounts;

    @ApiModelProperty("是否置顶")
    private Long weight;


    @ApiModelProperty("创建时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    @ApiModelProperty("作者id")
    private Long authorId;

    @ApiModelProperty("类别id")
    private Long categoryId;

    @ApiModelProperty("内容id")
    private Long bodyId;

    @TableField(exist = false)  //表示不是表里面的字段,标签
    private List<Tags> Tags;

    @TableField(exist = false)  //表示不是表里面的字段,作者
    private String author;

    @TableField(exist = false)  //表示不是表里面的字段,文章内容
    private Body body;
    @TableField(exist = false)  //表示不是表里面的字段,分类
    private Category category;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Long getCommentCounts() {
        return commentCounts;
    }

    public void setCommentCounts(Long commentCounts) {
        this.commentCounts = commentCounts;
    }

    public Long getViewCounts() {
        return viewCounts;
    }

    public void setViewCounts(Long viewCounts) {
        this.viewCounts = viewCounts;
    }

    public Long getWeight() {
        return weight;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getBodyId() {
        return bodyId;
    }

    public void setBodyId(Long bodyId) {
        this.bodyId = bodyId;
    }

    public List<Tags> getTags() {
        return Tags;
    }

    public void setTags(List<Tags> tags) {
        Tags = tags;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", commentCounts=" + commentCounts +
                ", viewCounts=" + viewCounts +
                ", weight=" + weight +
                ", createDate=" + createDate +
                ", authorId=" + authorId +
                ", categoryId=" + categoryId +
                ", bodyId=" + bodyId +
                ", Tags=" + Tags +
                ", author='" + author + '\'' +
                ", body=" + body +
                ", category=" + category +
                '}';
    }
}
