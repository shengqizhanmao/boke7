package com.lin.boke7admin.service;

import com.lin.boke7admin.common.Result;
import com.lin.boke7admin.controller.Param.AddRoleAndResource;
import com.lin.boke7admin.pojo.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lin.boke7admin.pojo.RoleResource;

/**
 * <p>
 * 用户角色表 服务类
 * </p>
 *
 * @author linSheng
 * @since 2022-09-07
 */
public interface RoleService extends IService<Role> {

    Result getRoleAndResource();
    Result saveRole(Role role);
    Result deleteRoleById(String id);
    Result updateRole(Role role);
    Result addRoleAndResource(AddRoleAndResource addRoleAndResource);
    Result deleteRoleResource(RoleResource roleResource);


}
