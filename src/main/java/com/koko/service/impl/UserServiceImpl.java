package com.koko.service.impl;

import com.koko.dao.UserMapper;
import com.koko.entity.Permission;
import com.koko.entity.Role;
import com.koko.entity.User;
import com.koko.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findUserByUsername(int username) {
        return userMapper.findUserByUsername(username);
    }

    @Override
    public Role findRoleByUsername(int username) {
        return userMapper.findRoleByUsername(username);
    }

    @Override
    public List<Permission> findPermissionByUsername(int username) {
        return userMapper.findPermissionByUsername(username);
    }
}
