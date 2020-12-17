package com.admin.common.starter.exception;


import com.admin.core.enums.HttpStatus;

/**
 * @author 吴邪
 * craeted: 2020/1/13
 */
public class InternalException extends BusinessException {

    public InternalException() {
        super(HttpStatus.INTERNAL_EXCEPTION);
    }

    public InternalException(String msg) {
        super(HttpStatus.INTERNAL_EXCEPTION, msg);
    }
}
