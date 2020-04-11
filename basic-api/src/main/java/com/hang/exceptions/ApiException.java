package com.hang.exceptions;

/**
 * @author hangs.zhang
 * @date 2018/11/20
 * *****************
 * function:
 */
public class ApiException extends RuntimeException {

    private Integer code;

    public Integer getCode() {
        return code;
    }

    public ApiException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public ApiException() {
        super();
    }

}
