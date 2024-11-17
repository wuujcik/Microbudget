package com.wuujcik.microbudget.ui.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.wuujcik.microbudget.util.TextFieldState


@Composable
fun EditableTextField(
    textState: TextFieldState,
    label: String,
    modifier: Modifier = Modifier
) {
    TextField(
        value = textState.text,
        onValueChange = { newText ->
            textState.text = newText
        },
        label = {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
            )
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        textStyle = MaterialTheme.typography.bodyMedium,
        isError = textState.showErrors(),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Text
        ),
        colors = TextFieldDefaults.colors().copy(
            focusedTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.87f),
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            cursorColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.87f),
//            focusedBorderColor = Color.Transparent,
//            unfocusedBorderColor = Color.Transparent,
//            disabledBorderColor = Color.Transparent,
        ),
    )
}