package com.pryanik.mastermind.logic

class MinimaxSieve : Guesser {
    private val available by lazy {
        getEverything(
            ('1'..'5').toList(),
            4
        ).map { it.joinToString("") }
            .toMutableList()
    }
    private lateinit var allAnswer: List<String>
    private lateinit var curGuess: String

    override fun startWithFirstGuess(): String {
        allAnswer = available.toList()
        curGuess = nextStep(available, allAnswer)
        return curGuess
    }

    override fun makeNextGuess(bulls: Int, cows: Int): String {
        if (available.size > 1) {
            available.removeAll {
                evaluate(it, curGuess) != bulls to cows
            }
            curGuess = nextStep(available, allAnswer)
            return curGuess
        }
        require(available.isNotEmpty()) { "Противоречивые данные" }
        return "Ответ"
    }

    override fun getNextAnswer(guess: String): Pair<Int, Int> {
        //Todo Логика
        return 0 to 0
    }

    /**
     * Вычисляет следующую версию ответа, при котором число удаляемых вариантов будет
     * максимальным в худшем случае (По правилу Минимакса)
     */
    private fun nextStep(
        available: List<String>,
        all: List<String>
    ): String {
        if (available.size == 1)
            return available.first()

        return all.minByOrNull { g ->
            available.map { s ->
                evaluate(g, s)
            }.counting()
                .values.maxOrNull()!!
        }!!
    }
}