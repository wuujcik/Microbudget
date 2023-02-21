package com.wuujcik.microbudget.ui.presentation.itemDetail.widgets

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wuujcik.data.entities.Currency
import com.wuujcik.data.entities.Spending
import com.wuujcik.microbudget.R
import com.wuujcik.microbudget.util.TextFieldState
import com.wuujcik.microbudget.ui.presentation.itemDetail.*
import com.wuujcik.microbudget.ui.theme.MicroBudgetTheme
import java.time.ZonedDateTime


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemDetailWidget(
    originalItem: Spending? = null,
    onBackPressed: () -> Unit = {},
    onDateClicked: () -> Unit = {},
    onCurrencyClicked: (Currency) -> Unit = {},
    onSaveClicked: (spending: Spending) -> Unit = {},
    currencies: List<Currency>,
    dateTime: ZonedDateTime,
    chosenCurrency: Currency
) {
    MicroBudgetTheme {
        Scaffold(
            topBar = {
                ItemDetailTopAppBar(
                    topAppBarText = stringResource(id = R.string.details),
                    onBackPressed = onBackPressed
                )
            },
            content = { contentPadding ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(contentPadding)
                ) {

                    val purposeState by rememberSaveable(stateSaver = PurposeStateSaver) {
                        mutableStateOf(PurposeState(originalText = originalItem?.purpose))
                    }
                    val amountState by rememberSaveable(stateSaver = AmountStateSaver) {
                        mutableStateOf(AmountState(originalText = originalItem?.amount?.toString()))
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    PurposeWidget(purposeState = purposeState)
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AmountWidget(amountState = amountState)
                        Spacer(modifier = Modifier.width(12.dp))
                        CurrencySpinner(
                            availableCurrencies = currencies,
                            selectedItem = chosenCurrency,
                            onItemSelected = onCurrencyClicked
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    DatePickerWidget(date = dateTime, onClick = onDateClicked)
                    Spacer(modifier = Modifier.weight(1f))
                    SaveButtonWidget(
                        spending = getSpending(
                            originalItem = originalItem,
                            purposeState = purposeState,
                            amountState = amountState,
                            currency = chosenCurrency,
                            dateTime = dateTime,
                        ),
                        onSaveClicked = onSaveClicked,
                        isSaveButtonEnabled = isSaveButtonEnabled(
                            amountState = amountState,
                            purposeState = purposeState
                        )
                    )
                }
            }
        )
    }
}


@Preview
@Composable
fun PreviewItemDetailWidget() {
    ItemDetailWidget(
        currencies = Currency.values().toList(),
        dateTime = ZonedDateTime.now(),
        chosenCurrency = Currency.CZECH
    )
}


@OptIn(ExperimentalMaterial3Api::class) // CenterAlignedTopAppBar is experimental in m3
@Composable
fun ItemDetailTopAppBar(topAppBarText: String, onBackPressed: () -> Unit) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        title = {
            Text(
                text = topAppBarText,
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackPressed) {
                Icon(
                    imageVector = Icons.Filled.ChevronLeft,
                    contentDescription = stringResource(id = R.string.back),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        },
        // We need to balance the navigation icon, so we add a spacer.
        actions = {
            Spacer(modifier = Modifier.width(68.dp))
        },
    )
}

private fun getSpending(
    originalItem: Spending? = null,
    purposeState: TextFieldState,
    amountState: TextFieldState,
    currency: Currency,
    dateTime: ZonedDateTime,
): Spending? {
    return if ((amountState.isValid && purposeState.isValid).not()) {
        null
    } else if (originalItem?.id != null) {
        Spending(
            id = originalItem.id,
            purpose = purposeState.text,
            amount = amountState.text.toDouble(), //TODO: validation
            currency = currency,
            date = dateTime,
        )
    } else {
        Spending(
            purpose = purposeState.text,
            amount = amountState.text.toDouble(), //TODO: validation
            currency = currency,
            date = dateTime,
        )
    }
}

private fun isSaveButtonEnabled(
    amountState: TextFieldState,
    purposeState: TextFieldState
): Boolean {
    if (amountState.isValid && purposeState.isValid) {
        return true
    }
    return false
}