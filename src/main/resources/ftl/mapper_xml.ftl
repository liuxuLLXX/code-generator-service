<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="${pkgName}.${meta.module}.${mapperFolder}.${meta.className}${MapperSuffix}">

    <!-- result map -->
    <resultMap type="${pkgName}.${meta.module}.${domainFolder}.${meta.className}" id="${meta.firstLowerClassName}Map">
        <#list meta.cols as col>
            <<#if (col.pkFlag)>id<#else>result</#if> property="${col.fieldName}" column="${col.colName}" javaType="${col.javaType}"/>
        </#list>
        <#list meta.fks as fk>
            <association property="${fk.fieldName}" column="${fk.fkColumn.fieldName}"
                         select="${pkgName}.${mapperFolder}.${fk.pkColumn.table.className}${MapperSuffix}.getById"/>
        </#list>
    </resultMap>

    <!-- sqlColumn-->
    <sql id="SQL_${meta.tableNameUC}_COLUMN">
        <#list meta.cols as col>
            ${meta.tableAlias}.${col.colName}<#if col_index + 1 < meta.cols?size>,</#if>
        </#list>
    </sql>

    <!-- sqlWhere -->
    <sql id="SQL_${meta.tableNameUC}_WHERE">
        <#list meta.cols as col>
            <#if col.colType == "String">
        <if test="null != ${col.fieldName} and ${col.fieldName}.length() != 0">
            <#else>
        <if test="null != ${col.fieldName}">
            </#if>
            AND ${meta.tableAlias}.${col.colName} = ${r'#{' + col.fieldName + '}'}
        </if>
        </#list>
    </sql>

    <#--getList-->
    <!-- Query All  -->
    <select id="getList" parameterType="${pkgName}.${meta.module}.${domainFolder}.${meta.className}" resultMap="${meta.firstLowerClassName}Map">
        select
        <include refid="SQL_${meta.tableNameUC}_COLUMN"/>
        from ${meta.tableNameUC} ${meta.tableAlias}
        <where>
            <include refid="SQL_${meta.tableNameUC}_WHERE"/>
        </where>
    </select>

    <!-- 根据ID查询 -->
    <select id="getById" resultMap="${meta.firstLowerClassName}Map">
        select
        <include refid="SQL_${meta.tableNameUC}_COLUMN"/>
        from ${meta.tableNameUC} ${meta.tableAlias}
        where ${meta.tableAlias}.id = ${r'#{id}'}
        and ${meta.tableAlias}.status != '9'
    </select>

    <#--create-->
    <!-- 新增 -->
    <insert id="create" parameterType="${pkgName}.${meta.module}.${domainFolder}.${meta.className}">
        insert into ${meta.tableNameUC}
        <trim prefix="(" suffix=")" suffixOverrides=",">
            <#list meta.cols as col>
            <if test="null != ${col.fieldName}">
                ${col.colName}<#if col_index + 1 < meta.cols?size>,</#if>
            </if>
            </#list>
        </trim>
        <trim prefix="values (" suffix=")" suffixOverrides=",">
            <#list meta.cols as col>
            <if test="null != ${col.fieldName}">
                ${r'#{' + col.fieldName + '}'}<#if col_index + 1 < meta.cols?size>,</#if>
            </if>
            </#list>
        </trim>
        <selectKey resultType="Long" order="AFTER" keyProperty="id">
            SELECT LAST_INSERT_ID() AS id
        </selectKey>
    </insert>

    <!-- 根据id删除（逻辑删除） -->
    <update id="deleteById">
        update ${meta.tableNameUC}
        set status = '9'
        where id = ${r'#{id}'}
    </update>


    <!-- update -->
    <update id="update">
        update ${meta.tableNameUC}
        <set>
            <#list meta.cols as col>
            <if test="null != ${col.fieldName}">
                ${col.colName} = ${r'#{' + col.fieldName + '}'}<#if col_index + 1 < meta.cols?size>,</#if>
            </if>
            </#list>
        </set>
        where id = ${r'#{id}'}
    </update>

</mapper>
