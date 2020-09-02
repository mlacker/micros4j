package com.mlacker.micros.domain.infrastructure.context

import com.mlacker.micros.domain.context.Principal
import org.springframework.util.Assert

object PrincipalHolder {
    private val principal = ThreadLocal<Principal>()

    @JvmStatic
    var context: Principal
        get() {
            var context = principal.get()
            if (context == null) {
                context = createEmptyPrincipal()
                principal.set(context)
            }
            return context
        }
        set(context) {
            Assert.notNull(context, "Only non-null Principal instances are permitted")
            principal.set(context)
        }

    @JvmStatic
    fun hasContext(): Boolean {
        return null != principal.get()
    }

    @JvmStatic
    fun removeContext() {
        principal.remove()
    }

    private fun createEmptyPrincipal(): Principal {
        return Principal(0)
    }
}