package net.vtstar.codegenerator.generate.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Auther: liuxu
 * @Date: 2019/2/21
 * @Description:
 */
@ApiModel
@Data
public class GenVo {

    @ApiModelProperty(notes = "被选中的表信息列表")
    private List<Table> tables;

    @ApiModelProperty(notes = "代码生成相关配置")
    private GeneratorConfig config;

    @ApiModelProperty(notes = "datasource信息")
    private DataSourceParams dataSourceParams;
}
