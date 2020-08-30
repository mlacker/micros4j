package com.mlacker.micros.utils.encrypt

import org.springframework.security.crypto.codec.Hex
import java.nio.charset.StandardCharsets
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import javax.crypto.*
import javax.crypto.spec.SecretKeySpec

enum class AesUtils {

    X;

    fun encrypt(content: String): String {
        return try {
            val cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM)
            cipher.init(Cipher.ENCRYPT_MODE, provideSecretKey())
            String(Hex.encode(cipher.doFinal(content.toByteArray(StandardCharsets.UTF_8))))
        } catch (ex: NoSuchAlgorithmException) {
            throw IllegalArgumentException(ex)
        } catch (ex: NoSuchPaddingException) {
            throw IllegalArgumentException(ex)
        } catch (ex: BadPaddingException) {
            throw IllegalArgumentException(ex)
        } catch (ex: IllegalBlockSizeException) {
            throw IllegalArgumentException(ex)
        } catch (ex: InvalidKeyException) {
            throw IllegalArgumentException(ex)
        }
    }

    fun decrypt(content: String): String {
        return try {
            val cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM)
            cipher.init(Cipher.DECRYPT_MODE, provideSecretKey())
            String(cipher.doFinal(Hex.decode(content)), StandardCharsets.UTF_8)
        } catch (ex: NoSuchAlgorithmException) {
            throw IllegalArgumentException(ex)
        } catch (ex: NoSuchPaddingException) {
            throw IllegalArgumentException(ex)
        } catch (ex: BadPaddingException) {
            throw IllegalArgumentException(ex)
        } catch (ex: IllegalBlockSizeException) {
            throw IllegalArgumentException(ex)
        } catch (ex: InvalidKeyException) {
            throw IllegalArgumentException(ex)
        }
    }

    private fun provideSecretKey(): SecretKey {
        val keyGen = KeyGenerator.getInstance(KEY_ALGORITHM)
        val secureRandom = SecureRandom.getInstance(SECURE_RANDOM_ALGORITHM)
        secureRandom.setSeed(PASSWORD.toByteArray(StandardCharsets.UTF_8))
        keyGen.init(128, secureRandom)
        return SecretKeySpec(keyGen.generateKey().encoded, KEY_ALGORITHM)
    }

    companion object {
        private const val PASSWORD = "samples"
        private const val KEY_ALGORITHM = "AES"
        private const val SECURE_RANDOM_ALGORITHM = "SHA1PRNG"
        private const val DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding"
    }
}