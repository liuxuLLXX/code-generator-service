package net.vtstar.codegenerator.generate.service;

import net.vtstar.codegenerator.generate.handler.DataTypeTransferHandler;
import net.vtstar.utils.asserts.ParamAssert;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: liuxu
 * @Date: 2019/6/27
 * @Description:
 */
@Component
public class DataHandlerChooser implements InitializingBean {

    private final ApplicationContext context;
    private final Map<String, DataTypeTransferHandler> handlerMap = new HashMap<>();

    public DataHandlerChooser(ApplicationContext context) {
        this.context = context;
    }

    public DataTypeTransferHandler choose(String driverName) {
        return handlerMap.get(driverName);
    }

    @Override
    public void afterPropertiesSet() {
        Map<String, DataTypeTransferHandler> map = context.getBeansOfType(DataTypeTransferHandler.class);
        ParamAssert.state(!CollectionUtils.isEmpty(map), "必须提供至少一个数据类型转换器");

        for (DataTypeTransferHandler handler : map.values()) {
            handlerMap.put(handler.support(), handler);
        }
    }
}
