package com.mlacker.micros.entitymapper

import org.modelmapper.internal.util.Primitives
import org.modelmapper.spi.ConditionalConverter
import org.modelmapper.spi.MappingContext

class IdConverter : ConditionalConverter<Long, Long> {

    override fun convert(context: MappingContext<Long, Long>): Long =
            (rootContext(context) as MappingContextProxy<*, *>).idAllocator.requestOrGet(context.source)

    override fun match(sourceType: Class<*>, destinationType: Class<*>): ConditionalConverter.MatchResult {
        return if (Long::class.java == (if (sourceType.isPrimitive) sourceType else Primitives.primitiveFor(sourceType))
                && Long::class.java == (if (destinationType.isPrimitive) destinationType else Primitives.primitiveFor(destinationType)))
            ConditionalConverter.MatchResult.FULL
        else ConditionalConverter.MatchResult.NONE
    }

    private fun rootContext(context: MappingContext<*, *>): MappingContext<*, *> =
            if (context.parent != null) rootContext(context.parent) else context
}