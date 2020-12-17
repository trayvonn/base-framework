package com.admin.common.starter.exception;

import com.admin.common.starter.enums.HttpStatusEnum;
import lombok.Data;

/**
 * @author 吴邪
 * craeted: 2020/1/9
 */
@Data
public abstract class BusinessException extends RuntimeException {
    private static final long serialVersionUID = -6175808415650755522L;
    private int code;
    private String msg;

    public BusinessException(HttpStatusEnum status) {
        super(status.getMsg());
        this.code = status.getCode();
        this.msg = status.getMsg();
    }

    public BusinessException(HttpStatusEnum status, String msg) {
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
