package com.koko.filter;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.koko.constant.JwtConstant;
import com.koko.constant.RedisConstant;
import com.koko.constant.StatusCode;
import com.koko.dto.JWTToken;
import com.koko.dto.ResponseBean;
import com.koko.exception.CustomException;
import com.koko.util.JWTUtil;
import com.koko.util.RedisClient;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

public class JwtFilter extends BasicHttpAuthenticationFilter {

    @Autowired
    private RedisClient redis;

    @Value("${config.refreshTokenExpireTime}")
    private String refreshTokenExpireTime;

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (isLoginAttempt(request,response)){
            try {
                this.executeLogin(request,response);
            } catch (Exception e) {
                String msg = e.getMessage();
                // 获取应用异常(该Cause是导致抛出此throwable(异常)的throwable(异常))
                Throwable throwable = e.getCause();
                if (throwable != null && throwable instanceof SignatureVerificationException){
                    // 该异常为JWT的AccessToken认证失败(Token或者密钥不正确)
                    msg = "token或密钥不正确("+throwable.getMessage()+")";
                }else if(throwable != null && throwable instanceof TokenExpiredException){
                    // 该异常为JWT的AccessToken已过期，判断RefreshToken未过期就进行AccessToken刷新
                    if (this.refreshToken(request, response)){
                        return true;
                    }else {
                        msg = "token已过期("+throwable.getMessage()+")";
                    }
                }else{
                    if (throwable != null){
                        msg = throwable.getMessage();
                    }
                }
                this.response401(request, response, msg);
                return false;
            }
        }
        return true;
    }


    /**
     * 这里我们详细说明下为什么重写 可以对比父类方法，只是将executeLogin方法调用去除了
     * 如果没有去除将会循环调用doGetAuthenticationInfo方法
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        this.sendChallenge(request,response);
        return false;
    }

    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        String token = this.getAuthzHeader(request);
        return token!=null;
    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        JWTToken token = new JWTToken(this.getAuthzHeader(request));
        this.getSubject(request,response).login(token);
        return true;
    }

    private Boolean refreshToken(ServletRequest request, ServletResponse response){
        String token = this.getAuthzHeader(request);
        String account = JWTUtil.getClaim(token, JwtConstant.ACCOUNT);
        if (redis.hasKey(RedisConstant.PREFIX_SHIRO_REFRESH_TOKEN +account)){
            String currentTimeMillisRedis = redis.get(RedisConstant.PREFIX_SHIRO_REFRESH_TOKEN + account).toString();
            if (JWTUtil.getClaim(token, JwtConstant.CURRENT_TIME_MILLIS).equals(currentTimeMillisRedis)){
                String currentTimeMillis = new Date(System.currentTimeMillis()).toString();
                redis.set(RedisConstant.PREFIX_SHIRO_REFRESH_TOKEN + account,currentTimeMillis,
                        Long.parseLong(refreshTokenExpireTime));
                token = JWTUtil.sign(account, currentTimeMillis);
                JWTToken jwtToken = new JWTToken(token);
                this.getSubject(request, response).login(jwtToken);
                HttpServletResponse httpServletResponse = (HttpServletResponse) response;
                httpServletResponse.setHeader("Authorization",token);
                httpServletResponse.setHeader("Access-Control-Expose-Headers", "Authorization");
                return true;
            }
        }
        return false;
    }



    private void response401(ServletRequest request, ServletResponse response, String msg) {
        HttpServletResponse resp = (HttpServletResponse) response;
        resp.setStatus(HttpStatus.UNAUTHORIZED.value());
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter out = null;
        try {
            out = resp.getWriter();
            String data = JSON.toJSONString(new ResponseBean(StatusCode.NOT_LOGIN, "无权访问(UnAuthorized)" + msg, null));
            out.append(data);
        } catch (IOException e) {
            throw new CustomException("直接返回Response信息出现IOException异常:"+e.getMessage());
        }finally {
            if (out != null){
                out.close();
            }
        }
    }

    /**
     * 提供跨域支持
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers",
                httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个OPTIONS请求，这里我们给OPTIONS请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }
}
