package com.lin.boke7qianduan.controller.param;

/**
 * @author lin
 */
public class GetEmailCode {
    String email;
    String code;

    @Override
    public String toString() {
        return "GetEmailCode{" +
                "email='" + email + '\'' +
                ", code='" + code + '\'' +
                '}';
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
