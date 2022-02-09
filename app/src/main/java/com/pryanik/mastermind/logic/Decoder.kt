package com.pryanik.mastermind.logic

interface Decoder {

    val curGuess: String?

    fun firstGuess(): ResultGuess

    fun nextGuess(bulls: Int, cows: Int): ResultGuess

    fun reset()

    fun isInitialized(): Boolean
}