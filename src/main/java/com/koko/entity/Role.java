package com.koko.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Table;

@Data
@Table
public class Role {
    @GeneratedValue(generator = "JDBC")
    private Integer roleId;
    private String roleName;
}
