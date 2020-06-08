package com.koko.entity;

import lombok.Data;

import javax.persistence.Table;

@Data
@Table
public class UserRole {
    private Integer account;
    private Integer roleId;
}
