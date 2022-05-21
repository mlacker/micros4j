package com.mlacker.micros.utils.encrypt

import org.springframework.util.DigestUtils
import java.util.*

enum class Md5Utils {

    X;

    fun computeHash(text: String) = DigestUtils.md5DigestAsHex(text.toByteArray()).uppercase(Locale.getDefault())

    fun verify(text: String, hash: String) = computeHash(text).equals(hash, ignoreCase = true)
}