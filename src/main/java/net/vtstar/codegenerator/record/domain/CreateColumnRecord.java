package net.vtstar.codegenerator.record.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
* Date: 2019-3-12
* @author liuxu
*/

@ApiModel("生成表字段记录")
@Data
public class CreateColumnRecord {

    @ApiModelProperty(notes = "序号")
    private Long id;
    
    @NotNull(message = "tableId is null")
    @ApiModelProperty(notes = "表id")
    private Long tableId;
    
    @NotNull(message = "columnName is null")
    @ApiModelProperty(notes = "字段名称")
    private String columnName;
    
    @NotNull(message = "columnType is null")
    @ApiModelProperty(notes = "字段类型")
    private String columnType;
    
    @ApiModelProperty(notes = "状态 1正常 9 已删除")
    private String status;
    
    @ApiModelProperty(notes = "创建时间")
    private Date createTime;
    
    @ApiModelProperty(notes = "更新时间")
    private Date updateTime;
    
}
