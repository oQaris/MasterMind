package com.pryanik.mastermind.logic

import com.pryanik.mastermind.logic.impl.MinimaxSieveAvgDecoder
import com.pryanik.mastermind.logic.impl.MinimaxSieveMaxDecoder
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Ignore
import org.junit.Test
import kotlin.math.max
import kotlin.system.measureTimeMillis

class DecoderTest {

    @Test
    fun maxAndAvgSteps_43_Test() {
        val answers = genPossibleAnswers(('1'..'4').toList(), 3)

        var resMax: Pair<Int, Double>
        var resAvg: Pair<Int, Double>

        val timeForMax = measureTimeMillis {
            resMax = maxAndAvgStepsBy(answers, ::MinimaxSieveMaxDecoder)
        }
        println()
        val timeForAvg = measureTimeMillis {
            resAvg = maxAndAvgStepsBy(answers, ::MinimaxSieveAvgDecoder)
        }

        assertTrue(timeForMax < 350)
        assertEquals(4, resMax.first)
        assertEquals(3.8125, resMax.second, 1e-7)

        assertTrue(timeForAvg < 230)
        assertEquals(5, resAvg.first)
        assertEquals(3.765625, resAvg.second, 1e-7)
    }

    @Test
    @Ignore("Долгий")
    fun maxAndAvgSteps_64_Test() {
        val answers = genPossibleAnswers(('0'..'5').toList(), 4)

        var resMax: Pair<Int, Double>
        var resAvg: Pair<Int, Double>
        val timeForMax = measureTimeMillis {
            resMax = maxAndAvgStepsBy(answers, ::MinimaxSieveMaxDecoder)
        }
        val timeForAvg = measureTimeMillis {
            resAvg = maxAndAvgStepsBy(answers, ::MinimaxSieveAvgDecoder)
        }

        println("MinimaxSieveMaxDecoder:")
        println("time: $timeForMax")
        println("max: ${resMax.first}")
        println("avg: ${resMax.second}")
        println()
        println("MinimaxSieveAvgDecoder:")
        println("time: $timeForAvg")
        println("max: ${resAvg.first}")
        println("avg: ${resAvg.second}")

        /*
        * MinimaxSieveMaxDecoder:
        * time: 1826055
        * max: 5
        * avg: 4.776234567901234
        *
        * MinimaxSieveAvgDecoder:
        * time: 1608346
        * max: 6
        * avg: 4.864197530864198
        * */
    }

    private fun maxAndAvgStepsBy(
        versions: List<String>,
        decoderCreator: (List<String>) -> Decoder
    ): Pair<Int, Double> {

        var max = 0
        var sum = 0
        versions.forEach { answer ->
            val decoder = decoderCreator(versions)

            var guess = decoder.firstGuess()
            var steps = 1
            while (!guess.isWin || guess.guess != answer) {
                val (bulls, cows) = evaluate(guess.guess, answer)
                guess = decoder.nextGuess(bulls, cows)
                steps++
            }
            println("$answer : $steps")
            max = max(max, steps)
            sum += steps
        }
        return max to (sum.toDouble() / versions.size)
    }
}