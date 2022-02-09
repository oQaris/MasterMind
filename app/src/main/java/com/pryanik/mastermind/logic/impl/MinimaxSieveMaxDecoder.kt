package com.pryanik.mastermind.logic.impl

import com.pryanik.mastermind.logic.Decoder
import com.pryanik.mastermind.logic.ResultGuess
import com.pryanik.mastermind.logic.counting
import com.pryanik.mastermind.logic.evaluate

class MinimaxSieveMaxDecoder(private val allAnswers: List<String>) : Decoder {
    private lateinit var available: MutableList<String>
    override var curGuess: String? = null
        private set

    override fun firstGuess(): ResultGuess {
        reset()
        return nextStep(available, allAnswers).also {
            curGuess = it.guess
        }
    }

    override fun nextGuess(bulls: Int, cows: Int): ResultGuess {
        requireNotNull(curGuess) { "Необходимо вызвать firstGuess()" }

        available.removeAll {
            evaluate(it, curGuess!!) != bulls to cows
        }
        require(available.isNotEmpty()) { "Противоречивые данные" }

        return nextStep(available, allAnswers).also {
            curGuess = it.guess
        }
    }

    override fun reset() {
        available = allAnswers.toMutableList()
        curGuess = null
    }

    override fun isInitialized(): Boolean {
        return curGuess != null
    }

    /**
     * Вычисляет следующую версию ответа, при котором число удаляемых вариантов будет
     * максимальным в худшем случае (По правилу Минимакса)
     */
    private fun nextStep(
        available: List<String>,
        all: List<String>
    ): ResultGuess {
        require(available.isNotEmpty() && all.isNotEmpty())

        if (available.size == 1)
            return ResultGuess(available.single(), true)

        // Чтобы уменьшить Максимальное число шагов ищем ответы ещё и в all
        return ResultGuess(all.minByOrNull { g ->
            available.map { s ->
                evaluate(g, s)
            }.counting()
                .values.maxOrNull()!!
        }!!, false)
    }
}