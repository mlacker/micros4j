package com.mlacker.micros.config

import com.mlacker.micros.entitymapper.EntityMapper
import com.mlacker.micros.entitymapper.EntityMapperImpl
import com.mlacker.micros.entitymapper.IdConverter
import com.mlacker.micros.entitymapper.MappingEngineProxy
import org.modelmapper.ModelMapper
import org.modelmapper.internal.InheritingConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MapperConfig {

    @Bean
    fun modelMapper(): ModelMapper = ModelMapper().also { configure(it) }

    @Bean
    fun entityMapper(modelMapper: ModelMapper): EntityMapper = EntityMapperImpl(modelMapper)

    fun configure(modelMapper: ModelMapper) {
        modelMapper.apply {
            configuration.fieldAccessLevel = org.modelmapper.config.Configuration.AccessLevel.PRIVATE
            configuration.isFieldMatchingEnabled = true
            configuration.isSkipNullEnabled = true

            configuration.converters.add(3, IdConverter())
        }

        ModelMapper::class.java.getDeclaredField("engine").apply {
            if (trySetAccessible()) set(modelMapper, MappingEngineProxy(modelMapper.configuration as InheritingConfiguration))
        }
    }
}
