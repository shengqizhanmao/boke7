package com.lin.boke7qianduan;

import com.lin.boke7qianduan.utils.SendEmailUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


import javax.annotation.Resource;
import java.io.File;

/**
 * @author lin
 */
@SpringBootTest
public class EmailTest {
    @Resource
    private SendEmailUtil emailUtil;

    @Test
    public void sendStringEmail() {
        // 测试文本邮件发送（无附件）
        String to = "1149566912@qq.com"; // 接收邮箱地址
        String title = "文本邮件发送测试";
        String content = "文本邮件发送测试";
        emailUtil.sendMessage(to, title, content);
    }
    @Test
    public void sendFileEmail() {
        // 测试单个附件邮件发送
        String to = "1149566912@qq.com"; // 接收邮箱地址
        String title = "温度信息附件邮件发送测试";
        String content = "温度信息附件邮件发送测试";
        File file = new File("G:\\2.txt");
        emailUtil.sendMessageCarryFile(to, title, content, file);
    }
    @Test
    public void sendFilesEmail() {
        // 测试多个附件邮件发送
        String to = "1149566912@qq.com"; // 接收邮箱地址
        String title = "多个温度信息附件邮件发送测试";
        String content = "多个温度信息附件邮件发送测试";
        File[] files = new File[2];
        files[0] = new File("G:\\2.txt");
        files[1] = new File("G:\\3.txt");
        emailUtil.sendMessageCarryFiles(to, title, content, files);
    }
}
