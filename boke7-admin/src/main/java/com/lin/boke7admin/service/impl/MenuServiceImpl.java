package com.lin.boke7admin.service.impl;

import com.lin.boke7admin.common.Result;
import com.lin.boke7admin.pojo.Menu;
import com.lin.boke7admin.mapper.MenuMapper;
import com.lin.boke7admin.pojo.User;
import com.lin.boke7admin.pojo.vo.UserTokenVo;
import com.lin.boke7admin.service.MenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lin.boke7admin.service.UserMenuService;
import com.lin.boke7admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author linSheng
 * @since 2022-09-18
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    MenuMapper menuMapper;
    @Autowired
    UserService userService;
    public Result getMenuByUserId(String token){
        UserTokenVo userByToken = userService.findUserByToken(token);
        String userId = userByToken.getId();
        List<Menu> listMenuByUserId = menuMapper.getListMenuByUserId(userId);
        return Result.succ("查询菜单成功",listMenuByUserId);
    }
}
