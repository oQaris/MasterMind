package com.pryanik.mastermind.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pryanik.mastermind.ui.theme.MasterMindTheme

@Composable
fun GuesserContent(
    enabled: Boolean = true,
    answer: Pair<String, String>,
    onGuessChanged: (String) -> Unit/*, confirmButton: @Composable () -> Unit*/
) {
    val (text, setText) = rememberSaveable { mutableStateOf("") }
    Column(
        Modifier
            .background(color = MaterialTheme.colors.secondary)
            .padding(20.dp)
    ) {
        Row(
            Modifier
                .align(Alignment.CenterHorizontally)
                .height(80.dp)
        ) {
            if (enabled) {
                TextWithSub("Bulls:", answer.first)
                Spacer(modifier = Modifier.width(20.dp))
                TextWithSub("Cows:", answer.second)
            } else Loading()
        }
        DigitsTextField(
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            value = text,
            onValueChange = setText,
            textLabel = "Guess",
        )
        Button(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 10.dp),
            enabled = enabled,
            onClick = { onGuessChanged(text) }) {
            Text("Make guess!")
        }
    }
}

@Composable
fun SolverContent(
    enabled: Boolean = true,
    guess: String,
    onSolverChanged: (Pair<String, String>) -> Unit
) {
    val (bulls, setBulls) = rememberSaveable { mutableStateOf("") }
    val (cows, setCows) = rememberSaveable { mutableStateOf("") }
    Column(
        Modifier
            .background(MaterialTheme.colors.secondaryVariant)
            .padding(20.dp)
    ) {
        Row(
            Modifier
                .align(Alignment.CenterHorizontally)
                .height(80.dp)
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
                .padding(top = 10.dp),
            enabled = enabled,
            onClick = { onSolverChanged(bulls to cows) }) {
            Text("Give answer!")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GuesserPreview() {
    MasterMindTheme {
        GuesserContent(true, "1" to "3") {}
    }
}

@Preview(showBackground = true)
@Composable
fun SolverPreview() {
    MasterMindTheme {
        SolverContent(false, "1234") {}
    }
}