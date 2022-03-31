package com.example.studentauth;

import com.example.studentauth.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @program: WebMvcConfig
 * @description:
 * @author: hanLinQi
 * @create: 2022-03-29 18:19
 **/
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        WebMvcConfigurer.super.addInterceptors(registry);
        this.loginInterceptor(registry);
    }

    private void loginInterceptor(InterceptorRegistry registry){
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(//释放登陆接口
                        "/login/**",
                        "/static/**"
                );
    }
}
