package com.mlacker.micros.expr.parse

interface CharacterSplitter {

    fun split(text: String): List<Segment>
}
