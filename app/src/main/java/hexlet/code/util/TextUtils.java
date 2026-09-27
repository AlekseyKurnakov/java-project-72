package hexlet.code.util;

public class TextUtils {

    public static String truncate(String text, int maxLength) {

        if (text != null && text.length() > maxLength) {
            return text.substring(0, maxLength) + "...";
        }
        return text;
    }
}
