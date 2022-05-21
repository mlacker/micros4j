package com.mlacker.micros.expr.types

import com.mlacker.micros.expr.Type
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.reflect.KClass

enum class BasicType : Type {
    Text,
    Integer,
    Decimal,
    Boolean,
    DateTime,
    Date,
    BinaryData,
    Any,
    Unknown;

    fun klass(): Class<*> {
        return when (this) {
            Text -> String::class.java
            Integer -> Long::class.javaObjectType
            Decimal -> BigDecimal::class.java
            Boolean -> kotlin.Boolean::class.javaObjectType
            DateTime -> LocalDateTime::class.java
            Date -> LocalDate::class.java
            BinaryData -> ByteArray::class.java
            else -> Any::class.java
        }
    }

    companion object {
        fun valueOf(klass: KClass<*>): BasicType {
            return when (klass) {
                String::class -> Text
                Long::class -> Integer
                BigDecimal::class -> Decimal
                kotlin.Boolean::class -> Boolean
                LocalDateTime::class -> DateTime
                LocalDate::class -> Date
                ByteArray::class -> BinaryData
                else -> Unknown
            }
        }
    }
}
