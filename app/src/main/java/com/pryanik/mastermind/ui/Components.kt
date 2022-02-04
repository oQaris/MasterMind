package com.pryanik.mastermind.ui

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly

@Composable
fun Loading() {
    val infiniteTransition = rememberInfiniteTransition()
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000),
            repeatMode = RepeatMode.Restart
        )
    )
    Icon(
        modifier = Modifier
            .rotate(rotation)
            .fillMaxSize(0.8f),
        imageVector = Icons.Rounded.Refresh,
        contentDescription = "Load"
    )
}

@Composable
fun TextWithSub(sub: String, text: String) = Row {
    Text(
        modifier = Modifier
            .align(Alignment.CenterVertically)
            .padding(top = 20.dp, end = 10.dp),
        text = sub
    )
    Text(
        style = MaterialTheme.typography.h2,
        text = text
    )
}

@Composable
fun DigitsTextField(
    modifier: Modifier = Modifier,
    enabled: Boolean,
    value: String,
    onValueChange: (String) -> Unit,
    textLabel: String
) {
    OutlinedTextField(
        modifier = modifier,
        enabled = enabled,
        value = value,
        onValueChange = {
            if (it.isDigitsOnly())
                onValueChange.invoke(it)
        },
        singleLine = true,
        label = { Text(text = textLabel) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}