package com.ccb.ha.fw;

import com.ccb.ha.fw.base.jwt.JwtConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;


@SpringBootApplication
public class FwApplication {
    private static final Logger log =
            LoggerFactory.getLogger(FwApplication.class);
    final JwtConst jwtConst;

    public FwApplication(JwtConst jwtConst) {
        this.jwtConst = jwtConst;
    }

    public static void main(String[] args) {
        SpringApplication.run(FwApplication.class, args);
    }

    @Component
    class StartupRunner implements CommandLineRunner {
        @Override
        public void run(String... args) throws Exception {
            if (log.isDebugEnabled()) {
                log.debug("args : [{}].", args);
                log.debug("jwtConst : [{}].", jwtConst);
            }
        }
    }

}
