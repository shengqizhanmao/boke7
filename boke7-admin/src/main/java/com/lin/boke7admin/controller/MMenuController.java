package com.lin.boke7admin.controller;

import com.lin.boke7admin.common.Result;
import com.lin.boke7admin.pojo.MMenu;
import com.lin.boke7admin.pojo.Menu;
import com.lin.boke7admin.service.MMenuService;
import com.lin.boke7admin.service.MenuService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author lin
 */
@RestController
@RequestMapping("/mMenu")
public class MMenuController {
    @Autowired
    MMenuService mMenuService;
    //获取menu
    @RequiresAuthentication
    @GetMapping("/get")
    public Result get(){
        List<MMenu> list = mMenuService.list();
        return Result.succ("获取用户菜单",list);
    }
    //添加menu
    @RequiresPermissions("mMenu:add")
    @PostMapping("/add")
    public Result add(@RequestBody MMenu mMenu){
        boolean b = mMenuService.save(mMenu);
        if (b){
            return Result.succ("添加用户菜单成功");
        }
        return Result.fail("添加用户菜单失败");
    }
    //修改menu
    @RequiresPermissions("mMenu:update")
    @PostMapping("/update")
    public Result update(@RequestBody MMenu mMenu){
        boolean b = mMenuService.updateById(mMenu);
        if (b){
            return Result.succ("修改用户菜单成功");
        }
        return Result.fail("修改用户菜单失败");
    }
    //删除menu
    @RequiresPermissions("mMenu:delete")
    @PostMapping("/delete")
    public Result delete(@RequestBody MMenu mMenu){
        boolean b = mMenuService.removeById(mMenu.getId());
        if (b){
            return Result.succ("删除用户菜单成功");
        }
        return Result.fail("删除用户菜单失败");
    }
}
