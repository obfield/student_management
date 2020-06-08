package com.koko.entity;

import lombok.Data;

import javax.persistence.Table;

@Data
@Table
public class User {
    private Integer account;
    private String password;
    private String passwordSalt;
    private String name;
    private Integer age;
    private Integer sex;
    private String address;
    private String avatar;
}
