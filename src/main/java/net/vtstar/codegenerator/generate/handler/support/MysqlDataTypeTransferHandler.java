package net.vtstar.codegenerator.generate.handler.support;

import net.vtstar.codegenerator.generate.handler.DataTypeTransferHandler;
import net.vtstar.codegenerator.utils.ConstantsUtils;
import net.vtstar.codegenerator.utils.DataSourceUtils;
import org.springframework.stereotype.Component;

/**
 * @Auther: liuxu
 * @Date: 2019/6/27
 * @Description:
 */
@Component
public class MysqlDataTypeTransferHandler implements DataTypeTransferHandler {

    @Override
    public String parseDataType(String colType) {

        if ("VARCHAR".equalsIgnoreCase(colType) || "CHAR".equalsIgnoreCase(colType) || "VARCHAR2".equalsIgnoreCase(colType)) {
            return ConstantsUtils.TYPE_STRING;
        }
        if ("TIMESTAMP".equalsIgnoreCase(colType)) {
            return ConstantsUtils.TYPE_TIMESTAMP;
        }
        if ("INT".equalsIgnoreCase(colType) || "SMALLINT".equalsIgnoreCase(colType)
                || "TINYINT".equalsIgnoreCase(colType) || "INTEGER".equalsIgnoreCase(colType) || "NUMBER".equalsIgnoreCase(colType)) {
            return ConstantsUtils.TYPE_INTEGER;
        }
        if ("BIGINT".equalsIgnoreCase(colType)) {
            return ConstantsUtils.TYPE_Long;
        }
        if ("DECIMAL".equalsIgnoreCase(colType)) {
            return ConstantsUtils.TYPE_DECIMAL;
        }
        if ("DATETIME".equalsIgnoreCase(colType) || "TIMESTAMP(6)".equalsIgnoreCase(colType)
                || "DATE".equalsIgnoreCase(colType)) {
            return ConstantsUtils.TYPE_DATE;
        }
        if ("BLOB".equalsIgnoreCase(colType)) {
            return ConstantsUtils.TYPE_INPUT_STREAM; // [B
        }
        if ("CLOB".equalsIgnoreCase(colType)) {
            return ConstantsUtils.TYPE_READER;
        }
        return ConstantsUtils.TYPE_STRING;
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
