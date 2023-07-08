package com.java1234.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java1234.entity.SysMenu;
import com.java1234.service.SysMenuService;
import com.java1234.mapper.SysMenuMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
* @author Yiming
* @description 针对表【sys_menu】的数据库操作Service实现，即转菜单树的实现
*/
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu>
    implements SysMenuService{


    /**
     * 构造菜单树
     * 前端需要存menulist
     * @param sysMenuList
     * @return
     */
     public List<SysMenu> buildTreeMenu(List<SysMenu> sysMenuList){
        List<SysMenu> resultMenuList = new ArrayList<>();

        for (SysMenu sysMenu : sysMenuList) {
            // 寻找子节点，二次遍历，寻找并添加层级关系
            for (SysMenu e : sysMenuList) {
                if (e.getParentId()==sysMenu.getId()) {
                    sysMenu.getChildren().add(e);
                }
            }
            // 判断父节点，添加到集合，父节点为零，则为最顶层结点
            if(sysMenu.getParentId()==0L){
                resultMenuList.add(sysMenu);
            }
        }
        return resultMenuList;
    }

}




