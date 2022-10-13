package com.lin.boke7admin.controller.Param;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.lin.boke7admin.pojo.Role;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author lin
 */
public class AddMenu {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;
    @ApiModelProperty("目录名称")
    private String title;
    @ApiModelProperty("路径")
    private String path;
    @ApiModelProperty("icon样式")
    private String icon;
    @ApiModelProperty("目录简介")
    private String effect;
    private List<Role> listRole;

    @Override
    public String toString() {
        return "AddMenu{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", path='" + path + '\'' +
                ", icon='" + icon + '\'' +
                ", effect='" + effect + '\'' +
                ", listRole=" + listRole +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public List<Role> getListRole() {
        return listRole;
    }

    public void setListRole(List<Role> listRole) {
        this.listRole = listRole;
    }
}
