package com.wuujcik.microbudget.ui.presentation.itemDetail.widgets

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.wuujcik.microbudget.R
import com.wuujcik.microbudget.util.TextFieldState
import com.wuujcik.microbudget.ui.presentation.itemDetail.AmountState


@OptIn(ExperimentalMaterial3Api::class) // OutlinedTextField is experimental in m3
@Composable
fun AmountWidget(amountState: TextFieldState = remember { AmountState() }) {
    TextField(
        value = amountState.text,
        onValueChange = { amountState.text = it },
        label = {
            Text(
                text = stringResource(id = R.string.amount_label),
                style = MaterialTheme.typography.bodyMedium,
            )
        },
        modifier = Modifier
            .onFocusChanged { focusState ->
                amountState.onFocusChange(focusState.isFocused)
                if (!focusState.isFocused) {
                    amountState.enableShowErrors()
                }
            },
        textStyle = MaterialTheme.typography.bodyMedium,
        isError = amountState.showErrors(),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Number
        ),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = MaterialTheme.colorScheme.surface,
            textColor = MaterialTheme.colorScheme.onSurface
                .copy(alpha = 0.87f),
        ),
    )
}