package com.admin.common.starter.exception;


import com.admin.common.starter.enums.HttpStatusEnum;

/**
 * @author 吴邪
 * craeted: 2020/1/9
 */
public class ParameterException extends BusinessException {

    public ParameterException() {
        super(HttpStatusEnum.BAD_REQUEST);
    }

    public ParameterException(String msg) {
        super(HttpStatusEnum.BAD_REQUEST, msg);
    }
}
