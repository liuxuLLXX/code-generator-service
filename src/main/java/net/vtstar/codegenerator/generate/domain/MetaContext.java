package net.vtstar.codegenerator.generate.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * 数据库解析上下文，便于处理表关联信息。
 */
public class MetaContext implements Serializable {
    private static final long serialVersionUCamelCaseUtilID = 1728685652591758021L;

    /**
     * TABLE_NAME -->  TABLE的map。
     */
    private Map<String, Table> tableMap = new HashMap<>();

    /**
     * 解析出的表的集合。
     */
    private Set<Table> tables = new LinkedHashSet<>();

    /**
     * 在上下文中添加表定义。
     * @param table 新增表
     */
    public void addTable(Table table) {
        if (tables.contains(table)) {
            return;
        }
        tables.add(table);
        tableMap.put(table.getTableName(), table);
    }

    /**
     * 根据表名获取表定义。
     * @param tableName 表名
     * @return 表定义
     */
    public Table getTable(String tableName) {
        return tableMap.get(tableName);
    }

    public Set<Table> getTables() {
        return tables;
    }
}
