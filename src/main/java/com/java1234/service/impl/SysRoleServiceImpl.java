package com.java1234.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java1234.entity.SysRole;
import com.java1234.service.SysRoleService;
import com.java1234.mapper.SysRoleMapper;
import org.springframework.stereotype.Service;

/**
* @author Yiming
* @description 针对表【sys_role】的数据库操作Service实现
*/
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole>
    implements SysRoleService{

}




