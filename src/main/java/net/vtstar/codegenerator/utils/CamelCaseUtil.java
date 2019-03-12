package net.vtstar.codegenerator.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 表名，字段名等的格式化工具类
 * Created by qiujingde on 2016/12/20.
 */
public final class CamelCaseUtil {

    /**
     *
     */
    private static final String LOWER_CASE_REGEX = "_(.?)";
    /**
     *
     */
    private static final String UPPER_CASE_REGEX = "(?:^|_)(.?)";

    /**
     * 防止实例化。
     */
    private CamelCaseUtil() { }

    /**
     *
     * @param rawStr 原始字符串。
     * @return lowerCamelCase形式
     */
    public static String lowerCamelCase(String rawStr) {
        return format(rawStr, LOWER_CASE_REGEX);
    }

    /**
     *
     * @param rawStr 原始字符串。
     * @return upperCamelCase形式
     */
    public static String upperCamelCase(String rawStr) {
        return format(rawStr, UPPER_CASE_REGEX);
    }

    /**
     * 获取缩略名。
     * @param rawStr 原始字符串
     * @return 缩略名。
     */
    public static String toAlias(String rawStr) {
        Pattern p = Pattern.compile(UPPER_CASE_REGEX);
        Matcher m = p.matcher(rawStr.toLowerCase());
        StringBuilder sb = new StringBuilder();
        while (m.find()) {
            sb.append(m.group(1).toLowerCase());
        }
        return sb.toString();
    }

    /**
     *
     * @param rawStr 原始字符串
     * @param regex 替换的字符串
     * @return 处理好的字符串
     */
    private static String format(String rawStr, String regex) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(rawStr.toLowerCase());
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, m.group(1).toUpperCase());
        }
        m.appendTail(sb);
        return sb.toString();
    }

}
