package com.mlacker.micros.entitymapper

import com.mlacker.micros.domain.annotation.NoArg
import com.mlacker.micros.config.MapperConfig
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class EntityMapperTest {

    private val mapper: EntityMapper = MapperConfig().run { entityMapper(modelMapper()) }

    @Test
    fun `basic mapping`() {
        val source = Source(1000, "1000")
        val target1 = mapper.map(source, Target::class)

        assertEquals(source.id, target1.id)
        assertEquals(source.name, target1.name)

        val target2 = Target(2000, "2000")
        mapper.map(source, target2)

        assertEquals(source.id, target2.id)
        assertEquals(source.name, target2.name)
    }

    @Test
    fun `basic mapping with id assignment`() {
        val source = Source(-1000, "1000")
        val target = mapper.map(source, Target::class)

        assertTrue { target.id > 0 }
        assertEquals(source.name, target.name)
    }

    @Test
    fun `nested mapping`() {
        val fooList = listOf(
                FooSource(1000, "1000"),
                FooSource(1001, "1001"),
                FooSource(-1002, "1002"),
                FooSource(-1003, "1003"),
                FooSource(1004, "1004"),
        )
        val barList = listOf(
                BarSource(2000, 1000),
                BarSource(2001, 1001),
                BarSource(-2002, -1002),
                BarSource(-2003, -1003),
        )
        val source = NestedSource(fooList, barList)
        val target = mapper.map(source, NestedTarget::class)

        assertEquals(source.fooList.size, target.fooList.size)

        source.fooList.forEachIndexed { i, fooSource ->
            val fooTarget = target.fooList[i]
            if (i < 2 || i == 4) {
                assertEquals(fooSource.id, fooTarget.id)
            } else {
                assertTrue { fooTarget.id > 0 }
            }
            assertEquals(fooSource.name, fooTarget.name)
        }

        assertEquals(source.barList.size, target.barList.size)
        source.barList.forEachIndexed { i, barSource ->
            val fooTarget = target.fooList[i]
            val barTarget = target.barList[i]
            if (i < 2) {
                assertEquals(barSource.id, barTarget.id)
            } else {
                assertTrue { barTarget.id > 0 }
            }
            assertEquals(fooTarget.id, barTarget.foo)
        }
    }

    class Source(val id: Long, var name: String)
    inner class NestedSource(val fooList: List<FooSource>, val barList: List<BarSource>)
    class FooSource(val id: Long, var name: String)
    class BarSource(val id: Long, val foo: Long)

    @NoArg
    class Target(val id: Long, var name: String)
    @NoArg
    inner class NestedTarget(val fooList: List<FooTarget>, val barList: List<BarTarget>)
    @NoArg
    class FooTarget(val id: Long, var name: String)
    @NoArg
    class BarTarget(val id: Long, val foo: Long)
}

