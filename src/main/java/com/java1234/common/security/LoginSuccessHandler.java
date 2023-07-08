package com.java1234.common.security;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.java1234.entity.R;
import com.java1234.entity.SysMenu;
import com.java1234.entity.SysRole;
import com.java1234.entity.SysUser;
import com.java1234.service.SysMenuService;
import com.java1234.service.SysRoleService;
import com.java1234.service.SysUserService;
import com.java1234.util.JwtUtils;
import com.java1234.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 登录成功处理
 * 要根据用户名生成token，返回前端
 * @author Yiming
 */
//注册为component,因为后续要注入SecurityConfig内
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysMenuService sysMenuService;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json;charset=UTF-8");   //设置响应头
        ServletOutputStream outputStream = httpServletResponse.getOutputStream();   //设置返回的信息流

        String username=authentication.getName(); // 获取用户名
        sysUserService.update(new UpdateWrapper<SysUser>().set("login_date",new Date()).eq("username",username)); // 更新最后登录日期

        // 生成jwt token
        String token=JwtUtils.genJwtToken(username);

        SysUser currentUser = sysUserService.getByUserName(username);


        // 获取当前用户拥有的权限菜单（具有树形结构）
        // 获取角色
        List<SysRole> roleList = sysRoleService.list(new QueryWrapper<SysRole>().inSql("id", "select role_id from sys_user_role where user_id=" + currentUser.getId()));

        // 设置角色
        currentUser.setRoles(roleList.stream().map(SysRole::getName).collect(Collectors.joining(",")));

        StringBuffer permsStr=new StringBuffer();

        // 获取菜单权限
        Set<SysMenu> menuSet = new HashSet<SysMenu>();
        for(SysRole sysRole:roleList){
            List<SysMenu> sysMenuList = sysMenuService.list(new QueryWrapper<SysMenu>().inSql("id", "select menu_id from sys_role_menu where role_id=" + sysRole.getId()));
            for(SysMenu sysMenu:sysMenuList){
                menuSet.add(sysMenu);
                permsStr.append(sysMenu.getPerms()+",");
            }
        }

        String perms[]= StringUtils.tokenizeToStringArray(permsStr.toString(),",");


        //把menuset转为menulist
        List<SysMenu> sysMenuList = new ArrayList<>(menuSet); // 转成集合List

//        根据menulist的order_number字段进行排序
        sysMenuList.sort(Comparator.comparing(SysMenu::getOrderNum));  // 排序

//        把集合转为treemenu，转菜单树
        List<SysMenu> menuList = sysMenuService.buildTreeMenu(sysMenuList); // 构造菜单树


        //向前端展示必要数据
        outputStream.write(JSONUtil.toJsonStr(R.ok("登录成功").put("authorization",token).put("menuList",menuList).put("currentUser",currentUser).put("perms",perms)).getBytes("UTF-8"));
        outputStream.flush();
        outputStream.close();
    }



}
