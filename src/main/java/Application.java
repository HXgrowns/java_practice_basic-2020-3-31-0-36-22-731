import java.util.Scanner;

public class Application {
    private final static String HOME_INFO = "您好，欢迎登陆学生考试系统，请输入您的用户名和密码(用户名:密码)：   \n" +
            "例如：张三:123";
    private final static String SUPER_USER_INFO = "您好，超级管理员，请选择你需要进行的操作：\n" +
            "    1. 查询   \n" +
            "      1.1 查询学生信息以及成绩  \n" +
            "        1.1.1 所有学生信息  \n" +
            "        1.1.2 指定学生姓名的信息以及所有课程的成绩  \n" +
            "        1.1.3 指定老师的所有学生及其成绩  \n" +
            "      1.2 查询课程信息  \n" +
            "        1.2.1 所有课程信息  \n" +
            "        1.2.2 指定课程名称的信息  \n" +
            "        1.2.3 指定老师的所有课程信息   \n" +
            "      1.3 查询老师信息  \n" +
            "        1.3.1 所有老师信息  \n" +
            "        1.3.2 指定老师信息  \n" +
            "    2. 新增  \n" +
            "      2.1 新增学生信息  \n" +
            "      2.2 新增课程信息     \n" +
            "    3. 修改    \n" +
            "      3.1 修改指定学生的成绩  \n" +
            "    4. 删除  \n" +
            "      4.1 删除指定学生  \n" +
            "      4.2 删除指定课程  \n" +
            "      4.3 删除指定老师 ";
    private final static String USER_PASSWORD_ERROR = "用户名或密码输入错误，请重新输入";

    public static void main(String[] args) {
        System.out.println(HOME_INFO);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String[] loginUser = scanner.next().trim().split(":");
            if (!CheckUtils.checkInputFormat(loginUser)) {
                System.out.println(USER_PASSWORD_ERROR);
                continue;
            }
            break;
        }
        System.out.println(SUPER_USER_INFO);

        boolean isLoop = true;
        Operate operate = new Operate();
        while (isLoop) {
            String selectedOperate = scanner.next().trim();
            switch (selectedOperate) {
                case "1.1.1":
                    operate.operate1_1_1();
                    break;
                case "1.1.2":
                    operate.operate1_1_2();
                    break;
                case "1.1.3":
                    operate.operate1_1_3();
                    break;
                case "1.2.1":
                    operate.operate1_2_1();
                    break;
                case "1.2.2":
                    operate.operate1_2_1();
                    break;
                case "1.2.3":
                    operate.operate1_2_3();
                    break;
                case "1.3.1":
                    operate.operate1_3_1();
                    break;
                case "1.3.2":
                    operate.operate1_3_2();
                    break;
                case "2.1":
                    operate.operate2_1();
                    break;
                case "2.2":
                    operate.operate2_2();
                    break;
                case "3.1":
                    operate.operate3_1();
                    break;
                case "4.1":
                    operate.operate4_1();
                    break;
                case "4.2":
                    operate.operate4_2();
                    break;
                case "4.3":
                    operate.operate4_3();
                    break;
                default:
                    isLoop = false;
                    break;
            }
        }
        operate.closeConnect();
    }

}