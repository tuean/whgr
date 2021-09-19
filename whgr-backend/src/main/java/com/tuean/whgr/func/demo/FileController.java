package com.tuean.whgr.func.demo;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Calendar;
import java.util.UUID;

@RestController
public class FileController {

    public static Logger logger = LoggerFactory.getLogger(FileController.class);


    @RequestMapping(value = "/file", method = RequestMethod.POST)
    public void storeBase64(@RequestBody Base64Info info) throws IOException {
        String base64 = info.getFileContent(); // 获取base64字符串
        byte[] fileBytes = Base64.getDecoder().decode(base64); // 转成数组
        String filename = UUID.randomUUID().toString();
        String fileOnServerPath = "/tmp" + File.separator + filename; // 定义服务器上的存储路径
        FileOutputStream out = new FileOutputStream(fileOnServerPath); // 文件输出流
        out.write(fileBytes);
        out.flush();
        out.close();
    }

    @RequestMapping(value = "/file/formdata", method = RequestMethod.POST)
    public void upload(@RequestParam("file") MultipartFile[] files) throws IOException {
        String basePath = "/tmp";

        Calendar calendar = Calendar.getInstance();
        String monthDir = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1);
        String dirPath = basePath + File.separator + monthDir;
        File dirFile = new File(dirPath);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }

        String oldFileName = files[0].getOriginalFilename();
        String extension = FilenameUtils.getExtension(oldFileName);
        String newFileName = UUID.randomUUID().toString().replace("-", "");
        String newFilePath = dirFile + File.separator + newFileName;
        File newFile = new File(newFilePath + "." + extension);
        files[0].transferTo(newFile);

    }

}
