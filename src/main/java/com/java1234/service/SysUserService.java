package com.java1234.service;

import com.java1234.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Yiming
* @description 针对表【sys_user】的数据库操作Service
*/
public interface SysUserService extends IService<SysUser> {

    SysUser getByUserName(String username);

    String getUserAuthorityInfo(Long userId);
}
