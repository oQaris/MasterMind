package com.pryanik.mastermind.logic

class Guesser {
    private val available by lazy { mutableListOf("1234", "4567", "8901") } //Todo Логика
    private lateinit var allAnswer: List<String>
    private lateinit var curGuess: String

    fun startWithFirstGuess(): String {
        allAnswer = available.toList()
        curGuess = "XXXX" //Todo Логика
        return curGuess
    }

    fun makeNextGuess(bulls: Int, cows: Int): String {
        //Todo Логика
        return "Ответ"
    }

    fun getNextAnswer(guess: String): Pair<String, String> {
        //Todo Логика
        return "Хз" to "Хз"
    }
}