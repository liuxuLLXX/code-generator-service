package net.vtstar.codegenerator.generate.domain;

import net.vtstar.codegenerator.utils.CamelCaseUtil;

import java.io.Serializable;

/**
 * 外键关系。
 */
public class ForeignKey implements Serializable {
    private static final long serialVersionUID = -8009397260222365611L;

    /**
     *
     */
    private Column fkColumn;
    /**
     *
     */
    private Column pkColumn;
    /**
     *
     */
    private String fkTableName;
    /**
     *
     */
    private String fkColumnName;
    /**
     * 实体关联字段名称。
     */
    private String fieldName;

    /**
     *
     */
    private String fieldNameU;

    /**
     * 关联字段名称解析。<br />
     * 1.如果主表主键名与外表的外键名称相同，取主表的firstLowerClassName;<br />
     * 2.如果外键的名称为FID_,关联字段名为parent;<br />
     * 3.从外键名称中去掉主键名称转换成lowerCamel，再加主表的ClassName。
     */
    public void resolveFieldName() {
        if (fkColumn.getColName().equals(pkColumn.getColName())) {
            fieldName = pkColumn.getTable().getFirstLowerClassName();
        } else if ("FID_".equals(fkColumn.getColName())) {
            fieldName = "parent";
        } else {
            String fName = fkColumn.getColName();
            fName = fName.replaceAll(pkColumn.getColName(), "");

            fieldName = CamelCaseUtil.lowerCamelCase(fName) + pkColumn.getTable().getClassName();
        }
        fieldNameU = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

    public Column getFkColumn() {
        return fkColumn;
    }

    public void setFkColumn(Column fkColumn) {
        this.fkColumn = fkColumn;
    }

    public Column getPkColumn() {
        return pkColumn;
    }

    public void setPkColumn(Column pkColumn) {
        this.pkColumn = pkColumn;
    }

    public String getFkTableName() {
        return fkTableName;
    }

    public void setFkTableName(String fkTableName) {
        this.fkTableName = fkTableName;
    }

    public String getFkColumnName() {
        return fkColumnName;
    }

    public void setFkColumnName(String fkColumnName) {
        this.fkColumnName = fkColumnName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldNameU() {
        return fieldNameU;
    }

    public void setFieldNameU(String fieldNameU) {
        this.fieldNameU = fieldNameU;
    }
}
