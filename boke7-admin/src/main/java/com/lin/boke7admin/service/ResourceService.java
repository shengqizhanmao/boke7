package com.lin.boke7admin.service;

import com.lin.boke7admin.common.Result;
import com.lin.boke7admin.pojo.Resource;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 资源表 服务类
 * </p>
 *
 * @author linSheng
 * @since 2022-09-07
 */
public interface ResourceService extends IService<Resource> {

    List<Resource> getListResourceByUserId(String id);
    List<Resource> getListResourceByRoleId(String id);
    Result getListResourceVo();
    Result getListResourceByTypeResources();
    Result addResource(Resource resource);
    Result updateResource(Resource resource);
    Result deleteResourceById(String id);

}
