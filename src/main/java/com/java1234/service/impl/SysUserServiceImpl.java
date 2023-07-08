package com.java1234.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java1234.common.constant.Constant;
import com.java1234.entity.SysMenu;
import com.java1234.entity.SysRole;
import com.java1234.entity.SysUser;
import com.java1234.mapper.SysMenuMapper;
import com.java1234.mapper.SysRoleMapper;
import com.java1234.service.SysUserService;
import com.java1234.mapper.SysUserMapper;
import com.java1234.util.RedisUtil;
import com.java1234.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
* @author Yiming
* @description 针对表【sys_user】的数据库操作Service实现
*/
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser>
    implements SysUserService{

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public SysUser getByUserName(String username) {
        return getOne(new QueryWrapper<SysUser>().eq("username",username));//数据表中username列
    }

    @Override
    public String getUserAuthorityInfo(Long userId) {
        //最后使用字符串拼接（前面拼接角色，后面拼接权限）
        StringBuffer authority=new StringBuffer();
        //根据用户id获取所有的角色信息集合
        //通过用户角色关联表获得用户所有的角色id，然后通过角色id获取所有的角色信息
        if(redisUtil.hasKey(Constant.AUTHORITY_KEY+userId)){
            System.out.println("有缓存");
            authority.append(redisUtil.get(Constant.AUTHORITY_KEY,String.valueOf(userId)));
        }else{
            System.out.println("没缓存");
            //得到rolelist，获取所有的角色权限
            List<SysRole> roleList = sysRoleMapper.selectList(new QueryWrapper<SysRole>().inSql("id", "select role_id from sys_user_role where user_id=" + userId));

            //大于零才拼接，才说明有角色权限
            if(roleList.size()>0){
                //通过stream流拼接得到规定格式字符串，获取的同时做字符串的拼接
                String roleCodeStrs=roleList.stream().map(r->"ROLE_"+r.getCode()).collect(Collectors.joining(","));
                authority.append(roleCodeStrs);
            }

            // 遍历所有角色，获取所有的菜单权限，得到sysMenuList，且不能重复。
            Set<String> menuCodeSet = new HashSet<String>();
            //遍历角色，通过角色获取权限
            for(SysRole sysRole:roleList){
                List<SysMenu> sysMenuList = sysMenuMapper.selectList(new QueryWrapper<SysMenu>().inSql("id", "select menu_id from sys_role_menu where role_id=" + sysRole.getId()));
                for(SysMenu sysMenu:sysMenuList){
                    //获取菜单权限编码,perms
                    String perms=sysMenu.getPerms();
                    //有些菜单权限编码为空，需要进行非空判断
                    if(StringUtil.isNotEmpty(perms)){
                        //自动去重
                        menuCodeSet.add(perms);
                    }
                }
            }
            //依然要除去没有权限的情况
            if(menuCodeSet.size()>0){
                //拼接,并且把权限编码搞到authority中
                authority.append(",");
                String menuCodeStrs = menuCodeSet.stream().collect(Collectors.joining(","));
                authority.append(menuCodeStrs);
            }
//            得到所需权限编码
            redisUtil.set(Constant.AUTHORITY_KEY,String.valueOf(userId),authority,10*60);
            //观察字符串是否正确
            System.out.println("authority:"+authority.toString());
        }
        //返回字符串
        return authority.toString();
    }
}




