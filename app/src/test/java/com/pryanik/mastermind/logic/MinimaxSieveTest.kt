package com.pryanik.mastermind.logic

import org.junit.Assert.assertEquals
import org.junit.Test

class MinimaxSieveTest {

    @Test
    fun startWithFirstGuessTest() {
        assertEquals("1122", MinimaxSieve().startWithFirstGuess())
    }

    @Test
    fun makeNextGuessTest() {
        val guesser = MinimaxSieve()
        guesser.startWithFirstGuess()
        assertEquals("3334", guesser.makeNextGuess(0, 0))
    }
}