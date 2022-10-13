package com.lin.boke7qianduan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lin.boke7qianduan.mapper.TagsMapper;
import com.lin.boke7qianduan.pojo.Tags;
import com.lin.boke7qianduan.service.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class TagsServiceImpl extends ServiceImpl<TagsMapper, Tags> implements TagsService {

    @Autowired
    private TagsMapper tagsMapper;

    public List<Tags> getListByArticleId(Long articleId) {
        return tagsMapper.getListByArticleId(articleId);
    }
}
