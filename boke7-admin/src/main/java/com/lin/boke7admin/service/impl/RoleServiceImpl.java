package com.lin.boke7admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.lin.boke7admin.common.Result;
import com.lin.boke7admin.controller.Param.AddRoleAndResource;
import com.lin.boke7admin.pojo.Resource;
import com.lin.boke7admin.pojo.Role;
import com.lin.boke7admin.mapper.RoleMapper;
import com.lin.boke7admin.pojo.RoleResource;
import com.lin.boke7admin.pojo.vo.RoleAndResourceVo;
import com.lin.boke7admin.service.ResourceService;
import com.lin.boke7admin.service.RoleResourceService;
import com.lin.boke7admin.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 用户角色表 服务实现类
 * </p>
 *
 * @author linSheng
 * @since 2022-09-07
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    ResourceService resourceService;

    @Autowired
    RoleResourceService roleResourceService;
    @Override
    public Result getRoleAndResource() {
        LambdaQueryWrapper<Role> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByAsc(Role::getSortNo);
        List<Role> listRole = roleMapper.selectList(lambdaQueryWrapper);
        List<RoleAndResourceVo> roleAndResourceVos = ListCopy(listRole);
        return Result.succ("查询角色成功",roleAndResourceVos);
    }

    @Override
    public Result saveRole(Role role) {
        role.setEnableFlag("1");
        if (StringUtils.isEmpty(role.getRoleName())){
            return Result.fail("添加失败,角色名称不能为空");
        }
        try{
            int insert = roleMapper.insert(role);
            if (insert==0){
                return Result.fail("添加失败");
            }
            return Result.succ("添加成功");
        }catch (Exception e){
            log.error("添加角色失败,原因是"+e.toString());
            return Result.fail("添加失败");
        }
    }

    @Override
    public Result deleteRoleById(String id) {
        if (id==null){
            return Result.fail("id不能为空");
        }
        try{
            int i = roleMapper.deleteById(id);
            if (i==0){
                return Result.fail("删除角色失败,该角色不存在");
            }
        }catch (Exception e){
            log.error("删除角色失败,原因是"+e);
            return Result.fail("删除失败");
        }
        return Result.succ("删除角色成功");
    }

    @Override
    public Result updateRole(Role role) {
        if (StringUtils.isEmpty(role.getId())){
            return Result.fail("id参数不能为空");
        }
        try{
            LambdaUpdateWrapper<Role> lambdaUpdateWrapper=new LambdaUpdateWrapper<>();
            lambdaUpdateWrapper.eq(Role::getId,role.getId())
                    .set(Role::getRoleName,role.getRoleName())
                    .set(Role::getDescription,role.getDescription())
                    .set(Role::getSortNo,role.getSortNo())
                    .set(Role::getLabel,role.getLabel());
            int update = roleMapper.update(role, lambdaUpdateWrapper);
            if(update==0){
                return Result.fail("修改角色失败");
            }
            return Result.succ("修改角色成功");
        }catch (Exception e){
            log.error("角色修改失败,原因:"+e);
            return Result.fail("修改失败");
        }
    }

    @Transactional
    @Override
    public Result addRoleAndResource(AddRoleAndResource addRoleAndResource) {
        List<String> listResourceId = addRoleAndResource.getListResourceId();
        String roleId = addRoleAndResource.getRoleId();
        if (StringUtils.isEmpty(roleId)) {
            return Result.fail("角色id参数不能为空");
        }
        for (String resourceId : listResourceId) {
            if (StringUtils.isEmpty(resourceId)) {
                return Result.fail("资源id参数不能为空");
            }
            RoleResource roleResource = new RoleResource();
            roleResource.setRoleId(roleId);
            roleResource.setResourceId(resourceId);
            roleResource.setEnableFlag("1");
            LambdaQueryWrapper<RoleResource> roleResourceLambdaQueryWrapper=new LambdaQueryWrapper<>();
            roleResourceLambdaQueryWrapper.eq(RoleResource::getRoleId,roleId);
            roleResourceLambdaQueryWrapper.eq(RoleResource::getResourceId,resourceId);
            try{
                RoleResource one = roleResourceService.getOne(roleResourceLambdaQueryWrapper);
                if (one!=null){
                    Result.fail("权限已经存在");
                }
            }catch (Exception e){
                log.error(e.toString());
                Result.fail("权限已经存在");
            }
            try {
                roleResourceService.save(roleResource);
            } catch (Exception e) {
                e.printStackTrace();
                Result.fail("添加权限失败");
            }
        }
        return Result.succ("添加权限成功");
    }

    @Override
    public Result deleteRoleResource(RoleResource roleResource) {
        String roleId = roleResource.getRoleId();
        String resourceId = roleResource.getResourceId();
        if (StringUtils.isEmpty(resourceId)) {
            return Result.fail("资源id参数不能为空");
        }
        if (StringUtils.isEmpty(roleId)) {
            return Result.fail("角色id参数不能为空");
        }
        LambdaUpdateWrapper<RoleResource> lambdaQueryWrapper=new LambdaUpdateWrapper<>();
        lambdaQueryWrapper.eq(RoleResource::getRoleId,roleId);
        lambdaQueryWrapper.eq(RoleResource::getResourceId,resourceId);
        boolean remove = roleResourceService.remove(lambdaQueryWrapper);
        if (remove){
            return Result.succ("权限删除成功");
        }
        return Result.fail("权限删除失败");
    }

    //Method
    private RoleAndResourceVo Copy(Role role){
        RoleAndResourceVo roleAndResourceVo = new RoleAndResourceVo();
        BeanUtils.copyProperties(role,roleAndResourceVo);
        if(role.getEnableFlag().equals("1")){
            roleAndResourceVo.setEnableFlag(true);
        }else{
            roleAndResourceVo.setEnableFlag(false);
        }
        List<Resource> listResourceByRoleId = resourceService.getListResourceByRoleId(role.getId());
        roleAndResourceVo.setListResourceName(listResourceByRoleId);
        return roleAndResourceVo;
    }
    private List<RoleAndResourceVo> ListCopy(List<Role> roles){
        List<RoleAndResourceVo> roleAndResourceVos = new ArrayList<>();
        for(Role role:roles){
            RoleAndResourceVo copy = Copy(role);
            roleAndResourceVos.add(copy);
        }
        return roleAndResourceVos;
    }
}
