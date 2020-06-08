package com.koko.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.koko.dao.CommonDao;
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
    private CommonDao commonDao;

    @Override
    public User findUserByAccount(int account) {
        return commonDao.findUserByAccount(account);
    }

    @Override
    public Role findRoleByAccount(int account) {
        return commonDao.findRoleByAccount(account);
    }

    @Override
    public List<Permission> findPermissionByAccount(int account) {
        return commonDao.findPermissionByAccount(account);
    }

    @Override
    public List<User> findAll() {
        return commonDao.findAll();
    }

    @Override
    public Page<User> findAll(int pageNum, int PageSize) {
        PageHelper.startPage(pageNum, PageSize);
        return (Page<User>) commonDao.findAll();
    }

    @Override
    public List<User> findAllUserByJob(int job) {
        return commonDao.findAllUserByJob(job);
    }

    @Override
    public List<Subject> findSubjectByAccount(int account) {
        return commonDao.findSubjectByAccount(account);
    }

    @Override
    public List<StudentScore> findStudentScoreByAccount(int account) {
        return commonDao.findStudentScoreByAccount(account);
    }

    @Override
    public void updateUserByAccount(User user) {
        commonDao.updateUserByAccount(user);
    }
}
