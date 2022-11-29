package com.hexagon.bean;

import java.util.List;

public class Student
{

    private String       name;
    private String       college;
    private Integer      id;
    private List<Course> courseList;

    public Student(String name, String college, Integer id, List<Course> courseList)
    {
        super();
        this.name = name;
        this.college = college;
        this.id = id;
        this.courseList = courseList;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getCollege()
    {
        return college;
    }

    public void setCollege(String college)
    {
        this.college = college;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public List<Course> getCourseList()
    {
        return courseList;
    }

    public void setCourse(List<Course> courseList)
    {
        this.courseList = courseList;
    }

}
