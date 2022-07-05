package com.tuean.whgr.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Slf4j
public class Util {

    public static boolean equals(String s1, String s2) {
        if (s1 == null || s2 == null) return false;
        return s1.equals(s2);
    }

    public static String eslipse(String s) {
        if (s == null) return "";
        if (s.length() < 20) return s;
        return s.substring(0, 20);
    }

    public static final String wrapperBase = "0123456789abcdefghijklmnopqrstuvwxyz";
    public static String getRandomString(int count) {
        Random random = new Random();
        StringBuffer sb = new StringBuffer();

        for(int i = 0; i < count; ++i) {
            int number = random.nextInt(wrapperBase.length());
            sb.append(wrapperBase.charAt(number));
        }

        return sb.toString();
    }

    public static JSONArray string2array(String source) {
        return JSONObject.parseArray(source);
    }

    public static void downloadResponse(HttpServletResponse httpServletResponse, Class clazz, List data) throws IOException {
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
//        httpServletResponse.setContentType("application/vnd.ms-excel");
        httpServletResponse.setContentType("application/octet-stream");
        httpServletResponse.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("数据", "UTF-8");
        httpServletResponse.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".xlsx");
        EasyExcel.write(httpServletResponse.getOutputStream(), clazz).sheet("1").doWrite(data);
        httpServletResponse.getOutputStream().flush();
        httpServletResponse.getOutputStream().close();
    }


    public static String makeReqId() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static Long getNowMills() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        return Long.parseLong(formatter.format(now));
    }


    /**
     * from
     *      https://stackoverflow.com/questions/35809827/java-8-completablefuture-allof-with-collection-or-list
     *
     * @param futuresList
     * @param <T>
     * @return
     */
    public static <T> CompletableFuture<List<T>> allOf(List<CompletableFuture<T>> futuresList) {
        CompletableFuture<Void> allFuturesResult =
                CompletableFuture.allOf(futuresList.toArray(new CompletableFuture[futuresList.size()]));
        return allFuturesResult.thenApply(v ->
                futuresList.stream().
                        map(future -> future.join()).
                        collect(Collectors.<T>toList())
        );
    }





    public static void main(String[] args) throws NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, UnsupportedEncodingException, NoSuchProviderException, InvalidKeyException, InvalidKeySpecException {
        String key = "192.168.";
        DES des = new DES(key);
        String info = "eboss," + getNowMills() + ",1qaz2wsx";
        String encrypt = des.encryptString(info);
        System.out.println(encrypt);
    }



}
