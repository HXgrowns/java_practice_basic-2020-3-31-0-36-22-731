package utils;

import java.util.List;

public class CheckUtils {
    private CheckUtils() {
    }

    public static boolean checkInputFormat(String[] inputs) {
        if (inputs.length != 2) {
            return false;
        }
        for (String input : inputs) {
            if (input.equals("")) {
                return false;
            }
        }
        return true;
    }

    public static <T> boolean isExitEntity(List<T> entitys) {
        if (entitys == null || entitys.size() == 0) {
            System.out.println("没有查到任何数据");
            return false;
        }
        return true;
    }

    public static boolean checkInsertParameter(String[] insertParameter) {
        return insertParameter.length == 4;
    }
}
