package com.ccb.ha.fw.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CorsConst {
    private static final Logger log =
            LoggerFactory.getLogger(CorsConst.class);
    @Value("${const.cors.blackList}")
    String blackList = null;
    @Value("${const.cors.whiteList}")
    String whiteList = "http://localhost:80";

    /**
     * 跨域的白名单
     *
     * @return
     */
    public String[] whiteList() {
        return this.toArray(this.whiteList);
    }

    /**
     * 黑名单
     *
     * @return
     */
    public String[] blackList() {
        return this.toArray(this.blackList);
    }

    /**
     * 将字符串按照|进行拆分形成字符串数组
     *
     * @param string
     * @return
     */
    private String[] toArray(String string) {
        if (log.isDebugEnabled()) {
            log.debug("toArray string = [{}].", string);
        }
        if ((string != null) && (string.length() > 0)) {
            String[] sites = string.split("\\|");
            if (log.isDebugEnabled()) {
                log.debug("toArray array = [{}].", sites);
                for (String str : sites) {
                    log.debug("toArray array = [{}].", str);
                }
            }
            return sites;
        }
        return new String[0];
    }

    public String getBlackList() {
        return blackList;
    }

    public void setBlackList(String blackList) {
        this.blackList = blackList;
    }


    @Override
    public String toString() {
        return "CorsConst{" +
                "whiteList='" + whiteList + '\'' +
                "blackList='" + blackList + '\'' +
                '}';
    }
}
