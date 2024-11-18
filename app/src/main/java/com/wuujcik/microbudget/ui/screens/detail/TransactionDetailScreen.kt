@file:OptIn(ExperimentalMaterial3Api::class)

package com.wuujcik.microbudget.ui.screens.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.wuujcik.microbudget.R
import com.wuujcik.microbudget.data.entities.Currency
import com.wuujcik.microbudget.data.entities.Transaction
import com.wuujcik.microbudget.ui.composables.DateButton
import com.wuujcik.microbudget.ui.composables.DatePickerDialog
import com.wuujcik.microbudget.ui.composables.EditableTextField
import com.wuujcik.microbudget.ui.composables.SpinnerWidget
import com.wuujcik.microbudget.util.TextFieldState
import com.wuujcik.microbudget.util.rememberFlow
import com.wuujcik.microbudget.util.serializers.LocalDate
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import java.time.ZoneOffset.UTC

@Destination<RootGraph>
@Composable
fun TransactionDetailScreen(
    navigator: DestinationsNavigator,
    arguments: TransactionDetailScreenArguments,
    viewModel: TransactionDetailViewModel = koinViewModel(),
) {

    LaunchedEffect(viewModel.isInitialized) {
        if (!viewModel.isInitialized) {
            viewModel.initialize(
                originalItem = arguments.originalItem,
            )
        }
    }

    rememberFlow(flow = viewModel.eventsFlow) { event ->
        when (event) {
            TransactionDetailViewModel.Event.ItemSaved -> {
                navigator.navigateUp()
            }
        }
    }

    val purposeState by rememberSaveable(stateSaver = PurposeStateSaver) {
        mutableStateOf(PurposeState(originalText = arguments.originalItem?.purpose))
    }
    val amountState by rememberSaveable(stateSaver = AmountStateSaver) {
        mutableStateOf(AmountState(originalText = arguments.originalItem?.amount?.toString()))
    }

    val allFieldsValid by remember {
        derivedStateOf {
            purposeState.text.isNotEmpty() &&
                    amountState.text.isNotEmpty() &&
                    amountState.text.matches("^-?\\d+([.,]\\d{1,2})?$".toRegex())
        }
    }

    var showDatePickerDialog by remember { mutableStateOf(false) }
    val datePickerState: DatePickerState = rememberDatePickerState(
        initialSelectedDateMillis = viewModel.selectedDate.value
            .atStartOfDay()
            .toInstant(UTC)
            .toEpochMilli()
    )

    if (showDatePickerDialog) {
        DatePickerDialog(
            state = datePickerState,
            onDateSelected = { selectedDateMillis ->
                selectedDateMillis?.let {
                    val selectedDate = LocalDate.ofEpochDay(it / (24 * 60 * 60 * 1000))
                    viewModel.onDateChosen(selectedDate)
                }
            },
            onDismiss = {
                showDatePickerDialog = false
            }
        )
    }

    TransactionDetailScreenContent(
        purposeState = purposeState,
        amountState = amountState,
        currencies = viewModel.availableCurrencies,
        selectedCurrency = viewModel.selectedCurrency.value,
        selectedDate = viewModel.selectedDate.value,
        isSaveButtonEnabled = allFieldsValid,
        onCurrencyClicked = {
            viewModel.onCurrencyChanged(it)
        },
        onDateClicked = {
            showDatePickerDialog = true
        },
        onSaveClicked = {
            viewModel.onSaveClicked(
                purpose = purposeState.text,
                amountText = amountState.text
            )
        },
        navigateUp = { navigator.navigateUp() },
    )
}


@Composable
fun TransactionDetailScreenContent(
    purposeState: TextFieldState,
    amountState: TextFieldState,
    selectedCurrency: Currency,
    currencies: List<Currency>,
    selectedDate: LocalDate,
    isSaveButtonEnabled: Boolean,
    onCurrencyClicked: (Currency) -> Unit,
    onDateClicked: () -> Unit,
    onSaveClicked: () -> Unit,
    navigateUp: () -> Unit,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                title = {
                    Text(
                        text = stringResource(id = R.string.details),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize(Alignment.Center)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = navigateUp) {
                        androidx.compose.material.Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.back),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                actions = {
                    Spacer(modifier = Modifier.width(68.dp))
                },
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
        ) {

            Spacer(modifier = Modifier.height(12.dp))

            EditableTextField(
                textState = purposeState,
                label = stringResource(id = R.string.purpose_label),
                modifier = Modifier
                    .onFocusChanged { focusState ->
                        purposeState.onFocusChange(focusState.isFocused)
                        if (!focusState.isFocused) {
                            purposeState.enableShowErrors()
                        }
                    }
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                EditableTextField(
                    textState = amountState,
                    label = stringResource(id = R.string.amount_label),
                    keyboardType = KeyboardType.Number,
                    modifier = Modifier
                        .weight(1f)
                        .onFocusChanged { focusState ->
                            amountState.onFocusChange(focusState.isFocused)
                            if (!focusState.isFocused) {
                                amountState.enableShowErrors()
                            }
                        }
                )
                Spacer(modifier = Modifier.width(12.dp))

                SpinnerWidget(
                    modifier = Modifier.wrapContentSize(),
                    dropDownModifier = Modifier.wrapContentSize(),
                    items = currencies,
                    selectedItem = selectedCurrency,
                    onItemSelected = onCurrencyClicked,
                    selectedItemFactory = { modifier, item ->
                        Row(
                            modifier = modifier
                                .padding(8.dp)
                                .wrapContentSize()
                        ) {
                            Text(item.currencyCode)

                            Icon(
                                imageVector = Icons.Filled.ArrowDropDown,
                                contentDescription = null
                            )
                        }
                    },
                    dropdownItemFactory = { item, _ ->
                        Text(text = item.currencyCode)
                    }
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            DateButton(
                date = selectedDate,
                onClick = onDateClicked
            )
            Spacer(modifier = Modifier.weight(1f))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(48.dp),
                shape = MaterialTheme.shapes.small,
                onClick = onSaveClicked,
                enabled = isSaveButtonEnabled,
            ) {
                Text(text = stringResource(id = R.string.save))
            }
        }
    }
}

@Serializable
data class TransactionDetailScreenArguments(
    val originalItem: Transaction? = null,
)