package com.pryanik.mastermind

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pryanik.mastermind.logic.Guesser
import com.pryanik.mastermind.ui.GuesserContent
import com.pryanik.mastermind.ui.SolverContent
import com.pryanik.mastermind.ui.theme.MasterMindTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MasterMindTheme {
                Surface(color = MaterialTheme.colors.background) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    var isGuesser by rememberSaveable { mutableStateOf(false) }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        val padding = 30.dp
        Surface(
            Modifier
                .padding(padding)
                .align(Alignment.CenterHorizontally)
        ) {
            Button(onClick = { isGuesser = !isGuesser }) {
                Text(text = "Перевенуть")
            }
        }
        Surface(
            modifier = Modifier
                .padding(padding)
                .align(Alignment.CenterHorizontally),
            shape = MaterialTheme.shapes.small,
            elevation = 5.dp,
        ) {
            InputFragment(isGuesser)
        }
        Surface(Modifier.padding(padding)) {
            Button(onClick = { /*TODO*/ }) {
                Text(text = "Настройки")
            }
        }
    }
}

val guesser = Guesser()

@Composable
fun InputFragment(
    isGuesser: Boolean
) {
    //Todo Рефакторинг
    var guess by rememberSaveable { mutableStateOf("----") }
    var answer by rememberSaveable { mutableStateOf("-" to "-") }

    var isEnableGuesser by rememberSaveable { mutableStateOf(false) }
    var isEnableSolver by rememberSaveable { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(true) {
        coroutineScope.launch {
            // Первый ход компьютера
            guess = guesser.startWithFirstGuess()
            isEnableGuesser = true
        }
    }

    if (isGuesser) GuesserContent(isEnableGuesser, answer) { innerGuess ->
        coroutineScope.launch {
            isEnableGuesser = false
            // Вычисления
            answer = guesser.getNextAnswer(innerGuess)
            isEnableGuesser = true
        }
    }
    else SolverContent(isEnableSolver, guess) { (bulls, cows) ->
        coroutineScope.launch {
            isEnableSolver = false
            // Вычисления
            guess = guesser.makeNextGuess(bulls.toInt(), cows.toInt())
            isEnableGuesser = true
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MasterMindTheme {
        MainScreen()
    }
}