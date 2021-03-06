package com.koko.dao;

import com.github.pagehelper.Page;
import com.koko.dto.StudentScore;
import com.koko.entity.Permission;
import com.koko.entity.Role;
import com.koko.entity.Subject;
import com.koko.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommonDao {

    Role findRoleByAccount(int account);

    List<Permission> findPermissionByAccount(int account);

    List<User> findAllUserByJob(int job);

    List<Subject> findSubjectByAccount(int account);

    List<StudentScore> findStudentScoreByAccount(int account);

    List<StudentScore> findStudentScoreBySubjectName(String subjectName);

}
