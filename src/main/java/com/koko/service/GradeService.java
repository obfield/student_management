package com.koko.service;

import com.koko.dto.StudentScore;
import com.koko.entity.Grade;

import java.util.List;

public interface GradeService {

    void addGrade(Grade grade);

    void delGrade(Grade grade);

    void updateGrade(Grade grade);

    List<StudentScore> selectGradeByAccount(int account);

    List<StudentScore> selectGradeBySubjectName(String subjectName);

}
