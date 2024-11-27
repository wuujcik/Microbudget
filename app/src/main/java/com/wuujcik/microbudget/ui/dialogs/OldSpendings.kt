package com.wuujcik.microbudget.ui.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.wuujcik.microbudget.R
import com.wuujcik.microbudget.data.entities.Currency
import com.wuujcik.microbudget.data.entities.Spending
import com.wuujcik.microbudget.ui.theme.MicroBudgetTheme
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter


@Composable
fun ShowOldSpendingsDialog(
    oldSpendings: List<Spending>,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnClickOutside = false,
            dismissOnBackPress = true,
        ),
        text = {
            OldSpendings(oldSpendings)
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(id = R.string.close))
            }
        })
}


@Composable
fun OldSpendings(
    spendings: List<Spending>,
) {
    LazyColumn {
        items(spendings.distinctBy { it.id }, key = { it.id }) { spending ->
            Row(
                modifier = Modifier
                    .padding(all = 1.dp)
                    .fillMaxWidth()

            ) {
                Surface(shape = MaterialTheme.shapes.small, shadowElevation = 4.dp) {
                    Column {
                        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
                        Text(
                            text = spending.date.format(formatter),
                            style = MaterialTheme.typography.labelMedium,
                            modifier = Modifier.padding(horizontal = 4.dp).padding(top = 4.dp),
                        )

                        Row(
                            modifier = Modifier
                                .padding(horizontal = 4.dp).padding(bottom = 4.dp)
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
    }
}


@Preview
@Composable
fun PreviewSpendings() {
    MicroBudgetTheme {
        OldSpendings(
            spendings = SampleData.spendingsSample
        )
    }
}

object SampleData {
    // Sample data
    val spendingsSample = listOf(
        Spending(
            date = ZonedDateTime.now(),
            purpose = "grocery",
            amount = 99.4,
            currency = Currency.CZECH
        ),
        Spending(
            date = ZonedDateTime.now(),
            purpose = "grocery",
            amount = 47.4,
            currency = Currency.CZECH
        ),
        Spending(
            date = ZonedDateTime.now().minusDays(1),
            purpose = "snacks",
            amount = 55.0,
            currency = Currency.CZECH
        ),
        Spending(
            date = ZonedDateTime.now().minusDays(2),
            purpose = "vacation",
            amount = 500.0,
            currency = Currency.EURO
        ),
    )
}
