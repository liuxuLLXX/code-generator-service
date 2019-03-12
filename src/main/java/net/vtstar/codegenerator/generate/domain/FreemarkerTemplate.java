package net.vtstar.codegenerator.generate.domain;

import freemarker.template.Template;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("生成java类文件的相关模板")
public class FreemarkerTemplate {

    @ApiModelProperty(notes = "模板")
    private Template template;

    @ApiModelProperty(notes = "包名的一部分")
    private String pkg;

    @ApiModelProperty(notes = "名称后缀")
    private String suffix = "";

    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

    public String getPkg() {
        return pkg;
    }

    public void setPkg(String pkg) {
        this.pkg = pkg;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}
