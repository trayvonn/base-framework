package com.admin.common.starter.exception;


import com.admin.common.starter.enums.HttpStatusEnum;

/**
 * @author 吴邪
 * craeted: 2020/1/10
 */
public class ForbiddenException extends BusinessException {

    public ForbiddenException(String msg) {
        super(HttpStatusEnum.FORBIDDEN, msg);
    }

    public ForbiddenException() {
        super(HttpStatusEnum.FORBIDDEN);
    }
}
