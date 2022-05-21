package com.mlacker.micros.expr.function.session

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.Function
import com.mlacker.micros.expr.Type
import com.mlacker.micros.expr.types.BasicType
import com.mlacker.micros.infrastructure.context.PrincipalHolder

class GetUserId(parameters: List<Expression>) : Function(parameters) {
    override val type: Type = BasicType.Integer

    override fun eval(variables: Map<String, Any>): Any = PrincipalHolder.context.id

    override fun generate(variables: Map<String, Any>): String {
        return PrincipalHolder.context.id.toString()
    }
}
