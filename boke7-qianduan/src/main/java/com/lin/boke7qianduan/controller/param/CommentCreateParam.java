package com.lin.boke7qianduan.controller.param;

/**
 * @author lin
 */
public class CommentCreateParam {
    private Long articleId;//评论文章id
    private String content;//评论内容
    private Long parent;//回复哪个评论的评论id
    private Long toUserId;//回复谁的用户id

    @Override
    public String toString() {
        return "CommentCreateParam{" +
                "articleId=" + articleId +
                ", content='" + content + '\'' +
                ", parent=" + parent +
                ", toUserId=" + toUserId +
                '}';
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getParent() {
        return parent;
    }

    public void setParent(Long parent) {
        this.parent = parent;
    }

    public Long getToUserId() {
        return toUserId;
    }

    public void setToUserId(Long toUserId) {
        this.toUserId = toUserId;
    }
}
