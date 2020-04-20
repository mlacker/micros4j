package com.lacker.micros.form.domain.component

import com.lacker.micros.domain.repository.Repository

interface FormRepository: Repository<FormComponent> {

    fun delete(id: Long)
}