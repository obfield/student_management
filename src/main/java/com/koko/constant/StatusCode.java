package com.koko.constant;

public interface StatusCode {
    /**
     * 非法访问
     */
    int UNLAWFUL = 401;
    /**
     * 不存在该页面
     */
    int NOT_FOUND = 404;
    /**
     * 服务器错误
     */
    int SERVER_ERROR = 500;
    /**
     * 语义错误
     */
    int ERROR = 400;
    /**
     * 未登录
     */
    int NOT_LOGIN = 402;
    /**
     * 一切正常
     */
    int OK = 200;
}
