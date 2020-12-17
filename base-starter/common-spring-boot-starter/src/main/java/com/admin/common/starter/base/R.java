package com.admin.common.starter.base;

import com.admin.common.starter.enums.HttpStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 自定义返回类
 *
 * @author 吴邪
 * @since  2020/5/13 15:17
 */
@Data
@AllArgsConstructor
public class R<T> {

    private int code;
    private String msg;
    private T data;

    public static R success() {
        return new R(HttpStatus.SUCCESS, null);
    }

    public static R fail(HttpStatus httpStatus) {
        return new R(httpStatus, null);
    }

    public static R fail(HttpStatus httpStatus, String msg) {
        return new R(httpStatus.getCode(), msg, null);
    }

    public static R fail(int code, String msg) {
        return new R(code, msg, null);
    }

    public static R fail(String msg) {
        return new R(HttpStatus.INTERNAL_EXCEPTION.getCode(), msg, null);
    }

    public static R exception() {
        return new R(HttpStatus.INTERNAL_EXCEPTION, null);
    }

    public static <T> R success(T data) {
        return new R(HttpStatus.SUCCESS, data);
    }

    public R(HttpStatus httpStatus, T data) {
        this.code = httpStatus.getCode();
        this.msg = httpStatus.getMsg();
        this.data = data;
    }


}
