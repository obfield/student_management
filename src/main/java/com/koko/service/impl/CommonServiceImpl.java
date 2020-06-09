package com.koko.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.koko.dao.CommonDao;
import com.koko.dao.UserDao;
import com.koko.dto.StudentScore;
import com.koko.entity.Permission;
import com.koko.entity.Role;
import com.koko.entity.Subject;
import com.koko.entity.User;
import com.koko.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    private CommonDao commonDao;

    @Autowired
    private UserDao userDao;

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
        return userDao.selectAll();
    }

    @Override
    public Page<User> findAll(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return (Page<User>) commonDao.findAllUserByJob(1);
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
    public List<StudentScore> findStudentScoreBySubjectName(String StudentName) {
        return commonDao.findStudentScoreBySubjectName(StudentName);
    }
}
