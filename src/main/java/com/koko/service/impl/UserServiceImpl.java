package com.koko.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.koko.dao.UserMapper;
import com.koko.dto.StudentScore;
import com.koko.entity.Permission;
import com.koko.entity.Role;
import com.koko.entity.User;
import com.koko.service.UserService;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findUserByAccount(int account) {
        return userMapper.findUserByAccount(account);
    }

    @Override
    public Role findRoleByAccount(int account) {
        return userMapper.findRoleByAccount(account);
    }

    @Override
    public List<Permission> findPermissionByAccount(int account) {
        return userMapper.findPermissionByAccount(account);
    }

    @Override
    public List<User> findAll() {
        return userMapper.findAll();
    }

    @Override
    public Page<User> findAll(int pageNum, int PageSize) {
        PageHelper.startPage(pageNum, PageSize);
        return (Page<User>) userMapper.findAll();
    }

    @Override
    public List<User> findAllUserByJob(int job) {
        return userMapper.findAllUserByJob(job);
    }

    @Override
    public List<Subject> findSubjectByAccount(int account) {
        return userMapper.findSubjectByAccount(account);
    }

    @Override
    public List<StudentScore> findStudentScoreByAccount(int account) {
        return userMapper.findStudentScoreByAccount(account);
    }
}
