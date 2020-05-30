package com.ccb.ha.fw.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class UUIDUtil {
    private static final Logger log
            = LoggerFactory.getLogger(UUIDUtil.class);

    /**
     * 获取一个UUID字符串，原始的，含有-
     *
     * @return
     */
    public static String uuid() {
        UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString();
        if(log.isDebugEnabled()){
            log.debug("UUID string = [{}].",uuidString);
        }
        return uuidString;
    }

    public static String uuidString(){
        UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString().replace("-","");
        if(log.isDebugEnabled()){
            log.debug("UUID string = [{}].",uuidString);
        }
        return uuidString;
    }
}
