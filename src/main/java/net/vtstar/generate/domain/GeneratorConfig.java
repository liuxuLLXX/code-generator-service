package net.vtstar.generate.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("代码生成相关配置")
public class GeneratorConfig {

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

    @ApiModelProperty(notes = "包名")
    private String packageName;

    @ApiModelProperty(notes = "输出路径")
    private String outPath;

    @ApiModelProperty(notes = "代码作者")
    private String author;

}
