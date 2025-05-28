package com.yourssu.rookieton.security;

import com.yourssu.rookieton.entity.converter.PrivacyEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

@Component
public class DataCipher implements PrivacyEncryptor {
    private static final String AES = "AES";
    private static final String AES_GCM_NO_PADDING = "AES/GCM/NoPadding";
    private static final int GCM_IV_LENGTH = 12;
    private static final int GCM_TAG_LENGTH = 16;

    @Value("${AES_SECRET_KEY}")
    private String secretKey;

    @Override
    public String encrypt(String plainText) throws Exception {
        Cipher cipher = Cipher.getInstance(AES_GCM_NO_PADDING);
        SecretKey secretKey = new SecretKeySpec(this.secretKey.getBytes(), AES);
        byte[] iv = new byte[GCM_IV_LENGTH];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(iv);
        GCMParameterSpec parameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, iv);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, parameterSpec);
        byte[] cipherText = cipher.doFinal(plainText.getBytes());
        byte[] encrypted = new byte[iv.length + cipherText.length];
        System.arraycopy(iv, 0, encrypted, 0, iv.length);
        System.arraycopy(cipherText, 0, encrypted, iv.length, cipherText.length);
        return Base64.getEncoder().encodeToString(encrypted);
    }

    @Override
    public String decrypt(String encryptedText) throws Exception {
        Cipher cipher = Cipher.getInstance(AES_GCM_NO_PADDING);
        SecretKey secretKey = new SecretKeySpec(this.secretKey.getBytes(), AES);
        byte[] decoded = Base64.getDecoder().decode(encryptedText);
        byte[] iv = Arrays.copyOfRange(decoded, 0, GCM_IV_LENGTH);
        byte[] cipherText = Arrays.copyOfRange(decoded, GCM_IV_LENGTH, decoded.length);
        GCMParameterSpec parameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, iv);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, parameterSpec);
        byte[] plainText = cipher.doFinal(cipherText);
        return new String(plainText);
    }
}
