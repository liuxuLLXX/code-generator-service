package net.vtstar.codegenerator.generate.service.support;

import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import net.vtstar.codegenerator.generate.domain.DataSourceParams;
import net.vtstar.codegenerator.generate.domain.GenVo;
import net.vtstar.codegenerator.generate.domain.GeneratorConfig;
import net.vtstar.codegenerator.generate.domain.Table;
import net.vtstar.codegenerator.generate.file.properties.GeneratorProperties;
import net.vtstar.codegenerator.generate.service.DefaultGenerator;
import net.vtstar.codegenerator.generate.service.GeneratorService;
import net.vtstar.codegenerator.record.domain.CreateColumnRecord;
import net.vtstar.codegenerator.record.domain.CreateTableRecord;
import net.vtstar.codegenerator.record.domain.OperateRecord;
import net.vtstar.codegenerator.record.service.CreateColumnRecordService;
import net.vtstar.codegenerator.record.service.CreateTableRecordService;
import net.vtstar.codegenerator.record.service.OperateRecordService;
import net.vtstar.codegenerator.utils.ConstantsUtils;
import net.vtstar.codegenerator.utils.DataSourceUtils;
import net.vtstar.codegenerator.utils.FilePathUtils;
import net.vtstar.codegenerator.utils.ZipUtils;
import net.vtstar.user.domain.UserInfo;
import net.vtstar.user.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

/**
 * @Auther: liuxu
 * @Date: 2019/3/13
 * @Description:
 */
@Profile("pro")
@Slf4j
@Service
public class ProGeneratorService implements GeneratorService {

    @Autowired
    private DefaultGenerator defaultGenerator;
    @Autowired
    private OperateRecordService operateRecordService;
    @Autowired
    private CreateTableRecordService createTableRecordService;
    @Autowired
    private CreateColumnRecordService createColumnRecordService;
    @Autowired
    private GeneratorProperties generatorProperties;

    /**
     * 根据表名生成文件。
     *
     * @param genVo 代码生成配置
     * @throws IOException       抛出的IO异常
     * @throws TemplateException 抛出的freemarker异常
     */
    @Override
    @Transactional
    public void doGenerator(GenVo genVo) throws Exception {
        GeneratorConfig config = genVo.getConfig();
        config.setOutPath(generatorProperties.getGenenratorpath() + "\\" + UserUtil.getUsername() + "\\");
        defaultGenerator.doGenerator(genVo);
        String sourcePath = config.getOutPath() + (config.getPackageName()).replaceAll("\\.", "\\\\") + "\\";
        sourcePath = FilePathUtils.getRealFilePath(sourcePath);
        String zipPath = config.getOutPath() + "code.zip";
        zipPath = FilePathUtils.getRealFilePath(zipPath);
        ZipUtils.createZip(sourcePath, zipPath, true);
        record(genVo.getDataSourceParams(), genVo.getTables());
    }

    private void record(DataSourceParams params, List<Table> choseTables) {
        // 记录
        UserInfo userInfo = UserUtil.getUserInfo();
        OperateRecord operateRecord = new OperateRecord();
        operateRecord.setUserId(userInfo.getId());
        operateRecord.setDbUsername(params.getJdbcUserName());
        operateRecord.setName(userInfo.getName());
        operateRecord.setUsername(userInfo.getUsername());

        if (ConstantsUtils.DRIVER_NAME_MYSQL.equals(params.getJdbcDriverName())) {
            operateRecord.setHost(DataSourceUtils.getMysqlDataBaseHost(params.getJdbcDriverUrl()));
            operateRecord.setDbName(DataSourceUtils.getMysqlDataBaseName(params.getJdbcDriverUrl()));
        } else if (ConstantsUtils.DRIVER_NAME_POSTGRESQL.equals(params.getJdbcDriverName())) {
            operateRecord.setHost(DataSourceUtils.getPostgresDataBaseHost(params.getJdbcDriverUrl()));
            operateRecord.setDbName(DataSourceUtils.getPostgresDataBaseName(params.getJdbcDriverUrl()));
        }
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
}
