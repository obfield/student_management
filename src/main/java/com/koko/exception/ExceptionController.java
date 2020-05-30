package com.koko.exception;

import com.koko.constant.StatusCode;
import com.koko.dto.ResponseBean;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ExceptionController {

    /**
     * 捕捉所有hiro异常
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ShiroException.class)
    public ResponseBean handle401(ShiroException e) {
        ResponseBean res = new ResponseBean();
        res.setCode(StatusCode.UNLAWFUL);
        res.setMsg("无效访问(Unauthorized):" + e.getMessage());
        return res;
    }

    /**
     * 单独捕捉Shiro(UnauthorizedException)异常，该异常为访问有权限管控的请求而该用户没有所需权限而抛出的异常
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseBean handle401(UnauthorizedException e) {
        ResponseBean res = new ResponseBean();
        res.setCode(StatusCode.UNLAWFUL);
        res.setMsg("无权访问(Unauthorized)，当前Subject没有本次请求所需权限:" + e.getMessage());
        return res;
    }

    /**
     * 单独捕捉Shiro(UnauthenticatedException)异常
     * 该异常是以游客身份访问有权限管控的请求，无法对匿名主体进行授权而授权失败抛出的异常
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthenticatedException.class)
    public ResponseBean handle401(UnauthenticatedException e) {
        ResponseBean res = new ResponseBean();
        res.setCode(StatusCode.UNLAWFUL);
        res.setMsg("无权访问(Unauthorized),当前Subject是匿名Subject，请先登录！");
        return res;
    }
    /**
     * 捕捉404异常
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseBean handle(NoHandlerFoundException e){
        ResponseBean res = new ResponseBean();
        res.setCode(StatusCode.NOT_FOUND);
        res.setMsg(e.getMessage());
        return res;
    }
    /**
     * 捕捉其他所有异常
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseBean globalException(HttpServletRequest request, Throwable ex){
        ResponseBean res = new ResponseBean();
        res.setCode(StatusCode.SERVER_ERROR);
        res.setMsg(ex.toString()+":"+ex.getMessage());
        return res;
    }
    /**
     * 捕捉自定义异常
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CustomException.class)
    public ResponseBean handle(CustomException e){
        ResponseBean res = new ResponseBean();
        res.setCode(StatusCode.ERROR);
        res.setMsg(e.getMessage());
        return res;
    }

    /**
     * 获取状态码
     */
    public HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }

    /**
     * 获取校验错误信息
     */
    public Map<String, Object> getValidError(List<FieldError> fieldErrors) {
        //initial capacity 初始化容量
        Map<String, Object> result = new HashMap<>(16);
        List<String> errorList = new ArrayList<>();
        StringBuffer errorMsg = new StringBuffer("校验异常(ValidException)");
        for (FieldError e : fieldErrors) {
            errorList.add(e.getField() + "-" + e.getDefaultMessage());
            errorMsg.append(e.getField() + "-" + e.getDefaultMessage() + ".");
        }
        result.put("errorList", errorList);
        result.put("errorMsg", errorMsg);
        return result;
    }
}
