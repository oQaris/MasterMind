package com.pryanik.mastermind

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pryanik.mastermind.logic.Decoder
import com.pryanik.mastermind.logic.Encoder
import com.pryanik.mastermind.logic.genPossibleAnswers
import com.pryanik.mastermind.logic.impl.MinimaxSieveEncoder
import com.pryanik.mastermind.logic.impl.MinimaxSieveMaxDecoder
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MyViewModel : ViewModel() {
    private val answers = genPossibleAnswers(('1'..'6').toList(), 4)

    private val decoder: Decoder = MinimaxSieveMaxDecoder(answers)
    private val encoder: Encoder = MinimaxSieveEncoder()

    var isLoadedDecoder = mutableStateOf(false)
    var isLoadedEncoder = mutableStateOf(false)

    var guess = mutableStateOf("")
    var answer = mutableStateOf(0 to 0)

    var handler = CoroutineExceptionHandler { context, exception ->
        println("Caught $exception")
    }

    val isWin = mutableStateOf(false)

    init {
        resetDecoder()
        resetEncoder()
    }

    fun resetDecoder() {
        isLoadedDecoder.value = false
        guess.value = ""
        decoder.reset()
        background {
            decoder.firstGuess()
            isLoadedDecoder.value = true
            guess.value = decoder.curGuess!!
        }
    }

    fun resetEncoder() {
        encoder.reset()
        isLoadedEncoder.value = false
        background {
            delay(3000)
            isLoadedEncoder.value = true
            answer.value = 1 to 1
        }
    }

    fun runDecoder(bulls: Int, cows: Int) = background {
        isLoadedDecoder.value = false
        val (guessI, isWinI) = decoder.nextGuess(bulls, cows)
        guess.value = guessI
        isWin.value = isWinI
        isLoadedDecoder.value = true
    }

    fun runEncoder(curGuess: String) = background {
        isLoadedEncoder.value = false
        answer.value = encoder.nextAnswer(curGuess)
        isLoadedEncoder.value = true
    }

    private fun background(block: suspend () -> Unit) {
        viewModelScope.launch(Dispatchers.IO + handler) {
            block.invoke()
        }
    }
}