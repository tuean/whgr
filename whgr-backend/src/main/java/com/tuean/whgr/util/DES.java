package com.tuean.whgr.util;

import org.apache.mina.util.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;

public class DES {

    static {
        if (Security.getProperty(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    private static final String ENCODING = "UTF-8";

    private static final String ALGORITHM_NAME = "DES";

    private static String strDefaultKey = "kmssSecureKey";

    private Cipher encryptCipher = null;

    private Cipher decryptCipher = null;

    public DES() throws NoSuchPaddingException, NoSuchAlgorithmException, UnsupportedEncodingException, NoSuchProviderException, InvalidKeyException, InvalidKeySpecException {
        this(strDefaultKey);
    }

    public DES(String strKey) throws NoSuchPaddingException, NoSuchAlgorithmException, UnsupportedEncodingException, NoSuchProviderException, InvalidKeyException, InvalidKeySpecException {
        this(strKey, false);
    }


    public DES(String strKey, boolean isRandom) throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, UnsupportedEncodingException, InvalidKeySpecException {
        Key key = null;
        if (!isRandom) {
            key = getKey(strKey);
        } else {
            key = getRandomKey(strKey);
        }
        encryptCipher = Cipher.getInstance("DES/ECB/PKCS5Padding", BouncyCastleProvider.PROVIDER_NAME);
        encryptCipher.init(Cipher.ENCRYPT_MODE, key);

        decryptCipher = Cipher.getInstance("DES/ECB/PKCS5Padding", BouncyCastleProvider.PROVIDER_NAME);
        decryptCipher.init(Cipher.ENCRYPT_MODE, key);
    }



    private Key getKey(String str) throws UnsupportedEncodingException, NoSuchProviderException, NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException {
        DESKeySpec dks = new DESKeySpec(str.getBytes(ENCODING));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(
                ALGORITHM_NAME, BouncyCastleProvider.PROVIDER_NAME
        );
        return keyFactory.generateSecret(dks);
    }

    private Key getRandomKey(String str) throws NoSuchProviderException, NoSuchAlgorithmException, UnsupportedEncodingException {
        KeyGenerator generateKey = KeyGenerator.getInstance(ALGORITHM_NAME, BouncyCastleProvider.PROVIDER_NAME);
        generateKey.init(new SecureRandom(str.getBytes(ENCODING)));
        return generateKey.generateKey();
    }

    public byte[] encrypt(byte[] bytes) throws BadPaddingException, IllegalBlockSizeException {
        return encryptCipher.doFinal(bytes);
    }

    public byte[] decrypt(byte[] bytes) throws BadPaddingException, IllegalBlockSizeException {
        return decryptCipher.doFinal(bytes);
    }

    public String encryptString(String str) throws UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException {
        return new String(Base64.encodeBase64(encrypt(str.getBytes(ENCODING)),
                true), ENCODING).replace("\n", "");
    }

    public String decryptString(String str) throws UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException {
        return new String(decrypt(Base64.decodeBase64(str.getBytes(ENCODING))), ENCODING);
    }





}
