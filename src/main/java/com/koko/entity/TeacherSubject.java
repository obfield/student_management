package com.koko.entity;

import lombok.Data;

import javax.persistence.Table;

@Data
@Table
public class TeacherSubject {
    private Integer account;
    private Integer subjectId;
}
