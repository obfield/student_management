package com.koko.service;

import com.koko.entity.Subject;

import java.util.List;

public interface SubjectService {

    void addSubject(String subjectName);

    void delSubject(String subjectName);

    List<Subject> selectAllSubject();

    List<Subject> selectSubjectByAccount(int account);

    void addSubjectByAccount(int account, String subjectName);

    void delSubjectByAccount(int account, String subjectName);
}
