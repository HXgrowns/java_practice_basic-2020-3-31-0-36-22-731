package service;

import entity.Course;
import entity.Student;
import entity.Teacher;
import repository.CourseRepository;
import repository.StudentRepository;
import repository.TeacherRepository;
import utils.CheckUtils;

import java.util.List;
import java.util.Scanner;

public class CourseService {
    private final static String SELETED_OPERATE_ERROR = "输入的选项有误，请重新输入";

    private Scanner scanner = new Scanner(System.in);
    private CourseRepository courseRepository = new CourseRepository();
    private StudentRepository studentRepository = new StudentRepository();
    private TeacherRepository teacherRepository = new TeacherRepository();

    public void queryAllCourse() {
        List<Course> courses = courseRepository.queryAll();
        if (courses.size() == 0) {
            System.out.println("没有查到任何数据");
            return;
        }
        for (Course cours : courses) {
            System.out.println(cours.getName());
        }
    }


    public void queryCourseByStudentName() {
        while (true) {
            System.out.println("请输入要查询的学生姓名");
            String studentName = scanner.next();
            Student student = studentRepository.queryStudentByName(studentName);
            if (student == null) {
                System.out.println("不存在该名同学，请重新输入");
                continue;
            }
            List<Course> courses = courseRepository.queryAllCourseByStudentName(studentName);
            if (!CheckUtils.isExitEntity(courses)) {
                break;
            }
            for (Course cours : courses) {
                System.out.println(cours);
            }
            break;
        }
    }


    public void queryCourseByteacher() {
        while (true) {
            System.out.println("请输入要查询的老师姓名");
            String teacherName = scanner.next();
            Teacher teacher = teacherRepository.queryTeacherByName(teacherName);
            if (teacher == null) {
                System.out.println("不存在该名老师，请重新输入");
                continue;
            }
            Course course = courseRepository.queryCourseByteacherName(teacherName);
            if (course == null) {
                System.out.println("没有查到任何数据");
                break;
            }
            System.out.println(course.getName());
            break;
        }
    }

    public void insertCourse() {
        while (true) {
            System.out.println("请输入要插入的课程名");
            String course = scanner.next().trim();
            if (courseRepository.insertCourse(course)) {
                System.out.format("添加课程[%s]成功！\n", course);
                break;
            }
            System.out.println("添加失败，请重新输入");
        }
    }

    public void deleteCourse() {
        while (true) {
            System.out.println("请输入您需要删除的课程名");
            String name = scanner.next().trim();
            Course course = courseRepository.queryCourseByCourseName(name);
            if (course == null) {
                System.out.println("该课程不存在，请重新输入");
                continue;
            }
            System.out.println("删除课程之后，该课程信息将不能恢复，是否要继续删除？\n1、是\n2、否");
            String isOrNo = scanner.next().trim();
            switch (isOrNo) {
                case "1":
                    courseRepository.delete(course.getName());
                    System.out.format("删除课程[%s]成功！\n", course.getName());
                    break;
                case "2":
                    break;
                default:
                    System.out.println(SELETED_OPERATE_ERROR);
                    break;
            }
            break;
        }
    }

    public void closeConnection() {
        courseRepository.closeConnection();
    }
}
