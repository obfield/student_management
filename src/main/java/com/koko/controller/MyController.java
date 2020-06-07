package com.koko.controller;

import com.github.pagehelper.Page;
import com.koko.constant.JwtConstant;
import com.koko.constant.RedisConstant;
import com.koko.constant.StatusCode;
import com.koko.dto.ResponseBean;
import com.koko.entity.User;
import com.koko.service.UserService;
import com.koko.util.JWTUtil;
import com.koko.util.RedisClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.List;

@Api("所有方法的操作接口")
@RestController
public class MyController {

    @Autowired
    private UserService userService;

    @Value("${config.refreshTokenExpireTime}")
    private String refreshTokenExpireTime;

    @Autowired
    private RedisClient redis;

    @ApiOperation("登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "account", value = "账户", defaultValue = "2017020155", required = true),
            @ApiImplicitParam(name = "password", value = "密码", defaultValue = "123456", required = true)
    })
    @PostMapping({"/", "/login"})
    public ResponseBean login(String account, String password, HttpServletResponse response) {

        try {
            User user = userService.findUserByAccount(Integer.parseInt(account));
            if (user == null || !user.getPassword().equals(password)) {
                return new ResponseBean(StatusCode.ERROR, "用户名或密码错误", null);
            }
            if (redis.hasKey(RedisConstant.PREFIX_SHIRO_REFRESH_TOKEN + account)) {
                redis.del(RedisConstant.PREFIX_SHIRO_REFRESH_TOKEN + account);
            }
            String currentTimeMillis = String.valueOf(System.currentTimeMillis());
            redis.set(RedisConstant.PREFIX_SHIRO_REFRESH_TOKEN + account, currentTimeMillis,
                    Integer.parseInt(refreshTokenExpireTime));
            String token = JWTUtil.sign(account, currentTimeMillis);
            response.setHeader("Authorization", token);
            response.setHeader("Access-Control-Expose-Headers", "Authorization");

            return new ResponseBean(StatusCode.OK, "成功登录", null);
        } catch (Exception e) {
            return new ResponseBean(StatusCode.ERROR, e.getMessage(), null);
        }
    }
    @RequiresAuthentication
    @ApiOperation("退出登录")
    @GetMapping("/logout")
    public ResponseBean logout(HttpServletRequest request) {
        try {
            String token = null;
            Enumeration<String> headerNames = request.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String key = headerNames.nextElement();
                String value = request.getHeader(key);
                //取出的headername为小写，所以需要使用equalsIgnoreCase
                if ("Authorization".equalsIgnoreCase(key)) {
                    token = value;
                }
            }
            if (token == null) {
                return new ResponseBean(StatusCode.ERROR, "token为空", null);
            }
            String account = JWTUtil.getClaim(token, JwtConstant.ACCOUNT);
            if (account == null) {
                return new ResponseBean(StatusCode.ERROR, "token失效或错误", null);
            }
            if (redis.hasKey(RedisConstant.PREFIX_SHIRO_REFRESH_TOKEN + account)) {
                redis.del(RedisConstant.PREFIX_SHIRO_REFRESH_TOKEN + account);
            }
            return new ResponseBean(StatusCode.OK, "成功退出", null);
        } catch (Exception e) {
            return new ResponseBean(StatusCode.ERROR, e.getMessage(), null);
        }
    }
    @RequiresAuthentication
    @ApiOperation("获取用户列表")
    @GetMapping("/users")
    public ResponseBean Users() {
        List<User> users = userService.findAll();
        return new ResponseBean().ok(users);
    }

    @RequiresAuthentication
    @ApiOperation("获取分页数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页数(第几页)", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页的记录数", required = true)
    })
    @GetMapping("/pages")
    public ResponseBean Pages(int pageNum, int pageSize) {
        Page<User> pages = userService.findAll(pageNum, pageSize);
        return new ResponseBean().ok(pages);
    }
}
