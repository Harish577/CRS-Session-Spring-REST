package com.hexagon.service;

import java.util.ArrayList;
import java.util.List;

import com.hexagon.bean.Course;
import com.hexagon.bean.Student;

public interface StudentServiceInterface
{
    public void addCourse(int studentID, int courseID);

    public void dropCourse(int studentID, int courseID);

    //    public Course getCourse();

    public ArrayList<Integer> getCourses(int studentID);

    public void registration(Student student);

    public void payFee(int studentID, boolean isPaid);

    public List listStudents();
    //    studentobj.getcourselist.add();
}
