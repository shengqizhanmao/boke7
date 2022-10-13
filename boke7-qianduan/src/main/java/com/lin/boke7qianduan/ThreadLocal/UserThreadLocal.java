package com.lin.boke7qianduan.ThreadLocal;

import com.lin.boke7qianduan.pojo.User;

/**
 * @author lin
 */
public class UserThreadLocal {
    private UserThreadLocal() {
    }

    //线程变量隔离
    private static final ThreadLocal<User> LOCAL = new ThreadLocal<>();

    public static void put(User user) {
        LOCAL.set(user);
    }

    public static User get() {
        return LOCAL.get();
    }

    public static void remove() {
        LOCAL.remove();
    }
}
