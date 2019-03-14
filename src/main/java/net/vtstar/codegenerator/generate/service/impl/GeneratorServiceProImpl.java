package net.vtstar.codegenerator.generate.service.impl;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import net.vtstar.codegenerator.generate.domain.FreemarkerTemplate;
import net.vtstar.codegenerator.generate.domain.GeneratorConfig;
import net.vtstar.codegenerator.generate.domain.Table;
import net.vtstar.codegenerator.generate.file.properties.GeneratorProperties;
import net.vtstar.codegenerator.generate.service.GeneratorService;
import net.vtstar.codegenerator.record.domain.CreateColumnRecord;
import net.vtstar.codegenerator.record.domain.CreateTableRecord;
import net.vtstar.codegenerator.record.domain.OperateRecord;
import net.vtstar.codegenerator.record.service.CreateColumnRecordService;
import net.vtstar.codegenerator.record.service.CreateTableRecordService;
import net.vtstar.codegenerator.record.service.OperateRecordService;
import net.vtstar.codegenerator.utils.ConstantsUtils;
import net.vtstar.codegenerator.utils.DataSourceUtils;
import net.vtstar.codegenerator.utils.ZipUtils;
import net.vtstar.user.domain.UserInfo;
import net.vtstar.user.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.text.DateFormat;
import java.util.*;

/**
 * @Auther: liuxu
 * @Date: 2019/3/13
 * @Description:
 */
@Profile("pro")
@Slf4j
@Service
public class GeneratorServiceProImpl implements GeneratorService {

    @Autowired
    private OperateRecordService operateRecordService;
    @Autowired
    private CreateTableRecordService createTableRecordService;
    @Autowired
    private CreateColumnRecordService createColumnRecordService;
    @Autowired
    private GeneratorProperties generatorProperties;

    /**
     * 用来加载加载模板
     */
    private Configuration cfg;

    private Template sqlMapTemplate;

    private List<FreemarkerTemplate> templates;

    /**
     * 初始化。
     *
     * @throws IOException io异常
     */
    @PostConstruct
    public void init() throws IOException {
        sqlMapTemplate = cfg.getTemplate("mapper_xml.ftl");

        templates = new ArrayList<>();

        // 生成domain
        FreemarkerTemplate ft1 = new FreemarkerTemplate();
        Template temp1 = cfg.getTemplate("domain.ftl");
        ft1.setTemplate(temp1);
        ft1.setPkg(ConstantsUtils.DOMAIN_PKG);
        templates.add(ft1);

        // mapper
        FreemarkerTemplate ft2 = new FreemarkerTemplate();
        Template temp2 = cfg.getTemplate("mapper_interface.ftl");
        ft2.setTemplate(temp2);
        ft2.setPkg(ConstantsUtils.MAPPER_PKG);
        ft2.setSuffix(ConstantsUtils.MAPPER_SUFFIX);
        templates.add(ft2);

        // service
        FreemarkerTemplate ft3 = new FreemarkerTemplate();
        Template temp3 = cfg.getTemplate("service.ftl");
        ft3.setTemplate(temp3);
        ft3.setPkg(ConstantsUtils.SERVICE_PKG);
        ft3.setSuffix(ConstantsUtils.SERVICE_SUFFIX);
        templates.add(ft3);

        // controller
        FreemarkerTemplate ft6 = new FreemarkerTemplate();
        Template temp6 = cfg.getTemplate("controller.ftl");
        ft6.setTemplate(temp6);
        ft6.setPkg(ConstantsUtils.CONTROLLER_PKG);
        ft6.setSuffix(ConstantsUtils.CONTROLLER_SUFFIX);
        templates.add(ft6);

    }

    /**
     * 根据表名生成文件。
     *
     * @param conf        代码生成配置
     * @param choseTables 要生成代码的表
     * @throws IOException       抛出的IO异常
     * @throws TemplateException 抛出的freemarker异常
     */
    @Override
    @Transactional
    public void doGenerator(GeneratorConfig conf, List<Table> choseTables) throws Exception {
        for (Table table : choseTables) {
            log.info("Start generating files of table " + table.getTableName() + ".........");
            Map<String, Object> context = buildContext(conf, table);
            // 生成sqlmap
            createSqlmapper(conf, context, sqlMapTemplate);

            // 生成java
            for (FreemarkerTemplate ft : templates) {
                createClass(conf, context, ft);
            }
        }

        ZipUtils.createZip(generatorProperties.getGenenratorPath() + "\\" + UserUtil.getUsername() + "\\" + (conf.getPackageName()).replaceAll("\\.", "\\\\") + "\\", generatorProperties.getGenenratorPath() + "\\" + UserUtil.getUsername() + "\\code.zip", true);
        record(conf, choseTables);

    }

    private void record(GeneratorConfig conf, List<Table> choseTables) {
        /**
         * 记录
         */
        UserInfo userInfo = UserUtil.getUserInfo();
        OperateRecord operateRecord = new OperateRecord();
        operateRecord.setUserId(userInfo.getId());
        operateRecord.setHost(DataSourceUtils.getDataBaseHost(conf.getJdbcDriverUrl()));
        operateRecord.setDbUsername(conf.getJdbcUserName());
        operateRecord.setDbName(DataSourceUtils.getDataBaseName(conf.getJdbcDriverUrl()));
        operateRecord.setName(userInfo.getName());
        operateRecord.setUsername(userInfo.getUsername());
        operateRecordService.create(operateRecord);

        choseTables.forEach(table -> {
            // 生成表记录
            CreateTableRecord tableRecord = new CreateTableRecord();
            tableRecord.setRecordId(operateRecord.getId());
            tableRecord.setTableName(table.getTableName());
            createTableRecordService.create(tableRecord);
            table.getCols().forEach(column -> {
                CreateColumnRecord columnRecord = new CreateColumnRecord();
                columnRecord.setTableId(tableRecord.getId());
                columnRecord.setColumnName(column.getColName());
                columnRecord.setColumnType(column.getColType());
                createColumnRecordService.create(columnRecord);
            });
        });
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
        String sqlMapFolder = generatorProperties.getGenenratorPath() + "\\" + UserUtil.getUsername() + "\\" + (conf.getPackageName() + "." + tm.getModule() + "."
                + "\\mybatis").replaceAll("\\.", "\\\\") + "\\";
        prepareFolder(sqlMapFolder);

        String sqlMapFilePath = sqlMapFolder + "\\" + tm.getClassName() + ConstantsUtils.MAPPER_SUFFIX + ".xml";
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

        String classFolder = generatorProperties.getGenenratorPath() + "\\" + UserUtil.getUsername() + "\\" + (conf.getPackageName() + "." + tm.getModule() + "."
                + temp.getPkg()).replaceAll("\\.", "\\\\") + "\\";
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
        File fd2 = new File(folder);
        fd2.mkdirs();
    }

    @Autowired
    public void setCfg(Configuration cfg) {
        this.cfg = cfg;
    }
}
