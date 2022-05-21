package com.mlacker.micros.utils.id

interface IdGenerator<T> {
    fun generate(): T
}