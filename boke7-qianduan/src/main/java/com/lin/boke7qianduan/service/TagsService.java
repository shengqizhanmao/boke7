package com.lin.boke7qianduan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lin.boke7qianduan.pojo.Tags;

import java.util.List;

/**
 *
 */
public interface TagsService extends IService<Tags> {
    List<Tags> getListByArticleId(Long articleId);
}
