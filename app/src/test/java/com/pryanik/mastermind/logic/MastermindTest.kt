package com.pryanik.mastermind.logic

import org.junit.Assert.assertEquals
import org.junit.Test

class MastermindTest {

    @Test
    fun countPossibleCombinationsTest() {
        assertEquals(
            1296, genPossibleAnswers(('1'..'6').toList(), 4).size
        )
        assertEquals(
            625, genPossibleAnswers(('1'..'5').toList(), 4).size
        )
        assertEquals(
            59049, genPossibleAnswers(('1'..'9').toList(), 5).size
        )
        assertEquals(
            1000, genPossibleAnswers(('0'..'9').toList(), 3).size
        )
    }

    @Test
    fun evaluateTest() {
        assertEquals(
            1 to 2, evaluate("1204", "1320")
        )
        assertEquals(
            1 to 1, evaluate("2234", "2022")
        )
        assertEquals(
            0 to 0, evaluate("1234", "5678")
        )
        assertEquals(
            1 to 3, evaluate("1234", "1423")
        )

        assertEquals(
            1 to 1, evaluate("1234", "13")
        )
        assertEquals(
            0 to 1, evaluate("67", "7890123")
        )
        assertEquals(
            0 to 0, evaluate("", "")
        )
    }
}