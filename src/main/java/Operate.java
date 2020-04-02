import java.util.List;
import java.util.Scanner;

public class Operate {
    private final static String SELETED_OPERATE_ERROR = "输入的选项有误，请重新输入";
    private final static String INSERT_INFO = "请输入学生信息(例如：学号：1001，姓名： 小明, 年龄： 18, 性别： 男):";
    private final static String PARAMETER_ERROR = "信息有误，请重新输入";


    private Scanner scanner = new Scanner(System.in);
    private DataRepository dataRepository = new DataRepository();

    public void operate1_1_1() {
        List<Student> students = dataRepository.queryAllStudent();
        if (students.size() == 0) {
            System.out.println("没有查到任何数据");
            return;
        }
        for (Student student : students) {
            System.out.println(student);
        }
    }

    public void operate1_1_2() {
        while (true) {
            System.out.println("请输入要查询的学生姓名");
            String studentName = scanner.next();
            Student student = dataRepository.queryStudentByName(studentName);
            if (student == null) {
                System.out.println("不存在该名同学，请重新输入");
                continue;
            }
            List<Course> courses = dataRepository.queryAllCourseByName(studentName);
            if (courses.size() == 0) {
                System.out.println("没有查到任何数据");
                break;
            }
            for (Course cours : courses) {
                System.out.println(cours);
            }
            break;
        }
    }

    public void operate1_1_3() {
        while (true) {
            System.out.println("请输入要查询的老师姓名");
            String teacherName = scanner.next();
            Teacher teacher = dataRepository.queryTeacherByName(teacherName);
            if (teacher == null) {
                System.out.println("不存在该名老师，请重新输入");
                continue;
            }
            List<Student> students = dataRepository.queryStudentsByTeacherName(teacherName);
            if (students.size() == 0) {
                System.out.println("没有查到任何数据");
                break;
            }
            for (Student student : students) {
                System.out.println(student + "，成绩：" + student.getScore());
            }
            break;
        }
    }

    public void operate1_2_1() {
        List<Course> courses = dataRepository.queryAllCourse();
        if (courses.size() == 0) {
            System.out.println("没有查到任何数据");
            return;
        }
        for (Course cours : courses) {
            System.out.println(cours.getName());
        }
    }

    public void operate1_2_3() {
        while (true) {
            System.out.println("请输入要查询的老师姓名");
            String teacherName = scanner.next();
            Teacher teacher = dataRepository.queryTeacherByName(teacherName);
            if (teacher == null) {
                System.out.println("不存在该名老师，请重新输入");
                continue;
            }
            Course course = dataRepository.queryCourseByteacherName(teacherName);
            if (course == null) {
                System.out.println("没有查到任何数据");
                break;
            }
            System.out.println(course.getName());
            break;
        }
    }

    public void operate1_3_1() {
        List<Teacher> teachers = dataRepository.queryAllTeacher();
        if (teachers.size() == 0) {
            System.out.println("没有查到任何数据");
            return;
        }
        for (Teacher teacher : teachers) {
            System.out.println(teacher);
        }
    }

    public void operate1_3_2() {
        while (true) {
            System.out.println("请输入要查询的老师姓名");
            String teacherName = scanner.next();
            Teacher teacher = dataRepository.queryTeacherByName(teacherName);
            if (teacher == null) {
                System.out.println("不存在该名老师，请重新输入");
                continue;
            }
            System.out.println(teacher);
            break;
        }
    }


    public void operate2_1() {
        while (true) {
            System.out.println(INSERT_INFO);
            String[] inputParameter = scanner.next().trim().split(",");
            if (!CheckUtils.checkInsertParameter(inputParameter)) {
                System.out.println(PARAMETER_ERROR);
                continue;
            }
            Student student = new Student(Integer.parseInt(inputParameter[0]), inputParameter[1], Integer.parseInt(inputParameter[2]), inputParameter[3]);
            if (dataRepository.insertStudent(student)) {
                System.out.format("添加学生[%s和%d]成功！\n", student.getName(), student.getNumber());
                break;
            }
            System.out.println("添加失败，请重新输入");
        }
    }

    public void operate2_2() {
        while (true) {
            System.out.println("请输入要插入的课程名");
            String course = scanner.next().trim();
            if (dataRepository.insertCourse(course)) {
                System.out.format("添加课程[%s]成功！\n", course);
                break;
            }
            System.out.println("添加失败，请重新输入");
        }
    }

    public void operate3_1() {
        while (true) {
            System.out.println("请输入要更新成绩，格式：学号，课程id，分数");
            String[] updateDatas = scanner.next().trim().split(",");
            int count = dataRepository.updateScoreBystudentNoAndCourseId(Integer.parseInt(updateDatas[0]), Integer.parseInt(updateDatas[1]), Float.parseFloat(updateDatas[2]));
            if (count == 0) {
                System.out.println("更新失败");
                continue;
            }
            System.out.println("更新成功");
            break;
        }
    }

    public void operate4_1() {
        while (true) {
            System.out.println("请输入您需要删除的学生学号");
            String number = scanner.next().trim();
            Student student = dataRepository.queryStudentByNumber(Integer.parseInt(number));
            if (student == null) {
                System.out.println("该学号不存在，请重新输入");
                continue;
            }
            System.out.println("删除学生之后，该学生信息将不能恢复，是否要继续删除？\n1、是\n2.否");
            String isOrNo = scanner.next().trim();
            switch (isOrNo) {
                case "1":
                    dataRepository.deleteStudent(Integer.parseInt(number));
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

    public void operate4_2() {
        while (true) {
            System.out.println("请输入您需要删除的课程名");
            String name = scanner.next().trim();
            Course course = dataRepository.queryCourseByCourseName(name);
            if (course == null) {
                System.out.println("该课程不存在，请重新输入");
                continue;
            }
            System.out.println("删除课程之后，该课程信息将不能恢复，是否要继续删除？\n1、是\n2.否");
            String isOrNo = scanner.next().trim();
            switch (isOrNo) {
                case "1":
                    dataRepository.deleteCourse(course.getName());
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

    public void operate4_3() {
        while (true) {
            System.out.println("请输入您需要删除的老师");
            String name = scanner.next().trim();
            Teacher teacher = dataRepository.queryTeacherByName(name);
            if (teacher == null) {
                System.out.println("该位老师不存在，请重新输入");
                continue;
            }
            System.out.println("删除老师之后，该老师信息将不能恢复，是否要继续删除？\n1、是\n2.否");
            String isOrNo = scanner.next().trim();
            switch (isOrNo) {
                case "1":
                    dataRepository.deleteTeacher(teacher.getName());
                    System.out.format("删除课程[%s]成功！\n", teacher.getName());
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

    public void closeConnect() {
        dataRepository.closeConnection();
    }
}
