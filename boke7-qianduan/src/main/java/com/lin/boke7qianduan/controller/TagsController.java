package com.lin.boke7qianduan.controller;

import com.lin.boke7qianduan.common.Result;
import com.lin.boke7qianduan.pojo.Tags;
import com.lin.boke7qianduan.service.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 操作标签
 *
 * @author lin
 */
@RestController
@RequestMapping("/tags")
public class TagsController {
    @Autowired
    TagsService tagsService;

    @GetMapping("/getListTags")
    public Result getListTags() {
        List<Tags> list = null;
        try {
            list = tagsService.list();
        } catch (Exception e) {
            return Result.fail("查询所有标签失败,系统错误");
        }
        if (list == null) {
            return Result.fail("查询所有标签失败,分类没有数据");
        }
        return Result.succ("查询所有标签成功", list);
    }
}
