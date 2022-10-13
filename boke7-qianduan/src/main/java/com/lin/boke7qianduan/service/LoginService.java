package com.lin.boke7qianduan.service;

import com.lin.boke7qianduan.common.Result;

/**
 * @author lin
 */
public interface LoginService {
    Result login(String username, String password);
    Result getEmailCode(String eamil);
    Result LoginEmail(String eamil,String code);
    Result logout(String token);
    Result register(String username, String password);
}
