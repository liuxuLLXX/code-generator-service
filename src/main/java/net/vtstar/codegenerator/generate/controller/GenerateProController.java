package net.vtstar.codegenerator.generate.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import net.vtstar.codegenerator.generate.domain.GenVo;
import net.vtstar.codegenerator.generate.domain.GeneratorConfig;
import net.vtstar.codegenerator.generate.domain.Table;
import net.vtstar.codegenerator.generate.file.properties.GeneratorProperties;
import net.vtstar.codegenerator.generate.service.GeneratorService;
import net.vtstar.codegenerator.generate.service.MetaService;
import net.vtstar.codegenerator.utils.ConstantsUtils;
import net.vtstar.codegenerator.utils.ZipUtils;
import net.vtstar.user.util.UserUtil;
import net.vtstar.utils.domain.Return;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Set;

/**
 * @Auther: liuxu
 * @Date: 2019/2/21
 * @Description:
 */
@Profile("pro")
@Slf4j
@Api(description = "代码生成模块")
@RestController
@RequestMapping("/api/generator")
public class GenerateProController {

    @Autowired
    private MetaService metaService;
    @Autowired
    private GeneratorService generatorService;
    @Autowired
    private GeneratorProperties generatorProperties;

    @PostMapping("/allTables")
    private Return allTables(@RequestBody GeneratorConfig config) throws Exception {
        Set<Table> tables = metaService.getTables(config).getTables();
        return Return.success(tables);
    }

    @PostMapping("/createCodePro")
    public void createCodePro(@RequestBody GenVo genVo,  HttpServletResponse response) throws Exception {
        generatorService.doGenerator(genVo.getConfig(), genVo.getTables());

        //将生成的zip 以流的形式发送给前端
        String filename = java.net.URLEncoder.encode(ConstantsUtils.FILE_NAME, "UTF-8").replaceAll("/+", "%20");
        String path = generatorProperties.getGenenratorPath() + "/"+ UserUtil.getUsername() + "/" + filename;
        File file = new File(path);
        if (file.exists()) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/force-download");
            response.setHeader("Content-Disposition", "attachment;fileName=\"" + filename + "\";filename*=utf-8''" + filename);
            response.setHeader("fileName", filename);
            InputStream in = new FileInputStream(file);
            OutputStream out = response.getOutputStream();
            StreamUtils.copy(in, out);
        }
        if (ZipUtils.deletefile(generatorProperties.getGenenratorPath()+ "/" + UserUtil.getUsername())) {
            log.info("delete  file success");
        }
    }
}
