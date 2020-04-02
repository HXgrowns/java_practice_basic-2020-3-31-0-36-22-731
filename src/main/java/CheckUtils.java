public class CheckUtils {
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

    public static boolean checkInsertParameter(String[] insertParameter) {
        return insertParameter.length == 4;
    }
}
