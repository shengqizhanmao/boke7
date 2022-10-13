package com.lin.boke7qianduan.controller;

import com.lin.boke7qianduan.common.Result;
import com.lin.boke7qianduan.common.aop.CacheAnnotation;
import com.lin.boke7qianduan.pojo.Menu;
import com.lin.boke7qianduan.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 操作列表
 * <p>
 * 前端控制器
 * </p>
 *
 * @author linSheng
 * @since 2022-08-04
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    MenuService menuService;

    @CacheAnnotation(name = "menu", expire = 10 * 60 * 1000)
    @GetMapping("/getAll")
    public Result getAll() {
        List<Menu> list = menuService.list();
        if (list == null) {
            return Result.fail("查询列表失败");
        }
        return Result.succ("查询列表成功", list);
    }
}
