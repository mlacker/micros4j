package com.lacker.micros.utils.id

interface IdGenerator<T> {
    fun generate(): T
}