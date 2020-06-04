package com.koko.dao;

import com.github.pagehelper.Page;
import com.koko.entity.Permission;
import com.koko.entity.Role;
import com.koko.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    User findUserByUsername(int username);
    Role findRoleByUsername(int username);
    List<Permission> findPermissionByUsername(int username);
    List<User> findAll();
}
