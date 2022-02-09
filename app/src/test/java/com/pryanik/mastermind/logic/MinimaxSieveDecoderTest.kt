package com.pryanik.mastermind.logic

import com.pryanik.mastermind.logic.impl.MinimaxSieveMaxDecoder
import org.junit.Assert.assertEquals
import org.junit.Test

class MinimaxSieveDecoderTest {
    private val answers = genPossibleAnswers(('1'..'6').toList(), 4)

    @Test
    fun startWithFirstGuessTest() {
        assertEquals(ResultGuess("1122", false), MinimaxSieveMaxDecoder(answers).firstGuess())
    }

    @Test
    fun makeNextGuessTest() {
        val guesser = MinimaxSieveMaxDecoder(answers)
        guesser.firstGuess()
        assertEquals(ResultGuess("3345", false), guesser.nextGuess(0, 0))
    }
}