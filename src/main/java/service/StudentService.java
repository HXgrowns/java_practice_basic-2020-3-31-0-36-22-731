package service;

import entity.Student;
import entity.Teacher;
import repository.StudentRepository;
import repository.TeacherRepository;
import utils.CheckUtils;

import java.util.List;
import java.util.Scanner;

public class StudentService {
    private final static String SELETED_OPERATE_ERROR = "输入的选项有误，请重新输入";

    private StudentRepository studentRepository = new StudentRepository();
    private TeacherRepository teacherRepository = new TeacherRepository();
    private Scanner scanner = new Scanner(System.in);

    public void queryAllStudent() {
        List<Student> students = studentRepository.queryAll();
        if (!CheckUtils.isExitEntity(students)) {
            return;
        }
        for (Student student : students) {
            System.out.println(student);
        }
    }


    public void queryAllStudentAndScoreByTeacher() {
        while (true) {
            System.out.println("请输入要查询的老师姓名");
            String teacherName = scanner.next();
            Teacher teacher = teacherRepository.queryTeacherByName(teacherName);
            if (teacher == null) {
                System.out.println("不存在该名老师，请重新输入");
                continue;
            }
            List<Student> students = studentRepository.queryStudentsByTeacherName(teacherName);
            if (!CheckUtils.isExitEntity(students)) {
                break;
            }
            for (Student student : students) {
                System.out.println(student + "，成绩：" + student.getScore());
            }
            break;
        }
    }

    public void insertStudent() {
        while (true) {
            System.out.println("请输入学生信息(例如：学号：1001，姓名： 小明, 年龄： 18, 性别： 男");
            String[] inputParameter = scanner.next().trim().split(",");
            if (inputParameter.length != 4) {
                System.out.println("信息有误，请重新输入");
                continue;
            }
            Student student = new Student(Integer.parseInt(inputParameter[0]), inputParameter[1], Integer.parseInt(inputParameter[2]), inputParameter[3]);
            if (studentRepository.insertStudent(student)) {
                System.out.format("添加学生[%s和%d]成功！\n", student.getName(), student.getNumber());
                break;
            }
            System.out.println("添加失败，请重新输入");
        }
    }

    public void updateScorebyStudentAndCourse() {
        while (true) {
            System.out.println("请输入要更新成绩，格式：学号，课程id，分数");
            String[] updateDatas = scanner.next().trim().split(",");
            int count = studentRepository.updateScoreBystudentNoAndCourseId(Integer.parseInt(updateDatas[0]), Integer.parseInt(updateDatas[1]), Float.parseFloat(updateDatas[2]));
            if (count == 0) {
                System.out.println("更新失败");
                continue;
            }
            System.out.println("更新成功");
            break;
        }
    }


    public void deleteStudent() {
        while (true) {
            System.out.println("请输入您需要删除的学生学号");
            String number = scanner.next().trim();
            Student student = studentRepository.queryStudentByNumber(Integer.parseInt(number));
            if (student == null) {
                System.out.println("该学号不存在，请重新输入");
                continue;
            }
            System.out.println("删除学生之后，该学生信息将不能恢复，是否要继续删除？\n1、是\n2、否");
            String isOrNo = scanner.next().trim();
            switch (isOrNo) {
                case "1":
                    studentRepository.delete(number);
                    System.out.format("删除学生[%s和%d]成功！\n", student.getName(), student.getNumber());
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
        studentRepository.closeConnection();
    }
}
