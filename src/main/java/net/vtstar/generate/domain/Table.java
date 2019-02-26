package net.vtstar.generate.domain;

import net.vtstar.generate.utils.CamelCaseUtil;
import net.vtstar.utils.StrUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 获得表的元素详细信息 包含表名称，和所有列。
 *
 * @author henry
 * @author qiujingde
 */
public class Table implements Serializable {

    private static final long serialVersionUID = 6343786075130346667L;
    /**
     * 基类已处理的字段。
     */
   /* private static final List<String> TYG_FIELDS =
            Arrays.asList("isFinal", "isDelete", "crtTime", "updTime", "crtUserId", "updUserId");
*/
    /**
     *
     */
    private String schema;

    /**
     *
     */
    private Set<String> rawPks;

    /**
     *
     */
    private List<ForeignKey> rawFks;

    /**
     * 外键列表
     */
    private List<ForeignKey> fks = new ArrayList<>();

    /**
     * 表名
     */
    private String tableName;

    /**
     * 首字母大写的表名
     */
    private String tableNameUC;

    /**
     * 表的描述
     */
    private String tableDesc;

    /**
     * 模块（说明该表的主要用途，例 equipment, 设备模块）
     */
    private String module;

    /**
     * 类名
     */
    private String className;

    /**
     * 表的别名
     */
    private String tableAlias;

    /**
     * 首字母小写的类名
     */
    private String firstLowerClassName;


    /**
     *
     */
    private boolean bizTable;

    /**
     * 字段的list
     */
    private List<Column> cols = new ArrayList<>();
    /**
     *
     */
    private List<Column> pkCols = new ArrayList<>();

    /**
     *
     */
    private Map<String, List<Column>> uniqueKeyMap = new LinkedHashMap<>();

    /**
     *
     */
    private Map<String, Column> columnMap = new HashMap<>();

    /**
     * 从表信息中根据字段名返回对应字段。
     *
     * @param colName 字段名
     * @return 对应字段定义
     */
    public Column getCol(String colName) {
        return columnMap.get(colName);
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public Set<String> getRawPks() {
        return rawPks;
    }

    public void setRawPks(Set<String> rawPks) {
        this.rawPks = rawPks;
    }

    public List<ForeignKey> getRawFks() {
        return rawFks;
    }

    public List<ForeignKey> getFks() {
        return fks;
    }

    public void setRawFks(List<ForeignKey> rawFks) {
        this.rawFks = rawFks;
    }

    public List getPkCols() {
        return pkCols;
    }

    public void setPkCols(List<Column> pkCols) {
        this.pkCols = pkCols;
    }

    /**
     * 添加列。
     *
     * @param col 要添加的列。
     */
    public void addCol(Column col) {
        col.setTable(this);
        col.setPkFlag(rawPks.contains(col.getColName()));
        cols.add(col);
        if (col.isPkFlag()) {
            pkCols.add(col);
        }
        columnMap.put(col.getColName(), col);
    }

    /**
     * 添加唯一约束。
     *
     * @param indexName  约束名
     * @param columnName 对应字段名
     */
    public void addUniqueKey(String indexName, String columnName) {
        uniqueKeyMap.computeIfAbsent(indexName, key -> new ArrayList<>());
        List<Column> keys = uniqueKeyMap.get(indexName);
        keys.add(columnMap.get(columnName));
    }

    public List getCols() {
        return cols;
    }

    public void setCols(List<Column> cols) {
        this.cols = cols;
    }

    /**
     * 去掉基类中公共字段的列的集合。
     *
     * @return 非公共字段集合。
     */
    public List<Column> getEntityCols() {
        List<Column> filterColumns = new ArrayList<>();
        for (Column col : cols) {
            // if (!TYG_FIELDS.contains(col.getFieldName())) {
            filterColumns.add(col);
            //}
        }
        return filterColumns;
    }

    public String getEntityName() {
        return tableName.toUpperCase();
    }

    public String getTableName() {
        return tableName;
    }

    /**
     * 设置表名，同时设置className, tableAlias, module, tableNameUC.
     *
     * @param tableName 表名。
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
        this.className = CamelCaseUtil.upperCamelCase(tableName);
        this.tableAlias = CamelCaseUtil.toAlias(tableName);
        this.module = tableName.split("_")[0];
        this.tableNameUC = tableName.toUpperCase();
        firstLowerClassName = CamelCaseUtil.lowerCamelCase(tableName);
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getTableDesc() {
        return tableDesc;
    }

    public void setTableDesc(String tableDesc) {
        this.tableDesc = tableDesc;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getTableAlias() {
        return tableAlias;
    }

    public void setTableAlias(String tableAlias) {
        this.tableAlias = tableAlias;
    }

    public String getFirstLowerClassName() {
        return firstLowerClassName;
    }

    public String getTableNameUC() {
        return tableNameUC;
    }

    public void setTableNameUC(String tableNameUC) {
        this.tableNameUC = tableNameUC;
    }

    public boolean isBizTable() {
        return bizTable;
    }

    public void setBizTable(boolean bizTable) {
        this.bizTable = bizTable;
    }

    public Collection<List<Column>> getUniqueKeys() {
        return uniqueKeyMap.values();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Table) {
            if (StrUtils.isNullOrBlank(tableName)) {
                return false;
            }
            Table that = (Table) obj;
            return tableName.equals(that.tableName);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
