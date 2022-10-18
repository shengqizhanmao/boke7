package com.lin.boke7qianduan;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Random;


@SpringBootTest(classes = ApplicationContext.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@Slf4j
class Boke7QianduanApplicationTests {

    @Test
    void contextLoads() {
    }
    @Test
    void text() {
        String checkCode = String.valueOf(new Random().nextInt(999999));
        System.out.println(checkCode);
    }

}
