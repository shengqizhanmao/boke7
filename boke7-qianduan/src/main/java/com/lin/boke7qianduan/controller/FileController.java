package com.lin.boke7qianduan.controller;

import com.lin.boke7qianduan.service.MinioService;
import com.lin.boke7qianduan.utils.MinioUtils;
import io.minio.GetObjectResponse;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @author lin
 */
@RestController
@RequestMapping("/file")
public class FileController {
    @Autowired
    private MinioUtils minioUtils;

    // 上传，上传成功会返回文件名
    @PostMapping
    public String upload(MultipartFile file) throws Exception {
        //获取上传文件的文件名
        String fileName = file.getOriginalFilename();
        // 为了避免文件名重复，使用UUID重命名文件，将横杠去掉
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String newFileName = uuid + fileName;
        // 上传
        String s = minioUtils.putObject(file.getInputStream(), newFileName, file.getContentType());
        // 返回文件名
        String fileName2=s+"/"+newFileName;
        return fileName2;
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