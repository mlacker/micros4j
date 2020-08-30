package com.mlacker.micros.utils.id

import java.util.concurrent.atomic.AtomicLong

class SequenceIdGenerator : IdGenerator<Long> {
    private val sequence = AtomicLong()

    private val timestamp get() = System.currentTimeMillis() - BASE_TIMESTAMP and 0x01FFFFFFFFFFL

    private val host = hashCode().toLong()
        get() = field and 0x03FF

    private fun getSequence() = sequence.getAndIncrement() and 0x0FFF

    override fun generate() = timestamp shl 22 or (host shl 12) or getSequence()

    companion object {
        private val INSTANCE = SequenceIdGenerator()
        private const val BASE_TIMESTAMP = 1262275200000L

        @JvmStatic
        fun generateId(): Long {
            return INSTANCE.generate()
        }
    }
}