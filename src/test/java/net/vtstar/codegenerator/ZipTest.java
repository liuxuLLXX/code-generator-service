package net.vtstar.codegenerator;

import net.vtstar.codegenerator.utils.ZipUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * @Auther: liuxu
 * @Date: 2019/3/14
 * @Description:
 */
public class ZipTest {

    public static void main(String[] args) throws FileNotFoundException {
        String sourcePath = "D:\\test";
        String zipPath = "D:\\test.zip";
        ZipUtils.createZip(sourcePath,zipPath,true);
    }
}
