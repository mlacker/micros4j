package com.mlacker.micros.utils.id

import java.util.*
import java.util.concurrent.atomic.AtomicInteger

class UUIDGenerator : IdGenerator<String> {

    private val counter = AtomicInteger()

    override fun generate() = generateUUID().toString().toUpperCase()

    private fun generateUUID(): UUID {
        val mostSignificantBits = System.currentTimeMillis() shl 20 or count.toLong()
        val leastSignificantBits = UUID.randomUUID().leastSignificantBits
        return UUID(mostSignificantBits, leastSignificantBits)
    }

    private val count: Int
        get() = counter.getAndIncrement() and 0x0FFF

    companion object {
        private val INSTANCE = UUIDGenerator()

        fun generateId(): String {
            return INSTANCE.generate()
        }
    }
}