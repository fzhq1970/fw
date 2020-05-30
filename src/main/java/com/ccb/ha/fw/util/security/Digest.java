package com.ccb.ha.fw.util.security;

import com.ccb.ha.fw.util.Base64Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author 冯志强
 */
public class Digest implements ISecurity{
    private static Logger log = LoggerFactory.getLogger(Digest.class);;
    protected String type = "MD";

    /**
     * Creates a new instance of Digest
     */
    public Digest(String type){
        this.type = type;
    }

    /**
     *功能：计算当前源串的MD5加密串
     *参数：
     *返回：
     *说明：如果失败，返回空数组
     */
    private byte[] calMd5(byte[] sourceString){
        //如果加密算法不存在或者与加密字符串为null，返回空数组
        byte[] result = new byte[0];
        if(sourceString == null){
            return  result;
        }
        //获取加密算法
        MessageDigest mdInstance = null;
        try {
            mdInstance = MessageDigest.getInstance(this.type);
        } catch (NoSuchAlgorithmException ex) {
            log.error("启动摘要加密算法失败");
            return result;
        }
        //计算加密结果
        mdInstance.reset();
        return mdInstance.digest(sourceString);
    }

    /**
     *功能：计算一个字符串的MD5加密串
     *参数：需要加密的字符串
     *返回：加密后的二进制数组
     *说明：如果失败，返回空数组
     */
    public byte[] encode(String source) {
        return this.calMd5(source.getBytes());
    }

    /**
     *功能：计算一个数组的的MD5加密串
     *参数：需要加密的字符串
     *返回：加密后的二进制数组
     *说明：如果失败，返回空数组
     */
    public byte[] encode(byte[] source) {
        return this.calMd5(source);
    }

    /**
     *功能：计算一个字符串的MD5加密串
     *参数：需要加密的字符串
     *返回：加密后的二进制数组
     *说明：如果失败，返回空数组
     */
    public String encodeString(String source) {
        byte[] md = this.encode(source);
        return Base64Util.encoder(md);
    }

    /**
     *功能：计算一个数组的的MD5加密串
     *参数：需要加密的字符串
     *返回：加密字符串数组
     *说明：如果失败，返回空数组
     */
    public String encodeString(byte[] source) {
        byte[] md = this.encode(source);
        return  Base64Util.encoder(md);
    }

    public byte[] decode(String source) {
        throw new UnsupportedOperationException("摘要算法不支持解密......");
    }

    public byte[] decode(byte[] source) {
        throw new UnsupportedOperationException("摘要算法不支持解密......");
    }

    public String decodeString(String source) {
        throw new UnsupportedOperationException("摘要算法不支持解密......");
    }

    public String decodeString(byte[] source) {
        throw new UnsupportedOperationException("摘要算法不支持解密......");
    }

    public String getSecurityName() {
        return "信息摘要算法："+this.type;
    }

    public void setKey(String newKey) {
        //摘要算法不需要密钥设置
    }

    public void init() {
        //摘要算法不需要初始化
    }

    public void reset() {
        //摘要算法不需要进行复位
    }
}
