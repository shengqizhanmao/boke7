package com.lin.boke7qianduan.controller.para;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author lin
 */
public class FriendsUserVo {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;
    private Long formUserId;
    private Long toUserId;
    @ApiModelProperty("1:好友,-1:非好友,0:申请好友中")
    private String status;
    private String toUserName;
    private String toNickName;
    private String toAvatar;

    @Override
    public String toString() {
        return "FriendsUserVo{" +
                "id=" + id +
                ", formUserId=" + formUserId +
                ", toUserId=" + toUserId +
                ", status='" + status + '\'' +
                ", toUserName='" + toUserName + '\'' +
                ", toNickName='" + toNickName + '\'' +
                ", toAvatar='" + toAvatar + '\'' +
                '}';
    }

    public String getToAvatar() {
        return toAvatar;
    }

    public void setToAvatar(String toAvatar) {
        this.toAvatar = toAvatar;
    }

    public String getToNickName() {
        return toNickName;
    }

    public void setToNickName(String toNickName) {
        this.toNickName = toNickName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }
}
