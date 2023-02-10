package com.wuujcik.microbudget.ui.presentation.itemDetail.widgets

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wuujcik.microbudget.R
import com.wuujcik.microbudget.util.TextFieldState
import com.wuujcik.microbudget.ui.presentation.itemDetail.PurposeState


@OptIn(ExperimentalMaterial3Api::class) // OutlinedTextField is experimental in m3
@Composable
fun PurposeWidget(purposeState: TextFieldState = remember { PurposeState() }) {
    TextField(
        value = purposeState.text,
        onValueChange = {
            purposeState.text = it
        },
        label = {
            Text(
                text = stringResource(id = R.string.purpose_label),
                style = MaterialTheme.typography.bodyMedium,
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .onFocusChanged { focusState ->
                purposeState.onFocusChange(focusState.isFocused)
                if (!focusState.isFocused) {
                    purposeState.enableShowErrors()
                }
            },
        textStyle = MaterialTheme.typography.bodyMedium,
        isError = purposeState.showErrors(),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Text
        ),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = MaterialTheme.colorScheme.surface,
            textColor = MaterialTheme.colorScheme.onSurface
                .copy(alpha = 0.87f),
        ),
    )
}


@Preview
@Composable
fun PreviewPurposeWidget() {
    PurposeWidget()
}