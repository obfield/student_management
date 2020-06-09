package com.koko.service.impl;

import com.koko.dao.SubjectDao;
import com.koko.dao.UserSubjectDao;
import com.koko.entity.Subject;
import com.koko.entity.UserSubject;
import com.koko.exception.CustomException;
import com.koko.service.CommonService;
import com.koko.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class SubjectServiceImpl implements SubjectService {
    @Autowired
    private SubjectDao subjectDao;

    @Autowired
    private CommonService commonService;

    @Autowired
    private UserSubjectDao userSubjectDao;

    @Transactional
    @Override
    public void addSubject(String subjectName) {
        Subject subject = new Subject();
        subject.setSubjectName(subjectName);
        try {
            int insert = subjectDao.insert(subject);
            if (insert == 0) {
                throw new CustomException("新增课程失败");
            }
        } catch (Exception e) {
            throw new CustomException("新增课程失败");
        }


    }

    @Transactional
    @Override
    public void delSubject(String subjectName) {
        try {
            Example example = new Example(Subject.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("subjectName", subjectName);
            int i = subjectDao.deleteByExample(example);
            if (i == 0) {
                throw new CustomException("删除课程失败");
            }
        } catch (Exception e) {
            throw new CustomException("删除课程失败");
        }
    }

    @Override
    public List<Subject> selectAllSubject() {
        List<Subject> subjects = subjectDao.selectAll();
        return subjects;
    }

    @Override
    public List<Subject> selectSubjectByAccount(int account) {
        List<Subject> subjects = commonService.findSubjectByAccount(account);
        return subjects;
    }

    @Transactional
    @Override
    public void addSubjectByAccount(int account, String subjectName) {
        try {
            Example example = new Example(Subject.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("subjectName",subjectName);
            Subject subject = subjectDao.selectOneByExample(example);
            UserSubject userSubject = new UserSubject();
            userSubject.setAccount(account);
            userSubject.setSubjectId(subject.getSubjectId());
            int i = userSubjectDao.insert(userSubject);
            if (i == 0){
                throw new CustomException("增加用户课程失败");
            }
        }catch (Exception e){
            throw new CustomException("增加用户课程失败");
        }
    }

    @Override
    public void delSubjectByAccount(int account, String subjectName) {
        try {
            Example example = new Example(Subject.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("subjectName",subjectName);
            Subject subject = subjectDao.selectOneByExample(example);
            UserSubject userSubject = new UserSubject();
            userSubject.setAccount(account);
            userSubject.setSubjectId(subject.getSubjectId());
            int i = userSubjectDao.delete(userSubject);
            if (i == 0){
                throw new CustomException("删除用户课程失败");
            }
        }catch (Exception e){
            throw new CustomException("删除用户课程失败");
        }
    }
}
