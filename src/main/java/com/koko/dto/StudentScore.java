package com.koko.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component("StudentScore")
public class StudentScore {
    private int account;
    private String name;
    private String subjectName;
    private int score;
}
