package com.admin.common.starter.exception;


import com.admin.common.starter.enums.HttpStatus;

/**
 * 系统异常
 */
public class InternalException extends BusinessException {

    public InternalException() {
        super(HttpStatus.INTERNAL_EXCEPTION);
    }

    public InternalException(String msg) {
        super(HttpStatus.INTERNAL_EXCEPTION, msg);
    }
}
