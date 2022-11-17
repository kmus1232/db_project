package gov.example.appDemo.controller;

import gov.example.appDemo.domain.Student;
import gov.example.appDemo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StudentController {

    @Autowired
    StudentRepository studentRepository;

    @GetMapping("/students/degree")
    public String getStudentDegree(@RequestParam String name) {

        Integer studentCount = studentRepository.getCountByName(name);
        if (studentCount == 0) {
            return "No such student";
        } else if (studentCount > 1) {
            return "There are multiple students with the same name. Please provide an email address instead";
        } else {
            List<Student> studentsByName = studentRepository.getStudentsByName(name);
            Student result = studentsByName.get(0);
            return result.getName() + " : " + result.getDegree();
        }
    }

    @GetMapping("/students/email")
    public String getStudentEmail(@RequestParam String name) {

        Integer studentCount = studentRepository.getCountByName(name);
        if (studentCount == 0) {
            return "No such student";
        } else if (studentCount > 1) {
            return "There are multiple students with the same name. Please contact the administrator by phone.";
        } else {
            List<Student> studentsByName = studentRepository.getStudentsByName(name);
            Student result = studentsByName.get(0);
            return result.getName() + " : " + result.getEmail();
        }
    }

    @GetMapping("/students/stat")
    public String getNumberOfStudentsByDegree(@RequestParam String degree) {
        Integer countByDegree = studentRepository.getCountByDegree(degree);
        return degree + " : " + countByDegree;
    }

    @PutMapping("/students/register")
    public String registerStudent(@RequestParam String name,
                                  @RequestParam String email,
                                  @RequestParam Integer graduation,
                                  @RequestParam String degree) {
        int updatedRow = studentRepository.insert(new Student(name, email, graduation, degree));

        if (updatedRow != -1) {
            return "Registration successful";
        } else {
            return "Already registered";
        }
    }
}
