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
public class PostgreDataTypeTransferHandler implements DataTypeTransferHandler {

    @Override
    public String parseDataType(String colType) {
        if ("varchar".equalsIgnoreCase(colType) || "text".equalsIgnoreCase(colType) || "bpchar".equalsIgnoreCase(colType)) {
            return ConstantsUtils.TYPE_STRING;
        }
        if ("int2".equalsIgnoreCase(colType)) {
            return ConstantsUtils.TYPE_SHORT;
        }
        if ("char".equalsIgnoreCase(colType)) {
            return ConstantsUtils.TYPE_CHARACTER;
        }
        if ("int4".equalsIgnoreCase(colType) || "serial".equalsIgnoreCase(colType)) {
            return ConstantsUtils.TYPE_INTEGER;
        }
        if ("int8".equalsIgnoreCase(colType)) {
            return ConstantsUtils.TYPE_Long;
        }
        if ("numeric".equalsIgnoreCase(colType)) {
            return ConstantsUtils.TYPE_DECIMAL;
        }
        if ("timestamptz".equalsIgnoreCase(colType)
                || "date".equalsIgnoreCase(colType)
                || "timestamp".equalsIgnoreCase(colType)) {
            return ConstantsUtils.TYPE_DATE;
        }
        if ("bytea".equalsIgnoreCase(colType)) {
            return ConstantsUtils.TYPE_INPUT_STREAM; // [B
        }
        return ConstantsUtils.TYPE_STRING;
    }

    @Override
    public String getDataBaseName(String url) {
        return DataSourceUtils.getPostgresDataBaseName(url);
    }

    @Override
    public String support() {
        return ConstantsUtils.DRIVER_NAME_POSTGRESQL;
    }
}
