package com.github.lang.exceptions;

import lombok.Getter;

/**
 * @author hangs.zhang
 * @date 2018/11/20
 * *****************
 * function:
 */
@Getter
public class ApiException extends RuntimeException {

    private Integer code;

    public ApiException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public ApiException() {
        super();
    }

}
