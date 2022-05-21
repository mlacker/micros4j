package com.mlacker.micros.expr.types

class LogicList<E> : ArrayList<E> {

    constructor()
    constructor(collection: Collection<E>) : super(collection)

    var currentRowNumber = -1
    val current: E?
        get() {
            return if (size != 0) {
                get(if (currentRowNumber == -1) 0 else currentRowNumber)
            } else {
                null
            }
        }
    val length: Long get() = size.toLong()
    val empty: Boolean get() = isEmpty()

    @JvmField
    val List = this

    fun next() = currentRowNumber++


    fun reset() {
        currentRowNumber = 0
    }
}
