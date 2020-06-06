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


    public ResponseBean ok(Object obj) {
        this.code = StatusCode.OK;
        this.msg = "请求正常";
        this.data = obj;
        return this;
    }
}
