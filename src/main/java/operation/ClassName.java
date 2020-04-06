package operation;

public enum ClassName {
    DELETE_COURSE("4.2", "DeleteCourse"),
    DELETE_STUDENT("4.1", "DeleteStudent"),
    DELETE_TEACHER("4.3", "DeleteTeacher"),

    INSERT_COURSE("2.2", "InsertCourse"),
    INSERT_STUDENT("2.1", "InsertStudent"),

    QUERYALLBYTEACHER("1.3.2", "QueryAllByTeacher"),
    QUERYALLCOURSE("1.2.1", "QueryAllCourse"),
    QUERYALLSTUDENT("1.1.1", "QueryAllStudent"),
    QUERYALLSTUDENTANDSCOREBYTEACHER("1.1.3", "QueryAllStudentAndScoreByTeacher"),
    QUERYALLTEACHER("1.3.1", "QueryAllTeacher"),
    QUERYCOURSEBYSTUDENTNAME("1.1.2", "QueryCourseByStudentName"),
    QUERYCOURSEBYTEACHER("1.2.3", "QueryCourseByteacher"),

    UPDATESCOREBYSTUDENTANDCOURSE("3.1", "UpdateScorebyStudentAndCourse");

    private String operateNumber;
    private String className;

    ClassName(String operateNumber, String className) {
        this.operateNumber = operateNumber;
        this.className = className;
    }

    public String getOperateNumber() {
        return operateNumber;
    }

    public String getClassName() {

        return className;
    }
}
