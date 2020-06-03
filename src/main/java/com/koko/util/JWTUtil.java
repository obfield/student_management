package com.koko.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.koko.constant.JwtConstant;
import com.koko.exception.CustomException;
import org.springframework.beans.factory.annotation.Value;

import java.io.UnsupportedEncodingException;
import java.util.Date;

public class JWTUtil {

    // 过期时间5分钟
    @Value("accessTokenExpireTime")
    private static String accessTokenExpireTime;

    @Value("encryptJWTKey")
    private static String encryptJWTKey;

    /**
     * 校验token是否正确
     *
     * @param token  密钥
     * @return 是否正确
     */
    public static boolean verify(String token) {
        try {
            String secret = getClaim(token, JwtConstant.ACCOUNT) + Base64Util.decodeThrowsException(encryptJWTKey);
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (UnsupportedEncodingException  e) {
            throw new CustomException("JWT解密出现UnsupportedEncodingException异常:"+e.getMessage());
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     *
     * @return token中包含的用户名
     */
    public static String getClaim(String token, String claim) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(claim).asString();
        } catch (JWTDecodeException e) {
            throw new CustomException("解密JWT公共信息出现JWTDecodeException异常:"+e.getMessage());
        }
    }

    /**
     * 生成签名,5min后过期
     *
     * @param account 账户名
     * @param currentTimeMillis  创建时间
     * @return 加密的token
     */
    public static String sign(String account, String currentTimeMillis) {
        try {
            String secret = account + Base64Util.decodeThrowsException(encryptJWTKey);
            //此处过期时间为毫秒，所以乘1000
            Date date = new Date(System.currentTimeMillis() + Long.parseLong(accessTokenExpireTime) * 1000);
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withClaim("account", account)
                    .withClaim("currentTimeMillis",currentTimeMillis)
                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (UnsupportedEncodingException e) {
            throw new CustomException("JWT加密出现UnsupportedEncodingException异常:"+e.getMessage());
        }
    }
}