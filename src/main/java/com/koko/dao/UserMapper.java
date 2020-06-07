package com.koko.dao;

import com.koko.dto.StudentScore;
import com.koko.entity.Permission;
import com.koko.entity.Role;
import com.koko.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.shiro.subject.Subject;

import java.util.List;

@Mapper
public interface UserMapper {
    User findUserByAccount(int account);

    Role findRoleByAccount(int account);

    List<Permission> findPermissionByAccount(int account);

    List<User> findAll();

    List<User> findAllUserByJob(int job);

    List<Subject> findSubjectByAccount(int account);

    List<StudentScore> findStudentScoreByAccount(int account);

}
