package com.lacker.micros.utils.id

import java.time.Clock

class SequenceIdGenerator : IdGenerator<Long> {

    private val system: Long = 0
    private var count: Long = 0

    @Synchronized
    private fun getCount(): Long {
        if (count > 0xFFF) {
            count = 0
        }

        return count++
    }

    override fun generate(): Long {
        val millis = Clock.systemUTC().millis()

        return ((millis and 0x01FFFFFFFFFF) shl 22) or ((system and 0x03FF) shl 12) or (getCount() and 0xFFF)
    }
}

interface IdGenerator<T> {
    fun generate(): T
}
