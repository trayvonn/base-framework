package com.admin.common.starter.exception;


import com.admin.common.starter.enums.HttpStatusEnum;

/**
 * @author 吴邪
 * craeted: 2020/1/10
 */
public class AuthenticateException extends BusinessException {

    public AuthenticateException(String msg) {
        super(HttpStatusEnum.AUTHENTICATE_FAIL, msg);
    }

    public AuthenticateException() {
        super(HttpStatusEnum.AUTHENTICATE_FAIL);
    }
}
