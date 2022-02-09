package com.pryanik.mastermind

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pryanik.mastermind.ui.DecoderInput
import com.pryanik.mastermind.ui.EncoderInput
import com.pryanik.mastermind.ui.theme.MasterMindTheme
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val model by viewModels<MyViewModel>()
        setContent {
            MasterMindTheme {
                Surface(color = MaterialTheme.colors.background) {
                    MainScreen(model)
                }
            }
        }
    }
}

@Composable
fun MainScreen(vm: MyViewModel) {
    var isUserDecoder by rememberSaveable { mutableStateOf(false) }
    val textGuess = rememberSaveable { mutableStateOf("") }
    val textBulls = rememberSaveable { mutableStateOf("") }
    val textCows = rememberSaveable { mutableStateOf("") }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        val padding = 30.dp
        Surface(
            Modifier
                .padding(padding)
                .align(Alignment.CenterHorizontally),
            shape = MaterialTheme.shapes.small,
            elevation = 5.dp,
        ) {
            Column(
                Modifier
                    .padding(10.dp)
            ) {
                Button(onClick = { isUserDecoder = !isUserDecoder }) {
                    Text(text = "Другой режим")
                }
                Spacer(modifier = Modifier.height(20.dp))
                Button(modifier = Modifier.align(Alignment.CenterHorizontally),
                    onClick = {
                        if (isUserDecoder) {
                            textGuess.value = ""
                            vm.resetEncoder()
                        } else {
                            textBulls.value = ""
                            textCows.value = ""
                            vm.resetDecoder()
                        }
                    }) {
                    Text(text = "Заново")
                }
            }
        }
        Surface(
            modifier = Modifier
                .padding(padding)
                .align(Alignment.CenterHorizontally),
            shape = MaterialTheme.shapes.small,
            elevation = 5.dp,
        ) {
            InputFragment(isUserDecoder, vm, textGuess, textBulls, textCows)
        }
        Surface(Modifier.padding(padding)) {
            Button(onClick = { /*TODO*/ }) {
                Text(text = "Настройки")
            }
        }
    }
}

@Composable
fun InputFragment(
    isUserDecoder: Boolean,
    vm: MyViewModel,
    textGuess: MutableState<String>,
    textBulls: MutableState<String>,
    textCows: MutableState<String>
) {
    //Todo Рефакторинг
    val guess by rememberSaveable { vm.guess }
    val answer by rememberSaveable { vm.answer }

    val isEnableDecoding by rememberSaveable { vm.isLoadedEncoder }
    val isEnableEncoding by rememberSaveable { vm.isLoadedDecoder }

    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        Modifier.requiredHeight(300.dp),
        scaffoldState = scaffoldState
    ) {
        fun errorHandler(e: Throwable) = scope.launch {
            scaffoldState.snackbarHostState.showSnackbar(
                e.toString() ?: "Ошибка в работе!"
            )
            vm.resetDecoder()
            vm.resetEncoder()
            textGuess.value = ""
            textBulls.value = ""
            textCows.value = ""
        }
        vm.handler = CoroutineExceptionHandler { _, e -> errorHandler(e) }
        if (isUserDecoder) DecoderInput(isEnableDecoding, answer, textGuess) { innerGuess ->
            vm.runEncoder(innerGuess)
        }
        else EncoderInput(isEnableEncoding, guess, textBulls, textCows) { (bulls, cows) ->
            try {
                vm.runDecoder(bulls.toInt(), cows.toInt())
            } catch (e: Exception) {
                errorHandler(e)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MasterMindTheme {
        MainScreen(MyViewModel())
    }
}
