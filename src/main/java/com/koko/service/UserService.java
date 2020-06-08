package com.koko.service;

import com.github.pagehelper.Page;
import com.koko.dto.StudentScore;
import com.koko.entity.Permission;
import com.koko.entity.Role;
import com.koko.entity.User;
import org.apache.shiro.subject.Subject;

import java.util.List;

public interface UserService {
    User findUserByAccount(int account);

    Role findRoleByAccount(int account);

    List<Permission> findPermissionByAccount(int account);

    List<User> findAll();

    Page<User> findAll(int pageNum, int pageSize);

    List<User> findAllUserByJob(int job);

    List<Subject> findSubjectByAccount(int account);

    List<StudentScore> findStudentScoreByAccount(int account);

    void updateUserByAccount(User user);
}
