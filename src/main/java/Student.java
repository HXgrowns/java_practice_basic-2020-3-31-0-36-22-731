public class Student {
    private int number;
    private String name;
    private int age;
    private String sex;
    private float score;

    public Student(int number, String name, int age, String sex) {
        this.number = number;
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    public Student(int number, String name, int age, String sex, float score) {
        this.number = number;
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.score = score;
    }

    public float getScore() {
        return score;
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
        return "学号： " + number +
                ", 姓名：" + name +
                ", 年龄：" + age +
                ", 性别：" + sex;
    }
}
