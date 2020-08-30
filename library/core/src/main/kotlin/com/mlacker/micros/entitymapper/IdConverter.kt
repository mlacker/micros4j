package com.mlacker.micros.entitymapper

import com.mlacker.micros.utils.id.SequenceIdGenerator
import com.mlacker.micros.utils.id.IdGenerator
import org.modelmapper.internal.util.Primitives
import org.modelmapper.spi.ConditionalConverter
import org.modelmapper.spi.MappingContext

class IdConverter : ConditionalConverter<Long, Long> {

    private val idGenerator: IdGenerator<Long> = SequenceIdGenerator()

    override fun convert(context: MappingContext<Long, Long>): Long {
        val rootContext = rootContext(context) as MappingContextProxy<*, *>
        val cacheIdMap = rootContext.cacheIdMap
        val source = context.source

        return if (source < 0) {
            if (!cacheIdMap.containsKey(source)) {
                cacheIdMap[source] = idGenerator.generate()
            }

            cacheIdMap[source]!!
        } else {
            source
        }
    }

    override fun match(sourceType: Class<*>, destinationType: Class<*>): ConditionalConverter.MatchResult {
        return if (Long::class.java == (if (sourceType.isPrimitive) sourceType else Primitives.primitiveFor(sourceType))
                && Long::class.java == (if (destinationType.isPrimitive) destinationType else Primitives.primitiveFor(destinationType)))
            ConditionalConverter.MatchResult.FULL
        else ConditionalConverter.MatchResult.NONE
    }

    private fun rootContext(context: MappingContext<*, *>): MappingContext<*, *> =
            if (context.parent != null) rootContext(context.parent) else context
}