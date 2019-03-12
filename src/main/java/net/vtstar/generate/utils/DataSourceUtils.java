package net.vtstar.generate.utils;

/**
 * @Auther: liuxu
 * @Date: 2019/2/21
 * @Description:
 */
public final class DataSourceUtils {

    /**
     *  根据传入的url得到数据库的地址
     * @param driverUrl
     * @return HOST
     */
    public static String getDataBaseName(String driverUrl) {
        String str = driverUrl.substring(13);
        return  str.substring(str.indexOf("/") + 1, str.indexOf("?"));
    }

  /*  public static void main(String[] args) {
        String driverUrl = "jdbc:mysql://10.100.172.135:3306/liuxu?useSSL=false&useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai";
        System.out.println(driverUrl.substring(13));
        System.out.println(driverUrl.indexOf("//"));
        String str = driverUrl.substring(13);
        System.out.println(str.indexOf("/"));
        str = str.substring(str.indexOf("/") + 1, str.indexOf("?"));

        System.out.println(str);
    }*/




}
