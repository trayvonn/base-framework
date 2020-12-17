package com.admin.common.starter.exception;


import com.admin.common.starter.enums.HttpStatusEnum;

/**
 * @author 吴邪
 * craeted: 2020/1/13
 */
public class InternalException extends BusinessException {

    public InternalException() {
        super(HttpStatusEnum.INTERNAL_EXCEPTION);
    }

    public InternalException(String msg) {
        super(HttpStatusEnum.INTERNAL_EXCEPTION, msg);
    }
}
