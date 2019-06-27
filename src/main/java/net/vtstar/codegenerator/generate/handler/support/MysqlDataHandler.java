package net.vtstar.codegenerator.generate.handler.support;

import net.vtstar.codegenerator.generate.handler.DefaultDataHandler;
import net.vtstar.codegenerator.utils.ConstantsUtils;
import net.vtstar.codegenerator.utils.DataSourceUtils;
import org.springframework.stereotype.Component;

/**
 * @Auther: liuxu
 * @Date: 2019/6/27
 * @Description:
 */
@Component
public class MysqlDataHandler extends DefaultDataHandler {

    @Override
    public String parseDataType(String colType) {

        if ("VARCHAR".equals(colType) || "CHAR".equals(colType) || "VARCHAR2".equals(colType)) {
            return ConstantsUtils.TYPE_STRING;
        }
        if ("TIMESTAMP".equals(colType)) {
            return ConstantsUtils.TYPE_TIMESTAMP;
        }
        if ("INT".equals(colType) || "SMALLINT".equals(colType)
                || "TINYINT".equals(colType) || "INTEGER".equals(colType) || "NUMBER".equals(colType)) {
            return ConstantsUtils.TYPE_INTEGER;
        }
        if ("BIGINT".equals(colType)) {
            return "Long";
        }
        if ("DECIMAL".equals(colType)) {
            return ConstantsUtils.TYPE_DECIMAL;
        }
        if ("DATETIME".equals(colType) || "TIMESTAMP(6)".equals(colType)
                || "DATE".equals(colType)) {
            return "java.util.Date";
        }
        if ("BLOB".equals(colType)) {
            return "Object";
        }
        return "String";
    }

    @Override
    public String getDataBaseName(String url) {
        return DataSourceUtils.getMysqlDataBaseName(url);
    }

    @Override
    public String support() {
        return ConstantsUtils.DRIVER_NAME_MYSQL;
    }
}
