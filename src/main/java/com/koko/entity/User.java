package com.koko.entity;

import lombok.Data;

@Data
public class User {
    private int username;
    private String password;
    private String passwordSalt;
    private String name;
    private int age;
    private int sex;
    private String address;
    private String avatar;
}
