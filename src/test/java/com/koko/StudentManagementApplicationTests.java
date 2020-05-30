package com.koko;

import com.koko.dao.UserMapper;
import com.koko.entity.Permission;
import com.koko.entity.Role;
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
    private UserMapper userMapper;

    @Test
    void contextLoads() {
//        Role role = userMapper.findRoleByUsername(2017020155);
        List<Permission> permissions = userMapper.findPermissionByUsername(2017020155);
        System.out.println(permissions);
    }

}
