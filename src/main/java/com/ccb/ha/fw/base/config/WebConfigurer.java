package com.ccb.ha.fw.base.config;

import com.ccb.ha.fw.base.CorsConst;
import com.ccb.ha.fw.base.FileConst;
import com.ccb.ha.fw.base.interceptor.JWTAuthenticationInterceptor;
import com.ccb.ha.fw.util.JsonUtils;
import com.ccb.ha.fw.util.LocalTimeUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.format.Formatter;
import org.springframework.web.servlet.config.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

@SpringBootConfiguration
public class WebConfigurer implements WebMvcConfigurer {
    private static final Logger log =
            LoggerFactory.getLogger(WebConfigurer.class);

    final FileConst fileConst;
    final CorsConst corsConst;

    public WebConfigurer(FileConst fileConst, CorsConst corsConst) {
        this.fileConst = fileConst;
        this.corsConst = corsConst;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (log.isDebugEnabled()) {
            log.debug("ResourceHandlerRegistry registry : [{}]", registry);
            log.debug("ResourceHandlerRegistry fileConst : [{}]", fileConst);
        }
        registry.addResourceHandler("/api/file/**")
                .addResourceLocations("file:" + this.fileConst.getUploadFilePath());
        if (log.isDebugEnabled()) {
            log.debug("ResourceHandlerRegistry registry : [{}]", registry);
        }
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(this.corsConst.whiteList())
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "PUT")
                .maxAge(3600);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if (log.isDebugEnabled()) {
            log.debug("registry : [{}].", registry);
        }
        InterceptorRegistration ir = registry.addInterceptor(newJWTInterceptor());
        ir.addPathPatterns("/**");
        if (log.isDebugEnabled()) {
            log.debug("InterceptorRegistration : [{}].", ir);
        }
    }


    /**
     * 生成一个拦截器实例
     *
     * @return
     */
    @Bean
    public JWTAuthenticationInterceptor newJWTInterceptor() {
        JWTAuthenticationInterceptor jwti = new JWTAuthenticationInterceptor();
        return jwti;
    }

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = JsonUtils.objectMapper();
        return objectMapper;
    }

    /***
     * Date日期类型转换器
     */
    @Bean
    public Formatter<Date> dateFormatter() {
        return new Formatter<Date>() {
            @Override
            public Date parse(String text, Locale locale) {
                Date date = null;
                try {
                    date = DateUtils.parseDate(text,
                            LocalTimeUtils.DEFAULT_DATE_TIME_PATTERN);
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
                return date;
            }

            @Override
            public String print(Date date, Locale locale) {
                return DateFormatUtils.format(date,
                        LocalTimeUtils.DEFAULT_DATE_TIME_PATTERN,
                        TimeZone.getDefault(),
                        locale);
            }
        };
    }

    @Bean
    public Formatter<LocalDate> localDateFormatter() {
        return new Formatter<LocalDate>() {
            @Override
            public LocalDate parse(String text, Locale locale) {
                return LocalDate.parse(text,
                        DateTimeFormatter.ofPattern(
                                LocalTimeUtils.DEFAULT_DATE_PATTERN, locale));
            }

            @Override
            public String print(LocalDate object, Locale locale) {
                return DateTimeFormatter.ofPattern(
                        LocalTimeUtils.DEFAULT_DATE_PATTERN, locale).format(object);
            }
        };
    }

    @Bean
    public Formatter<LocalTime> localTimeFormatter() {
        return new Formatter<LocalTime>() {
            @Override
            public LocalTime parse(String text, Locale locale) {
                return LocalTime.parse(text,
                        DateTimeFormatter.ofPattern(LocalTimeUtils.DEFAULT_TIME_PATTERN, locale));
            }

            @Override
            public String print(LocalTime object, Locale locale) {
                return DateTimeFormatter.ofPattern(
                        LocalTimeUtils.DEFAULT_TIME_PATTERN, locale).format(object);
            }
        };
    }

    @Bean
    public Formatter<LocalDateTime> localDateTimeFormatter() {
        return new Formatter<LocalDateTime>() {
            @Override
            public String print(LocalDateTime localDateTime, Locale locale) {
                return DateTimeFormatter.ofPattern(
                        LocalTimeUtils.DEFAULT_DATE_TIME_PATTERN, locale).format(localDateTime);
            }

            @Override
            public LocalDateTime parse(String text, Locale locale) {
                return LocalDateTime.parse(text,
                        DateTimeFormatter.ofPattern(LocalTimeUtils.DEFAULT_DATE_TIME_PATTERN, locale));
            }
        };
    }
}
