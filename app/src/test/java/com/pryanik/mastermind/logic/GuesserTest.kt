package com.pryanik.mastermind.logic

import org.junit.Test
import kotlin.math.max

class GuesserTest {

    @Test
    fun maxAndAvgStepsTest() {
        val answers = getEverything(
            ('1'..'5').toList(),
            4
        ).map { it.joinToString("") }

        var max = 0
        var avg = 0
        answers.forEach { answer ->
            var steps = 0

            val guesser: Guesser = MinimaxSieve() // Выбрать реализацию
            var guess = guesser.startWithFirstGuess()
            while (guess != "Ответ") {
                val (bulls, cows) = evaluate(guess, answer)
                guess = guesser.makeNextGuess(bulls, cows)
                steps++
            }
            println("$answer : $steps")
            max = max(max, steps)
            avg += steps
        }
        println("max: $max, avg: ${avg.toDouble() / answers.size}")
    }
}