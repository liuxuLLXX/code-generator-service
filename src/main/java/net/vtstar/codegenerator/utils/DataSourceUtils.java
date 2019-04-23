package net.vtstar.codegenerator.utils;

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
    public static String getDataBaseHost(String driverUrl) {
        String str = driverUrl.substring(13);
        return str.substring(0, str.indexOf(":"));
    }

    public static void main(String[] args) {
        String str = "jdbc:postgresql://node1.vtstar.osc:32345/vtcloud_dev";
        System.out.println(getPostgresDataBaseName(str));
    }

}
