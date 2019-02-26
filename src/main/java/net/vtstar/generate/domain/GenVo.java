package net.vtstar.generate.domain;

import lombok.Data;

import java.util.List;

/**
 * @Auther: liuxu
 * @Date: 2019/2/21
 * @Description:
 */
@Data
public class GenVo {

    private List<Table> tables;

    private GeneratorConfig config;
}
