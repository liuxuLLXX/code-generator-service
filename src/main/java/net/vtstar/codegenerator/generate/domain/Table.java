package net.vtstar.codegenerator.generate.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.vtstar.codegenerator.utils.CamelCaseUtil;
import net.vtstar.utils.StrUtils;

import java.io.Serializable;
import java.util.*;

@ApiModel("获得表的元素详细信息 包含表名称，和所有列。")
@Data
public class Table implements Serializable {

    private static final long serialVersionUID = 6343786075130346667L;

    @ApiModelProperty(notes = "schema对象")
    private String schema;

    @ApiModelProperty(notes = "主键名称的set集合")
    private Set<String> rawPks;

    @ApiModelProperty(notes = "外键名称的set集合")
    private List<ForeignKey> rawFks;

    @ApiModelProperty(notes = "外键列表")
    private List<ForeignKey> fks = new ArrayList<>();

    @ApiModelProperty(notes = "表名")
    private String tableName;

    @ApiModelProperty(notes = "首字母大写的表名")
    private String tableNameUC;

    @ApiModelProperty(notes = "表的描述(注释)")
    private String tableDesc;

    @ApiModelProperty(notes = "模块（说明该表的主要用途，例 module = equipment, 表示这个表是设备模块）")
    private String module;

    @ApiModelProperty(notes = "类名")
    private String className;

    @ApiModelProperty(notes = "表的别名")
    private String tableAlias;

    @ApiModelProperty(notes = "首字母小写的类名")
    private String firstLowerClassName;


    /**
     *
     */
    private boolean bizTable;

    @ApiModelProperty(notes = "表中的字段的list")
    private List<Column> cols = new ArrayList<>();

    @ApiModelProperty(notes = "主键集合")
    private List<Column> pkCols = new ArrayList<>();

    @ApiModelProperty(notes = "唯一索引")
    private Map<String, List<Column>> uniqueKeyMap = new LinkedHashMap<>();

    @ApiModelProperty(notes = "字段的map")
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

    /*public Collection<List<Column>> getUniqueKeys() {
        Collection<List<Column>> list = uniqueKeyMap.values();
        return uniqueKeyMap.values();
    }*/

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
