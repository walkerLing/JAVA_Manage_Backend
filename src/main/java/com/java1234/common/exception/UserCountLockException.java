package com.java1234.common.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 有关用户账号封禁异常，自定义实现的异常
 * @author Yiming
 */
public class UserCountLockException extends AuthenticationException {

    public UserCountLockException(String msg, Throwable t) {
        super(msg, t);
    }

    public UserCountLockException(String msg) {
        super(msg);
    }
}
