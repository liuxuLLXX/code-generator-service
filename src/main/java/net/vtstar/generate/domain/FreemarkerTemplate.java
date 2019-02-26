package net.vtstar.generate.domain;

import freemarker.template.Template;

/**
 * 生成java类文件的相关模板。<br />
 * Created by qiujingde on 2016/12/5。
 */
public class FreemarkerTemplate {

    /**
     *
     */
    private Template template;
    /**
     *
     */
    private String pkg;
    /**
     *
     */
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
