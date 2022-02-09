package com.pryanik.mastermind.logic

interface Encoder {
    fun nextAnswer(guess: String): Pair<Int, Int>

    fun reset()
}