package com.java1234.service;

import com.java1234.entity.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Yiming
* @description 针对表【sys_menu】的数据库操作Service，将order菜单转换为菜单树
*/
public interface SysMenuService extends IService<SysMenu> {

     public List<SysMenu> buildTreeMenu(List<SysMenu> sysMenuList);

}
