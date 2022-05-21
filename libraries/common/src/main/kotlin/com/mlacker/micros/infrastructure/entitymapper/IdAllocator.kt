package com.mlacker.micros.infrastructure.entitymapper

import com.mlacker.micros.utils.id.IdGenerator
import com.mlacker.micros.utils.id.SequenceIdGenerator

class IdAllocator {
    private val idGenerator: IdGenerator<Long> = SequenceIdGenerator()
    private val cacheIdMap = mutableMapOf<Long, Long>()

    fun requestOrGet(value: Long): Long = when {
        value < 0L -> {
            if (!cacheIdMap.containsKey(value)) {
                cacheIdMap[value] = idGenerator.generate()
            }

            cacheIdMap[value]!!
        }
        value == 0L -> {
            idGenerator.generate()
        }
        else -> {
            value
        }
    }
}