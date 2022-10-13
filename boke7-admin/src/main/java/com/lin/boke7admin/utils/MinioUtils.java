package com.lin.boke7admin.utils;
import io.minio.BucketExistsArgs;
import io.minio.GetObjectArgs;
import io.minio.GetObjectResponse;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;

/**
 * @author lin
 */
@Slf4j
@Component
public class MinioUtils {
    private final String bucket;
    private final MinioClient minioClient;
    private final String url;
    public MinioUtils( @Value("${minio.url}") String url,
                       @Value("${minio.access}") String access,
                       @Value("${minio.secret}") String secret,
                       @Value("${minio.bucket}") String bucket) throws Exception {
        this.url=url;
        this.bucket = bucket;
        minioClient = MinioClient.builder()
                .endpoint(url)
                .credentials(access, secret)
                .build();
        // 初始化Bucket
        initBucket();
    }

    private void initBucket() throws Exception {
        // 应用启动时检测Bucket是否存在
        boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
        // 如果Bucket不存在，则创建Bucket
        if (!found) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
            log.info("成功创建 Bucket [{}]", bucket);
        }
    }

    /**
     * 上传文件
     * @param is 输入流
     * @param object 对象（文件）名
     * @param contentType 文件类型
     */
    public String putObject(InputStream is, String object, String contentType) throws Exception {
        long start = System.currentTimeMillis();
        minioClient.putObject(PutObjectArgs.builder()
                .bucket(bucket)
                .object(object)
                .contentType(contentType)
                .stream(is, -1, 1024 * 1024 * 10) // 不得小于 5 Mib
                .build());
//        log.info("成功上传文件至云端 [{}]，耗时 [{} ms]", object, System.currentTimeMillis() - start);
        String url2=url+"/"+bucket;
        return url2;
    }

    /**
     * 获取文件流
     * @param object 对象（文件）名
     * @return 文件流
     */
    public GetObjectResponse getObject(String object) throws Exception {
        long start = System.currentTimeMillis();
        GetObjectResponse response = minioClient.getObject(GetObjectArgs.builder()
                .bucket(bucket)
                .object(object)
                .build());
//        log.info("成功获取 Object [{}]，耗时 [{} ms]", object, System.currentTimeMillis() - start);
        return response;
    }

    /**
     * 删除对象（文件）
     * @param object 对象（文件名）
     */
    public void removeObject(String object) throws Exception {
        minioClient.removeObject(RemoveObjectArgs.builder()
                .bucket(bucket)
                .object(object)
                .build());
//        log.info("成功删除 Object [{}]", object);
    }
}
