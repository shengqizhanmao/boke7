package com.lin.boke7qianduan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lin.boke7qianduan.ThreadLocal.UserThreadLocal;
import com.lin.boke7qianduan.common.Result;
import com.lin.boke7qianduan.controller.param.CommentCreateParam;
import com.lin.boke7qianduan.mapper.CommentMapper;
import com.lin.boke7qianduan.pojo.Article;
import com.lin.boke7qianduan.pojo.Comment;
import com.lin.boke7qianduan.pojo.User;
import com.lin.boke7qianduan.pojo.vo.CommentVo;
import com.lin.boke7qianduan.pojo.vo.UserVo;
import com.lin.boke7qianduan.service.ArticleService;
import com.lin.boke7qianduan.service.CommentService;
import com.lin.boke7qianduan.service.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author linSheng
 * @since 2022-08-11
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private ArticleService articleService;

    //查询评论,根据文章id查询评论
    @Override
    public Result getCommentsByArticleId(Long articleId) {
        LambdaQueryWrapper<Comment> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Comment::getArticleId, articleId);
        lambdaQueryWrapper.eq(Comment::getLevel, 1);
        lambdaQueryWrapper.orderByAsc(Comment::getLayer);
        List<Comment> comments = commentMapper.selectList(lambdaQueryWrapper);
        List<CommentVo> commentVoList = copyList(comments);
//        System.out.println(commentVoList);
        if (commentVoList == null) {
            return Result.fail("查询评论失败");
        }
        return Result.succ("查询评论成功", commentVoList);
    }

    //创建评论
    @Override
    public Result create(CommentCreateParam commentCreateParam) {
        if (commentCreateParam.getContent() == "") {
            return Result.succ(false, 200, "评论不能为空", null);
        }
        User user = UserThreadLocal.get();
        Comment comment = new Comment();
        comment.setArticleId(commentCreateParam.getArticleId());
        comment.setAuthorId(user.getId());
        comment.setContent(commentCreateParam.getContent());
        comment.setCreateDate(new Date(System.currentTimeMillis()));
        Long parent = commentCreateParam.getParent();
        comment.setParentId(parent);
        if ((parent == null || parent == 0L || parent == 0)) {
            comment.setLevel(1L);
        } else {
            comment.setLevel(2L);
        }
        Long toUserId = commentCreateParam.getToUserId();
        if ((toUserId == null || toUserId == 0L || toUserId == 0)) {
            comment.setToUid(0L);
        } else {
            comment.setToUid(toUserId);
        }
        //获取所在层数,修改层数+1
        LambdaQueryWrapper<Comment> commentLambdaQueryWrapper = new LambdaQueryWrapper<>();
        commentLambdaQueryWrapper.eq(Comment::getArticleId, commentCreateParam.getArticleId());
        commentLambdaQueryWrapper.eq(Comment::getLevel, comment.getLevel());
        commentLambdaQueryWrapper.eq(Comment::getParentId, parent);
        Long aLong = 0L;
        try {
            aLong = commentMapper.selectLayerByLevelByArticleIdByToUid(commentLambdaQueryWrapper);
        } catch (Exception e) {
            System.out.println(e);
        }
//        Long aLong = commentMapper.selectLayerByLevelByArticleIdByToUid(commentLambdaQueryWrapper);
        if (aLong == null) {
            aLong = 0L;
        }
        comment.setLayer(aLong + 1L);
        int insert = commentMapper.insert(comment);
        //进行评论数量加1
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Article::getId, commentCreateParam.getArticleId());
        Article one = articleService.getOne(lambdaQueryWrapper);
        //查询文章评论的数量
        LambdaQueryWrapper<Comment> comment2LambdaQueryWrapper = new LambdaQueryWrapper<>();
        comment2LambdaQueryWrapper.eq(Comment::getArticleId, commentCreateParam.getArticleId());
        Long aLong1 = commentMapper.selectCommentCountComment(comment2LambdaQueryWrapper);
        //评论数量加1
        one.setCommentCounts(aLong1);
        boolean b = articleService.updateById(one);
        if (b == false) {
            return Result.fail("修改评论数量失败");
        }
        if (insert == 1) {
            return Result.succ("评论成功");
        } else if (insert == 0) {
            return Result.fail("评论失败");
        } else {
            return Result.fail(406, "评论系统出现错误");
        }
    }


    //提供方法
    @NotNull
    private List<CommentVo> copyList(List<Comment> comments) {
        List<CommentVo> commentVoList = new ArrayList<>();
        for (Comment comment : comments) {
            commentVoList.add(copy(comment));
        }
        return commentVoList;
    }

    @NotNull
    private CommentVo copy(Comment comment) {
        CommentVo commentVo = new CommentVo();
        BeanUtils.copyProperties(comment, commentVo);
        Long authorId = comment.getAuthorId();
        //作者
        UserVo userVo = userService.findGetUserVoById(authorId);
        commentVo.setAuthor(userVo);
        //子评论
        Long level = comment.getLevel();
        if (level == 1L) {
            Long id = comment.getId();
            List<CommentVo> commentVoList = findCommentsByParentId(id);
            commentVo.setChildrens(commentVoList);
        }
        //to User 给谁评论
        if (level > 1) {
            Long toUid = comment.getToUid();
            UserVo getUserVoById = userService.findGetUserVoById(toUid);
            commentVo.setToUser(getUserVoById);
        }
        return commentVo;
    }

    @NotNull
    private List<CommentVo> findCommentsByParentId(Long id) {
        LambdaQueryWrapper<Comment> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(Comment::getParentId, id);
        lambdaQueryWrapper.eq(Comment::getLevel, 2);
        lambdaQueryWrapper.orderByAsc(Comment::getLayer);
        List<CommentVo> commentVos = copyList(commentMapper.selectList(lambdaQueryWrapper));
        return commentVos;
    }
}
