package com.mlacker.micros.expr.function.string

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.Function
import com.mlacker.micros.expr.Type
import com.mlacker.micros.expr.types.BasicType
import com.mlacker.micros.expr.types.ListType
import com.mlacker.micros.expr.types.LogicList

class Split(parameters: List<Expression>) : Function(parameters) {
    init {
        assertCount(parameters, 2)
        assertType(parameters, 0, BasicType.Text)
        assertType(parameters, 1, BasicType.Text)
    }

    override val type: Type = ListType(BasicType.Text)

    override fun eval(variables: Map<String, Any>): Any {
        val text = convertToValue<String>(BasicType.Text, 0, variables)!!
        val delimiters = convertToValue<String>(BasicType.Text, 1, variables)!!

        return LogicList(text.split(delimiters))
    }
}
