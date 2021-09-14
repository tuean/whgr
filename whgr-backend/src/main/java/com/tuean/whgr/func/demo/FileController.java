package com.tuean.whgr.func.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

@RestController
public class FileController {

    public static Logger logger = LoggerFactory.getLogger(FileController.class);


    @RequestMapping(value = "/file", method = RequestMethod.POST)
    public void storeBase64(@RequestBody Base64Info info) throws IOException {
        String base64 = info.getFileContent(); // 获取base64字符串
        byte[] fileBytes = Base64.getDecoder().decode(base64); // 转成数组
        String fileOnServerPath = "/tmp/test.log"; // 定义服务器上的存储路径
        FileOutputStream out = new FileOutputStream(fileOnServerPath); // 文件输出流
        out.write(fileBytes);
        out.flush();
        out.close();
    }


}
