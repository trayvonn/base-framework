package com.admin.common.starter.exception;


import com.admin.core.enums.HttpStatus;

/**
 * @author 吴邪
 * craeted: 2020/1/10
 */
public class AuthenticateException extends BusinessException {

    public AuthenticateException(String msg) {
        super(HttpStatus.AUTHENTICATE_FAIL, msg);
    }

    public AuthenticateException() {
        super(HttpStatus.AUTHENTICATE_FAIL);
    }
}
