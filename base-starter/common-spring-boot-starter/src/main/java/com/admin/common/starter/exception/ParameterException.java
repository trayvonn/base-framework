package com.admin.common.starter.exception;


import com.admin.common.starter.enums.HttpStatus;

/**
 * 参数异常
 */
public class ParameterException extends BusinessException {

    public ParameterException() {
        super(HttpStatus.BAD_REQUEST);
    }

    public ParameterException(String msg) {
        super(HttpStatus.BAD_REQUEST, msg);
    }
}
