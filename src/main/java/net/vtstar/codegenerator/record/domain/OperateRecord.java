package net.vtstar.codegenerator.record.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
* Date: 2019-3-12
* @author liuxu
*/

@ApiModel("操作记录")
@Data
public class OperateRecord {

    @ApiModelProperty(notes = "序号")
    private Long id;
    
    @NotNull(message = "userId is null")
    @ApiModelProperty(notes = "用户ID")
    private Long userId;
    
    @NotNull(message = "name is null")
    @ApiModelProperty(notes = "姓名")
    private String name;

    @NotNull(message = "name is null")
    @ApiModelProperty(notes = "用户名")
    private String username;

    @NotNull(message = "host is null")
    @ApiModelProperty(notes = "数据库地址")
    private String host;
    
    @NotNull(message = "dbUsername is null")
    @ApiModelProperty(notes = "数据库登录用户")
    private String dbUsername;

    @ApiModelProperty(notes = "数据库名")
    private String dbName;

    @ApiModelProperty(notes = "状态 1正常 9 已删除")
    private String status;
    
    @ApiModelProperty(notes = "创建时间")
    private Date createTime;
    
    @ApiModelProperty(notes = "更新时间")
    private Date updateTime;


    private List<CreateTableRecord> tableRecordList;
    

}
