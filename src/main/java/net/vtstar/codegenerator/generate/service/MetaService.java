package net.vtstar.codegenerator.generate.service;

import lombok.extern.slf4j.Slf4j;
import net.vtstar.codegenerator.generate.advice.exception.GeneratorException;
import net.vtstar.codegenerator.generate.handler.DataTypeTransferHandler;
import net.vtstar.codegenerator.generate.domain.*;
import net.vtstar.codegenerator.utils.ConstantsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.*;


/**
 * @author ray
 */
@Slf4j
@Service
public class MetaService {

    @Autowired
    private DataHandlerChooser handlerChooser;

    /**
     * 获取所有表。
     *
     * @param params 生成配置
     * @return 所有的表。
     * @throws InstantiationException 实例化异常。
     * @throws IllegalAccessException 非法的访问级别。
     * @throws ClassNotFoundException 找不到类。
     * @throws SQLException           sql异常。
     */
    public MetaContext getTables(DataSourceParams params) throws ClassNotFoundException, SQLException, GeneratorException {
        log.debug("begin connect database....");
        Class.forName(params.getJdbcDriverName());
        MetaContext context = new MetaContext();
        Connection conn = null;
        try {
            conn = getConnection(params);
            DatabaseMetaData dm = conn.getMetaData();
            getAllTableInfo(dm, params, context);
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
     * @param params 生成配置。
     * @return 数据库连接。
     * @throws SQLException sql异常。
     */
    private Connection getConnection(DataSourceParams params) throws SQLException {
        Connection conn;
        if (params.getJdbcDriverName().equals(ConstantsUtils.JDBC_DRIVER_URL)) {
            Properties props = new Properties();
            props.setProperty("user", params.getJdbcUserName());
            props.setProperty("password", params.getJdbcPassword());
            // 设置可以获取remarks信息
            props.setProperty("remarks", "true");
            // 设置可以获取tables remarks信息
            props.setProperty("useInformationSchema", "true");

            conn = DriverManager.getConnection(params.getJdbcDriverUrl(), props);
        } else {
            conn = DriverManager.getConnection(params.getJdbcDriverUrl(),
                    params.getJdbcUserName(), params.getJdbcPassword());
        }
        return conn;
    }

    /**
     * 获取表信息。
     *
     * @param dm      dm
     * @param context context
     * @throws SQLException sql exception
     */
    private void getAllTableInfo(DatabaseMetaData dm, DataSourceParams params, MetaContext context)
            throws SQLException {
        log.debug("begin parse database....");

        String schema = params.getJdbcSchema();
        DataTypeTransferHandler dataTypeTransferHandler = handlerChooser.choose(params.getJdbcDriverName());
        String dataBaseName = dataTypeTransferHandler.getDataBaseName(params.getJdbcDriverUrl());

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
            getCols(dm, dataBaseName, tmd, dataTypeTransferHandler);
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
    private void getCols(DatabaseMetaData dm, String dataBaseName, Table table, DataTypeTransferHandler handler)
            throws SQLException {
        ResultSet rsCol = dm.getColumns(dataBaseName, table.getSchema(), table.getTableName(), null);
        while (rsCol.next()) {
            table.addCol(getColumn(rsCol, handler));
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
    private Column getColumn(ResultSet rsCol, DataTypeTransferHandler handler) throws SQLException {
        Column col = new Column();

        col.setColName(rsCol.getString(ConstantsUtils.COLUMN_NAME));
        col.setColDesc(rsCol.getString(ConstantsUtils.REMARKS) == null ? "" : rsCol.getString(ConstantsUtils.REMARKS));
        col.setLength(rsCol.getString(ConstantsUtils.COLUMN_SIZE));
        col.setNullable(rsCol.getString(ConstantsUtils.NULLABLE));

        String colType = rsCol.getString("TYPE_NAME");
        String digits = rsCol.getString("DECIMAL_DIGITS");
        col.setColType(colType);
        col.setJavaType(handler.parseDataType(colType));
        col.setFormatCode(parseFormatCode(colType, digits));
        return col;
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
