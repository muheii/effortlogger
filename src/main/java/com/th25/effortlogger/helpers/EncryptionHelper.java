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

/**
 * EncryptionHelper provides functionality for secure data encryption/decryption
 * 
 * This class implements AES encryption with the following features:
 * Password-based key derivation using PBKDF2
 * Unique salt generation per entry for enhanced security
 * CBC mode with PKCS5 padding for secure block cipher operation
 */
public class EncryptionHelper {

    /**
     * Derives a SecretKey from a password using PBKDF2
     * 
     * @param password The user password to derive the key from
     * @param salt A unique salt value to prevent rainbow table attacks
     * @return A 256-bit AES SecretKey
     */
    public static SecretKey getKeyFromPassword(String password, String salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 256);

        return new SecretKeySpec(factory.generateSecret(spec)
                .getEncoded(), "AES");
    }

    /**
     * Generates a random initialization vector for AES encryption.
     * The IV prevents identical plaintext blocks from producing identical ciphertext.
     */
    public static IvParameterSpec generateIv() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }

    /**
     * Encrypts using AES
     * 
     * @param algorithm The encryption algorithm (AES/CBC/PKCS5Padding)
     * @param input The plaintext to encrypt
     * @param key The SecretKey for encryption
     * @param iv The initialization vector
     * @return Base64-encoded encrypted string
     */
    public static String encrypt(String algorithm, String input, SecretKey key, IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        byte[] cipherText = cipher.doFinal(input.getBytes());
        return Base64.getEncoder().encodeToString(cipherText);
    }

    /**
     * Decrypts ciphertext using AES algorithm.
     * 
     * @param algorithm The encryption algorithm (AES/CBC/PKCS5Padding)
     * @param cipherText The Base64-encoded encrypted text
     * @param key The SecretKey for decryption
     * @param iv The Initialization Vector used during encryption
     * @return The decrypted plaintext
     */
    public static String decrypt(String algorithm, String cipherText, SecretKey key, IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        byte[] plainText = cipher.doFinal(Base64.getDecoder()
                .decode(cipherText));
        return new String(plainText);
    }

    /**
     * Encrypts log entry data with user-specific encryption.
     * 1. Generates a unique salt for this log entry
     * 2. Creates an IV for CBC mode
     * 3. Derives a key from the user's password and salt
     * 4. Encrypts the log data
     * 5. Returns a composite string with ciphertext, encoded key, and salt
     * 
     * @param plainText The log data to encrypt
     * @param password The user's password for encryption
     * @return Formatted string with encrypted data and necessary decryption components
     */
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
