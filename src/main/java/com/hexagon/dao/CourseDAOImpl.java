package com.hexagon.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.hexagon.bean.Course;
import com.hexagon.bean.Student;

@Repository
public class CourseDAOImpl //implements CourseDAOInterface
{
    private static List<Course> courses;
    {
        courses = new ArrayList<Course>();
        courses.add(new Course(101, "Java"));
        courses.add(new Course(102, "ExtJS"));
        courses.add(new Course(103, "NodeJS"));
        courses.add(new Course(104, "REACT"));
        courses.add(new Course(105, "JSP/Servlets"));
        courses.add(new Course(106, "JDBC"));
        courses.add(new Course(107, "Angular"));
        courses.add(new Course(108, "HTML"));
        courses.add(new Course(109, "CSS"));
        courses.add(new Course(110, "Vue.js"));
        courses.add(new Course(111, "Hibernate"));
        courses.add(new Course(112, "Spring"));
        courses.add(new Course(113, "Springboot"));
        courses.add(new Course(114, "JMS"));
        courses.add(new Course(115, "TIBCO"));
    }

    /**
     * Return customer object for given id from dummy database. If customer is not found for id, returns null.
     * 
     * @param id Course id
     * @return Course object for given id
     */
    public Course getCourse(Integer id)
    {
        for (Course c : courses)
        {
            if (c.getId().equals(id))
            {
                return c;
            }
        }
        return null;
    }
}
