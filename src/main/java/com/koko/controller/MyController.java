package com.koko.controller;

import com.koko.constant.RedisConstant;
import com.koko.constant.StatusCode;
import com.koko.dto.ResponseBean;
import com.koko.entity.User;
import com.koko.service.UserService;
import com.koko.service.impl.UserServiceImpl;
import com.koko.util.JWTUtil;
import com.koko.util.RedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@RestController
public class MyController {

    @Autowired
    private UserService userService;

    @Value("${config.refreshTokenExpireTime}")
    private String refreshTokenExpireTime;

    @Autowired
    private RedisClient redis;

    @PostMapping({"/", "/login"})
    public ResponseBean login(String account, String password, HttpServletResponse response) {

        try {
            User user = userService.findUserByUsername(Integer.parseInt(account));
            if (user == null || !user.getPassword().equals(password)) {
                return new ResponseBean(StatusCode.ERROR, "用户名或密码错误", null);
            }
            if (redis.hasKey(RedisConstant.PREFIX_SHIRO_CACHE + account)){
                redis.del(RedisConstant.PREFIX_SHIRO_CACHE + account);
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
}
