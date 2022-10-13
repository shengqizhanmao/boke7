package com.lin.boke7qianduan.pojo.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author lin
 */
public class ArticleVo {
    @ApiModelProperty("id")
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

}
