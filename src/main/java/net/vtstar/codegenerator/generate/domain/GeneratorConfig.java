package net.vtstar.codegenerator.generate.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("代码生成相关配置")
public class GeneratorConfig {

    @ApiModelProperty(notes = "包名")
    private String packageName;

    @ApiModelProperty(notes = "输出路径")
    private String outPath;

    @ApiModelProperty(notes = "代码作者")
    private String author;

}
