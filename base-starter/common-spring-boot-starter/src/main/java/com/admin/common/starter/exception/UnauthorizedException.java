package com.admin.common.starter.exception;


import com.admin.core.enums.HttpStatus;

/**
 * @author 吴邪
 * craeted: 2020/1/9
 */
public class UnauthorizedException extends BusinessException {

    public UnauthorizedException() {
        super(HttpStatus.UNAUTHORIZED);
    }

    public UnauthorizedException(String msg) {
        super(HttpStatus.UNAUTHORIZED, msg);
    }
}
