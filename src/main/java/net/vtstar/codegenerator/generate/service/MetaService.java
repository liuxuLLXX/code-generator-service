package net.vtstar.codegenerator.generate.service;

import lombok.extern.slf4j.Slf4j;
import net.vtstar.codegenerator.generate.advice.exception.GeneratorException;
import net.vtstar.codegenerator.generate.domain.*;
import net.vtstar.codegenerator.utils.ConstantsUtils;
import net.vtstar.codegenerator.utils.DataSourceUtils;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.*;


/**
 * @author ray
 */
@Slf4j
@Service
public class MetaService {
    /**
     * 获取所有表。
     *
     * @param genConfig 生成配置
     * @return 所有的表。
     * @throws InstantiationException 实例化异常。
     * @throws IllegalAccessException 非法的访问级别。
     * @throws ClassNotFoundException 找不到类。
     * @throws SQLException           sql异常。
     */
    public MetaContext getTables(GeneratorConfig genConfig) throws ClassNotFoundException, SQLException, GeneratorException {
        log.debug("begin connect database....");
        Class.forName(genConfig.getJdbcDriverName());
        MetaContext context = new MetaContext();
        Connection conn = null;
        try {
            conn = getConnection(genConfig);
            DatabaseMetaData dm = conn.getMetaData();
            if (ConstantsUtils.DRIVER_NAME_MYSQL.equals(genConfig.getJdbcDriverName())) {
                getAllTableInfo(dm, DataSourceUtils.getMysqlDataBaseName(genConfig.getJdbcDriverUrl()), genConfig.getJdbcSchema(), context);
            } else if (ConstantsUtils.DRIVER_NAME_POSTGRES.equals(genConfig.getJdbcDriverName())) {
                getAllTableInfo(dm, DataSourceUtils.getPostgresDataBaseName(genConfig.getJdbcDriverUrl()), genConfig.getJdbcSchema(), context);
            }
        } catch (SQLException sqlE) {
            log.error(sqlE.getMessage());
            throw new GeneratorException("数据库连接失败，请检查填写的数据库信息是否正确！");
        } finally {
            if (null != conn) {
                conn.close();
            }
        }

        resolveFks(context);

        return context;
    }

    /**
     * 获取数据库连接。
     *
     * @param genConfig 生成配置。
     * @return 数据库连接。
     * @throws SQLException sql异常。
     */
    private Connection getConnection(GeneratorConfig genConfig) throws SQLException {

        /*DataSourceProperties properties = new DataSourceProperties();

         *//* props.setProperty("user", genConfig.getJdbcUserName());
            props.setProperty("password", genConfig.getJdbcPassword());
            // 设置可以获取remarks信息
            props.setProperty("remarks", "true");
            // 设置可以获取tables remarks信息
            props.setProperty("useInformationSchema", "true");*//*

        properties.setDriverClassName(genConfig.getJdbcDriverName());
        properties.setUrl(genConfig.getJdbcDriverUrl());
        properties.setUsername(genConfig.getJdbcUserName());
        properties.setPassword(genConfig.getJdbcPassword());
        DataSource dataSource = properties.initializeDataSourceBuilder()
                .type(properties.getType())
                .build();

        Connection conn = dataSource.getConnection();
        return conn;*/
        Connection conn;
        if (genConfig.getJdbcDriverName().equals(ConstantsUtils.JDBC_DRIVER_URL)) {
            Properties props = new Properties();

            props.setProperty("user", genConfig.getJdbcUserName());
            props.setProperty("password", genConfig.getJdbcPassword());
            // 设置可以获取remarks信息
            props.setProperty("remarks", "true");
            // 设置可以获取tables remarks信息
            props.setProperty("useInformationSchema", "true");

            conn = DriverManager.getConnection(genConfig.getJdbcDriverUrl(), props);
        } else {
            conn = DriverManager.getConnection(genConfig.getJdbcDriverUrl(),
                    genConfig.getJdbcUserName(), genConfig.getJdbcPassword());
        }
        return conn;
    }

