package com.tuean.whgr.web;

import cn.hutool.core.date.StopWatch;
import com.tuean.whgr.entity.BaseResponse;
import com.tuean.whgr.util.HttpUtil;
import org.dhatim.fastexcel.Workbook;
import org.dhatim.fastexcel.Worksheet;
import org.dhatim.fastexcel.reader.ReadableWorkbook;
import org.dhatim.fastexcel.reader.Row;
import org.dhatim.fastexcel.reader.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

@RestController
public class ExcelController {

    private static Logger logger = LoggerFactory.getLogger(ExcelController.class);


    @RequestMapping(value = "/excel/import", method = RequestMethod.POST)
    public BaseResponse importExcel(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Runtime r = Runtime.getRuntime();
        long startMem = r.freeMemory();

        try (ReadableWorkbook wb = new ReadableWorkbook(file.getInputStream())) {

            List<String> result = new ArrayList<>(2 << 16);

            Sheet sheet = wb.getFirstSheet();
            try (Stream<Row> rows = sheet.openStream()) {
                rows.forEach(n -> {
                    String id = n.getCellAsString(0).orElse("");
                    String code = n.getCellAsString(1).orElse("");
                    result.add(id + code);
                });
            }
            logger.info("size:{}", result.size());
        } catch (Exception var) {
            logger.error("read excel file error", var);
            return BaseResponse.error();
        } finally {
            stopWatch.stop();
            logger.info("cost: {} ms", stopWatch.getTotalTimeMillis());
            long endMem = r.freeMemory();
            logger.info("memory use: {} b", endMem - startMem);
        }

        return BaseResponse.ok();
    }


    @RequestMapping(value = "/excel/export", method = RequestMethod.GET)
    public void exportExcel() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        HttpServletResponse response = servletRequestAttributes.getResponse();
        Runtime r = Runtime.getRuntime();
        long startMem = r.freeMemory();
        try (OutputStream out = response.getOutputStream()){
            Workbook wb = new Workbook(out, "demo", "1.0");
            Worksheet ws = wb.newWorksheet("Sheet 1");
            ws.range(0, 0, 1, 1).merge();
            ws.value(0, 0, "This is a string in A1");

            ws.range(0, 10, 0, 12).merge();
            ws.value(0, 10, "merged header");

            ws.value(1, 10, "h1");
            ws.value(1, 11, "h1");
            ws.value(1, 12, "h1");

            wb.finish();

            HttpUtil.downloadResponse(response, "test", "xlsx");

        } catch (IOException var) {
            logger.error("export excel file error", var);
        } finally {
            stopWatch.stop();
            logger.info("cost: {} ms", stopWatch.getTotalTimeMillis());
            long endMem = r.freeMemory();
            logger.info("memory use: {} b", startMem - endMem);
        }

    }



}
