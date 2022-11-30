package com.hexagon.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.hexagon.bean.Student;

@Repository
public class StudentDAOImpl //implements StudentDAOInterface
{

    private static List<Student> students;
    {
        students = new ArrayList<Student>();
        ArrayList<Integer> CourseList1 = new ArrayList<Integer>();
        CourseList1.add(101);
        CourseList1.add(102);
        CourseList1.add(103);
        CourseList1.add(104);
        ArrayList<Integer> CourseList2 = new ArrayList<Integer>();
        CourseList2.add(101);
        CourseList2.add(105);
        CourseList2.add(106);
        CourseList2.add(107);
        ArrayList<Integer> CourseList3 = new ArrayList<Integer>();
        CourseList3.add(101);
        CourseList3.add(108);
        CourseList3.add(109);
        CourseList3.add(110);
        ArrayList<Integer> CourseList4 = new ArrayList<Integer>();
        CourseList4.add(101);
        CourseList4.add(111);
        CourseList4.add(112);
        CourseList4.add(113);
        students.add(new Student(101, "John", "Doe", "djohn@gmail.com", "121-232-3435", CourseList1, true));
        students.add(new Student(102, "Russ", "Smith", "sruss@gmail.com", "343-545-2345", CourseList2, true));
        students.add(new Student(103, "Kate", "Williams", "kwilliams@gmail.com", "876-237-2987", CourseList3, true));
        students.add(new Student(104, "Viral", "Patel", "vpatel@gmail.com", "356-758-8736", CourseList4, false));
    }

    /**
     * Return customer object for given id from dummy database. If customer is not found for id, returns null.
     * 
     * @param id Student id
     * @return Student object for given id
     */
    public Student getStudent(Integer id)
    {

        for (Student s : students)
        {
            if (s.getId().equals(id))
            {
                return s;
            }
        }
        return null;
    }

    /**
     * Returns list of customers from dummy database.
     * 
     * @return list of customers
     */
    public List listStudents()
    {
        return students;
    }
}
