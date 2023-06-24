package testenum;

import java.util.regex.Pattern;

public class TestEnum {
    public static void main(String[] args) {
        System.out.println(BooleanEnum.fromValue("true"));
        System.out.println(BooleanEnum.fromValue("1"));
        System.out.println(BooleanEnum.fromValue("yes"));

        System.out.println(BooleanEnum.fromValue("false"));
        System.out.println(BooleanEnum.fromValue("0"));
        System.out.println(BooleanEnum.fromValue("no"));
    }

    private static boolean isValidBoolean(String value) {
        String booleanPattern = "^(?i)(true|false|1|0|yes|no)$";
        return Pattern.matches(booleanPattern, value);
    }
}
