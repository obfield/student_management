package com.koko;

import com.koko.dao.SubjectDao;
import com.koko.dao.CommonDao;
import com.koko.entity.Subject;
import com.koko.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
class StudentManagementApplicationTests {

    @Autowired
    private UserService userService;

    @Autowired
    private CommonDao commonDao;

    @Autowired
    private SubjectDao subjectDao;

    @Test
    void contextLoads() {

        Example example = new Example(Subject.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("subjectId",2);
        List<Subject> subjects = subjectDao.selectByExample(example);
        System.out.println(subjects);
    }

}
