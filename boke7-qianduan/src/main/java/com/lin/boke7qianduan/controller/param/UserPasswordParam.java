package com.lin.boke7qianduan.controller.param;

/**
 * @author lin
 */
public class UserPasswordParam {
    private String password;
    private String xpassword;

    @Override
    public String toString() {
        return "UserPasswordParam{" +
                "password='" + password + '\'' +
                ", xpassword='" + xpassword + '\'' +
                '}';
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getXpassword() {
        return xpassword;
    }

    public void setXpassword(String xpassword) {
        this.xpassword = xpassword;
    }
}
