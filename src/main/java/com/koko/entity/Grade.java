package com.koko.entity;

import lombok.Data;

import javax.persistence.Table;

@Data
@Table
public class Grade {
    private Integer account;
    private Integer subjectId;
    private Integer score;
}
