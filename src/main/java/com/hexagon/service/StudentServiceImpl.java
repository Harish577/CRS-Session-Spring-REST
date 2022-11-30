package com.hexagon.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexagon.bean.Course;
import com.hexagon.bean.Student;
import com.hexagon.dao.CourseDAOImpl;
import com.hexagon.dao.StudentDAOImpl;

@Service
public class StudentServiceImpl implements StudentServiceInterface
{

    @Autowired
    private StudentDAOImpl studentDAO;
    @Autowired
    private CourseDAOImpl  CourseDAO;

    @Override
    public void addCourse(int studentID, int courseID)
    {
        Student student = studentDAO.getStudent(studentID);
        ArrayList<Integer> courseList = student.getCourseList();
        courseList.add(courseID);
        student.setCourseList(courseList);
    }

    @Override
    public void dropCourse(int studentID, int courseID)
    {
        Student student = studentDAO.getStudent(studentID);
        ArrayList<Integer> courseList = student.getCourseList();
        courseList.remove(courseID);
        student.setCourseList(courseList);
    }

    @Override
    public ArrayList<Integer> getCourses(int studentID)
    {
        Student student = studentDAO.getStudent(studentID);
        return student.getCourseList();
    }

    @Override
    public void registration(Student student)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void payFee(int studentID, boolean isPaid)
    {
        Student student = studentDAO.getStudent(studentID);
        student.setFeePaid(isPaid);
    }

    /**
     * Returns list of customers from dummy database.
     * 
     * @return list of customers
     */
    public List listStudents()
    {
        return studentDAO.listStudents();
    }

    public Student getStudent(int studentID)
    {
        return studentDAO.getStudent(studentID);
    }
    
    public Course getCourse(int courseID)
    {
        return CourseDAO.getCourse(courseID);
    }
}
