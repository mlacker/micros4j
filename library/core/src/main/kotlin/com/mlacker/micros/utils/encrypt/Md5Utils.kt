package com.mlacker.micros.utils.encrypt

import org.springframework.util.DigestUtils

enum class Md5Utils {

    X;

    fun computeHash(text: String) = DigestUtils.md5DigestAsHex(text.toByteArray()).toUpperCase()

    fun verify(text: String, hash: String) = computeHash(text).equals(hash, ignoreCase = true)
}