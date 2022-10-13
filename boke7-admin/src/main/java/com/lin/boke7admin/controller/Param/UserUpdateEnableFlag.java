package com.lin.boke7admin.controller.Param;

/**
 * @author lin
 */
public class UserUpdateEnableFlag {
    private String id;
    private String enableFlag;

    @Override
    public String toString() {
        return "UserUpdateEnableFlag{" +
                "id='" + id + '\'' +
                ", EnableFlag='" + enableFlag + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEnableFlag() {
        return enableFlag;
    }

    public void setEnableFlag(String enableFlag) {
        this.enableFlag = enableFlag;
    }
}
