package com.wuujcik.microbudget.ui.presentation.spendingList.widgets

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wuujcik.data.DateTimeFormatter.toEuropeanDateFormat
import com.wuujcik.data.entities.Currency
import com.wuujcik.data.entities.Spending
import com.wuujcik.microbudget.ui.theme.MicroBudgetTheme
import java.time.ZonedDateTime

@Composable
fun ItemCardWidget(
    spending: Spending,
    onClick: ((Spending) -> Unit),
) {
    Row(
        modifier = Modifier
            .padding(all = 8.dp)
            .fillMaxWidth()
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {
                    },
                    onTap = {
                        onClick(spending)
                    }
                )
            }

    ) {
        Surface(shape = MaterialTheme.shapes.small, shadowElevation = 4.dp) {
            Column {
                Text(
                    text = spending.date.toEuropeanDateFormat(),
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(all = 8.dp),
                )

                Row(
                    modifier = Modifier
                        .padding(all = 8.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = spending.purpose,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = String.format("%.2f", spending.amount),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    Spacer(modifier = Modifier.width(1.dp))
                    Text(
                        text = spending.currency.currencySymbol,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewItemCardWidget() {
    MicroBudgetTheme {
        ItemCardWidget(
            spending = Spending(
                date = ZonedDateTime.now(),
                purpose = "grocery",
                amount = 99.4,
                currency = Currency.CZECH
            ),
            onClick = {},
        )
    }
}
