package com.example.config;

import com.example.config.databinding.DateDeserializer;
import com.example.config.databinding.DateSerializer;
import com.example.utils.JsonUtil;
import com.example.utils.SpringUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Date;

@Configuration
public class UtilsConfig {

    final ApplicationContext applicationContext;

    public UtilsConfig(
            ApplicationContext applicationContext
    ) {
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    void setUp() {
        /*JsonUtil*/
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        mapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);
        mapper.configure(DeserializationFeature.USE_BIG_INTEGER_FOR_INTS, true);
        mapper.configure(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS, true);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        SimpleModule module = new SimpleModule();
        module.addSerializer(Date.class, new DateSerializer());
        module.addDeserializer(Date.class, new DateDeserializer());
        mapper.registerModule(module);
        JsonUtil.setObjectMapper(mapper);
        /*SpringUtil*/
        SpringUtil.setApplicationContext(applicationContext);
    }


}
