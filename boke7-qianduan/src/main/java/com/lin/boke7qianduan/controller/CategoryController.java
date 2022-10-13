package com.lin.boke7qianduan.controller;

import com.lin.boke7qianduan.common.Result;
import com.lin.boke7qianduan.controller.param.AddCategoryParam;
import com.lin.boke7qianduan.pojo.Category;
import com.lin.boke7qianduan.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author linSheng
 * @since 2022-08-09
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/getListCategory")
    public Result getListCategories() {
        List<Category> list = null;
        try {
            list = categoryService.list();
        } catch (Exception e) {
            return Result.fail("查询所有分类失败,系统错误");
        }
        if (list == null) {
            return Result.fail("查询所有分类失败,分类没有数据");
        }
        return Result.succ("查询所有分类成功", list);
    }

    @PostMapping("/addCategory")
    public Result addCategory(@RequestBody AddCategoryParam addCategoryParam) {
        Category category = new Category();
        category.setCategoryName(addCategoryParam.getCategoryName());
        category.setDescription(addCategoryParam.getDescription());
        boolean save = categoryService.save(category);
        if (save) {
            return Result.succ("添加分类成功");
        }
        return Result.fail("添加分类失败");
    }
}
