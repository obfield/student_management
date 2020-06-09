package com.koko.service;

import com.koko.dto.AddUser;
import com.koko.entity.User;
import org.springframework.stereotype.Service;

public interface UserService {

    void addUser(AddUser addUser);

    void delUser(int account);

    void updateUser(User user);

    User selectUser(int account);
}
