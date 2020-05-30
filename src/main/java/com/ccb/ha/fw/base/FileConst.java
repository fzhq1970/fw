package com.ccb.ha.fw.base;

import com.ccb.ha.fw.base.exception.BaseException;
import com.ccb.ha.fw.util.UUIDUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FileConst {
    private static final Logger log =
            LoggerFactory.getLogger(FileConst.class);

    public static final long K = 1024;
    public static final long M = K * 1024;
    public static final long G = M * 1024;
    public static final long T = G * 1024;


    @Value("${const.file.upload.path}")
    String uploadFilePath = "E:\\temp\\upload";
    @Value("${const.file.upload.maxSize}")
    long maxSize = 10 * M;
    @Value("${const.file.upload.type}")
    String[] type;

    public String getUploadFilePath() {
        return uploadFilePath;
    }

    public void setUploadFilePath(String uploadFilePath) {
        this.uploadFilePath = uploadFilePath;
    }

    public long getMaxSize() {
        return this.maxSize;
    }

    /**
     * 获取一个参数，最大上传文件大小
     *
     * @return
     */
    public String getMaxSizeString() {
        long  size = getMaxSize();
        if (size >= T) {
            String str = Long.toString(size / T);
            return str + "T";
        }
        if (size >= G) {
            String str = Long.toString(size / G);
            return str + "G";
        }
        if (size >= M) {
            String str = Long.toString(size / M);
            return str + "M";
        }
        if (size >= K) {
            String str = Long.toString(size / K);
            return str + "K";
        }
        return Long.toString(size);
    }

    public void setMaxSize(long maxSize) {
        this.maxSize = maxSize;
    }

    public String[] getType() {
        return type;
    }

    public void setType(String type) {
        if ((type == null) || (type.length() > 0)) {
            this.type = type.split("\\|");
        } else {
            this.type = new String[0];
        }
    }

    /**
     * 判断能否上传
     *
     * @param extName 文件的扩展名称
     * @return
     */
    public boolean canUpload(String extName) {
        if ((this.type == null) || (this.type.length < 1)) {
            //没有设置可以上传的文件格式，默认全部都可以
            return true;
        }
        if ((extName == null) || (extName.length() < 1)) {
            //不能上传没有扩展名的文件
            return false;
        }
        for (int i = 0; i < this.type.length; i++) {
            //找到了扩展名称，可以上传
            if (extName.equalsIgnoreCase(this.type[i])) {
                return true;
            }
        }
        return false;
    }

    /**
     * 根据提供的文件名称，生成新的文件名称，如果是不允许上传的文件抛出异常
     *
     * @param fileName
     * @return
     */
    public String newFileName(String fileName) throws BaseException {
        if ((fileName == null) || (fileName.length() < 1)) {
            //文件名不正确
            throw new BaseException(StaticConst.ERROR_CODE, "文件名称有误");
        }
        //检查扩展名称，有扩展名称
        int index = fileName.lastIndexOf('.');
        if ((index < 0) || (index >= fileName.length())) {
            //扩展名称不正确
            throw new BaseException(StaticConst.ERROR_CODE, "不允许上传此类文件");
        }
        String extName = fileName.substring(index);
        if (!this.canUpload(extName)) {
            //不允许上传的文件类型
            throw new BaseException(StaticConst.ERROR_CODE, "不允许上传此类文件");
        }
        //产生一个新的文件名称
        String newFilename = (new StringBuilder(UUIDUtil.uuidString())
                .append(".")
                .append(extName))
                .toString();
        if (log.isDebugEnabled()) {
            log.debug("FILE ： [{}] = > [{}].",
                    fileName,
                    newFilename);
        }
        return newFilename;
    }

    @Override
    public String toString() {
        return "FileConst{" +
                "uploadFilePath='" + uploadFilePath + '\'' +
                ", maxSize=" + maxSize +
                ", type=" + this.type +
                '}';
    }
}
