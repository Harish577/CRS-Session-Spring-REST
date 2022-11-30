package com.hexagon.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexagon.service.StudentServiceImpl;

@RestController
public class StudentController
{
    @Autowired
    private StudentServiceImpl StudentService;

    @GetMapping("/students")
    public List getStudents()
    {
        return StudentService.listStudents();
    }

    @PostMapping("/students/{studentid}/{courseid}")
    public ResponseEntity addCource(@PathVariable Integer studentid, @PathVariable Integer courseid)//, @RequestBody Student student)
    {
        if (null == StudentService.getStudent(studentid))
        {
            return new ResponseEntity("No Student found for ID " + studentid, HttpStatus.NOT_FOUND);
        }
        else if (null == StudentService.getCourse(courseid))
        {
            return new ResponseEntity("No Course found for ID " + courseid, HttpStatus.NOT_FOUND);
        }

        StudentService.addCourse(studentid, courseid);

        return new ResponseEntity(studentid, HttpStatus.OK);
    }

    @DeleteMapping("/students/{studentid}/{courseid}")
    public ResponseEntity deleteCource(@PathVariable Integer studentid, @PathVariable Integer courseid)//, @RequestBody Student student)
    {
        if (null == StudentService.getStudent(studentid))
        {
            return new ResponseEntity("No Student found for ID " + studentid, HttpStatus.NOT_FOUND);
        }
        else if (null == StudentService.getCourse(courseid))
        {
            return new ResponseEntity("No Course found for ID " + courseid, HttpStatus.NOT_FOUND);
        }

        StudentService.dropCourse(studentid, courseid);

        return new ResponseEntity(studentid, HttpStatus.OK);
    }

    @PutMapping("/students/{studentid}/{isFeePaid}")
    public ResponseEntity payFee(@PathVariable Integer studentid, @PathVariable Boolean isFeePaid)//, @RequestBody Student student)
    {
        if (null == StudentService.getStudent(studentid))
        {
            return new ResponseEntity("No Student found for ID " + studentid, HttpStatus.NOT_FOUND);
        }

        StudentService.payFee(studentid, isFeePaid);

        return new ResponseEntity(studentid, HttpStatus.OK);
    }
}
