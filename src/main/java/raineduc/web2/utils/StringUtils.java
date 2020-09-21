package raineduc.web2.utils;

public class StringUtils {
    public static boolean isEmptyOrNull(String s) {
        return (s == null || s.trim().isEmpty());
    }
}
