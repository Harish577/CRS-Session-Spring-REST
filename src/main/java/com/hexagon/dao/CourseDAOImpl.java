package com.hexagon.dao;

import org.springframework.stereotype.Repository;

@Repository
public class CourseDAOImpl implements CourseDAOInterface
{

    private String courseName;
    private String professor;
    private String department;

    public String getCourseName()
    {
        return courseName;
    }

    public void setCourseName(String courseName)
    {
        this.courseName = courseName;
    }

    public String getProfessor()
    {
        return professor;
    }

    public void setProfessor(String professor)
    {
        this.professor = professor;
    }

    public String getDepartment()
    {
        return department;
    }

    public void setDepartment(String department)
    {
        this.department = department;
    }

    public CourseDAOImpl(String courseName, String professor, String department)
    {
        super();
        this.courseName = courseName;
        this.professor = professor;
        this.department = department;

    }
}
