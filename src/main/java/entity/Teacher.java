package entity;

public class Teacher {
    private int number;
    private String name;
    private int age;
    private String sex;
    private String courseName;

    public Teacher(int number, String name, int age, String sex, String courseName) {
        this.number = number;
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.courseName = courseName;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getSex() {
        return sex;
    }

    @Override
    public String toString() {
        return "工号： " + number +
                ", 姓名：" + name +
                ", 年龄：" + age +
                ", 性别：" + sex +
                ", 授课科目：" + courseName;
    }
}
