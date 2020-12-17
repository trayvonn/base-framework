package com.admin.common.starter.exception;


import com.admin.core.enums.HttpStatus;

/**
 * 认证失败
 */
public class AuthenticateException extends BusinessException {

    public AuthenticateException(String msg) {
        super(HttpStatus.AUTHENTICATE_FAIL, msg);
    }

    public AuthenticateException() {
        super(HttpStatus.AUTHENTICATE_FAIL);
    }
}
