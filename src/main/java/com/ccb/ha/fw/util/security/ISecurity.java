package com.ccb.ha.fw.util.security;

/**
 * @author 冯志强
 */
public interface ISecurity {
    /**
     * 功能：设置加密密钥
     * 参数：newKey 新的加密密钥
     * 返回：
     * 说明：
     *
     * @param newKey
     */
    public void setKey(String newKey);

    /**
     * 功能：加密算法初始化
     * 参数：
     * 返回：
     * 说明：
     */
    public void init();

    /**
     * 功能：加密算法复位
     * 参数：
     * 返回：
     * 说明：
     */
    public void reset();

    /**
     * 功能：计算当前源串的加密串
     * 参数：需要加密的字符串
     * 返回：加密后的二进制数组
     * 说明：如果失败，返回空数组
     *
     * @param source
     * @return
     */
    public byte[] encode(String source);

    /**
     * 功能：计算一个数组的的MD5加密串
     * 参数：需要加密的字符串
     * 返回：加密后的二进制数组
     * 说明：如果失败，返回空数组
     *
     * @param source
     * @return
     */
    public byte[] encode(byte[] source);

    /**
     * 功能：计算一个字符串的加密串
     * 参数：需要加密的字符串
     * 返回：加密后的二进制数组
     * 说明：如果失败，返回空数组
     *
     * @param source
     * @return
     */
    public String encodeString(String source);

    /**
     * 功能：计算一个数组的的加密串
     * 参数：需要加密的字符串
     * 返回：加密字符串数组
     * 说明：如果失败，返回空数组
     *
     * @param source
     * @return
     */
    public String encodeString(byte[] source);

    /**
     * 功能：计算当前加密穿串的明文串
     * 参数：需要解密的字符串
     * 返回：解密后的二进制数组
     * 说明：如果失败，返回空数组
     *
     * @param source
     * @return
     */
    public byte[] decode(String source);

    /**
     * 功能：计算当前加密穿串的明文串
     * 参数：需要解密的数组
     * 返回：解密后的二进制数组
     * 说明：如果失败，返回空数组
     *
     * @param source
     * @return
     */
    public byte[] decode(byte[] source);

    /**
     * 功能：计算当前加密穿串的明文串
     * 参数：需要解密的数组
     * 返回：解密后的二进制数组
     * 说明：如果失败，返回空数组
     *
     * @param source
     * @return
     */
    public String decodeString(String source);

    /**
     * 功能：计算当前加密穿串的明文串
     * 参数：需要解密的字符数组
     * 返回：解密后的字符串
     * 说明：如果失败，返回空串
     *
     * @param source
     * @return
     */
    public String decodeString(byte[] source);

    /**
     * 功能：获得加密方法名称
     * 参数：
     * 返回：
     * 说明：
     *
     * @return
     */
    public String getSecurityName();
}
