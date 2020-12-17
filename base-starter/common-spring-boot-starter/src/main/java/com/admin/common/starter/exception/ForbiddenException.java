package com.admin.common.starter.exception;


import com.admin.core.enums.HttpStatus;

/**
 * @author 吴邪
 * craeted: 2020/1/10
 */
public class ForbiddenException extends BusinessException {

    public ForbiddenException(String msg) {
        super(HttpStatus.FORBIDDEN, msg);
    }

    public ForbiddenException() {
        super(HttpStatus.FORBIDDEN);
    }
}
