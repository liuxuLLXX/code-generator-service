package net.vtstar.codegenerator.utils;

import lombok.experimental.UtilityClass;

/**
 * @Auther: liuxu
 * @Date: 2019/2/21
 * @Description:
 */
@UtilityClass
public class ConstantsUtils {

    /**
     * 数据库类型与其java类型的映射
     */
    public static final String TYPE_STRING = "String";

    public static final String TYPE_TIMESTAMP = "Long";

    public static final String TYPE_Long = "Long";

    public static final String TYPE_INTEGER = "Integer";

    public static final String TYPE_DECIMAL = "BigDecimal";

    public static final String TYPE_CHARACTER = "Character";

    public static final String TYPE_DATE = "java.util.Date";

    public static final String TYPE_SHORT = "short";

    public static final String TYPE_INPUT_STREAM = "java.io.InputStream";

    public static final String TYPE_READER = "java.io.Reader";


    public static final String MAPPER_PKG = "mapper";

    public static final String MAPPER_SUFFIX = "Mapper";

    public static final String DOMAIN_PKG = "domain";

    public static final String CONTROLLER_PKG = "controller";

    public static final String CONTROLLER_SUFFIX = "Controller";

    public static final String SERVICE_PKG = "service";

    public static final String SERVICE_SUFFIX = "Service";

    public static final String TABLE_ALIAS = "tableAlias";


    public static final String TENANT_DEFAULT = "default";

    public static final String JDBC_DRIVER_URL = "com.mysql.cj.jdbc.Driver";

    public static final String COLUMN_NAME = "COLUMN_NAME";

    public static final String REMARKS = "REMARKS";

    public static final String COLUMN_SIZE = "COLUMN_SIZE";

    public static final String NULLABLE = "NULLABLE";

    public static final String TYPE_NAME = "TYPE_NAME";

    public static final String DECIMAL_DIGITS = "DECIMAL_DIGITS";

    public static final String PKTABLE_NAME = "PKTABLE_NAME";

    public static final String PKCOLUMN_NAME = "PKCOLUMN_NAME";

    public static final String FKTABLE_NAME = "FKTABLE_NAME";

    public static final String FKCOLUMN_NAME = "FKCOLUMN_NAME";

    public static final String FILE_NAME = "code.zip";

    public static final String DRIVER_NAME_MYSQL = "com.mysql.cj.jdbc.Driver";

    public static final String DRIVER_NAME_POSTGRESQL = "org.postgresql.Driver";

    public static final String MAPPER_INTERFACE_PATH = "/mapper_interface.ftl";

    public static final String MAPPER_XML_PATH = "/mapper_xml.ftl";

    public static final String CONTROLLER_PATH = "/controller.ftl";

    public static final String DOMAIN_PATH = "/domain.ftl";

    public static final String SERVICE_PATH = "/service.ftl";

}
