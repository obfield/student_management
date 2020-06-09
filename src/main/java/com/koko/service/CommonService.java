package com.koko.service;

import com.github.pagehelper.Page;
import com.koko.dto.StudentScore;
import com.koko.entity.Permission;
import com.koko.entity.Role;
import com.koko.entity.Subject;
import com.koko.entity.User;

import java.util.List;

public interface CommonService {

    Role findRoleByAccount(int account);

    List<Permission> findPermissionByAccount(int account);

    List<User> findAll();

    Page<User> findAll(int pageNum, int pageSize);

    List<User> findAllUserByJob(int job);

    List<Subject> findSubjectByAccount(int account);

    List<StudentScore> findStudentScoreByAccount(int account);

    List<StudentScore> findStudentScoreBySubjectName(String StudentName);
}
