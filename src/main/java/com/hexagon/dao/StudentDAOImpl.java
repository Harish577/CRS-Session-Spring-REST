package com.hexagon.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class StudentDAOImpl implements StudentDAOInterface
{

    private String              name;
    private String              college;
    private Integer             id;
    private List<CourseDAOImpl> courseList;

    public StudentDAOImpl(String name, String college, Integer id, List<CourseDAOImpl> courseList)
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

    public List<CourseDAOImpl> getCourseList()
    {
        return courseList;
    }

    public void setCourse(List<CourseDAOImpl> courseList)
    {
        this.courseList = courseList;
    }

}
