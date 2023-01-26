package com.example.utils;

import org.springframework.context.ApplicationContext;

public class SpringUtil {

    private static ApplicationContext applicationContext;

    public static Object getBean(String name) {
        return SpringUtil.applicationContext.getBean(name);
    }

    public static <T> T getBean(String name, Class<T> requiredType) {
        return SpringUtil.applicationContext.getBean(name, requiredType);
    }

    public static <T> T getBean(Class<T> requiredType) {
        return SpringUtil.applicationContext.getBean(requiredType);
    }

    public static void setApplicationContext(ApplicationContext applicationContext) {
        SpringUtil.applicationContext = applicationContext;
    }
}
