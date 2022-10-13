package com.lin.boke7admin.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author lin
 */
@TableName("menu")
@ApiModel(value = "用户Menu对象", description = "")
public class MMenu implements Serializable {
        private static final long serialVersionUID = 1L;
        @ApiModelProperty("主键")
        @TableId(value = "id", type = IdType.ASSIGN_ID)
        private Long id;
        @ApiModelProperty("目录名称")
        private String title;
        @ApiModelProperty("路径")
        private String path;
        @ApiModelProperty("icon样式")
        private String icons;
        @ApiModelProperty("目录简介")
        private String effect;

    @Override
    public String toString() {
        return "MMenu{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", path='" + path + '\'' +
                ", icons='" + icons + '\'' +
                ", effect='" + effect + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getIcons() {
        return icons;
    }

    public void setIcons(String icons) {
        this.icons = icons;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }
}
