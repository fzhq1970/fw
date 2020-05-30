package com.ccb.ha.fw.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.InstantSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonUtils {
    private static final Logger log =
            LoggerFactory.getLogger(JsonUtils.class);

    /**
     * 转换为JSON字符串
     *
     * @param src
     * @return
     */
    public static String obj2Json(Object src) {
        if (log.isDebugEnabled()) {
            log.debug("objectToJson: [{}].", src);
        }
        ObjectMapper objectMapper = JsonUtils.objectMapper();
        try {
            String value = objectMapper.writeValueAsString(src);
            if (log.isDebugEnabled()) {
                log.debug("objectToJson: src = [{}], dest = [{}].",
                        src, value);
            }
            return value;
        } catch (JsonProcessingException e) {
            log.error("objectToJson: src = [{}], error = [{}].",
                    src, e.getMessage());
            return "";
        }
    }

    /**
     * 将JSON串转换成类型实例
     *
     * @param src
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T json2Pojo(String src, Class<T> type) {
        if (log.isDebugEnabled()) {
            log.debug("formatToPojo: src = [{}], type = [{}].", src, type);
        }
        if ((src == null) || (src.isEmpty())) {
            return null;
        }
        ObjectMapper objectMapper = JsonUtils.objectMapper();
        try {
            T value = objectMapper.readValue(src, type);
            if (log.isDebugEnabled()) {
                log.debug("objectToJson: src = [{}], dest = [{}].",
                        src, value);
            }
            return value;
        } catch (JsonProcessingException e) {
            log.error("objectToJson: src = [{}], error = [{}].",
                    src, e.getMessage());
            return null;
        }
    }


    public static Map<String, Object> json2Obj(String src) {
        if (log.isDebugEnabled()) {
            log.debug("formatToPojo: src = [{}]", src);
        }
       return JsonUtils.json2Pojo(src,Map.class);
    }

    /**
     * @param src
     * @param type
     * @param <T>
     * @return
     */
    public static <T> List<T> json2List(String src, Class<T> type) {
        if (log.isDebugEnabled()) {
            log.debug("jsonToList: src = [{}], dest = [{}].",
                    src, type);
        }
        if ((src != null) && (!src.isEmpty())) {
            ObjectMapper objectMapper = JsonUtils.objectMapper();
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, type);
            try {
                List<T> list = objectMapper.readValue(src, javaType);
                if (log.isDebugEnabled()) {
                    log.debug("jsonToList: src = [{}], dest = [{}].",
                            src, list);
                }
                return list;
            } catch (JsonProcessingException e) {
                log.debug("jsonToList: src = [{}], error = [{}].",
                        src, e.getMessage());
            }
        }
        return new ArrayList<>();
    }

    /**
     * 处理localtime
     *
     * @return
     */
    public static ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class,
                new LocalDateTimeSerializer(LocalTimeUtils.dateTimeFormatter()));
        javaTimeModule.addDeserializer(
                LocalDateTime.class,
                new LocalDateTimeDeserializer(LocalTimeUtils.dateTimeFormatter()));
        javaTimeModule.addSerializer(
                LocalDate.class,
                new LocalDateSerializer(LocalTimeUtils.dateFormatter()));
        javaTimeModule.addDeserializer(LocalDate.class,
                new LocalDateDeserializer(LocalTimeUtils.dateFormatter()));
        javaTimeModule.addSerializer(LocalTime.class,
                new LocalTimeSerializer(LocalTimeUtils.timeFormatter()));
        javaTimeModule.addDeserializer(LocalTime.class,
                new LocalTimeDeserializer(LocalTimeUtils.timeFormatter()));

        objectMapper.registerModule(javaTimeModule);
        return objectMapper;
    }
}

