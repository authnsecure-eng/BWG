package com.pcmc.bwg.service;

import com.pcmc.bwg.config.AadhaarProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

@Service
public class AadhaarService {

    private static final Logger log = LoggerFactory.getLogger(AadhaarService.class);
    private static final String AES_TRANSFORM = "AES/CBC/PKCS5Padding";
    private static final int IV_LENGTH = 16;

    private final SecretKeySpec keySpec;

    public AadhaarService(AadhaarProperties aadhaarProperties) {
        this.keySpec = deriveKey(aadhaarProperties.getEncryptionKey());
    }

    public String hash(String aadhaarNo) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(aadhaarNo.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            log.error("Failed to hash Aadhaar number", e);
            throw new IllegalStateException("Unable to hash Aadhaar number", e);
        }
    }

    public String encrypt(String aadhaarNo) {
        try {
            byte[] iv = new byte[IV_LENGTH];
            new SecureRandom().nextBytes(iv);
            Cipher cipher = Cipher.getInstance(AES_TRANSFORM);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(iv));
            byte[] encrypted = cipher.doFinal(aadhaarNo.getBytes(StandardCharsets.UTF_8));

            byte[] combined = new byte[iv.length + encrypted.length];
            System.arraycopy(iv, 0, combined, 0, iv.length);
            System.arraycopy(encrypted, 0, combined, iv.length, encrypted.length);
            return Base64.getEncoder().encodeToString(combined);
        } catch (Exception e) {
            log.error("Failed to encrypt Aadhaar number", e);
            throw new IllegalStateException("Unable to encrypt Aadhaar number", e);
        }
    }

    public String decrypt(String cipherTextBase64) {
        try {
            byte[] combined = Base64.getDecoder().decode(cipherTextBase64);
            byte[] iv = Arrays.copyOfRange(combined, 0, IV_LENGTH);
            byte[] encrypted = Arrays.copyOfRange(combined, IV_LENGTH, combined.length);

            Cipher cipher = Cipher.getInstance(AES_TRANSFORM);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(iv));
            return new String(cipher.doFinal(encrypted), StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("Failed to decrypt Aadhaar number", e);
            throw new IllegalStateException("Unable to decrypt Aadhaar number", e);
        }
    }

    private SecretKeySpec deriveKey(String secret) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] fullHash = digest.digest(secret.getBytes(StandardCharsets.UTF_8));
            byte[] key16 = Arrays.copyOf(fullHash, 16);
            return new SecretKeySpec(key16, "AES");
        } catch (Exception e) {
            log.error("Failed to derive Aadhaar encryption key", e);
            throw new IllegalStateException("Unable to derive Aadhaar encryption key", e);
        }
    }
}
