package com.hexagon.bean;

public class Course
{

    private Integer    id;

    private String courseName;
    //    private String professor;
    //    private String department;

    public Integer getId()
    {
        return id;
    }

    public Course(Integer id, String courseName)
    {
        super();
        this.id = id;
        this.courseName = courseName;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getCourseName()
    {
        return courseName;
    }

    public void setCourseName(String courseName)
    {
        this.courseName = courseName;
    }
}
