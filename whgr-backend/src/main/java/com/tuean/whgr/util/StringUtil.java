package com.tuean.whgr.util;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.exception.ExceptionUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Formatter;
import java.util.Random;

/**
 * Created by zhongxiaotian on 2018/7/4.
 */
public class StringUtil {

    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();

    /**
     * copy from
     *      https://stackoverflow.com/questions/9655181/how-to-convert-a-byte-array-to-a-hex-string-in-java
     *
     * 大写
     *
     * @param bytes
     * @return
     */
    public static String bytesToHexUpperCase(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    /**
     * copy from
     *      DingDing example
     *
     * 小写
     *
     * @param hash
     * @return
     */
    public static String bytesToHexLowerCase(byte[] hash) {
        Formatter formatter = new Formatter();
        byte[] var2 = hash;
        int var3 = hash.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            byte b = var2[var4];
            formatter.format("%02x", new Object[]{Byte.valueOf(b)});
        }

        String result = formatter.toString();
        formatter.close();
        return result;
    }

    /**
     * 生成指定长度 包含 0-9 a-z 的随机字符串
     *
     * @param length
     * @return
     */
    public static String randomString(int length){
        if(length < 1){
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer();
        Random random = new Random();
        double next;
        for(int x = 0; x < length; x++){
            next = random.nextDouble();
            if(next < 0.5){
                // numbers
                Double d = next * 2 * 10;
                stringBuffer.append(d.intValue());
            }else{
                // alphabet
                int alt = random.nextInt(26);
                stringBuffer.append((char) ('a' + alt));
            }
        }
        return stringBuffer.toString();
    }


    /**
     * 生成指定长度 包含 0-9 a-z A-Z 的随机字符串
     *
     * @param length
     * @return
     */
    public static String randomFullString(int length){
        if(length < 1){
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer();
        Random random = new Random();
        double next;
        for(int x = 0; x < length; x++){
            next = random.nextDouble();
            if(next < (float) 1 / 3){
                // numbers
                Double d = next * 2 * 10;
                stringBuffer.append(d.intValue());
            }else if(next > (float) 2 / 3){
                // alphabet uppercase
                int alt = random.nextInt(26);
                stringBuffer.append((char) ('a' + alt));
            }else{
                // alphabet lowercase
                int alt = random.nextInt(26);
                stringBuffer.append((char) ('A' + alt));
            }
        }
        return stringBuffer.toString();
    }


    /**
     * 罗马数字 转为 阿拉伯数字
     *      from leetcode
     *
     * @param s
     * @return
     */
    public static int romanToInt(String s) {
        char[] sc = s.toCharArray();
        int ans = toInt(sc[0]);
        for (int i = 1; i < s.length(); i++) {
            ans += toInt(sc[i]);
            if (toInt(sc[i - 1]) < toInt(sc[i])) {
                ans -= toInt(sc[i - 1]) * 2;
            }
        }
        return ans;
    }

    static int toInt(char s) {
        switch(s) {
            case 'I':return 1;
            case 'V':return 5;
            case 'X':return 10;
            case 'L':return 50;
            case 'C':return 100;
            case 'D':return 500;
            case 'M':return 1000;
            default: return 0;
        }
    }



    public static String inputStreamToStringByApacheUtils(InputStream inputStream, String encoding){
        try {
            StringWriter writer = new StringWriter();
            IOUtils.copy(inputStream, writer, encoding);
            return writer.toString();
        } catch (IOException var) {
            var.printStackTrace();
        }
        return null;
    }

    public static String inputStreamToString(InputStream inputStream){
        java.util.Scanner s = new java.util.Scanner(inputStream).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    public static String getStackTrace(Throwable a){
        return ExceptionUtils.getStackTrace(a);
    }


    /**
     * copy from
     *      dingding example
     *
     * @param count
     * @return
     */
    public static String getRandomStr(int count) {
        String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();

        for(int i = 0; i < count; ++i) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }

        return sb.toString();
    }

    /**
     * copy from
     *      dingding example
     *
     * @param count
     * @return
     */
    public static byte[] int2Bytes(int count) {
        byte[] byteArr = new byte[]{(byte)(count >> 24 & 255), (byte)(count >> 16 & 255), (byte)(count >> 8 & 255), (byte)(count & 255)};
        return byteArr;
    }

    /**
     * copy from
     *      dingding example
     *
     * @param byteArr
     * @return
     */
    public static int bytes2int(byte[] byteArr) {
        int count = 0;

        for(int i = 0; i < 4; ++i) {
            count <<= 8;
            count |= byteArr[i] & 255;
        }

        return count;
    }


    /**
     * 将整数num转化为32位的二进制
     *
     * @param num
     * @return
     */
    public static String toFullBinaryString(int num) {
        char[] chs = new char[Integer.SIZE];
        for (int i = 0; i < Integer.SIZE; i++) {
            chs[Integer.SIZE - 1 - i] = (char) (((num >> i) & 1) + '0');
            System.out.println(chs[Integer.SIZE - 1 - i]);
        }
        return new String(chs);
    }

    public static String binaryStringToInteger(String source) {
        return Integer.valueOf(source,2).toString();
    }



}
