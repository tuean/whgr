package com.tuean.whgr;

import com.tuean.whgr.util.FileUtil;
import com.tuean.whgr.util.Html2Pdf;

import java.io.*;

public class Html2PdfTest {

    private static String htmlPath = "/Users/tuean/Downloads/test.html";

    private static String outPath = "/Users/tuean/Downloads/";

    private static String fileName = "export.pdf";

    public static void main(String[] args) throws Exception {
        String content = FileUtil.fileContent(htmlPath);
        System.out.println(content);
        ByteArrayOutputStream stream = Html2Pdf.convertHtmlToPdf(content);
        FileOutputStream out = new FileOutputStream(outPath + File.separator + fileName);
        stream.writeTo(out);
    }

}
