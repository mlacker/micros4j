package com.mlacker.micros.expr.function.datetime

import com.mlacker.micros.expr.types.BasicType
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.LocalDate

class CurrDateTest {
    private val currDate: CurrDate = CurrDate(listOf())

    @Test
    fun getType() {
        Assertions.assertEquals(BasicType.Date, currDate.type)
    }

    @Test
    fun eval() {
        Assertions.assertEquals(LocalDate.now(), currDate.eval(emptyMap()))
    }

    @Test
    fun testToString() {
        Assertions.assertEquals("CurrDate()", currDate.toString())
    }
}