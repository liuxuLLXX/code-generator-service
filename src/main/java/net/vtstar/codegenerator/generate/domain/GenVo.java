package net.vtstar.codegenerator.generate.domain;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * @Auther: liuxu
 * @Date: 2019/2/21
 * @Description:
 */
@ApiModel()
@Data
public class GenVo {

    private List<Table> tables;

    private GeneratorConfig config;
}
