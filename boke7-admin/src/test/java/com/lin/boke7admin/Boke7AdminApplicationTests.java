package com.lin.boke7admin;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.lin.boke7admin.mapper.MUserMapper;
import com.lin.boke7admin.mapper.UserMapper;
import com.lin.boke7admin.pojo.MUser;
import com.lin.boke7admin.utils.MinioUtils;
import io.minio.GetObjectResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

@SpringBootTest
class Boke7AdminApplicationTests {
    @Autowired
    private MinioUtils minioUtils;
    @Test
    void contextLoads() {
    }
    /**
     * 最简单的写
     * <p>2. 直接写即可
     */
    @Autowired
   UserMapper userMapper;
    @Autowired
    MUserMapper mUserMapper;
    @Test
    public void simpleWrite() {
        // 写
        File file = new File("./src/test/java/com/lin/boke7admin/xlsx/MUser3.xlsx");
        List<MUser> users = mUserMapper.selectList(null);
        try {
            // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
            // 如果这里想使用03 则 传入excelType参数即可
            EasyExcel.write(file.getAbsolutePath(), MUser.class).sheet("MUser3").doWrite(users);
            InputStream is = new FileInputStream(file);
            minioUtils.putObject(is, "MUser3.xlsx", ".xlsx");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void simpleRead() {
        String fileName = System.getProperty("user.dir")+"/src/test/java/com/lin/boke7admin/xlsx/MUser3.xlsx";
        System.out.println(fileName);
        EasyExcel.read(fileName, MUser.class, new MUserListener()).sheet().doRead();
    }
    @Test
    public void simpleRead2() throws Exception {
        // 获取文件流
        GetObjectResponse objectResponse = minioUtils.getObject("MUser3.xlsx");
        EasyExcel.read(objectResponse, MUser.class, new MUserListener()).excelType(ExcelTypeEnum.XLSX).sheet().doRead();
    }
}
