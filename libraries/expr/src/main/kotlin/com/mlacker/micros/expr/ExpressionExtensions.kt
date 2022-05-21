package com.mlacker.micros.expr

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

val FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
fun LocalDateTime.toChinaString(): String = this.format(FORMATTER)
