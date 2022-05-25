package com.example.springsecuritylearning.Controller;

import com.example.springsecuritylearning.model.Student;
import org.checkerframework.checker.regex.qual.PartialRegex;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.swing.plaf.PanelUI;
import java.util.Arrays;
import java.util.List;

import static com.example.springsecuritylearning.security.ApplicationUserRole.STUDENT;

@RequestMapping("/management/api/v1/student")
@RestController
public class StudentManagmentController {

    private static final List<Student> student_list = Arrays.asList(
            new Student(1, "anna smith"),
            new Student(2, "Edi"),
            new Student(3, ""),
            new Student(4, "James")
    );
    // Methode based authority permissions
    // hasRole('ROLE_') hasAnyRole('ROLE_), hasAuthority('permision) hasAnyAuthority('permission')
    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMINTRAINEE')")
    public List<Student> getAllStudent(){
        return student_list;
    }
    @PostMapping
    @PreAuthorize("hasAuthority('student:write')")
    public void registerNewStudent(@RequestBody Student student){
        System.out.println(student);
    }
    @DeleteMapping("{studentId}")
    @PreAuthorize("hasAuthority('student:write')")
    public void deleteStudent(@PathVariable("studentId") Integer studentId){
        System.out.println(studentId);
    }
    @PutMapping("{studentId}")
    @PreAuthorize("hasAuthority('student:write')")
    private void updateStudent(@PathVariable("studentId") Integer studentId,
                               @RequestBody Student student){
        System.out.println(String.format("%s %s",studentId,student));
    }
}
