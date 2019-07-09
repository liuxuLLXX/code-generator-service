package ${pkgName}.${meta.module}.${domainFolder};

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.math.BigDecimal;

/**
* Date: ${currentDate}
* @author ${author}
*/

@ApiModel("${meta.tableDesc}")
@Data
public class ${meta.className} {

    <#list meta.entityCols as col>
        <#if "id" != col.fieldName && "status" != col.fieldName && col.fieldName != "createTime" && col.fieldName != "updateTime">
    @NotNull(message = "${col.fieldName} is null")
        </#if>
        <#if "STRING" == col.fieldName && col.fieldName != "status">
    @Length(min = 1, max = 32, message = "${col.colDesc}长度需要在{min}和{max}个字符之间")
        </#if>
    @ApiModelProperty(notes = "${col.colDesc}")
    private ${col.javaType} ${col.fieldName};
    ${""}
    </#list>
}
