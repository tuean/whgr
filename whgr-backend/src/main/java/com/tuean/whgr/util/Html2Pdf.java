package com.tuean.whgr.util;

import com.lowagie.text.*;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;

public class Html2Pdf {

//    private static String fontUrl = System.getProperty("catalina.home") + File.separator  + "webapps" +
//            File.separator + "File" + File.separator + "Formwork" + File.separator + "SIMSUN.TTC";

    private static String fontUrl = "/Users/tuean/IdeaProjects/whgr/whgr-backend/src/main/resources/simsun.ttc";

    private static String headPath = "";

    public static ByteArrayOutputStream convertHtmlToPdf(String htmlContent) throws Exception {

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ITextRenderer renderer = new ITextRenderer();
        // 解决中文支持问题
        ITextFontResolver fontResolver = renderer.getFontResolver();

        fontResolver.addFont(fontUrl, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);

        renderer.setDocumentFromString(htmlContent);
        //解决图片的相对路径问题
        String baseUrl = "file:" + System.getProperty("catalina.home") + File.separator  + "webapps" + File.separator + "files";
        renderer.getSharedContext().setBaseURL(baseUrl);

        renderer.layout();
        renderer.createPDF(os);
        os.write(htmlContent.getBytes());


        os.flush();
        os.close();
        return os;
    }

    public static OutputStream addHeaderAndAvatar(OutputStream out){
        Document document = null;

        try{
            Image logo = Image.getInstance(headPath);
            logo.setAlignment(Image.MIDDLE);
            logo.scaleAbsoluteHeight(20);
            logo.scaleAbsoluteWidth(20);
            logo.scalePercent(100);
            Chunk chunk = new Chunk(logo,0,-45);
            HeaderFooter headerFooter = new HeaderFooter(new Phrase(chunk), false);
            headerFooter.setAlignment(Element.ALIGN_CENTER);
            headerFooter.setBorder(Rectangle.NO_BORDER);
            document = new Document();
            PdfWriter.getInstance(document, out);
            document.open();
            document.newPage();
            document.setHeader(headerFooter);

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(document != null){
                document.close();
            }
        }



        return out;
    }

}

