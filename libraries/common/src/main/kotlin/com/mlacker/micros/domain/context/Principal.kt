package com.mlacker.micros.domain.context

data class Principal(
        var id: Long,
        var name: String? = null,
        var applicationId: Long? = null
)