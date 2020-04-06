package repository;

import entity.Student;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StudentRepository extends Repository {

    @Override
    public List<Student> queryAll() {
        List<Student> students = new ArrayList<>();
        String query = "SELECT * FROM student";
        try (Statement statement = super.getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int number = resultSet.getInt("number");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String sex = resultSet.getString("sex");
                students.add(new Student(number, name, age, sex));
            }
            return students;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public void delete(String number) {
        String delete = "DELETE FROM student WHERE number = ?";
        try (PreparedStatement preparedStatement = super.getConnection().prepareStatement(delete)) {
            preparedStatement.setInt(1, Integer.parseInt(number));
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Student queryStudentByParameter(String querySql, String parameter) {
        try (PreparedStatement preparedStatement = super.getConnection().prepareStatement(querySql)) {
            preparedStatement.setString(1, parameter);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int number = resultSet.getInt("number");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String sex = resultSet.getString("sex");
                return new Student(number, name, age, sex);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Student queryStudentByNumber(int number) {
        String query = "SELECT * FROM student WHERE number = ?";
        return queryStudentByParameter(query, number + "");
    }

    public Student queryStudentByName(String studentName) {
        String query = "SELECT * FROM student WHERE name = ?";
        return queryStudentByParameter(query, studentName);

    }

    public List<Student> queryStudentsByTeacherName(String teacherName) {
        List<Student> students = new ArrayList<>();
        String query = "SELECT student.*, examination.score FROM student, examination " +
                "WHERE teacher_no = (SELECT number FROM teacher WHERE name = ?) AND " +
                "student.number = examination.student_no;";
        try (PreparedStatement preparedStatement = super.getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, teacherName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int number = resultSet.getInt("number");
                String studentName = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String sex = resultSet.getString("sex");
                float score = resultSet.getFloat("score");
                students.add(new Student(number, studentName, age, sex, score));
            }
            return students;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean insertStudent(Student student) {
        Student queriedStudent = queryStudentByNumber(student.getNumber());
        if (queriedStudent != null) {
            System.out.println("该用户已存在");
            return false;
        }
        String insert = "INSERT INTO student (number, name, age, sex) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = super.getConnection().prepareStatement(insert)) {
            preparedStatement.setInt(1, student.getNumber());
            preparedStatement.setString(2, student.getName());
            preparedStatement.setInt(3, student.getAge());
            preparedStatement.setString(4, student.getSex());
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int updateScoreBystudentNoAndCourseId(int studentNo, int courseId, float score) {

        String query = "UPDATE examination SET score = ? WHERE student_no = ? AND course_id = ?;";
        try (PreparedStatement preparedStatement = super.getConnection().prepareStatement(query)) {
            preparedStatement.setFloat(1, score);
            preparedStatement.setInt(2, studentNo);
            preparedStatement.setInt(3, courseId);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
