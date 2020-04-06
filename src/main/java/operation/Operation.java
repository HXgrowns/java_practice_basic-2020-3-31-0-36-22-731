package operation;

import service.CourseService;
import service.StudentService;
import service.TeacherService;

public class Operation {
    private StudentService studentService = new StudentService();
    private CourseService courseService = new CourseService();
    private TeacherService teacherService = new TeacherService();

    public void queryAllStudent() {
        studentService.queryAllStudent();
    }

    public void queryAllCourseByStudentName() {
        courseService.queryCourseByStudentName();
    }

    public void queryAllStudentAndScoreByTeacher() {
        studentService.queryAllStudentAndScoreByTeacher();
    }

    public void queryAllCourse() {
        courseService.queryAllCourse();
    }

    public void queryByCourseName() {
        courseService.queryAllCourse();
    }

    public void queryCourseByteacher() {
        courseService.queryCourseByteacher();
    }

    public void queryAllTeacher() {
        teacherService.queryAllTeacher();
    }

    public void queryByTeacherName() {
        teacherService.queryAllByTeacher();
    }

    public void insertStudent() {
        studentService.insertStudent();
    }

    public void insertCourse() {
        courseService.insertCourse();
    }

    public void updateScorebyStudentAndCourse() {
        studentService.updateScorebyStudentAndCourse();
    }

    public void deleteStudent() {
        studentService.deleteStudent();
    }

    public void deleteCourse() {
        courseService.deleteCourse();
    }

    public void deleteTeacher() {
        teacherService.deleteTeacher();
    }

    public void closeConnection() {
        studentService.closeConnection();
    }
}
