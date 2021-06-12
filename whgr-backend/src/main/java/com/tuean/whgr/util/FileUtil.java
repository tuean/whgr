package com.tuean.whgr.util;


import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

public class FileUtil {

    public static String fileContent(String filePath) throws IOException {
        return Files.asCharSource(new File(filePath), Charset.defaultCharset()).read();
    }

}
