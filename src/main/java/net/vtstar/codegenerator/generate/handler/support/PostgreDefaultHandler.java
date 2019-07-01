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
public class PostgreDefaultHandler extends DefaultDataHandler {

    @Override
    public String parseDataType(String colType) {
        if ("varchar".equals(colType) || "text".equals(colType) || "bpchar".equals(colType)) {
            return ConstantsUtils.TYPE_STRING;
        }
        if ("int2".equals(colType)) {
            return ConstantsUtils.TYPE_SHORT;
        }
        if ("char".equals(colType)) {
            return ConstantsUtils.TYPE_CHARACTER;
        }
        if ("int4".equals(colType) || "serial".equals(colType)) {
            return ConstantsUtils.TYPE_INTEGER;
        }
        if ("int8".equals(colType)) {
            return "Long";
        }
        if ("numeric".equals(colType)) {
            return ConstantsUtils.TYPE_DECIMAL;
        }
        if ("timestamptz".equals(colType) || "date".equals(colType)) {
            return "java.util.Date";
        }
        if ("BLOB".equals(colType)) {
            return "Object";
        }
        return "String";
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
