package com.koko;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.koko.dao.UserMapper;
import com.koko.dto.StudentScore;
import com.koko.entity.Permission;
import com.koko.entity.Role;
import com.koko.entity.User;
import com.koko.service.UserService;
import org.apache.shiro.subject.Subject;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
class StudentManagementApplicationTests {

    @Autowired
    private UserService userService;

    @Test
    void contextLoads() {
        List<User> users = userService.findAll();
//        PageInfo pageInfo = new PageInfo(users);
//        String string = JSON.toJSONString(pageInfo);
//        String string = JSON.toJSONString(users);
//        System.out.println(string);
        List<Subject> subjects = userService.findSubjectByAccount(2017020155);
        System.out.println(subjects);
    }

}
