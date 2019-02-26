package net.vtstar.generate.domain;

import lombok.Data;

/**
 * 代码生成相关配置。 <br />
 * Date: 2016年12月07日 上午11:11:35 <br />
 *
 * @author qiujingde
 * @since V1.0.0
 */
@Data
public class GeneratorConfig {

    /**
     * 驱动名称 mysql: com.mysql.cj.jdbc.Driver
     */
    private String jdbcDriverName;
    /**
     *
     */
    private String jdbcDriverUrl;
    /**
     *
     */
    private String jdbcUserName;
    /**
     *
     */
    private String jdbcPassword;
    /**
     *
     */
    private String jdbcSchema;
    /**
     *
     */
    private String packageName;
    /**
     *
     */
    private String outPath;
    /**
     * 代码作者
     */
    private String author;

}
