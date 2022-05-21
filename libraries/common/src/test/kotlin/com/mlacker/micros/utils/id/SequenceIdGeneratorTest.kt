package com.mlacker.micros.utils.id

import org.junit.jupiter.api.Test

class SequenceIdGeneratorTest {

    private val generator: IdGenerator<Long> = SequenceIdGenerator()

    @Test
    fun generate() {
        var id : Long = 0
        for (i in 1..500) {
            id = generator.generate()
        }
        println(id shr 22 and 0x01FFFFFFFFFF)
        println(id shr 12 and 0x03FF)
        println(id and 0xFFF)
        println(id)
    }
}