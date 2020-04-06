package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public abstract class Repository {
    private Connection connection;

    public Repository() {
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/student_teacher_super?useUnicode=true&characterEncoding=utf-8&serverTimezone=Hongkong", "root", "root");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public abstract <T> List<T> queryAll();

    public abstract void delete(String parameter);

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        try {
            this.connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
