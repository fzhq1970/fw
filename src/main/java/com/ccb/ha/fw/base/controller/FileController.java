package com.ccb.ha.fw.base.controller;

import com.ccb.ha.fw.base.FileConst;
import com.ccb.ha.fw.base.Result;
import com.ccb.ha.fw.base.exception.BaseException;
import com.ccb.ha.fw.util.UUIDUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/file")
public class FileController extends BaseController {
    private static final Logger log =
            LoggerFactory.getLogger(FileController.class);

    @Autowired
    FileConst fileConst;

    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    public Result upload(MultipartFile file) {
        //文件长度超长了
        if (file.getSize() > this.fileConst.getMaxSize()) {
            return Result.error("上传文件过大，不能超过[{}].",
                    this.fileConst.getMaxSizeString());
        }
        String fn = file.getOriginalFilename();
        //产生一个新的文件名称
        try {
            String newFilename = this.fileConst.newFileName(fn);
            if (log.isDebugEnabled()) {
                log.debug("UPLOAD FILE ： [{}] = > [{}].",
                        fn, newFilename);
            }
            String folder = this.fileConst.getUploadFilePath();
            File imageFolder = new File(folder);
            File f = new File(imageFolder, newFilename);
            if (!f.getParentFile().exists()) {
                f.getParentFile().mkdirs();
            }
            file.transferTo(f);
            return Result.success(newFilename);
        } catch (IOException e) {
            log.error("upload file failed: {}.", e.getMessage());
            return Result.error(e.getMessage());
        } catch (BaseException be) {
            log.error("upload file failed: {}.", be.getMessage());
            return Result.error(be);
        }
    }

    @CrossOrigin
    @PostMapping("/covers")
    public String coversUpload(MultipartFile file) throws Exception {
        String folder = "E:/temp/upload";
        File imageFolder = new File(folder);
        File f = new File(imageFolder, UUIDUtil.uuidString()
                + file.getOriginalFilename()
                .substring(file.getOriginalFilename().length() - 4));
        if (!f.getParentFile().exists())
            f.getParentFile().mkdirs();
        try {
            file.transferTo(f);
            String imgURL = "http://localhost:5000/fw/api/file/" + f.getName();
            return imgURL;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

}