    /**
     * 获取表信息。
     *
     * @param dm           dm
     * @param schema       schema
     * @param dataBaseName dataBaseName 数据库名
     * @param context      context
     * @throws SQLException sql exception
     */
    private void getAllTableInfo(DatabaseMetaData dm, String dataBaseName, String schema, MetaContext context)
            throws SQLException {
        log.debug("begin parse database....");
        /**
         * catalog: 目录（数据库名）
         * schema:
         * tableNamePattern:
         */
        ResultSet rs = dm.getTables(dataBaseName, schema, null, new String[]{"TABLE"});
        while (rs.next()) {
            Table tmd = getBaseInfoTable(rs);

            // 获取主键信息，仅保存主键对应的字段名，用于获取字段列表时判断字段是否为主键。
            getRawPks(dm, dataBaseName, tmd);
            // column
            getCols(dm, dataBaseName, tmd);
            //唯一索引
            getUniqueKeys(dm, dataBaseName, tmd);
            //获取外键信息
            getRawFks(dm, dataBaseName, tmd);

            context.addTable(tmd);
        }
        log.debug("end parse database....");
    }

    /**
     * 解析外键中的主表信息。
     * 注意：原外键信息是按照主表解析。
     *
     * @param context 上下文
     */
    private void resolveFks(MetaContext context) {
        context.getTables()
                .forEach(table -> table.getRawFks()
                        .forEach(fk -> {
                            Table fkTable = context.getTable(fk.getFkTableName());
                            fk.setFkColumn(fkTable.getCol(fk.getFkColumnName()));
                            fkTable.getFks().add(fk);
                            fk.resolveFieldName();
                        }));
    }

    /**
     * 获取基础信息。
     *
     * @param rs resultSet
     * @return Table
     * @throws SQLException sql exception
     */
    private Table getBaseInfoTable(ResultSet rs)
            throws SQLException {
        Table tmd = new Table();


        tmd.setTableName(rs.getString("TABLE_NAME"));
        tmd.setTableDesc(rs.getString("REMARKS") == null ? "" : rs.getString("REMARKS"));
        tmd.setSchema(rs.getString("TABLE_SCHEM"));

        return tmd;
    }

    /**
     * 获取主键信息。
     *
     * @param dm           dm
     * @param dataBaseName
     * @param table        table
     * @throws SQLException sql exception
     */
    private void getRawPks(DatabaseMetaData dm, String dataBaseName, Table table)
            throws SQLException {
        Set<String> pks = new HashSet<>();
        ResultSet rsPK = dm.getPrimaryKeys(dataBaseName, table.getSchema(), table.getTableName());

        while (rsPK.next()) {
            pks.add(rsPK.getString("COLUMN_NAME"));
        }

        table.setRawPks(pks);
    }

    /**
     * 获取表字段。
     *
     * @param dm           dm
     * @param dataBaseName
     * @param table        table
     * @throws SQLException sql exception
     */
    private void getCols(DatabaseMetaData dm, String dataBaseName, Table table)
            throws SQLException {
        ResultSet rsCol = dm.getColumns(dataBaseName, table.getSchema(), table.getTableName(), null);
        while (rsCol.next()) {
            table.addCol(getColumn(rsCol));
        }
    }

    /**
     * 获取唯一约束信息。
     *
     * @param dm           dm
     * @param dataBaseName
     * @param table        table
     * @throws SQLException sql exception
     */
    private void getUniqueKeys(DatabaseMetaData dm, String dataBaseName, Table table)
            throws SQLException {
        ResultSet rsUniKeys = dm.getIndexInfo(dataBaseName, table.getSchema(), table.getTableName(), true, true);
        while (rsUniKeys.next()) {
            String indexName = rsUniKeys.getString("INDEX_NAME");
            if (indexName == null || "PRIMARY".equals(indexName)) {
                continue;
            }
            table.addUniqueKey(indexName, rsUniKeys.getString("COLUMN_NAME"));
        }
    }

