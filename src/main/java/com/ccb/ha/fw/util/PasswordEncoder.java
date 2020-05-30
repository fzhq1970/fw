package com.ccb.ha.fw.util;

import com.ccb.ha.fw.util.security.SHA1;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoder {
    private static final Logger log =
            LoggerFactory.getLogger(PasswordEncoder.class);

    /**
     * 加密
     *
     * @param password
     * @return
     */
    public String passwordEncode(String password, String salt) {
        if (log.isDebugEnabled()) {
            log.debug("passwordEncode : [{}].", password);
            log.debug("passwordEncode salt : [{}].", salt);
        }

        SHA1 sha1 = new SHA1();
        String cipher = sha1.encodeString(salt + password);
        if (log.isDebugEnabled()) {
            log.debug("passwordEncode cipher : [{}].", cipher);
        }
        return cipher;
    }
}
