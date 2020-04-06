package repository;

import entity.Teacher;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TeacherRepository extends Repository {

    @Override
    public List<Teacher> queryAll() {
        List<Teacher> teachers = new ArrayList<>();
        String query = "SELECT teacher.*, course.name AS course_name FROM teacher, course WHERE teacher.course_id = course.id";
        try (Statement statement = super.getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int number = resultSet.getInt("number");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String sex = resultSet.getString("sex");
                String courseName = resultSet.getString("course_name");
                teachers.add(new Teacher(number, name, age, sex, courseName));
            }
            return teachers;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public void delete(String teacherName) {
        String delete = "DELETE FROM teacher WHERE name = ?";
        try (PreparedStatement preparedStatement = super.getConnection().prepareStatement(delete)) {
            preparedStatement.setString(1, teacherName);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Teacher queryTeacherByName(String teacherName) {
        String query = "SELECT teacher.*, course.name AS course_name FROM teacher, course WHERE teacher.course_id = course.id AND teacher.name = ?";
        try (PreparedStatement preparedStatement = super.getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, teacherName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int number = resultSet.getInt("number");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String sex = resultSet.getString("sex");
                String courseName = resultSet.getString("course_name");
                return new Teacher(number, name, age, sex, courseName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
