package com.lin.boke7qianduan.service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lin.boke7qianduan.pojo.Friends;
import com.lin.boke7qianduan.pojo.User;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(value = "/websocket/{username}/{formUsername}")
@Component
public class WebSocketService {
    public static UserService userService;
    public static FriendsService friendsService;
    private static Map<String,Session> map = new HashMap<String, Session>();
    //用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<WebSocketService> webSocketSet = new CopyOnWriteArraySet<WebSocketService>();
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    private String username;
    private String formUsername;
    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username,@PathParam("formUsername") String formUsername) throws IOException {
        this.session = session;
//        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
//        userLambdaQueryWrapper.eq(User::getUsername,username);
//        User one = userService.getOne(userLambdaQueryWrapper);
//        LambdaQueryWrapper<User> userLambdaQueryWrapper2 = new LambdaQueryWrapper<>();
//        userLambdaQueryWrapper2.eq(User::getUsername,formUsername);
//        User one2 = userService.getOne(userLambdaQueryWrapper2);
        this.username =username;
        map.put(username, session);
        webSocketSet.add(this);     //加入set中
        System.out.println("session:"+session);
        System.out.println("map:"+map);
//        List<Friends> friendsByFormUserIdAnToUserId = friendsService.getFriendsByFormUserIdAnToUserId(one.getId(), one2.getId());
//        if (friendsByFormUserIdAnToUserId.size()!=0){
//            List<Friends> friendsByFormUserIdAnToUserId1 = friendsService.getFriendsByFormUserIdAnToUserId(one2.getId(), one.getId());
//            if (friendsByFormUserIdAnToUserId1.size()!=0){
//                friendsByFormUserIdAnToUserId.addAll(friendsByFormUserIdAnToUserId1);
//            }
//            friendsByFormUserIdAnToUserId.sort(friendsService.getComparator());
            System.out.println("有新连接加入！"+username+"加入聊天室,当前在线人数为" + webSocketSet.size());
//            for (Friends friend:friendsByFormUserIdAnToUserId) {
//                synchronized(this.session){
//                    this.session.getBasicRemote().sendText(friend.getToUserId()+":"+friend.getMsg());
//                }
//            }
//        }
        this.session.getAsyncRemote().sendText("当前在线人数为："+webSocketSet.size());
    }
    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);  //从set中删除
        System.out.println("有一连接关闭！当前在线人数为" + webSocketSet.size());
    }
    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(String message, Session session,@PathParam("username") String username,@PathParam("formUsername") String formUsername) {
        System.out.println("来自客户端的消息-->"+username+": " + message);
        //保存
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getUsername,username);
        User one = userService.getOne(userLambdaQueryWrapper);
        LambdaQueryWrapper<User> userLambdaQueryWrapper2 = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper2.eq(User::getUsername,formUsername);
        User one2 = userService.getOne(userLambdaQueryWrapper2);

        Friends friends = new Friends();
        friends.setFormUserId(one.getId());
        friends.setToUserId(one2.getId());
        friends.setMsg(message);
        friends.setCreated(new Date(System.currentTimeMillis()));
        friendsService.save(friends);
        Session formSession = map.get(username);
        Session toSession = map.get(formUsername);
        System.out.println(friends);
        System.out.println("session:"+session);
        System.out.println("map:"+map);
        formSession.getAsyncRemote().sendText(JSON.toJSONString(friends));
        toSession.getAsyncRemote().sendText(JSON.toJSONString(friends));
        //群发消息
//        broadcast(username+": "+message);
    }
    /**
     * 发生错误时调用
     *
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }
    /**
     * 群发自定义消息
     * */
    public  void broadcast(String message){
        for (WebSocketService item : webSocketSet) {
            //同步异步说明参考：http://blog.csdn.net/who_is_xiaoming/article/details/53287691
            //            //this.session.getBasicRemote().sendText(message);
            item.session.getAsyncRemote().sendText(message);//异步发送消息.
        }
    }
}
