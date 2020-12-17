package com.admin.common.starter.exception;


import com.admin.common.starter.enums.HttpStatusEnum;

/**
 * @author 吴邪
 * craeted: 2020/1/9
 */
public class UnauthorizedException extends BusinessException {

    public UnauthorizedException() {
        super(HttpStatusEnum.UNAUTHORIZED);
    }

    public UnauthorizedException(String msg) {
        super(HttpStatusEnum.UNAUTHORIZED, msg);
    }
}
