package com.mlacker.micros.domain.infrastructure.context

import com.mlacker.micros.domain.infrastructure.context.PrincipalHolder.context
import com.mlacker.micros.domain.infrastructure.context.PrincipalHolder.removeContext
import java.util.concurrent.Callable

class ThreadLocalCallableDelegate<V>(private val delegate: Callable<V>) : Callable<V> {

    private val principal = context

    override fun call(): V {
        context = principal
        return try {
            delegate.call()
        } finally {
            removeContext()
        }
    }
}