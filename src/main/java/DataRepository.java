import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataRepository {
    private Connection connection;

    public DataRepository() {
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/student_teacher_super?useUnicode=true&characterEncoding=utf-8&serverTimezone=Hongkong", "root", "root");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Student> queryAllStudent() {
        List<Student> students = new ArrayList<>();
        String query = "SELECT * FROM student";
        try (Statement statement = this.connection.createStatement()) {
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

    public List<Teacher> queryAllTeacher() {
        List<Teacher> teachers = new ArrayList<>();
        String query = "SELECT teacher.*, course.name AS course_name FROM teacher, course WHERE teacher.course_id = course.id";
        try (Statement statement = this.connection.createStatement()) {
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

    public Student queryStudentByNumber(int number) {
        String query = "SELECT * FROM student WHERE number = ?";
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(query)) {
            preparedStatement.setInt(1, number);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
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

    public Teacher queryTeacherByName(String teacherName) {
        String query = "SELECT teacher.*, course.name AS course_name FROM teacher, course WHERE teacher.course_id = course.id AND teacher.name = ?";
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(query)) {
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

    public Student queryStudentByName(String studentName) {
        String query = "SELECT * FROM student WHERE name = ?";
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(query)) {
            preparedStatement.setString(1, studentName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int number = resultSet.getInt("number");
                int age = resultSet.getInt("age");
                String sex = resultSet.getString("sex");
                return new Student(number, studentName, age, sex);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public List<Student> queryStudentsByTeacherName(String teacherName) {
        List<Student> students = new ArrayList<>();
        String query = "SELECT student.*, examination.score FROM student, examination " +
                "WHERE teacher_no = (SELECT number FROM teacher WHERE name = ?) AND " +
                "student.number = examination.student_no;";
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(query)) {
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

    public List<Course> queryAllCourseByName(String studentName) {
        List<Course> courses = new ArrayList<>();
        String query = "SELECT student.*, course.name AS course_name, examination.score FROM student, course, examination " +
                "WHERE student.name = ? AND " +
                "student.number = examination.student_no " +
                "AND course.id  = examination.course_id";

        try (PreparedStatement preparedStatement = this.connection.prepareStatement(query)) {
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

    public boolean insertStudent(Student student) {
        Student queriedStudent = queryStudentByNumber(student.getNumber());
        if (queriedStudent != null) {
            System.out.println("该用户已存在");
            return false;
        }
        String insert = "INSERT INTO student (number, name, age, sex) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(insert)) {
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

    public Course queryCourseByCourseName(String courseName) {
        String query = "SELECT * FROM course WHERE name = ?";
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(query)) {
            preparedStatement.setString(1, courseName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                return new Course(name);
            }
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
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(insert)) {
            preparedStatement.setString(1, courseName);
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void deleteStudent(int number) {
        String delete = "DELETE FROM student WHERE number = ?";
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(delete)) {
            preparedStatement.setInt(1, number);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Course> queryAllCourse() {
        List<Course> courses = new ArrayList<>();
        String query = "SELECT * FROM course";
        try (Statement statement = this.connection.createStatement()) {
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

    public Course queryCourseByteacherName(String teacherName) {
        String query = "SELECT course.* FROM course WHERE id = (SELECT course_id FROM teacher WHERE name = ?)";
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(query)) {
            preparedStatement.setString(1, teacherName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                return new Course(name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int updateScoreBystudentNoAndCourseId(int studentNo, int courseId, float score) {

        String query = "UPDATE examination SET score = ? WHERE student_no = ? AND course_id = ?;";
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(query)) {
            preparedStatement.setFloat(1, score);
            preparedStatement.setInt(2, studentNo);
            preparedStatement.setInt(3, courseId);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void deleteCourse(String name) {
        String delete = "DELETE FROM course WHERE name = ?";
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(delete)) {
            preparedStatement.setString(1, name);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteTeacher(String name) {
        String delete = "DELETE FROM teacher WHERE name = ?";
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(delete)) {
            preparedStatement.setString(1, name);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            this.connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}