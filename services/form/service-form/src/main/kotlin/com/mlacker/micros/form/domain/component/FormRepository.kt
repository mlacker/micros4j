package com.mlacker.micros.form.domain.component

import com.mlacker.micros.domain.repository.Repository

interface FormRepository: Repository<FormComponent> {

    fun delete(id: Long)
}