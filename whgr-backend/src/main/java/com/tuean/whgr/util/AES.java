package com.tuean.whgr.util;

import com.alibaba.fastjson.JSON;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xxx
 * Date: 2018-03-28 下午3:52
 */
public class AES {

    private static final String AES = "AES";

    /**
     * AES key 字节长度 只能为 16, 24, 32
     */
    private static final String CRYPT_KEY = "qwerasdfzxcvtgby";

    private static final String SPLIT = "\\|";

    // http://imsfz.gjzq.cn:8090/ims-web/login/eboss?request_url=http%3A%2F%2Fimsfz.gjzq.cn%3A8090%2Fims-web%2Findex%3Fst3d718522532248555520&system_id=2&encrypt=

    public static void main(String[] args) throws Exception {
//        String source = "ismp|zhongxiaotian|" + System.currentTimeMillis();
//        String source = "ismp|zhongxiaotian|" + "1618975195725";
        String source = "10005";
        String encrypt = encrypt(source);
        System.out.println(encrypt);
        String decrypt = decrypt(encrypt);
        System.out.println(decrypt);
        System.out.println(JSON.toJSONString(decrypt.split(SPLIT)));
        System.out.println("http://imsfz.gjzq.cn:8090/ims-web/login/eboss?request_url=http%3A%2F%2Fimsfz.gjzq.cn%3A8090%2Fims-web%2Findex%3Fst3d718522532248555520&system_id=2&encrypt=" + encrypt);
    }


    /**
     * 加密服务  使用默认crypt_key
     *
     * @param data
     * @return
     * @throws Exception
     */
    public final static String encrypt(String data) throws Exception{
        return encrypt(data, CRYPT_KEY);
    }


    /**
     * 解密服务  使用默认crypt_key
     *
     * @param data
     * @return
     * @throws Exception
     */
    public final static String decrypt(String data) throws Exception {
        return decrypt(data, CRYPT_KEY);
    }


    /**
     * 加密服务 使用自定义crypt_key
     *
     * @param data
     * @param key 注意自定义的key字节长最好不要超过128
     * @return
     * @throws Exception
     */
    public static String encrypt(String data, String key) throws Exception {
        if(data == null || key == null){
            throw new Exception("数据源为空 or 加密key为空");
        }
        try {
            return byte2hex(encrypt(data.getBytes(), key));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 解密服务 使用自定义crypt_key
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static String decrypt(String data, String key) throws Exception {
        if(data == null || key == null) {
            throw new Exception("数据源为空 or 加密key为空");
        }
        try {
            return new String(decrypt(hex2byte(data.getBytes()), key));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 加密方法实现
     *
     * @param src
     * @param key
     * @return
     */
    private static byte[] encrypt(byte[] src, String key) throws Exception {
        try {
            Cipher cipher = Cipher.getInstance(AES);
            SecretKeySpec securekey = new SecretKeySpec(key.getBytes(), AES);
            // 设置密钥和加密形式
            cipher.init(Cipher.ENCRYPT_MODE, securekey);
            return cipher.doFinal(src);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException var1) {
            var1.printStackTrace();
            throw new Exception("加密算法异常");
        } catch (InvalidKeyException var2){
            var2.printStackTrace();
            throw new Exception("加密key设置异常");
        }
    }


    /**
     * 解密方法实现
     *
     * @param src
     * @param key
     * @return
     * @throws Exception
     */
    private static byte[] decrypt(byte[] src, String key) throws Exception {
        try {
            Cipher cipher = Cipher.getInstance(AES);
            // 设置加密Key
            SecretKeySpec securekey = new SecretKeySpec(key.getBytes(), AES);
            // 设置密钥和解密形式
            cipher.init(Cipher.DECRYPT_MODE, securekey);
            return cipher.doFinal(src);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException var1) {
            var1.printStackTrace();
            throw new Exception("加密算法异常");
        } catch (InvalidKeyException var2){
            var2.printStackTrace();
            throw new Exception("加密key设置异常");
        }
    }


    /**
     * 二进制转十六进制字符串
     *
     * @param b
     * @return
     */
    private static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1){
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs.toUpperCase();
    }


    /**
     * 十六进制转二进制
     *
     * @param b
     * @return
     */
    private static byte[] hex2byte(byte[] b) {
        if ((b.length % 2) != 0) {
            throw new IllegalArgumentException("长度不是偶数");
        }
        byte[] b2 = new byte[b.length / 2];
        for (int n = 0; n < b.length; n += 2) {
            String item = new String(b, n, 2);
            b2[n / 2] = (byte) Integer.parseInt(item, 16);
        }
        return b2;
    }
}
