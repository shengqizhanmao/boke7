package com.lin.boke7admin.controller;

import com.lin.boke7admin.common.Result;

import com.lin.boke7admin.pojo.Menu;
import com.lin.boke7admin.service.MenuService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author linSheng
 * @since 2022-09-18
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    MenuService menuService;
    //获取menu
    @RequiresAuthentication
    @GetMapping("/get")
    public Result get(@RequestHeader("Authorization") String token){
    return menuService.getMenuByUserId(token);
    }
    //添加menu
    @RequiresPermissions("menu:add")
    @PostMapping("/add")
    public Result add(@RequestBody Menu menu){
        boolean b = menuService.save(menu);
        if (b){
            return Result.succ("添加菜单成功");
        }
        return Result.fail("添加菜单失败");
    }
    //修改menu
    @RequiresPermissions("menu:update")
    @PostMapping("/update")
    public Result update(@RequestBody Menu menu){
        boolean b = menuService.updateById(menu);
        if (b){
            return Result.succ("修改菜单成功");
        }
        return Result.fail("修改菜单失败");
    }
    //删除menu
    @RequiresPermissions("menu:delete")
    @PostMapping("/delete")
    public Result delete(@RequestBody Menu menu){
        boolean b = menuService.removeById(menu.getId());
        if (b){
            return Result.succ("删除菜单成功");
        }
        return Result.fail("删除菜单失败");
    }
}
