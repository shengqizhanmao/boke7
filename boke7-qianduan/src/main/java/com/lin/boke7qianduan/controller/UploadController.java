package com.lin.boke7qianduan.controller;

import com.lin.boke7qianduan.common.Result;
import com.lin.boke7qianduan.utils.MinioUtils;
import com.lin.boke7qianduan.utils.QiniuUtils;
import io.minio.GetObjectResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * @author lin
 */
@Slf4j
@RestController
@RequestMapping("/upload")
public class UploadController {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private QiniuUtils qiniuUtils;
    @Autowired
    private MinioUtils minioUtils;

    @PostMapping
    public Result upload(@RequestParam("image") MultipartFile file) {
//        //获取上传文件的文件名
//        String fileName = file.getOriginalFilename();
//        //uuid获取32位随机数列,
//        String uuid = UUID.randomUUID().toString();
//        String newFileName = uuid + fileName;
//        //进行上传七牛云，云服务器，按量付费，
//        boolean upload = qiniuUtils.upload(file, newFileName);
//        if (upload) {
//            return Result.succ("上传图片成功", qiniuUtils.url + newFileName);
//        }
//        return Result.fail(20001, "上传图片失败");
        //获取上传文件的文件名
        String fileName = file.getOriginalFilename();
        // 为了避免文件名重复，使用UUID重命名文件，将横杠去掉
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String newFileName = uuid + fileName;
        // 上传
        try{
            String s = minioUtils.putObject(file.getInputStream(), newFileName, file.getContentType());
            // 返回文件名
            String fileName2=s+"/"+newFileName;
            return Result.succ("上传图片成功",fileName2);
        }catch (Exception e){
            log.error("上传图片失败，原因是"+e);
            return Result.fail(500,"上传图片失败");
        }
    }
    // 根据文件名下载文件
    @GetMapping("{fileName}")
    public void download(HttpServletRequest request, HttpServletResponse response, @PathVariable("fileName") String fileName) throws Exception  {
        // 设置响应类型
        response.setCharacterEncoding(request.getCharacterEncoding());
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        // 获取文件流
        GetObjectResponse objectResponse = minioUtils.getObject(fileName);
        // 将文件流输出到响应流
        IOUtils.copy(objectResponse, response.getOutputStream());
        // 结束
        response.flushBuffer();
        objectResponse.close();
    }

    // 根据文件名删除文件
    @DeleteMapping("{fileName}")
    public String remove(@PathVariable("fileName") String fileName) throws Exception  {
        minioUtils.removeObject(fileName);
        return "success";
    }
}
