package com.hexagon.bean;

public class Course
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

    public Course(String courseName, String professor, String department)
    {
        super();
        this.courseName = courseName;
        this.professor = professor;
        this.department = department;

    }
}
