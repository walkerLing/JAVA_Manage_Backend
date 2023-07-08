package com.java1234.config;

import com.java1234.common.security.*;
import com.java1234.common.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * spring security配置
 * @author Yiming
 */
@Configuration
@EnableWebSecurity
//全局方法，方法置于之前
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    //注入
    @Autowired
    private LoginFailureHandler loginFailureHandler;

    @Autowired
    private LoginSuccessHandler loginSuccessHandler;

    @Autowired
    private CaptchaFilter captchaFilter;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Autowired
    private JwtLogoutSuccessHandler jwtLogoutSuccessHandler;

    @Autowired
    MyUserDetailServiceImpl myUserDetailService;

//    密码加密
    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

//    jwt认证的构造，需要加自定义配置，才能在默认的SecutityConfig配置之前加上jwt的配置
    @Bean
    JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter=new JwtAuthenticationFilter(authenticationManager());
        return jwtAuthenticationFilter;
    }

    // 请求白名单，定义拦截规则
    private static final String URL_WHITELIST[]={
            "/login",
            "/logout",
            "/captcha",
            "/password",
            "/image/**"
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 跨域支持 csrf攻击关闭
        http
        .cors()
        .and()
        .csrf()
        .disable()

        // 登录配置
        .formLogin()
                .successHandler(loginSuccessHandler)
                .failureHandler(loginFailureHandler)
        //登出配置
        .and()
                .logout()
                .logoutSuccessHandler(jwtLogoutSuccessHandler)


        // 禁用session
        .and()
            .sessionManagement()
                 .sessionCreationPolicy(SessionCreationPolicy.STATELESS)//无状态

        // 配置拦截规则
        .and()
                .authorizeRequests()
                        .antMatchers(URL_WHITELIST).permitAll()     //放行所有
                        .anyRequest().authenticated()      //其他的都要进行认证

        // 异常处理器
        .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)


        // 配置自定义jwt过滤器
        .and()
                .addFilter(jwtAuthenticationFilter())
                .addFilterBefore(captchaFilter, UsernamePasswordAuthenticationFilter.class)
        ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailService);//实现自定义实现
    }
}
