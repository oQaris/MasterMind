package com.pryanik.mastermind.logic

interface Guesser {
    fun startWithFirstGuess(): String

    fun makeNextGuess(bulls: Int, cows: Int): String

    fun getNextAnswer(guess: String): Pair<Int, Int>
}