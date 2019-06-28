package net.vtstar.codegenerator.utils;

import org.apache.tomcat.util.bcel.Const;

/**
 * @Auther: liuxu
 * @Date: 2019/2/21
 * @Description:
 */
public final class DataSourceUtils {

    /**
     * 根据传入的url得到数据库名
     *
     * @param driverUrl
     * @return HOST
     */
    public static String getMysqlDataBaseName(String driverUrl) {
        String str = driverUrl.substring(13);
        return str.substring(str.indexOf("/") + 1, str.indexOf("?"));
    }

    /**
     * 根据传入的url得到数据库名
     *
     * @param driverUrl
     * @return HOST
     */
    public static String getPostgresDataBaseName(String driverUrl) {
        String str = driverUrl.substring(18);
        return str.substring(str.indexOf("/") + 1);
    }

    /**
     * 根据传入的url得到数据库的地址
     *
     * @param driverUrl
     * @return HOST
     */
    public static String getMysqlDataBaseHost(String driverUrl) {
        String str = driverUrl.substring(13);
        return str.substring(0, str.indexOf(":"));
    }

    /**
     * 根据传入的url得到数据库的地址
     *
     * @param driverUrl
     * @return HOST
     */
    public static String getPostgresDataBaseHost(String driverUrl) {
        String str = driverUrl.substring(18);
        return str.substring(0, str.indexOf(":"));
    }

    public static String getDataBaseType(String driverName) {
        String driver = "";
        switch (driverName) {
            case ConstantsUtils.DRIVER_NAME_MYSQL:
                driver = "mysql";
                break;
            case ConstantsUtils.DRIVER_NAME_POSTGRESQL:
                driver = "postgre";
                break;
            default:
                break;
        }
        return driver;
    }


}
