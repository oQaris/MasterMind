package com.pryanik.mastermind.logic

import com.google.common.collect.Collections2
import com.google.common.collect.Sets
import com.google.common.math.LongMath
import kotlin.math.min

data class ResultGuess(val guess: String, val isWin: Boolean)

data class BullsAndCows(val bulls: Int)

fun genPossibleAnswers(values: List<Char> = ('1'..'6').toList(), k: Int) =
    Array(LongMath.checkedPow(values.size.toLong(), k).toInt()) { idx ->
        CharArray(k) {
            values[(idx / LongMath.pow(values.size.toLong(), k - 1 - it)
                    % values.size).toInt()]
        }
    }.map { it.joinToString("") }

fun getEverythingNotRepeat(values: List<Char>, k: Int) =
    Sets.combinations(values.toSet(), k)
        .flatMap { Collections2.orderedPermutations(it) }
        .map { it.toCharArray() }

/**
 * Возвращает число быков и коров для текущей догадки
 */
fun evaluate(guess: String, answer: String): Pair<Int, Int> {
    val counterAnswer = answer.toList().counting()

    val matches = guess.toList().counting().entries
        .sumOf { (t, u) -> min(u, counterAnswer[t] ?: 0) }

    val bulls = guess.zip(answer).count { it.first == it.second }
    return bulls to matches - bulls
}

fun evaluateCows(guess: String, answer: String): Int {
    val counterAnswer = answer.toList().counting()
    var matches = 0
    guess.toList().counting().forEach { (t, u) ->
        matches += min(u, counterAnswer[t] ?: 0)
    }
    val bulls = evaluateBulls(guess, answer)
    return matches - bulls
}

fun evaluateBulls(guess: String, answer: String) =
    guess.zip(answer).count { it.first == it.second }


fun <T> Iterable<T>.counting() = groupingBy { it }.eachCount().toMap()