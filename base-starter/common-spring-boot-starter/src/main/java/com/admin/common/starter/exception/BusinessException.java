package com.admin.common.starter.exception;

import com.admin.common.starter.enums.HttpStatus;
import lombok.Getter;
import lombok.Setter;

/**
 * 业务异常
 */
@Getter
@Setter
public abstract class BusinessException extends RuntimeException {
    private static final long serialVersionUID = -6175808415650755522L;
    private int code;
    private String msg;

    public BusinessException(HttpStatus status) {
        super(status.getMsg());
        this.code = status.getCode();
        this.msg = status.getMsg();
    }

    public BusinessException(HttpStatus status, String msg) {
        super(msg);
        this.code = status.getCode();
        this.msg = msg;
    }

    public BusinessException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

}
