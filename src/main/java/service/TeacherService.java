package service;

import entity.Teacher;
import repository.TeacherRepository;
import utils.CheckUtils;

import java.util.List;
import java.util.Scanner;

public class TeacherService {
    private final static String SELETED_OPERATE_ERROR = "输入的选项有误，请重新输入";
    private Scanner scanner = new Scanner(System.in);
    private TeacherRepository teacherRepository = new TeacherRepository();

    public void queryAllTeacher() {
        List<Teacher> teachers = teacherRepository.queryAll();
        if (!CheckUtils.isExitEntity(teachers)) {
            return;
        }
        for (Teacher teacher : teachers) {
            System.out.println(teacher);
        }
    }

    public void queryAllByTeacher() {
        while (true) {
            System.out.println("请输入要查询的老师姓名");
            String teacherName = scanner.next();
            Teacher teacher = teacherRepository.queryTeacherByName(teacherName);
            if (teacher == null) {
                System.out.println("不存在该名老师，请重新输入");
                continue;
            }
            System.out.println(teacher);
            break;
        }
    }

    public void deleteTeacher() {
        while (true) {
            System.out.println("请输入您需要删除的老师");
            String name = scanner.next().trim();
            Teacher teacher = teacherRepository.queryTeacherByName(name);
            if (teacher == null) {
                System.out.println("该位老师不存在，请重新输入");
                continue;
            }
            System.out.println("删除老师之后，该老师信息将不能恢复，是否要继续删除？\n1、是\n2、否");
            String isOrNo = scanner.next().trim();
            switch (isOrNo) {
                case "1":
                    teacherRepository.delete(teacher.getName());
                    System.out.format("删除老师[%s]成功！\n", teacher.getName());
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
        teacherRepository.closeConnection();
    }
}
