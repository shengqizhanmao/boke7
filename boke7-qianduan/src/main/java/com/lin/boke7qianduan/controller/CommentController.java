package com.lin.boke7qianduan.controller;

import com.lin.boke7qianduan.common.Result;
import com.lin.boke7qianduan.controller.param.CommentCreateParam;
import com.lin.boke7qianduan.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author linSheng
 * @since 2022-08-11
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    //获取评论
    @GetMapping("/article/{ArticleId}")
    public Result comments(@PathVariable("ArticleId") Long articleId) {
        return commentService.getCommentsByArticleId(articleId);
    }

    //进行评论
    @PostMapping("/create")
    public Result create(@RequestBody CommentCreateParam commentCreateParam) {
        return commentService.create(commentCreateParam);
    }
}
