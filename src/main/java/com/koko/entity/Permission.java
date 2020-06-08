package com.koko.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Table;

@Data
@Table
public class Permission {
    @GeneratedValue(generator = "JDBC")
    private Integer permissionId;
    private String permissionCode;
    private String permissionName;
}
