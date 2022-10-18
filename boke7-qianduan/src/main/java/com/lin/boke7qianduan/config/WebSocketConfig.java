package com.lin.boke7qianduan.config;


import com.lin.boke7qianduan.service.FriendsService;
import com.lin.boke7qianduan.service.UserService;
import com.lin.boke7qianduan.service.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author lin
 */

@Configuration
public class WebSocketConfig  {
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    @Autowired
    public void setUserService( UserService userService){
        WebSocketService.userService=userService;
    }
    @Autowired
    public void setFriendsService( FriendsService friendsService){
        WebSocketService.friendsService=friendsService;
    }


}