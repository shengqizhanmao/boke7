package com.lin.boke7qianduan;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;

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
