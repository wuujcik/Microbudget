package com.wuujcik.microbudget.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wuujcik.microbudget.data.entities.Transaction
import com.wuujcik.microbudget.util.DateTimeFormatter.toEuropeanDateFormat

@Composable
fun TransactionItem(
    transaction: Transaction,
    onClick: ((Transaction) -> Unit),
) {
    Row(
        modifier = Modifier
            .padding(all = 8.dp)
            .fillMaxWidth()
            .clickable { onClick(transaction) }

    ) {
        Surface(shape = MaterialTheme.shapes.small, shadowElevation = 4.dp) {
            Column {
                Text(
                    text = transaction.date.toEuropeanDateFormat(),
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(all = 8.dp),
                )

                Row(
                    modifier = Modifier
                        .padding(all = 8.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = transaction.purpose,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = String.format("%.2f", transaction.amount),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    Spacer(modifier = Modifier.width(1.dp))
                    Text(
                        text = transaction.currency.currencySymbol,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
        }
    }
}
