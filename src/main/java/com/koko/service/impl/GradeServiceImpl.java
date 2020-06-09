package com.koko.service.impl;

import com.koko.dao.GradeDao;
import com.koko.dao.SubjectDao;
import com.koko.dto.StudentScore;
import com.koko.entity.Grade;
import com.koko.entity.Subject;
import com.koko.exception.CustomException;
import com.koko.service.CommonService;
import com.koko.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class GradeServiceImpl implements GradeService {

    @Autowired
    private GradeDao gradeDao;

    @Autowired
    private SubjectDao subjectDao;

    @Autowired
    private CommonService commonService;

    @Override
    public void addGrade(Grade grade) {
        try {
            gradeDao.insert(grade);
        } catch (Exception e) {
            throw new CustomException("新增成绩失败");
        }
    }

    @Override
    public void delGrade(Grade grade) {
        try {
            gradeDao.delete(grade);
        } catch (Exception e) {
            throw new CustomException("删除成绩失败");
        }
    }

    @Override
    public void updateGrade(Grade grade) {
        try {
            Example example = new Example(Grade.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("account", grade.getAccount())
                    .andEqualTo("subjectId",grade.getSubjectId());
            gradeDao.updateByExample(grade, example);
        } catch (Exception e) {
            throw new CustomException("更新成绩失败");
        }
    }

    @Override
    public List<StudentScore> selectGradeByAccount(int account) {
        try {
            List<StudentScore> studentScores = commonService.findStudentScoreByAccount(account);
            return studentScores;
        } catch (Exception e) {
            throw new CustomException("查询成绩失败");
        }
    }

    @Override
    public List<StudentScore> selectGradeBySubjectName(String subjectName) {
        try {
            List<StudentScore> studentScores = commonService.findStudentScoreBySubjectName(subjectName);
            return studentScores;
        } catch (Exception e) {
            throw new CustomException("查询成绩失败");
        }
    }
}
