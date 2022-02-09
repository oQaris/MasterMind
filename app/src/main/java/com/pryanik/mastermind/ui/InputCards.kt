package com.pryanik.mastermind.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pryanik.mastermind.ui.theme.MasterMindTheme

val outputHeight = 80.dp
val innerPadding = 20.dp
val topPaddingOnButton = 10.dp

//Todo Рефакторинг - добавить confirmButton: @Composable () -> Unit

@Composable
fun DecoderInput(
    enabled: Boolean = true,
    answer: Pair<Int, Int>,
    textGuess: MutableState<String>,
    encoderAction: (String) -> Unit
) {
    val (guess, setGuess) = textGuess
    Column(
        Modifier
            .background(color = MaterialTheme.colors.secondary)
            .padding(innerPadding)
    ) {
        Row(
            Modifier
                .align(Alignment.CenterHorizontally)
                .height(outputHeight)
        ) {
            if (enabled) {
                TextWithSub("Bulls:", answer.first.toString())
                Spacer(modifier = Modifier.width(20.dp))
                TextWithSub("Cows:", answer.second.toString())
            } else Loading()
        }
        DigitsTextField(
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            value = guess,
            onValueChange = setGuess,
            textLabel = "Guess",
        )
        Button(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = topPaddingOnButton),
            enabled = enabled,
            onClick = {
                encoderAction(guess)
                textGuess.value = ""
            }) {
            Text("Make guess!")
        }
    }
}

@Composable
fun EncoderInput(
    enabled: Boolean = true,
    guess: String,
    textBulls: MutableState<String>,
    textCows: MutableState<String>,
    decoderAction: (Pair<String, String>) -> Unit
) {
    val (bulls, setBulls) = textBulls
    val (cows, setCows) = rememberSaveable { textCows }
    Column(
        Modifier
            .background(MaterialTheme.colors.secondaryVariant)
            .padding(innerPadding)
    ) {
        Row(
            Modifier
                .align(Alignment.CenterHorizontally)
                .height(outputHeight)
        ) {
            if (enabled) TextWithSub("Guess:", guess)
            else Loading()
        }
        Row(Modifier.fillMaxWidth()) {
            DigitsTextField(
                modifier = Modifier.weight(1f),
                enabled = enabled,
                value = bulls,
                onValueChange = setBulls,
                textLabel = "Bulls"
            )
            Spacer(modifier = Modifier.width(5.dp))
            DigitsTextField(
                modifier = Modifier.weight(1f),
                enabled = enabled,
                value = cows,
                onValueChange = setCows,
                textLabel = "Cows",
            )
        }
        Button(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = topPaddingOnButton),
            enabled = enabled,
            onClick = {
                decoderAction(bulls to cows)
                textBulls.value = ""
                textCows.value = ""
            }) {
            Text("Give answer!")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GuesserPreview() {
    MasterMindTheme {
        val txt = remember { mutableStateOf("1234") }
        DecoderInput(true, 1 to 3, txt) {}
    }
}

@Preview(showBackground = true)
@Composable
fun SolverPreview() {
    MasterMindTheme {
        val txtB = remember { mutableStateOf("2") }
        val txtC = remember { mutableStateOf("2") }
        EncoderInput(false, "1234", txtB, txtC) {}
    }
}