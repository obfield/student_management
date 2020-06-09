package com.koko.service.impl;

import com.koko.constant.StatusCode;
import com.koko.dao.GradeDao;
import com.koko.dao.UserDao;
import com.koko.dao.UserRoleDao;
import com.koko.dao.UserSubjectDao;
import com.koko.dto.AddUser;
import com.koko.dto.ResponseBean;
import com.koko.entity.Grade;
import com.koko.entity.User;
import com.koko.entity.UserRole;
import com.koko.entity.UserSubject;
import com.koko.exception.CustomException;
import com.koko.service.UserService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserRoleDao userRoleDao;

    @Autowired
    private UserSubjectDao userSubjectDao;

    @Autowired
    private GradeDao gradeDao;


    @Transactional
    @Override
    public void addUser(AddUser addUser) {
        try {
            User user = new User();
            user.setAccount(addUser.getAccount());
            user.setPassword(addUser.getPassword());
            user.setName(addUser.getName());
            user.setAge(addUser.getAge());
            user.setSex(addUser.getSex());
            user.setAddress(addUser.getAddress());
            user.setAvatar(addUser.getAvatar());
            int addUserSuccess = userDao.insert(user);
            UserRole userRole = new UserRole();
            userRole.setAccount(addUser.getAccount());
            userRole.setRoleId(addUser.getRoleId());
            int addUserRoleSuccess = userRoleDao.insert(userRole);
            if (addUserSuccess == 0 || addUserRoleSuccess == 0) {
                throw new CustomException("新增用户失败");
            }
        } catch (Exception e) {
            throw new CustomException("新增用户失败");
        }

    }

    @Transactional
    @Override
    public void delUser(int account) {
        try {
            Example example = new Example(User.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("account", account);
            int i = userDao.deleteByExample(example);
//
//            Example example1 = new Example(UserRole.class);
//            Example.Criteria criteria1 = example1.createCriteria();
//            criteria1.andEqualTo("account", account);
//            int j = userRoleDao.deleteByExample(example1);
//
//            Example example2 = new Example(UserSubject.class);
//            Example.Criteria criteria2 = example2.createCriteria();
//            criteria2.andEqualTo("account", account);
//            int k = userSubjectDao.deleteByExample(example2);
//
//            Example example3 = new Example(Grade.class);
//            Example.Criteria criteria3 = example3.createCriteria();
//            criteria3.andEqualTo("account",account);
//            int l = gradeDao.deleteByExample(example3);

//            if (i == 0 || j == 0 || k == 0 || l == 0) {
//                throw new CustomException("删除用户失败");
//            }
            if (i == 0) {
                throw new CustomException("删除用户失败");
            }
        } catch (Exception e) {
            throw new CustomException("删除用户失败");
        }

    }

    @Transactional
    @Override
    public void updateUser(User user) {
        try {
            int i = userDao.updateByPrimaryKeySelective(user);
            if (i == 0){
                throw new CustomException("更新用户失败");
            }
        }catch (Exception e){
            throw new CustomException("更新用户失败");
        }
    }

    @Transactional
    @Override
    public User selectUser(int account) {
        try {
            User user = userDao.selectByPrimaryKey(account);
            return user;
        }catch (Exception e){
            throw new CustomException("查询用户失败");
        }
    }
}
