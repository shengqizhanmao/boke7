package com.lin.boke7qianduan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lin.boke7qianduan.common.Result;
import com.lin.boke7qianduan.controller.param.CommentCreateParam;
import com.lin.boke7qianduan.pojo.Comment;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author linSheng
 * @since 2022-08-11
 */
public interface CommentService extends IService<Comment> {

    Result getCommentsByArticleId(Long articleId);

    Result create(CommentCreateParam commentCreateParam);
}
