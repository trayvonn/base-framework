package com.admin.common.starter.exception;


import com.admin.core.enums.HttpStatus;

/**
 * @author 吴邪
 * craeted: 2020/1/9
 */
public class ParameterException extends BusinessException {

    public ParameterException() {
        super(HttpStatus.BAD_REQUEST);
    }

    public ParameterException(String msg) {
        super(HttpStatus.BAD_REQUEST, msg);
    }
}
