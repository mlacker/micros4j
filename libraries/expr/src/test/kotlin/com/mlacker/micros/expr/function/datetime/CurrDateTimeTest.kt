package com.mlacker.micros.expr.function.datetime

import com.mlacker.micros.expr.types.BasicType
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.Duration
import java.time.LocalDateTime

class CurrDateTimeTest {
    private val currDateTime: CurrDateTime = CurrDateTime(listOf())

    @Test
    fun getType() {
        Assertions.assertEquals(BasicType.DateTime, currDateTime.type)
    }

    @Test
    fun eval() {
        val durationMillis =
            Duration.between(LocalDateTime.now(), currDateTime.eval(emptyMap()) as LocalDateTime).toMillis()
        Assertions.assertEquals(true, durationMillis < 50)
    }

    @Test
    fun testToString() {
        (1..100).map { it * 2 } // 执行100次
            .filter { it % 3 == 0 } // 执行100次
            .first()
        println((1..200).first { println(it); it * 2 % 3 == 0 }) // 只执行了三次
    }
}