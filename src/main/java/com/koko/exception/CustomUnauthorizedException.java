package com.koko.exception;
//自定义401无权限异常
public class CustomUnauthorizedException extends RuntimeException{
    public CustomUnauthorizedException() {
    }

    public CustomUnauthorizedException(String message) {
        super(message);
    }
}
