package com.java1234.common.security;

import com.java1234.common.constant.JwtConstant;
import com.java1234.entity.CheckResult;
import com.java1234.entity.SysUser;
import com.java1234.service.SysUserService;
import com.java1234.util.JwtUtils;
import com.java1234.util.StringUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * jwt自定义认证
 * 由于做了前后端分离配置，通过jwt生成token，所以我们要搞一个jwt自定义认证过滤器，来实现jwt token认证；
 * @author Yiming
 */
public class JwtAuthenticationFilter  extends BasicAuthenticationFilter {

    @Autowired
    private MyUserDetailServiceImpl myUserDetailService;

    @Autowired
    private SysUserService sysUserService;

    // 请求白名单
    private static final String URL_WHITELIST[]={
            "/captcha"
    };

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    //通过request获取里面的token，再通过JwtUtils验证token是否存在、过期、错误
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = request.getHeader("token");
        System.out.println("request.getRequestURI()"+request.getRequestURI());//有些请求需要放行，让后面的Filter做处理
        //若token为空，则放行，让后面的Filter处理，或者包含在白名单中的，也放行（Filter栈）
        if(StringUtil.isEmpty(token) || new ArrayList<String>(Arrays.asList(URL_WHITELIST)).contains(request.getRequestURI())){
            chain.doFilter(request,response);
            return;
        }
        //返回jwt验证判断结果
        CheckResult checkResult = JwtUtils.validateJWT(token);
        //如果验证失败
        if(!checkResult.isSuccess()){
            switch (checkResult.getErrCode()){
                case JwtConstant.JWT_ERRCODE_NULL: throw new JwtException("Token不存在");
                case JwtConstant.JWT_ERRCODE_FAIL: throw new JwtException("Token验证不通过");
                case JwtConstant.JWT_ERRCODE_EXPIRE: throw new JwtException("Token过期");
            }
        }

        //认证成功后，获取主体信息claims
        Claims claims = JwtUtils.parseJWT(token);
        String username = claims.getSubject();
        //根据用户名获取用户实体
        SysUser sysUser = sysUserService.getByUserName(username);
//        根据传入的用户id查询所有的角色和权限信息
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(username,null,myUserDetailService.getUserAuthority(sysUser.getId()));
//        获取到的实体放进security上下文中，鉴权
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//            继续将请求丢进过滤器链
        chain.doFilter(request,response);

    }
}
