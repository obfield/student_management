package com.koko.service;

import com.koko.entity.Permission;
import com.koko.entity.Role;
import com.koko.entity.User;

import java.util.List;

public interface UserService {
    User findUserByUsername(int username);
    Role findRoleByUsername(int username);
    List<Permission> findPermissionByUsername(int username);
}
