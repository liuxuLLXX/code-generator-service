package net.vtstar.codegenerator.utils;

/**
 * @Auther: liuxu
 * @Date: 2019/3/14
 * @Description:
 */
public final class FilePathUtils {
    public static final String FILE_SEPARATOR = System.getProperty("file.separator");
    //public static final String FILE_SEPARATOR = File.separator;

    public static String getRealFilePath(String path) {
        return path.replace("/", FILE_SEPARATOR).replace("\\", FILE_SEPARATOR);
    }

    public static String getHttpURLPath(String path) {
        return path.replace("\\", "/");
    }

}
