package com.example.springsecuritylearning.Controller;

import com.example.springsecuritylearning.model.Student;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.Arrays;
import java.util.List;

@RequestMapping("api/v1/student")
@RestController
public class StudentController {
    private static final List<Student> student_list = Arrays.asList(
            new Student(1, "anna smith"),
            new Student(2, "Edi"),
            new Student(3, ""),
            new Student(4, "James")
    );

    @GetMapping("/{studentId}")
    public Student getStudent(@PathVariable("studentId") Integer studentId){
        return student_list.stream()
                .filter(student -> studentId.equals(student.getStudentId()))
                .findFirst()
                .orElseThrow(()-> new IllegalStateException("Student " + studentId + "does not exist"));
    }
}
