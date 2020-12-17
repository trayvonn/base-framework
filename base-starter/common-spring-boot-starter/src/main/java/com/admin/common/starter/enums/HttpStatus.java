package com.admin.common.starter.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * HTTP状态枚举类
 *
 * @author 吴邪
 * @since 2020/5/13 15:20
 */
@Getter
@AllArgsConstructor
public enum HttpStatus {
    SUCCESS(0, "成功"),
    BAD_REQUEST(400, "无效的参数"),
    UNAUTHORIZED(401, "尚未认证"),
    AUTHENTICATE_FAIL(402, "认证失败"),
    FORBIDDEN(403, "权限不足"),
    INTERNAL_EXCEPTION(500, "内部异常");

    private int code;
    private String msg;
}
