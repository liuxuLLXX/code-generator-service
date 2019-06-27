package net.vtstar.codegenerator.generate.handler;

/**
 * @Auther: liuxu
 * @Date: 2019/6/27
 * @Description:
 */
public abstract class DefaultDataHandler {

    public abstract String parseDataType(String colType);

    public abstract String getDataBaseName(String url);

    public abstract String support();

}
