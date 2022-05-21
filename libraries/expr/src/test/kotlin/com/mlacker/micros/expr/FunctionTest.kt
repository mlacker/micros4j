package com.mlacker.micros.expr

import com.mlacker.micros.expr.exception.ExpressionTypeException
import com.mlacker.micros.expr.types.BasicType
import com.mlacker.micros.expr.types.EntityType
import com.mlacker.micros.expr.types.ListType
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime

class FunctionTest {

    class TestFunction(parameters: List<Expression>) : Function(parameters) {
        override val type: Type get() = TODO()
        override fun eval(variables: Map<String, Any>): Any? {
            TODO("Not yet implemented")
        }
    }

    private val parameters = listOf<Expression>(
        Constant("1", BasicType.Text),
        Constant(22L, BasicType.Integer),
        Constant(LocalDate.of(2012, 12, 12), BasicType.Date),
        Constant(BigDecimal("33.2"), BasicType.Decimal),
        Constant(true, BasicType.Boolean),
        Constant(LocalDateTime.of(2012, 12, 12, 0, 0, 0), BasicType.DateTime)
    )
    private val func = TestFunction(parameters)

    @Test
    fun compatibilityTest() {
        assert(func.determineCompatibility(BasicType.Decimal, BasicType.Integer))
        assert(func.determineCompatibility(BasicType.Decimal, BasicType.Decimal))
        assert(!func.determineCompatibility(BasicType.Decimal, BasicType.Text))
        assert(!func.determineCompatibility(BasicType.Decimal, BasicType.DateTime))
        assert(!func.determineCompatibility(BasicType.Decimal, BasicType.Boolean))
        assert(!func.determineCompatibility(BasicType.Decimal, EntityType(1L)))

        assert(func.determineCompatibility(BasicType.DateTime, BasicType.Date))
        assert(func.determineCompatibility(BasicType.DateTime, BasicType.DateTime))
        assert(!func.determineCompatibility(BasicType.DateTime, BasicType.Decimal))
        assert(!func.determineCompatibility(BasicType.DateTime, BasicType.Integer))

        assert(func.determineCompatibility(BasicType.Text, BasicType.Boolean))
        assert(func.determineCompatibility(BasicType.Text, BasicType.Integer))
        assert(func.determineCompatibility(BasicType.Text, BasicType.Decimal))
        assert(func.determineCompatibility(BasicType.Text, BasicType.Date))
        assert(func.determineCompatibility(BasicType.Text, BasicType.DateTime))
        assert(func.determineCompatibility(BasicType.Text, BasicType.Text))
        assert(!func.determineCompatibility(BasicType.Text, BasicType.BinaryData))
        assert(!func.determineCompatibility(BasicType.Text, ListType(BasicType.Text)))

        assert(func.determineCompatibility(BasicType.Integer, BasicType.Integer))
    }

    @Test
    fun convertTest() {
        val variables = mapOf<String, Any>("a" to BigDecimal(1))
        // TypeCastException
        assertThrows<ExpressionTypeException> { func.convertToValue<Long>(BasicType.Integer, 0, variables) }
        // Long -> Decimal
        assert(func.convertToValue<BigDecimal>(BasicType.Decimal, 1, variables) == BigDecimal("22"))
        // Date to DateTime
        assert(
            func.convertToValue<LocalDateTime>(BasicType.DateTime, 2, variables) == LocalDateTime.of(
                2012,
                12,
                12,
                0,
                0,
                0
            )
        )
        // to Text
        assert(func.convertToValue<String>(BasicType.Text, 0, variables) == "1")
        assert(func.convertToValue<String>(BasicType.Text, 1, variables) == "22")
        assert(func.convertToValue<String>(BasicType.Text, 2, variables) == "2012-12-12")
        assert(func.convertToValue<String>(BasicType.Text, 3, variables) == "33.2")
        assert(func.convertToValue<String>(BasicType.Text, 4, variables) == "true")
        assert(func.convertToValue<String>(BasicType.Text, 5, variables) == "2012-12-12 00:00:00")
    }
}
