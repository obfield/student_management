package com.koko.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table
public class Permission {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer permissionId;
    private String permissionCode;
    private String permissionName;
}
