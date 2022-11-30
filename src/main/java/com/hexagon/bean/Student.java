package com.hexagon.bean;

import java.util.ArrayList;

public class Student
{

    private int                id;
    private String             firstName;
    private String             lastName;
    private String             email;
    private String             mobile;
    private ArrayList<Integer> courseList;
    private boolean            isFeePaid;

    public Student(int id, String firstName, String lastName, String email, String mobile, ArrayList<Integer> courseList, boolean isFeePaid)
    {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.mobile = mobile;
        this.courseList = courseList;
        this.isFeePaid = isFeePaid;
        //        this.dateOfBirth = new Date();
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getMobile()
    {
        return mobile;
    }

    public void setMobile(String mobile)
    {
        this.mobile = mobile;
    }

    public ArrayList<Integer> getCourseList()
    {
        return courseList;
    }

    public void setCourseList(ArrayList<Integer> courseList)
    {
        this.courseList = courseList;
    }

    public boolean isFeePaid()
    {
        return isFeePaid;
    }

    public void setFeePaid(boolean isFeePaid)
    {
        this.isFeePaid = isFeePaid;
    }
    
    //    public Student(String name, String college, Integer id, ArrayList<Course> courseList)
    //    {
    //        super();
    //        this.name = name;
    //        this.college = college;
    //        this.id = id;
    //        this.courseList = courseList;
    //    }

}
