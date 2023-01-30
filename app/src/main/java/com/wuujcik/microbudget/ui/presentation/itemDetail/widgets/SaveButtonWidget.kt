package com.wuujcik.microbudget.ui.presentation.itemDetail.widgets

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.wuujcik.data.entities.Currency
import com.wuujcik.data.entities.Spending
import com.wuujcik.microbudget.R
import com.wuujcik.microbudget.ui.base.TextFieldState
import java.time.ZonedDateTime


@Composable
fun SaveButtonWidget(
    date: ZonedDateTime = ZonedDateTime.now(),
    purposeState: TextFieldState,
    amountState: TextFieldState,
    currency: Currency = Currency.CZECH,
    onSaveClicked: (spending: Spending) -> Unit,
    isSaveButtonEnabled: Boolean
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(48.dp),
        shape = MaterialTheme.shapes.small,
        onClick = {
            onSaveClicked(
                Spending(
                    date = date,
                    purpose = purposeState.text,
                    amount = amountState.text.toDouble(), //TODO: validation
                    currency = currency
                )
            )
        },
        enabled = isSaveButtonEnabled,
    ) {
        Text(text = stringResource(id = R.string.save))
    }
}