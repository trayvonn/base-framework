package com.admin.common.starter.exception;


import com.admin.core.enums.HttpStatus;

/**
 * 尚未认证
 */
public class UnauthorizedException extends BusinessException {

    public UnauthorizedException() {
        super(HttpStatus.UNAUTHORIZED);
    }

    public UnauthorizedException(String msg) {
        super(HttpStatus.UNAUTHORIZED, msg);
    }
}
