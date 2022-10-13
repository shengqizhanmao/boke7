package com.lin.boke7admin.service;

import com.lin.boke7admin.common.Result;
import com.lin.boke7admin.pojo.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author linSheng
 * @since 2022-09-18
 */
public interface MenuService extends IService<Menu> {

    Result getMenuByUserId(String userId);
}
