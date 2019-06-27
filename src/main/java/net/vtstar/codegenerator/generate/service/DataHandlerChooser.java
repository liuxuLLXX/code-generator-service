package net.vtstar.codegenerator.generate.service;

import net.vtstar.codegenerator.generate.handler.DefaultDataHandler;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: liuxu
 * @Date: 2019/6/27
 * @Description:
 */
@Component
public class DataHandlerChooser implements ApplicationContextAware {

    private ApplicationContext context;

    private Map<String, DefaultDataHandler> chooseMap = new HashMap<>();

    public DefaultDataHandler choose(String driverName) {
        return chooseMap.get(driverName);
    }


    @PostConstruct
    public void register() {
        Map<String, DefaultDataHandler> converterMap = context.getBeansOfType(DefaultDataHandler.class);
        for (DefaultDataHandler converter : converterMap.values()) {
            chooseMap.put(converter.support(), converter);
        }
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

}
