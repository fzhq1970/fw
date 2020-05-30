package com.ccb.ha.fw.base.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class ErrorConfig implements ErrorPageRegistrar {
    private static final Logger log =
            LoggerFactory.getLogger(ErrorConfig.class);

    @Override
    public void registerErrorPages(ErrorPageRegistry registry) {
        if(log.isDebugEnabled()){
            log.debug("registry = [{}].",registry);
        }
        ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/index.html");
        registry.addErrorPages(error404Page);
        if(log.isDebugEnabled()){
            log.debug("registry = [{}].",registry);
        }
    }
}
