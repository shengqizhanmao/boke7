package com.lin.boke7qianduan.controller.para;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lin.boke7qianduan.pojo.Friends;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @author lin
 */
public class ListFriendsVo {
    private Long formUserId;
    private Long toUserId;
    private String formUsername;
    private String formNickname;
    private String toUsername;
    private String toNickname;
    private List<Friends> ListFriends;

    @Override
    public String toString() {
        return "ListFriendsVo{" +
                "formUserId=" + formUserId +
                ", toUserId=" + toUserId +
                ", formUsername='" + formUsername + '\'' +
                ", formNickname='" + formNickname + '\'' +
                ", toUsername='" + toUsername + '\'' +
                ", toNickname='" + toNickname + '\'' +
                ", ListFriends=" + ListFriends +
                '}';
    }

    public Long getFormUserId() {
        return formUserId;
    }

    public void setFormUserId(Long formUserId) {
        this.formUserId = formUserId;
    }

    public Long getToUserId() {
        return toUserId;
    }

    public void setToUserId(Long toUserId) {
        this.toUserId = toUserId;
    }

    public String getFormUsername() {
        return formUsername;
    }

    public void setFormUsername(String formUsername) {
        this.formUsername = formUsername;
    }

    public String getFormNickname() {
        return formNickname;
    }

    public void setFormNickname(String formNickname) {
        this.formNickname = formNickname;
    }

    public String getToUsername() {
        return toUsername;
    }

    public void setToUsername(String toUsername) {
        this.toUsername = toUsername;
    }

    public String getToNickname() {
        return toNickname;
    }

    public void setToNickname(String toNickname) {
        this.toNickname = toNickname;
    }

    public List<Friends> getListFriends() {
        return ListFriends;
    }

    public void setListFriends(List<Friends> listFriends) {
        ListFriends = listFriends;
    }
}
