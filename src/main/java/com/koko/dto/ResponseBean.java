package com.koko.dto;

import com.koko.constant.StatusCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseBean {
    private int code;
    private String msg;
    private Object data;

    public ResponseBean(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResponseBean ok(Object obj) {
        this.code = StatusCode.OK;
        this.msg = "请求成功";
        this.data = obj;
        return this;
    }
    public ResponseBean ok() {
        this.code = StatusCode.OK;
        this.msg = "请求成功";
        this.data = null;
        return this;
    }
}
