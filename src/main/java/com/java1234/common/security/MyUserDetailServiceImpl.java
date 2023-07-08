package com.java1234.common.security;

import com.java1234.common.exception.UserCountLockException;
import com.java1234.entity.SysUser;
import com.java1234.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * 用户登录查库实现，不再使用默认密码
 * 自定义UserDetails
 * @author Yiming
 */
@Service
public class MyUserDetailServiceImpl implements UserDetailsService {

    //因为要查库，所以用到了用户的sysUserService的实现
    @Autowired
    SysUserService sysUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser=sysUserService.getByUserName(username);//service层实现方法
        if(sysUser==null){
            throw new UsernameNotFoundException("用户名不存在！");
        }else if("1".equals(sysUser.getStatus())){  //数据库层账号被禁用设为1
            throw new UserCountLockException("该用户账号已被封禁，具体联系管理员！");
        }
        return new User(sysUser.getUsername(),sysUser.getPassword(),getUserAuthority(sysUser.getId()));
    }

    /**
     * 获取用户权限信息 包括角色 菜单权限信息
     * @param userId
     * @return 传入逗号隔开的字符串可以返回权限集合
     */
    public List<GrantedAuthority> getUserAuthority(Long userId){
        System.out.println("xxxx");
        //后面为权限编码
        // 格式ROLE_admin,ROLE_common,system:user:resetPwd,system:role:delete,system:user:list,system:menu:query,system:menu:list,system:menu:add,system:user:delete,system:role:list,system:role:menu,system:user:edit,system:user:query,system:role:edit,system:user:add,system:user:role,system:menu:delete,system:role:add,system:role:query,system:menu:edit
        //传入userId得到返回的特定格式字符串
        String authority=sysUserService.getUserAuthorityInfo(userId);
//        打印验证字符串是否符合要求
        System.out.println("authority="+authority);
        return AuthorityUtils.commaSeparatedStringToAuthorityList(authority);
    }
}
