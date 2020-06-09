package com.koko.config.shiro;

import com.koko.constant.JwtConstant;
import com.koko.constant.RedisConstant;
import com.koko.dao.UserDao;
import com.koko.dto.JWTToken;
import com.koko.entity.Permission;
import com.koko.entity.Role;
import com.koko.entity.User;
import com.koko.service.CommonService;
import com.koko.util.JWTUtil;
import com.koko.util.RedisClient;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MyRealm extends AuthorizingRealm {

    @Autowired
    private CommonService commonService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private RedisClient redis;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = JWTUtil.getClaim(principalCollection.toString(), JwtConstant.ACCOUNT);
        Role role = commonService.findRoleByAccount(Integer.valueOf(username));
        List<Permission> permissionList = commonService.findPermissionByAccount(Integer.valueOf(username));
        Set<String> permissions = new HashSet<>();
        for (Permission permission : permissionList){
            permissions.add(permission.getPermissionCode());
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRole(role.getRoleName());
        info.addStringPermissions(permissions);
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String token = (String) authenticationToken.getCredentials();
        String account = JWTUtil.getClaim(token,JwtConstant.ACCOUNT);
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("account", Integer.valueOf(account));
        User user = userDao.selectOneByExample(example);
//        User user = commonService.findUserByAccount(Integer.valueOf(account));
        if (user == null){
            throw new AuthenticationException("用户不存在！");
        }
        if(JWTUtil.verify(token) ) {
            if (redis.hasKey(RedisConstant.PREFIX_SHIRO_REFRESH_TOKEN + account)) {
                String currentTimeMillisRedis = redis.get(RedisConstant.PREFIX_SHIRO_REFRESH_TOKEN + account).toString();
                if (JWTUtil.getClaim(token, JwtConstant.CURRENT_TIME_MILLIS).equals(currentTimeMillisRedis)) {
                    return new SimpleAuthenticationInfo(token, token, "myRealm");
                }
            }
        }
        throw new AuthenticationException("令牌过期或不正确");
    }
}
