package com.admin.common.starter.exception;


import com.admin.common.starter.enums.HttpStatus;

/**
 * 权限不足
 */
public class ForbiddenException extends BusinessException {

    public ForbiddenException(String msg) {
        super(HttpStatus.FORBIDDEN, msg);
    }

    public ForbiddenException() {
        super(HttpStatus.FORBIDDEN);
    }
}
