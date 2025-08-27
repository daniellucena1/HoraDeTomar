package br.upe.horaDeTomar.ui.components

import android.widget.NumberPicker
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import br.upe.horaDeTomar.ui.themes.white

@Composable
fun NumberPicker(
    modifier: Modifier = Modifier,
    number: TextFieldValue,
    onNumberChange: (TextFieldValue) -> Unit,
    textStyle: TextStyle = MaterialTheme.typography.displayLarge,
    backgroundColor: Color = MaterialTheme.colorScheme.surface
) {
    val numericKeyBoard = KeyboardOptions(keyboardType = KeyboardType.Number)
    val textFieldColors = TextFieldDefaults.colors(
        focusedContainerColor = backgroundColor,
        unfocusedContainerColor = backgroundColor,
        disabledContainerColor = backgroundColor,

        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent,

        cursorColor = MaterialTheme.colorScheme.primary
    )

    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    LaunchedEffect(isFocused) {
        onNumberChange(
            number.copy(
                selection = if (isFocused) {
                    TextRange(
                        start = 0,
                        end = number.text.length
                    )
                } else {
                    number.selection
                },
            ),
        )
    }
    TextField(
        modifier = modifier
            .clearFocusOnKeyboardDismiss()
            .onFocusChanged { focusState ->
                if (!focusState.isFocused && number.text.isEmpty()) {
                    onNumberChange(TextFieldValue("00"))
                } else if (!focusState.isFocused && number.text.isNotEmpty()) {
                    onNumberChange(TextFieldValue(number.text.padStart(2, '0')))
                }
            },
        value = number,
        onValueChange = { onNumberChange(it) },
        textStyle = textStyle,
        keyboardOptions = numericKeyBoard,
        colors = textFieldColors,
        interactionSource = interactionSource,
        shape = RoundedCornerShape(20.dp),
        singleLine = true
    )
}

@OptIn(ExperimentalLayoutApi::class)
fun Modifier.clearFocusOnKeyboardDismiss(): Modifier = composed {
    var isFocused by remember { mutableStateOf(false) }
    var keyboardAppearedSinceLastFocused by remember { mutableStateOf(false) }
    if (isFocused) {
        val imeIsVisible = WindowInsets.isImeVisible
        val focusManager = LocalFocusManager.current
        LaunchedEffect(imeIsVisible) {
            if (imeIsVisible) {
                keyboardAppearedSinceLastFocused = true
            } else if (keyboardAppearedSinceLastFocused) {
                focusManager.clearFocus()
            }
        }
    }
    onFocusEvent {
        if (isFocused != it.isFocused) {
            isFocused = it.isFocused
            if (isFocused) {
                keyboardAppearedSinceLastFocused = false
            }
        }
    }
}
