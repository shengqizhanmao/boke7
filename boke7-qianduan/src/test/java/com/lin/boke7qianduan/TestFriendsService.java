package com.lin.boke7qianduan;

import com.lin.boke7qianduan.pojo.Friends;
import com.lin.boke7qianduan.service.FriendsService;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;

/**
 * @author lin
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestFriendsService {
    public Long a;
    public Long b;
    @Before
    public void be(){
        this.a = System.currentTimeMillis();
    }
    @After
    public void af(){
        this.b = System.currentTimeMillis();
        this.b=(b-a);
        log.info("花了"+b+"毫秒");
    }
    @Resource(name = "friendsService")
    public FriendsService friendsService;
    @Test
    public void getListFriendsVoTest(){
        friendsService.getListFriendsVoByFormUserId(1L);
    }
    @Test
    public void getFriendsByFormUserIdAnToUserId(){
        List<Friends> friendsByFormUserIdAnToUserId = friendsService.getFriendsByFormUserIdAnToUserId(1L, 2L);
        List<Friends> friendsByFormUserIdAnToUserId2 = friendsService.getFriendsByFormUserIdAnToUserId(2L, 1L);
        friendsByFormUserIdAnToUserId.addAll(friendsByFormUserIdAnToUserId2);
        friendsByFormUserIdAnToUserId.sort(friendsService.getComparator());
        log.info(friendsByFormUserIdAnToUserId.toString());
    }
}
