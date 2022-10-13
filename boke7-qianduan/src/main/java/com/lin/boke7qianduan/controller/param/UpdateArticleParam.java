package com.lin.boke7qianduan.controller.param;

import com.lin.boke7qianduan.pojo.Category;
import com.lin.boke7qianduan.pojo.Tags;

import java.util.List;

/**
 * @author lin
 */
public class UpdateArticleParam {

    private Long id;
    private List<Tags> tags;
    private String title;
    private String summary;
    private String content;
    private String contentHtml;
    private Category category;

    @Override
    public String toString() {
        return "UpdateArticleParam{" +
                "id=" + id +
                ", tags=" + tags +
                ", title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", content='" + content + '\'' +
                ", contentHtml='" + contentHtml + '\'' +
                ", category=" + category +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentHtml() {
        return contentHtml;
    }

    public void setContentHtml(String contentHtml) {
        this.contentHtml = contentHtml;
    }

    public void setTags(List<Tags> tags) {
        this.tags = tags;
    }

    public List<Tags> getTags() {
        return tags;
    }

}
