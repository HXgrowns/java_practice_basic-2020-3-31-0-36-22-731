package repository;

import entity.Course;
import entity.Student;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import java.util.List;

public class CourseRepository extends Repository {

    @Override
    public List<Course> queryAll() {
        List<Course> courses = new ArrayList<>();
        String query = "SELECT * FROM course";
        try (Statement statement = super.getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                courses.add(new Course(name));
            }
            return courses;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public void delete(String id) {
        String delete = "DELETE FROM course WHERE id = ?";
        try (PreparedStatement preparedStatement = super.getConnection().prepareStatement(delete)) {
            preparedStatement.setInt(1, Integer.parseInt(id));
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Course queryCourseByName(String querySql, String name) {
        try (PreparedStatement preparedStatement = super.getConnection().prepareStatement(querySql)) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String courseName = resultSet.getString("name");
                return new Course(courseName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Course queryCourseByCourseName(String courseName) {
        String query = "SELECT * FROM course WHERE name = ?";
        return queryCourseByName(query, courseName);
    }

    public Course queryCourseByteacherName(String teacherName) {
        String query = "SELECT course.* FROM course WHERE id = (SELECT course_id FROM teacher WHERE name = ?)";
        return queryCourseByName(query, teacherName);
    }

    public List<Course> queryAllCourseByStudentName(String studentName) {
        List<Course> courses = new ArrayList<>();
        String query = "SELECT student.*, course.name AS course_name, examination.score FROM student, course, examination " +
                "WHERE student.name = ? AND " +
                "student.number = examination.student_no " +
                "AND course.id  = examination.course_id";

        try (PreparedStatement preparedStatement = super.getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, studentName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int number = resultSet.getInt("number");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String sex = resultSet.getString("sex");
                Student student = new Student(number, name, age, sex);
                String course_name = resultSet.getString("course_name");
                float score = resultSet.getFloat("score");
                courses.add(new Course(course_name, score, student));
            }
            return courses;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    public boolean insertCourse(String courseName) {
        Course queriedCourse = queryCourseByCourseName(courseName);
        if (queriedCourse != null) {
            System.out.println("该科目已存在");
            return false;
        }
        String insert = "INSERT INTO course (name) VALUES (?)";
        try (PreparedStatement preparedStatement = super.getConnection().prepareStatement(insert)) {
            preparedStatement.setString(1, courseName);
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void closeConnection() {
        super.closeConnection();
    }

}
