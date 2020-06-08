package com.koko.entity;

import lombok.Data;

import javax.persistence.Table;

@Data
@Table
public class UserSubject {
    private Integer account;
    private Integer subjectId;
}
