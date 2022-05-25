package com.example.springsecuritylearning.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Student {
    private final Integer studentId;
    private final String studentName;

}
