package com.mlacker.micros.form.domain.component

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class FormComponentTest {

    @Test
    fun `clone form`() {
        val form1 = FormComponent(1, "clone form", "")
        val form2 = form1.clone()

        assertNotEquals(form1.id, form2.id)
        assertNotSame(form1.children, form2.children)

        assertEquals(form1.name, form2.name)
    }
}