    /**
     * 获取外键信息，对应主表仅保存表名，字段名信息。
     *
     * @param dm           dm
     * @param dataBaseName
     * @param table        table
     * @throws SQLException sql exception
     */
    private void getRawFks(DatabaseMetaData dm, String dataBaseName, Table table)
            throws SQLException {
        ResultSet fks = dm.getExportedKeys(dataBaseName, table.getSchema(), table.getTableName());
        List<ForeignKey> rawFks = new ArrayList<>();
        while (fks.next()) {
            if (!table.getTableName().equals(fks.getString(ConstantsUtils.PKTABLE_NAME))) {
                log.info("表信息不对应！");
                continue;
            }

            ForeignKey fk = new ForeignKey();

            fk.setPkColumn(table.getCol(fks.getString(ConstantsUtils.PKCOLUMN_NAME)));
            fk.setFkTableName(fks.getString(ConstantsUtils.FKTABLE_NAME));
            fk.setFkColumnName(fks.getString(ConstantsUtils.FKCOLUMN_NAME));

            rawFks.add(fk);
        }
        table.setRawFks(rawFks);
    }

    /**
     * 读取当前数据，构建对应的Column实例。
     *
     * @param rsCol 数据集
     * @return Column
     * @throws SQLException sql异常。
     */
    private Column getColumn(ResultSet rsCol) throws SQLException {
        Column col = new Column();

        col.setColName(rsCol.getString(ConstantsUtils.COLUMN_NAME));
        col.setColDesc(rsCol.getString(ConstantsUtils.REMARKS) == null ? "" : rsCol.getString(ConstantsUtils.REMARKS));
        col.setLength(rsCol.getString(ConstantsUtils.COLUMN_SIZE));
        col.setNullable(rsCol.getString(ConstantsUtils.NULLABLE));

        String colType = rsCol.getString("TYPE_NAME");
        String digits = rsCol.getString("DECIMAL_DIGITS");
        String ct = parseDataType(colType, digits);
        col.setColType(colType);
        col.setJavaType("TIMESTAMP".equals(colType) ? "Date" : ct);
        col.setFormatCode(parseFormatCode(colType, digits));
        return col;
    }

    /**
     * 将数据类型转换为java类型。
     *
     * @param colType 列的数据库数据类型
     * @param digits  数据类型的长度
     * @return java类型。
     */
    private String parseDataType(String colType, String digits) {
        if ("VARCHAR".equals(colType) || "CHAR".equals(colType) || "VARCHAR2".equals(colType)) {
            return ConstantsUtils.TYPE_STRING;
        }
        if ("TIMESTAMP".equals(colType)) {
            return ConstantsUtils.TYPE_TIMESTAMP;
        }
        if ("INT".equals(colType) || "SMALLINT".equals(colType)
                || "TINYINT".equals(colType) || "INTEGER".equals(colType) || "NUMBER".equals(colType)) {
            return "0".equals(digits) ? ConstantsUtils.TYPE_INTEGER : ConstantsUtils.TYPE_DECIMAL;
        }
        if ("BIGINT".equals(colType)) {
            return "Long";
        }
        if ("DECIMAL".equals(colType)) {
            return ConstantsUtils.TYPE_DECIMAL;
        }
        if ("DATETIME".equals(colType) || "TIMESTAMP(6)".equals(colType) || "DATE".equals(colType)) {
            return "java.util.Date";
        }
        if ("BLOB".equals(colType)) {
            return "Object";
        }
        return "String";
    }

    /**
     * entity类toString方法需要的，字段对应String.format代码。
     *
     * @param colType 字段类型
     * @param digits  字段长度
     * @return format代码
     */
    private String parseFormatCode(String colType, String digits) {
        if ("TIMESTAMP".equals(colType)) {
            return "tc";
        }
        if ("INT".equals(colType) || "SMALLINT".equals(colType) || "TINYINT".equals(colType)
                || "INTEGER".equals(colType) || "NUMBER".equals(colType)) {
            return "0".equals(digits) ? "d" : "g";
        }
        if ("BIGINT".equals(colType)) {
            return "d";
        }
        if ("DATETIME".equals(colType) || "TIMESTAMP(6)".equals(colType) || "DATE".equals(colType)) {
            return "tc";
        }
        return "s";
    }

}
