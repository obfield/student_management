package com.koko.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddUser {
    private Integer account;
    private String password;
    private String passwordSalt;
    private String name;
    private Integer age;
    private Integer sex;
    private String address;
    private String avatar;
    private Integer roleId;
}
