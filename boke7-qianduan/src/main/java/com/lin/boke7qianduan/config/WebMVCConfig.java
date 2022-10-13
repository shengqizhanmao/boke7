package com.lin.boke7qianduan.config;

import com.lin.boke7qianduan.handler.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author lin
 */
@Configuration
public class WebMVCConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    //跨域
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8080", "http://localhost:3006", "http://localhost:3007")
                .allowedOriginPatterns("http://localhost:8080", "http://localhost:3006", "http://localhost:3007")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true)
                .maxAge(3600)
                .allowedHeaders("*");
    }

    /***
     * 自定义资源映射
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //swagger进行配置
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");//ui地址
        registry.addResourceHandler("/doc.html").addResourceLocations("classpath:/META-INF/resources/");//增强版ui地址
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    /***
     * 拦截
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/articles/createArticle")
                .addPathPatterns("/articles/updateArticle")             //拦截全部
                .addPathPatterns("/articles/deleteArticle/{id}")
                .addPathPatterns("/articles/getTotalByUserId")
                .addPathPatterns("/articles/getArticleByUserId/{page}/{pageSize}")
                .addPathPatterns("/user/getUser")
                .addPathPatterns("/user/updateUser")
                .addPathPatterns("/user/setUserAvatar")
                .addPathPatterns("/user/getUserAvatar")
                .addPathPatterns("/user/setUserPassword")
                .addPathPatterns("/category/addCategory")
                .addPathPatterns("/comment/create")
                .addPathPatterns("/upload");
//                .excludePathPatterns("/articles/**") //获取文章
//                .excludePathPatterns("/login");         //登录
//                .excludePathPatterns("/register")       //注册
//                .excludePathPatterns("/menu/**");
    }


}
