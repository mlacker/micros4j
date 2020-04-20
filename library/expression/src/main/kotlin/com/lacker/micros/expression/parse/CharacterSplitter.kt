package com.lacker.micros.expression.parse

interface CharacterSplitter {

    fun split(text: String): List<Segment>
}