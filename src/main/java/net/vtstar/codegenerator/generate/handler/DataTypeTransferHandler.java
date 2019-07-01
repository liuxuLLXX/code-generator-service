package net.vtstar.codegenerator.generate.handler;

/**
 * @Auther: liuxu
 * @Date: 2019/6/27
 * @Description:
 */
public interface DataTypeTransferHandler {
    /**
     * 数据库字段类型(jdbcType)与Java数据类型(javaType)转换
     */
    String parseDataType(String colType);

    /**
     * 从JDBC URL中获取数据库的名称
     */
    String getDataBaseName(String url);

    /**
     * 处理器支持数据库类型
     */
    String support();
}
