package com.koko.config;

import com.koko.dto.JWTToken;
import com.koko.entity.Permission;
import com.koko.entity.Role;
import com.koko.entity.User;
import com.koko.service.UserService;
import com.koko.util.JWTUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.awt.SystemColor.info;

public class MyRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = JWTUtil.getUsername(principalCollection.toString());
        Role role = userService.findRoleByUsername(Integer.valueOf(username));
        List<Permission> permissionList = userService.findPermissionByUsername(Integer.valueOf(username));
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
        String username = JWTUtil.getUsername(token);
        User user = userService.findUserByUsername(Integer.valueOf(username));
        if (JWTUtil.verify(token,username,user.getPassword())){
            throw new AuthenticationException("username or password error");
        }
        return new SimpleAuthenticationInfo(token, token, "my_realm");
    }
}
