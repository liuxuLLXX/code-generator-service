package net.vtstar.codegenerator.generate.service;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import net.vtstar.codegenerator.generate.domain.FreemarkerTemplate;
import net.vtstar.codegenerator.generate.domain.GenVo;
import net.vtstar.codegenerator.generate.domain.GeneratorConfig;
import net.vtstar.codegenerator.generate.domain.Table;
import net.vtstar.codegenerator.utils.ConstantsUtils;
import net.vtstar.codegenerator.utils.DataSourceUtils;
import net.vtstar.codegenerator.utils.FilePathUtils;
import net.vtstar.utils.asserts.ParamAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.text.DateFormat;
import java.util.*;

/**
 * @Auther: liuxu
 * @Date: 2019/6/27
 * @Description:
 */
@Slf4j
@Component
public class DefaultGenerator {
    /**
     * 用来加载加载模板
     */
    private Configuration cfg;

    private Map<String, Template> mapperXmlMap = new HashMap<>();

    private Map<String, List<FreemarkerTemplate>> templateMap = new HashMap<>();

    @Value("${support.list}")
    List<String> supportList;

    /**
     * 初始化。
     *
     * @throws IOException io异常
     */
    @PostConstruct
    public void init() throws IOException {
        for (String support : supportList) {
            mapperXmlMap.put(support, cfg.getTemplate(support + ConstantsUtils.MAPPER_XML_PATH));
            List<FreemarkerTemplate> templates = new ArrayList<>();
            // 生成domain
            FreemarkerTemplate ft1 = new FreemarkerTemplate();
            Template temp1 = cfg.getTemplate(support + ConstantsUtils.DOMAIN_PATH);
            ft1.setTemplate(temp1);
            ft1.setPkg(ConstantsUtils.DOMAIN_PKG);
            templates.add(ft1);

            // mapper
            FreemarkerTemplate ft2 = new FreemarkerTemplate();
            Template temp2 = cfg.getTemplate(support + ConstantsUtils.MAPPER_INTERFACE_PATH);
            ft2.setTemplate(temp2);
            ft2.setPkg(ConstantsUtils.MAPPER_PKG);
            ft2.setSuffix(ConstantsUtils.MAPPER_SUFFIX);
            templates.add(ft2);

            // service
            FreemarkerTemplate ft3 = new FreemarkerTemplate();
            Template temp3 = cfg.getTemplate(support + ConstantsUtils.SERVICE_PATH);
            ft3.setTemplate(temp3);
            ft3.setPkg(ConstantsUtils.SERVICE_PKG);
            ft3.setSuffix(ConstantsUtils.SERVICE_SUFFIX);
            templates.add(ft3);

            // controller
            FreemarkerTemplate ft6 = new FreemarkerTemplate();
            Template temp6 = cfg.getTemplate(support + ConstantsUtils.CONTROLLER_PATH);
            ft6.setTemplate(temp6);
            ft6.setPkg(ConstantsUtils.CONTROLLER_PKG);
            ft6.setSuffix(ConstantsUtils.CONTROLLER_SUFFIX);
            templates.add(ft6);
            templateMap.put(support, templates);
        }

    }

    public void doGenerator(GenVo genVo) throws Exception {
        for (Table table : genVo.getTables()) {
            log.info("Start generating files of table " + table.getTableName() + ".........");
            String driver = DataSourceUtils.getDataBaseType(genVo.getDataSourceParams().getJdbcDriverName());
            ParamAssert.notEmpty(driver, "目前不支持该数据库 : " + genVo.getDataSourceParams().getJdbcDriverName());
            templateMap.get(genVo.getDataSourceParams().getJdbcDriverUrl());

            Map<String, Object> context = buildContext(genVo.getConfig(), table);
            // 生成sqlmap
            createSqlmapper(genVo.getConfig(), context,  mapperXmlMap.get(driver));
            // 生成java
            for (FreemarkerTemplate ft : templateMap.get(driver)) {
                createClass(genVo.getConfig(), context, ft);
            }
        }
    }

