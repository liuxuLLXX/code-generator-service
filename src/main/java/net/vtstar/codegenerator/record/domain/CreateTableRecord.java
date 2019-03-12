package net.vtstar.codegenerator.record.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.math.BigDecimal;
import java.util.List;

/**
* Date: 2019-3-12
* @author liuxu
*/

@ApiModel("生成表记录")
@Data
public class CreateTableRecord {

    @ApiModelProperty(notes = "序号")
    private Long id;
    
    @NotNull(message = "recordId is null")
    @ApiModelProperty(notes = "生成记录id")
    private Long recordId;
    
    @NotNull(message = "tableName is null")
    @ApiModelProperty(notes = "表名称")
    private String tableName;
    
    @ApiModelProperty(notes = "状态 1正常 9 已删除")
    private String status;
    
    @ApiModelProperty(notes = "创建时间")
    private Date createTime;
    
    @ApiModelProperty(notes = "更新时间")
    private Date updateTime;

    private List<CreateColumnRecord> columnRecordList;
    

}
