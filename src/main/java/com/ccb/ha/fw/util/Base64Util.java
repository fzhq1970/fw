package com.ccb.ha.fw.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Base64;

public class Base64Util {
    private final static Base64.Decoder decoder = Base64.getDecoder();
    private final static Base64.Encoder encoder = Base64.getEncoder();
    private final static Logger log = LoggerFactory.getLogger(Base64Util.class);

    /**
     * @param src
     * @return
     */
    public static String encoder(String src) {
        if (src == null) {
            src = "";
        }
        return Base64Util.encoder.encodeToString(src.getBytes());
    }

    /**
     *
     * @param bytes
     * @return
     */
    public static String encoder(byte[] bytes){
        if (bytes == null) {
            bytes = "".getBytes();
        }
        return Base64Util.encoder.encodeToString(bytes);
    }

    /**
     *
     * @param src
     * @return
     */
    public static String decode(String src) {
        if (src == null) {
            return "";
        }
        return new String(Base64Util.decoder.decode(src));
    }
}
