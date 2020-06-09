package com.koko.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table
public class Subject {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer subjectId;
    private String subjectName;
}
