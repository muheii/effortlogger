package com.th25.effortlogger.helpers;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;

public class EncryptionHelper {
    public static SecretKey getKeyFromPassword(String password, String salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 256);

        return new SecretKeySpec(factory.generateSecret(spec)
                .getEncoded(), "AES");
    }

    public static IvParameterSpec generateIv() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }

    public static String encrypt(String algorithm, String input, SecretKey key, IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        byte[] cipherText = cipher.doFinal(input.getBytes());
        return Base64.getEncoder().encodeToString(cipherText);
    }

    public static String decrypt(String algorithm, String cipherText, SecretKey key, IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        byte[] plainText = cipher.doFinal(Base64.getDecoder()
                .decode(cipherText));
        return new String(plainText);
    }

    public static String encryptLog(String plainText, String password) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, InvalidKeySpecException {
        // generate salt per log
        final Random r = new SecureRandom();
        byte[] salt = new byte[16];
        r.nextBytes(salt);

        StringBuilder sb = new StringBuilder();

        for (byte b : salt) {
            sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        }

        IvParameterSpec ivParameterSpec = generateIv();
        SecretKey key = getKeyFromPassword(password, sb.toString());
        String cipherText = encrypt("AES/CBC/PKCS5Padding", plainText, key, ivParameterSpec);

        return cipherText + "," + Base64.getEncoder().encodeToString(key.getEncoded()) + "," + sb;
    }
}
