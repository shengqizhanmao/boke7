package com.lin.boke7admin.service;

import com.lin.boke7admin.common.Result;
import com.lin.boke7admin.pojo.MUser;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author linSheng
 * @since 2022-09-20
 */
public interface MUserService extends IService<MUser> {

    Result addMUser(MUser mUser);

    Result updateMUser(MUser mUser);

    Result deleteMUser(Long id);

    Result upAvatar(MultipartFile file,Long id);
}
