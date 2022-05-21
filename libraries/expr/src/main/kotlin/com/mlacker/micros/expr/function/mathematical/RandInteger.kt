package com.mlacker.micros.expr.function.mathematical

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.Function
import com.mlacker.micros.expr.types.BasicType
import kotlin.random.Random

class RandInteger(parameters: List<Expression>) : Function(parameters) {
    init {
        assertCount(parameters, 2)
        assertType(parameters, 0, BasicType.Integer)
        assertType(parameters, 1, BasicType.Integer)
    }

    override val type = BasicType.Decimal

    override fun eval(variables: Map<String, Any>): Any? {
        val start = convertToValue<Long>(BasicType.Integer, 0, variables)!!
        val end = convertToValue<Long>(BasicType.Integer, 1, variables)!!

        return Random.nextLong(start, end + 1)
    }

    override fun generate(variables: Map<String, Any>): String {
        return "floor(${parameters[0].generate(variables)} + rand() * (${parameters[1].generate(variables)} - ${parameters[0].generate(variables)}))"
    }
}
