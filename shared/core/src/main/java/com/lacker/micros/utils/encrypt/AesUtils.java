package com.lacker.micros.utils.encrypt;

import org.springframework.security.crypto.codec.Hex;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public enum AesUtils {

    X;

    private static final String PASSWORD = "samples";
    private static final String KEY_ALGORITHM = "AES";
    private static final String SECURE_RANDOM_ALGORITHM = "SHA1PRNG";
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

    public String encrypt(String content) {
        try {
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, provideSecretKey());
            return new String(Hex.encode(cipher.doFinal(content.getBytes(StandardCharsets.UTF_8))));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException | InvalidKeyException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    public String decrypt(String content) {
        try {
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, provideSecretKey());
            return new String(cipher.doFinal(Hex.decode(content)), StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException | InvalidKeyException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    private SecretKey provideSecretKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance(KEY_ALGORITHM);
        SecureRandom secureRandom = SecureRandom.getInstance(SECURE_RANDOM_ALGORITHM);
        secureRandom.setSeed(PASSWORD.getBytes(StandardCharsets.UTF_8));
        keyGen.init(128, secureRandom);
        return new SecretKeySpec(keyGen.generateKey().getEncoded(), KEY_ALGORITHM);
    }
}
