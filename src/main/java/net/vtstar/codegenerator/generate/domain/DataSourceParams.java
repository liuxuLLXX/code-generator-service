package net.vtstar.codegenerator.generate.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Auther: liuxu
 * @Date: 2019/6/27
 * @Description:
 */
@Data
@ApiModel("数据库信息")
public class DataSourceParams {

    @ApiModelProperty(notes = "驱动名称 默认为mysql的驱动 com.mysql.cj.jdbc.Driver")
    private String jdbcDriverName;

    @ApiModelProperty(notes = "URL")
    private String jdbcDriverUrl;

    @ApiModelProperty(notes = "username")
    private String jdbcUserName;

    @ApiModelProperty("password")
    private String jdbcPassword;

    @ApiModelProperty(notes = "schema")
    private String jdbcSchema;
}
