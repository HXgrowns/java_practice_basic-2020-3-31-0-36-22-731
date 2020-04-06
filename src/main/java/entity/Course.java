package entity;

public class Course {
    private String name;
    private double score;
    private Student student;

    public Course(String name) {
        this.name = name;
    }

    public Course(String name, double score, Student student) {
        this.name = name;
        this.score = score;
        this.student = student;
    }

    public Student getStudent() {
        return student;
    }

    public String getName() {
        return name;
    }

    public double getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "" + student + '\'' +
                ", 课程名：" + name +
                ", 成绩：" + score;
    }
}