    /**
     * 构建代码生成需要的上下文。
     *
     * @param conf 代码生成配置
     * @param tm   生成代码的表
     * @return 上下文
     */
    private Map<String, Object> buildContext(GeneratorConfig conf, Table tm) {
        Map<String, Object> context = new HashMap<>();
        context.put("meta", tm);
        context.put("pkgName", conf.getPackageName());
        context.put("author", conf.getAuthor());

        context.put("mapperFolder", ConstantsUtils.MAPPER_PKG);
        context.put("MapperSuffix", ConstantsUtils.MAPPER_SUFFIX);

        context.put("ServiceSuffix", ConstantsUtils.SERVICE_SUFFIX);
        context.put("serviceFolder", ConstantsUtils.SERVICE_PKG);

        context.put("ControllerSuffix", ConstantsUtils.CONTROLLER_SUFFIX);
        context.put("controllerFolder", ConstantsUtils.CONTROLLER_PKG + "." + tm.getModule());

        context.put("hasFk", tm.getFks().size() > 0);
        context.put("domainFolder", ConstantsUtils.DOMAIN_PKG);
        context.put("tableAlias", ConstantsUtils.TABLE_ALIAS);
        context.put("currentDate", DateFormat.getDateInstance().format(new Date()));
        return context;
    }

    /**
     * 生成sqlmap文件。
     *
     * @param conf     代码生成配置
     * @param context  上下文
     * @param template 模板
     * @throws IOException       io异常
     * @throws TemplateException freemarker异常
     */
    private void createSqlmapper(GeneratorConfig conf, Map<String, Object> context, Template template)
            throws IOException, TemplateException {

        Table tm = (Table) context.get("meta");
        String sqlMapFolder = conf.getOutPath() + (conf.getPackageName() + "." + tm.getModule() + "."
                + "mybatis").replaceAll("\\.", "\\\\") + "\\";

        sqlMapFolder = FilePathUtils.getRealFilePath(sqlMapFolder);
        log.info("mapper.xml path now ------->" + sqlMapFolder);
        prepareFolder(sqlMapFolder);

        String sqlMapFilePath = sqlMapFolder + tm.getClassName() + ConstantsUtils.MAPPER_SUFFIX + ".xml";
        process(context, template, sqlMapFilePath);
    }


    /**
     * 生成java文件。
     *
     * @param conf    生成配置
     * @param context 上下文
     * @param temp    模板
     * @throws IOException       io异常
     * @throws TemplateException freemarker异常
     */
    private void createClass(GeneratorConfig conf, Map<String, Object> context, FreemarkerTemplate temp)
            throws IOException, TemplateException {
        Table tm = (Table) context.get("meta");

        String classFolder = conf.getOutPath() + (conf.getPackageName() + "." + tm.getModule() + "."
                + temp.getPkg()).replaceAll("\\.", "\\\\") + "\\";

        classFolder = FilePathUtils.getRealFilePath(classFolder);
        log.info("mapper.xml path now ------->" + classFolder);
        prepareFolder(classFolder);

        String classFilePath = classFolder + tm.getClassName() + temp.getSuffix() + ".java";
        process(context, temp.getTemplate(), classFilePath);
    }

    /**
     * 文件生成。
     *
     * @param context  上下文
     * @param template 模板
     * @param filePath 生成路径
     * @throws IOException       io异常
     * @throws TemplateException freemarker异常
     */
    private void process(Map<String, Object> context, Template template, String filePath)
            throws IOException, TemplateException {
        try (FileOutputStream out = new FileOutputStream(filePath, false)) {
            StringWriter writer = new StringWriter();
            template.process(context, writer);
            out.write((writer.toString()).getBytes("utf-8"));
            out.flush();
        }
    }

    /**
     * 确认对应文件夹已生成。
     *
     * @param folder 文件夹
     */
    private void prepareFolder(String folder) {
        log.info("begin generator catalog, path ----> {}", folder);
        File fd2 = new File(folder);
        fd2.mkdirs();
    }

    @Autowired
    public void setCfg(Configuration cfg) {
        this.cfg = cfg;
    }
}
