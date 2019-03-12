package net.vtstar.generate.config;

import lombok.extern.slf4j.Slf4j;
import net.vtstar.generate.utils.ConstantsUtils;

@Slf4j
public class TenantContext {

    private static final ThreadLocal<String> cache = new ThreadLocal<>();

    public static void set(String key) {
        cache.set(key);
    }

    public static String get() {
        String key = cache.get();
        if (null == key) {
            key = ConstantsUtils.TENANT_DEFAULT;
        }

        log.debug("getTenantCode -> {}", key);
        return key;
    }

    public static void clear() {
        log.trace("clear context");
        cache.remove();
    }
}
